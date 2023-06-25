package GUI;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DB.MemberDAO;

public class Stock_Login extends JFrame implements ActionListener {
	
	private JLabel jl_Welcome;
	private JLabel jl_Id;
	private JLabel jl_Pwd;
	private JTextField jtf_Id;
	private JPasswordField jpf_Pwd;
	private JButton jb_Login;
	private JButton jb_Join;
	
	public Stock_Login() {
		jl_Welcome = new JLabel("성적관리 프로그램");
		jl_Id = new JLabel("Id");
		jl_Pwd = new JLabel("Password");
		jb_Login = new JButton("Login");
		jtf_Id = new JTextField(15);
		jpf_Pwd = new JPasswordField(15);
		jb_Join = new JButton("Join");
		
		this.jpf_Pwd.enableInputMethods(true);
		
		jb_Login.addActionListener(this);
		jb_Join.addActionListener(this);
		
		setLayout(null);
		display();
		
		setSize(400,250);
		setVisible(true);
		
	}
	
	
	//비번값 가져오기
	protected String pwdToString(JPasswordField pwd) {
		String pwdToString="";
		char[] secret_pwd = pwd.getPassword();
		for(char c: secret_pwd) {
			pwdToString+=Character.toString(c);
		}
			
		return pwdToString;
	}
		
		
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if(e.getActionCommand()=="Login") {
			int userId = Integer.parseInt(jtf_Id.getText());
			String pwd=pwdToString(jpf_Pwd);
			
			//info[o]=pwd, info[1]=role, info[2]=username;
			String[] info = MemberDAO.getDAO().check_Login(userId, pwd);
			
			if(pwd.equals(info[0])) {
				JOptionPane.showMessageDialog(null,  "로그인 성공!\n환영합니다. ","로그인 성공",JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				
				String role = MemberDAO.getDAO().memberRole(userId);
				
				if(role.equals("교수")) {
					new Win_Professor(userId, info[2]);
		
				}else {
					new Win_Student(userId, info[2]);
				}
				
				
			}else {
				JOptionPane.showConfirmDialog(null, "일치하는 회원정보가 없습니다.","로그인 실패",JOptionPane.WARNING_MESSAGE);
			}
			
			
			
		}else if(e.getActionCommand()=="Join") {
			new Stock_Join();
		}
		
	}
	
	
	public void display() {
		jl_Welcome.setHorizontalAlignment(JLabel.CENTER);
		jl_Id.setHorizontalAlignment(JLabel.RIGHT);
		jl_Pwd.setHorizontalAlignment(JLabel.RIGHT);
		
		jl_Welcome.setBounds(150,10,100,50);
		jl_Id.setBounds(30,60,80,40);
		jl_Pwd.setBounds(30,110,80,40);
		jtf_Id.setBounds(120, 60, 200, 40);
		jpf_Pwd.setBounds(120, 110, 200, 40);
		jb_Login.setBounds(50,160,140,40);
		jb_Join.setBounds(210, 160, 140, 40);
		//FlowLayout 왼쪽 정렬
		//FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT);
		add(jl_Welcome);
		add(jl_Id);
		add(jl_Pwd);
		add(jtf_Id);
		add(jpf_Pwd);
		add(jb_Login);
		add(jb_Join);
	}
	

}
