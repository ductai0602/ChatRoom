package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class Server extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
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
	public Server() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 537, 189);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Server");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 30));
		lblNewLabel.setBounds(137, 43, 129, 43);
		contentPane.add(lblNewLabel);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("OFF");
		tglbtnNewToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tglbtnNewToggleButton.isSelected()) {
					tglbtnNewToggleButton.setText("ON");
					runServer();
				}else {
					tglbtnNewToggleButton.setText("OFF");
				}
			}
		});
		tglbtnNewToggleButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tglbtnNewToggleButton.setBackground(Color.LIGHT_GRAY);
		tglbtnNewToggleButton.setBounds(276, 41, 138, 51);
		contentPane.add(tglbtnNewToggleButton);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Server.class.getResource("/img/Hinh_nen_2.png")));
		lblNewLabel_1.setBounds(-157, -15, 813, 202);
		contentPane.add(lblNewLabel_1);
	}
	
	private void runServer() {
        try {
            Class<?> myClass = Class.forName("controller.ChatServer");
            Method mainMethod = myClass.getMethod("main", String[].class);
            String[] params = null; 
            mainMethod.invoke(null, (Object) params);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi chạy Server", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
