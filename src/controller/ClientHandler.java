package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;

public class ClientHandler extends Thread {
	private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;
    private Map<String, PublicKey> clientDSApublicKeys;
    private Map<String, PrintWriter> clientWriters;
    private PrivateKey rsaPrivKey;
    private PublicKey rsaPubKey;

    public ClientHandler(Socket socket, PrivateKey rsaPrivKey, PublicKey rsaPubKey, Map<String, PublicKey> clientDSApublicKeys, Map<String, PrintWriter> clientWriters) throws IOException {
        this.socket = socket;
        this.rsaPrivKey = rsaPrivKey;
        this.rsaPubKey = rsaPubKey;
        this.clientDSApublicKeys = clientDSApublicKeys;
        this.clientWriters = clientWriters;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            // Read client name and DSA public key
            clientName = in.readLine();
            String encodedDsaPubKey = in.readLine();

            // Decode and store the DSA public key
            byte[] decodedDsaPubKey = Base64.getDecoder().decode(encodedDsaPubKey);
            KeyFactory dsaKeyFactory = KeyFactory.getInstance("DSA");
            X509EncodedKeySpec dsaKeySpec = new X509EncodedKeySpec(decodedDsaPubKey);
            PublicKey dsaPubKey = dsaKeyFactory.generatePublic(dsaKeySpec);
            synchronized (clientDSApublicKeys) {
                clientDSApublicKeys.put(clientName, dsaPubKey);
            }

            // Register the client's PrintWriter
            synchronized (clientWriters) {
                clientWriters.put(clientName, out);
            }

            // Send RSA public key to the client
            String encodedRsaPubKey = Base64.getEncoder().encodeToString(rsaPubKey.getEncoded());
            out.println(encodedRsaPubKey);

            // Send chat history to the new client
            out.println(ChatServer.getChatHistory());

            // Notify all clients about the new client
            ChatServer.broadcast(clientName + " has joined the chat");

            String receivedMsg;
            while ((receivedMsg = in.readLine()) != null) {
                String[] parts = receivedMsg.split(":", 3);
                if (parts.length < 3) {
                    System.out.println("Invalid message format");
                    continue;
                }
                String sender = parts[0];
                String encryptedMessage = parts[1];
                String signature = parts[2];

                String message = decryptMessage(encryptedMessage);

                boolean isVerified = verifySignature(sender, message, signature);
                if (isVerified) {
                    System.out.println("Verified message from " + sender + ": " + message);
                    ChatServer.broadcast(sender + ": " + message);
                } else {
                    System.out.println("Failed to verify message from " + sender);
                }
            }
        } catch (SocketException e) {
            System.out.println("Connection with client lost");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            synchronized (clientDSApublicKeys) {
                clientDSApublicKeys.remove(clientName);
            }
            synchronized (clientWriters) {
                clientWriters.remove(clientName);
            }
            ChatServer.broadcast(clientName + " has left the chat");
        }
    }

    private String decryptMessage(String encryptedMessage) throws Exception {
        byte[] decodedMessage = Base64.getDecoder().decode(encryptedMessage);
        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.DECRYPT_MODE, rsaPrivKey);
        byte[] decryptedBytes = rsaCipher.doFinal(decodedMessage);
        return new String(decryptedBytes);
    }

    private boolean verifySignature(String sender, String message, String signature) throws Exception {
        PublicKey pubKey = clientDSApublicKeys.get(sender);
        if (pubKey == null) {
            System.out.println("No public key found for " + sender);
            return false;
        }
        Signature verifyAlg = Signature.getInstance("DSA");
        verifyAlg.initVerify(pubKey);
        verifyAlg.update(message.getBytes());
        byte[] sigBytes = Base64.getDecoder().decode(signature);
        return verifyAlg.verify(sigBytes);
    }
    
}
