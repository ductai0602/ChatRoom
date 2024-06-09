package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Set;

public class ClientHandler extends Thread {
	private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;
    private Set<PrintWriter> clientWriters;

    public ClientHandler(Socket socket, Set<PrintWriter> clientWriters) {
        this.socket = socket;
        this.clientWriters = clientWriters;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            synchronized (clientWriters) {
                clientWriters.add(out);
            }

            // Gửi lịch sử trò chuyện cho client mới kết nối
            out.println(ChatServer.getChatHistory());

            clientName = in.readLine();
            ChatServer.broadcast(clientName + " has joined the chat");

            String message;
            while ((message = in.readLine()) != null) {
                ChatServer.broadcast(clientName + ": " + message);
            }
        }catch(SocketException e) {
        	System.out.print("Kết nối với client đã bị ngắt");
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            synchronized (clientWriters) {
                clientWriters.remove(out);
            }
            ChatServer.broadcast(clientName + " has left the chat");
        }
    }
}
