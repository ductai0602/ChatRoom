package controller;

import java.io.File;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ChatServer {
	private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static Document chatHistoryDoc;
    private static File chatHistoryFile = new File("chatHistory.xml");

    public static void main(String[] args) {
        System.out.println("Chat server started...");

        try {
            loadChatHistory();
            ServerSocket serverSocket = new ServerSocket(12345);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientWriters);
                clientHandler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadChatHistory() throws Exception {
        if (chatHistoryFile.exists()) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            chatHistoryDoc = dBuilder.parse(chatHistoryFile);
            chatHistoryDoc.getDocumentElement().normalize();
        } else {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            chatHistoryDoc = dBuilder.newDocument();
            Element rootElement = chatHistoryDoc.createElement("chatHistory");
            chatHistoryDoc.appendChild(rootElement);
        }
    }

    private static synchronized void saveChatHistory() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(chatHistoryDoc);
        StreamResult result = new StreamResult(chatHistoryFile);
        transformer.transform(source, result);
    }

    public static synchronized void broadcast(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
        saveMessageToHistory(message);
    }

    private static synchronized void saveMessageToHistory(String message) {
        Element rootElement = chatHistoryDoc.getDocumentElement();
        Element msgElement = chatHistoryDoc.createElement("message");
        msgElement.appendChild(chatHistoryDoc.createTextNode(message));
        rootElement.appendChild(msgElement);

        try {
            saveChatHistory();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static synchronized String getChatHistory() {
        StringBuilder history = new StringBuilder();
        NodeList messageNodes = chatHistoryDoc.getElementsByTagName("message");
        for (int i = 0; i < messageNodes.getLength(); i++) {
            history.append(messageNodes.item(i).getTextContent()).append("\n");
        }
        return history.toString();
    }
}
