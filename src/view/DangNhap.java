package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.DOM_xml;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class DangNhap extends JFrame {
	public String emailPattern = "^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$";

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField txt_Pass;
	private JTextField txt_Name;
	private JButton btnOk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DangNhap frame = new DangNhap();
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
	public DangNhap() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ĐĂNG NHẬP");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(10, 72, 416, 54);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tên:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(53, 189, 107, 39);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Mật khẩu:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(53, 257, 107, 39);
		contentPane.add(lblNewLabel_1_1);
		
		txt_Pass = new JPasswordField();
		txt_Pass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				validateField();
			}
		});
		txt_Pass.setFont(new Font("Tahoma", Font.BOLD, 18));
		txt_Pass.setBounds(144, 257, 248, 39);
		contentPane.add(txt_Pass);
		
		txt_Name = new JTextField();
		txt_Name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				validateField();
			}
		});
		txt_Name.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_Name.setBounds(144, 189, 248, 39);
		contentPane.add(txt_Name);
		txt_Name.setColumns(10);
		
		JButton btnDangKy = new JButton("ĐĂNG KÝ");
		btnDangKy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new DangKy().setVisible(true);
			}
		});
		btnDangKy.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDangKy.setBounds(23, 356, 137, 39);
		contentPane.add(btnDangKy);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login();
			}
		});
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnOk.setBounds(287, 356, 122, 39);
		btnOk.setEnabled(false);
		contentPane.add(btnOk);
		
		JButton btnQuenMatKhau = new JButton("Quên Mật Khẩu");
		btnQuenMatKhau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new QuenMatKhau().setVisible(true);
			}
		});
		btnQuenMatKhau.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnQuenMatKhau.setBounds(23, 432, 199, 39);
		contentPane.add(btnQuenMatKhau);
		
		JButton btnThoat = new JButton("Thoát");
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnThoat.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnThoat.setBounds(355, 10, 71, 39);
		contentPane.add(btnThoat);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(DangNhap.class.getResource("/img/Hinh_nen_3.png")));
		lblNewLabel_2.setBounds(-121, 0, 736, 666);
		contentPane.add(lblNewLabel_2);
		
		this.setLocationRelativeTo(null);
	}

	protected void Login() {
		// TODO Auto-generated method stub
		String name = txt_Name.getText();
		String pass = new String(txt_Pass.getPassword());
		if(DOM_xml.validateLogin(name, pass)) {
			JOptionPane.showMessageDialog(contentPane, "Đăng nhập thành công!");
			setVisible(false);
			new Home(name).setVisible(true);
		}else {
			JOptionPane.showMessageDialog(contentPane, "Đăng nhập không thành công!");
		}
	}
	
	private void validateField() {
		String name = txt_Name.getText();
		String pass = new String(txt_Pass.getPassword());
		if(!name.equals("") && !pass.equals("")) {
			btnOk.setEnabled(true);
		}else {
			btnOk.setEnabled(false);
		}
	}
}
