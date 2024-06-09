package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.*;

public class ChatClient {
	private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String name;

    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public ChatClient(String serverAddress) throws IOException {
        // Kết nối tới server
        socket = new Socket(serverAddress, 12345);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Tạo giao diện đồ họa
        frame = new JFrame("Chat Client");
        chatArea = new JTextArea(20, 50);
        chatArea.setEditable(false);
        inputField = new JTextField(40);
        sendButton = new JButton("Send");

        JPanel panel = new JPanel();
        panel.add(inputField);
        panel.add(sendButton);

        frame.getContentPane().add(new JScrollPane(chatArea), BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Lấy tên người dùng
        name = JOptionPane.showInputDialog(frame, "Enter your name:");
        out.println(name);

        // Xử lý sự kiện khi người dùng nhấn nút gửi tin nhắn
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Bắt đầu một luồng để nhận tin nhắn từ server
        new Thread(new Runnable() {
            public void run() {
                receiveMessages();
            }
        }).start();
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (message != null && !message.trim().isEmpty()) {
            out.println(message);
            inputField.setText("");
        }
    }

    private void receiveMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                chatArea.append(message + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new ChatClient("localhost");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
