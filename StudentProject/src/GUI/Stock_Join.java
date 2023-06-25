package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import DB.Member;
import DB.MemberDAO;

public class Stock_Join extends JFrame implements ActionListener {
	
	JLabel jl_Title;
	JLabel jl_Id;
	JLabel jl_Pwd;
	JLabel jl_Pwd_Check;
	JLabel jl_Name;
	JLabel jl_Birth;
	JLabel jl_CheckId;
	JTextField jtf_Id;
	JPasswordField jpf_Pwd;
	JPasswordField jpf_Pwd_Check;
	JTextField jtf_Name;
	JTextField jtf_Birth;
	JRadioButton jr_pro;
	JRadioButton jr_stu;
	ButtonGroup group;
	JButton jb_CheckId;
	JButton jb_Ok;
	JButton jb_Cancel;
	boolean idCheckIsOk =false;
	
	
	
	//객체 생성, 화면구
	public Stock_Join() {
		setLayout(new BorderLayout());
		
		jl_Title = new JLabel("회원등록");
		jl_Id = new JLabel("학번");
		jl_Pwd = new JLabel("비밀번호");
		jl_Pwd_Check = new JLabel("비밀번호 확인");
		jl_Name = new JLabel("이름");
		jl_Birth = new JLabel("생년월일");
		jl_CheckId = new JLabel("학번확인하기(신분선택필수)");
		jr_pro = new JRadioButton("교수");
		jr_stu = new JRadioButton("학생");
		jtf_Id  = new JTextField(15);
		jpf_Pwd = new JPasswordField(20);
		jpf_Pwd_Check= new JPasswordField(20);
		jtf_Name = new JTextField(15);
		jtf_Birth = new JTextField(15);
		jb_CheckId = new JButton("학번확인");
		group = new ButtonGroup();
		group.add(jr_pro);
		group.add(jr_stu);
		jb_Ok = new JButton("확인");
		jb_Cancel=new JButton("취소");
		
		jb_Ok.addActionListener(this);
		jb_Cancel.addActionListener(this);
		jb_CheckId.addActionListener(this);
		
		this.jpf_Pwd.enableInputMethods(true);
		this.jpf_Pwd_Check.enableInputMethods(true);
		
		display();
		setSize(400,500);
		setVisible(true);
	
	}
	
	//Gui요소 세부 설정
	public void display() {
		JPanel ct = new JPanel();
		ct.setLayout(new GridLayout(7,2));
		ct.setBorder(BorderFactory.createEmptyBorder(10,10,20,40));
		
		jl_Id.setHorizontalAlignment(JLabel.CENTER);
		jl_Pwd.setHorizontalAlignment(JLabel.CENTER);
		jl_Pwd_Check.setHorizontalAlignment(JLabel.CENTER);
		jl_Name.setHorizontalAlignment(JLabel.CENTER);
		jl_Birth.setHorizontalAlignment(JLabel.CENTER);
		jl_CheckId.setHorizontalAlignment(JLabel.CENTER);
		jl_Title.setFont(jl_Title.getFont().deriveFont(18.0f));
		
		jtf_Name.setEditable(false);
		jtf_Birth.setEditable(false);
		
		ct.add(jr_pro);
		ct.add(jr_stu);
		ct.add(jl_Id);
		ct.add(jtf_Id);
		ct.add(jl_CheckId);
		ct.add(jb_CheckId);
		ct.add(jl_Pwd);
		ct.add(jpf_Pwd);
		ct.add(jl_Pwd_Check);
		ct.add(jpf_Pwd_Check);
		ct.add(jl_Name);
		ct.add(jtf_Name);
		ct.add(jl_Birth);
		ct.add(jtf_Birth);

		
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1,2));
		p2.add(jb_Ok);
		p2.add(jb_Cancel);

		
		jl_Title.setHorizontalAlignment(JLabel.CENTER);
		add(jl_Title,BorderLayout.NORTH);
		add(ct,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
		
	}
	
	
	//비번 중복검사
	public boolean pwdisDiffrent(String pwd, String pwdCheck) {
		boolean pwdIsOk =true;
		if(pwd.equals(pwdCheck)==false) {
			pwdIsOk = false;
		}
		
		return pwdIsOk;
		
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
	
	
	//Join 기능 활성
	@Override
	public void actionPerformed(ActionEvent e) {
		int userId;
		String userPwd;
		String userPwdCheck;
		String userName;
		String userBirth;
		String userRole="";
		
		
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="확인") {
			userId = Integer.parseInt(jtf_Id.getText());
			userPwd = pwdToString(jpf_Pwd);
			userPwdCheck = pwdToString(jpf_Pwd_Check);
			userName = jtf_Name.getText();
			userBirth=jtf_Birth.getText();
			if(jr_pro.isSelected()==true) {
				userRole=jr_pro.getText();
			}else if(jr_stu.isSelected() == true) {
				userRole=jr_stu.getText();
			}
			boolean pwdIsOk = pwdisDiffrent(userPwd, userPwdCheck);
			
			System.out.println(pwdIsOk);
			System.out.println(idCheckIsOk);
			
			if(pwdIsOk && idCheckIsOk) {
				Member user = new Member();
				user.setUserId(userId);
				user.setUserPwd(userPwd);
				user.setUserName(userName);
				user.setUserBirth(userBirth);
				user.setUserRole(userRole);
				
				boolean isOk = MemberDAO.getDAO().insertMember(user);
						
				JOptionPane.showMessageDialog(null,  "회원등록 성공!\n"+userId+ "님, 환영합니다. ","회원등록 완료",JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}else if(idCheckIsOk){
				JOptionPane.showConfirmDialog(null, "비밀번호가 불일치 합니다. 다시 입력하세요.","회원등록 실패!",JOptionPane.WARNING_MESSAGE);	
			}else if(pwdIsOk){
				JOptionPane.showConfirmDialog(null, "학번확인을 해주세요.\n사전에 학번이 등록돼야 회원등록이 가능합니다.\n자세한 사항은 관리자에게 문의하시길 바랍니다.","회원등록 실패!",JOptionPane.WARNING_MESSAGE);	
			}else {
				JOptionPane.showConfirmDialog(null, "회원등록을 위한 정보를 입력해주세요.","회원등록 실패",JOptionPane.WARNING_MESSAGE);
			}
		}else if(e.getActionCommand()=="학번확인"){
			userId=Integer.parseInt(jtf_Id.getText());
			if(jr_pro.isSelected()==true) {
				userRole=jr_pro.getText();
			}else if(jr_stu.isSelected() == true) {
				userRole=jr_stu.getText();
			}
			
			idCheckIsOk = MemberDAO.getDAO().check_JoinID(userRole, userId);
			boolean memberCheckIsOk =MemberDAO.getDAO().checkMember(userId);
			
			
			System.out.println("check Joinid: "+idCheckIsOk);
			System.out.println("memberCheckIsOk: "+ memberCheckIsOk);
			if(!memberCheckIsOk&&idCheckIsOk) {
				String[] info = MemberDAO.getDAO().getUserInfo(userRole, userId);
				JOptionPane.showMessageDialog(null,  "학번조회 성공!\n"+info[0]+ "님의 정보를 불러옵니다.","회원조회 성공",JOptionPane.INFORMATION_MESSAGE);
				jtf_Name.setText(info[0]);
				jtf_Birth.setText(info[1]);
			}else if(!idCheckIsOk) {
				JOptionPane.showConfirmDialog(null, "학번을 다시한번 확인하시기 바랍니다.\n사전에 학번이 등록돼야 회원등록이 가능합니다.\n자세한 사항은 관리자에게 문의하시길 바랍니다.","학번조회 실패",JOptionPane.WARNING_MESSAGE);
				
			}else {
				JOptionPane.showConfirmDialog(null, "이미 등록된 획원입니다.","등록된 회원",JOptionPane.WARNING_MESSAGE);
				
			}
			
		}else {
			dispose();
		}
		
	}

}
