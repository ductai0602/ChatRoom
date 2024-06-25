package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.DOM_xml;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class QuenMatKhau extends JFrame {
	public String emailPattern = "^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$";

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JButton btnFind;
	private JButton btnOk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuenMatKhau frame = new QuenMatKhau();
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
	public QuenMatKhau() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnThoat = new JButton("Thoát");
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnThoat.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnThoat.setBounds(351, 10, 75, 39);
		contentPane.add(btnThoat);
		
		JLabel lblQunMtKhu = new JLabel("QUÊN MẬT KHẨU");
		lblQunMtKhu.setHorizontalAlignment(SwingConstants.CENTER);
		lblQunMtKhu.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblQunMtKhu.setBounds(10, 72, 416, 54);
		contentPane.add(lblQunMtKhu);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				validateEmail();
			}
		});
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setColumns(10);
		textField.setBounds(144, 157, 219, 39);
		contentPane.add(textField);
		
		JLabel lblNewLabel_1 = new JLabel("Email:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(53, 157, 107, 39);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Mật khẩu:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(53, 245, 107, 39);
		contentPane.add(lblNewLabel_1_1);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				validatePass();
			}
		});
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 18));
		passwordField.setBounds(144, 245, 248, 39);
		passwordField.setEditable(false);
		contentPane.add(passwordField);
		
		JButton btnDangNhap = new JButton("ĐĂNG NHẬP");
		btnDangNhap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new DangNhap().setVisible(true);
			}
		});
		btnDangNhap.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDangNhap.setBounds(10, 417, 137, 39);
		contentPane.add(btnDangNhap);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ForgotPass();
			}
		});
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnOk.setBounds(304, 417, 122, 39);
		btnOk.setEnabled(false);
		contentPane.add(btnOk);
		
		btnFind = new JButton("Tìm");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindEmail();
			}
		});
		btnFind.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnFind.setBounds(363, 157, 63, 39);
		btnFind.setEnabled(false);
		contentPane.add(btnFind);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				validatePass();
			}
		});
		passwordField_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		passwordField_1.setBounds(144, 332, 248, 39);
		passwordField_1.setEditable(false);
		contentPane.add(passwordField_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Nhập Lại:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1.setBounds(53, 332, 107, 39);
		contentPane.add(lblNewLabel_1_1_1);
	}

	protected void FindEmail() {
		// TODO Auto-generated method stub
		String email = textField.getText();
		if(DOM_xml.checkEmail(email)) {
			JOptionPane.showMessageDialog(contentPane, "Email đã được tìm thấy!");
			textField.setEditable(false);
			btnFind.setEnabled(false);
			passwordField.setEditable(true);
			passwordField_1.setEditable(true);
		}else {
			JOptionPane.showMessageDialog(contentPane, "Email không trùng khớp. Vui lòng nhập lại!");
		}
	}

	protected void ForgotPass() {
		// TODO Auto-generated method stub
		String email = textField.getText();
		String pass = new String(passwordField.getPassword());
		if(DOM_xml.updatePass(email, pass)) {
			JOptionPane.showMessageDialog(contentPane, "Mật khẩu đã được cập nhật!");
			setVisible(false);
			new DangNhap().setVisible(true);
		}else {
			JOptionPane.showMessageDialog(contentPane, "Có xảy ra lỗi. Vui lòng thử lại!");
			clear();
		}
	}

	private void clear() {
		textField.setText("");
		passwordField.setText("");
		passwordField_1.setText("");
		passwordField.setEditable(false);
		passwordField_1.setEditable(false);
		textField.setEditable(true);
		btnFind.setEnabled(true);
	}
	
	private void validateEmail(){
		String email = textField.getText();
		if(email.matches(emailPattern)) {
			btnFind.setEnabled(true);
		}else {
			btnFind.setEnabled(false);
		}
	}
	
	private void validatePass() {
		String pass1 = new String(passwordField.getPassword());
		String pass2 = new String(passwordField_1.getPassword());
		if(!pass1.equals("") && !pass2.equals("") && pass1.equals(pass2)) {
			btnOk.setEnabled(true);
		}else {
			btnOk.setEnabled(false);
		}
	}
}
