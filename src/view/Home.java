package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_Mess;
	private JLabel lbl_Name;
	private JButton btnNewButton_2;
	private JTextArea textArea;
	
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private String name;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		initComponent();
	}
	
	public Home(String username){
		try {
	        initComponent();
	        socket = new Socket("localhost", 12345);
	        out = new PrintWriter(socket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        name = username;
	        out.println(name);
	        lbl_Name.setText(name);
	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	                receiveMess();
	            }
	        }).start();
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "Không thể kết nối đến server", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	        System.exit(1); 
	    }
	}
	
	private void initComponent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 706, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbl_Name = new JLabel("");
		lbl_Name.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_Name.setBounds(10, 10, 361, 30);
		contentPane.add(lbl_Name);
		
		JButton btnNewButton = new JButton("Đăng xuất");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(null, "Bạn muốn đăng xuất?", "Select", JOptionPane.YES_NO_OPTION);
				if(a==0) {
					setVisible(false);
					new DangNhap().setVisible(true);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(460, 10, 134, 30);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_1.setBounds(633, 10, 49, 30);
		contentPane.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 672, 268);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		tf_Mess = new JTextField();
		tf_Mess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMess();
			}
		});
		tf_Mess.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_Mess.setBounds(10, 328, 521, 42);
		contentPane.add(tf_Mess);
		tf_Mess.setColumns(10);
		
		btnNewButton_2 = new JButton("Gửi");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMess();
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_2.setBounds(541, 328, 141, 42);
		contentPane.add(btnNewButton_2);
	}

	protected void sendMess() {
		// TODO Auto-generated method stub
		String mess = tf_Mess.getText();
		if(mess != null && !mess.trim().isEmpty()) {
			out.println(mess);
			tf_Mess.setText("");
		}
	}
	
	private void receiveMess() {
		try {
			String mess;
			while((mess = in.readLine()) != null) {
				textArea.append(mess + "\n");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
