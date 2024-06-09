package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import controller.DOM_xml;
import model.Account;

public class DangKy extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JPasswordField txtPass;
	private JTextField txtEmail;
	private JTextField txtAddress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DangKy frame = new DangKy();
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
	public DangKy() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnThoat = new JButton("");
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnThoat.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnThoat.setBounds(378, 10, 48, 39);
		contentPane.add(btnThoat);
		
		JLabel lblngK = new JLabel("ĐĂNG KÝ");
		lblngK.setHorizontalAlignment(SwingConstants.CENTER);
		lblngK.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblngK.setBounds(10, 72, 416, 54);
		contentPane.add(lblngK);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtName.setColumns(10);
		txtName.setBounds(144, 163, 248, 39);
		contentPane.add(txtName);
		
		JLabel lblNewLabel_1 = new JLabel("Tên:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(53, 163, 107, 39);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Mật khẩu:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(53, 295, 107, 39);
		contentPane.add(lblNewLabel_1_1);
		
		txtPass = new JPasswordField();
		txtPass.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtPass.setBounds(144, 295, 248, 39);
		contentPane.add(txtPass);
		
		JButton btnDangNhap = new JButton("ĐĂNG NHẬP");
		btnDangNhap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new DangNhap().setVisible(true);
			}
		});
		btnDangNhap.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDangNhap.setBounds(10, 444, 144, 39);
		contentPane.add(btnDangNhap);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Signup();
			}
		});
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnOk.setBounds(304, 444, 122, 39);
		contentPane.add(btnOk);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtEmail.setColumns(10);
		txtEmail.setBounds(144, 231, 248, 39);
		contentPane.add(txtEmail);
		
		JLabel lblNewLabel_1_2 = new JLabel("Email");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_2.setBounds(53, 231, 107, 39);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Địa Chỉ:");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_2_1.setBounds(53, 360, 107, 39);
		contentPane.add(lblNewLabel_1_2_1);
		
		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtAddress.setColumns(10);
		txtAddress.setBounds(144, 360, 248, 39);
		contentPane.add(txtAddress);
	}

	protected void Signup() {
	    // TODO Auto-generated method stub
	    String name = txtName.getText();
	    String email = txtEmail.getText();
	    String pass = new String(txtPass.getPassword());
	    String address = txtAddress.getText();
	    Account user = new Account(name, email, pass, address);
	    if (!name.isEmpty() && !email.isEmpty() && !pass.isEmpty() && !address.isEmpty()) {
	        if(DOM_xml.checkAccount(name, email)) {
	        	JOptionPane.showMessageDialog(contentPane, "Tên tài khoản hoặc Email đã được đăng ký");
	        	clear();
	        }else {
	        	try {
		            DOM_xml.saveToXML(user);
		            JOptionPane.showMessageDialog(contentPane, "Đăng ký thành công");
		            setVisible(false);
		            new DangNhap().setVisible(true);
		        } catch (Exception e) {
		            e.printStackTrace();
		            JOptionPane.showMessageDialog(contentPane, "Lỗi khi đăng ký");
		            clear();
		        }
	        }
	    } else {
	        JOptionPane.showMessageDialog(contentPane, "Vui lòng điền thông tin đầy đủ");
	    }
	}

	private void clear() {
		txtName.setText("");
		txtEmail.setText("");
		txtAddress.setText("");
		txtPass.setText("");
	}
}
