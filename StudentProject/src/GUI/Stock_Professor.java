package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import DB.ServiceDAO;

public class Stock_Professor extends JFrame implements ActionListener {
	
	private JLabel jl_Title, jl_Id, jl_Name, jl_Subject, jl_Score, jl_Grade;
	private JTextField jtf_Score;
	private JTextField jtf_Grade;
	private JButton jb_Ok, jb_Close;
	
	
	String id, subjectId, score, grade;
	
	Stock_Professor(String text, String id, String name, String subject){
		this.id=id;
		this.subjectId=subject;
		jl_Title = new JLabel(text);
		jl_Id = new JLabel("학번:\t\t"+id);
		jl_Name = new JLabel("이름:\t\t"+name);
		jl_Subject = new JLabel("과목:\t\t"+subject);
		jl_Score = new JLabel("점수");
		jl_Grade = new JLabel("학점");
		jtf_Score = new JTextField(10);
		jtf_Grade = new JTextField(10);
		jb_Ok = new JButton(text);
		jb_Close = new JButton("취소");
		
		setLayout(null);
		display();
		setSize(400,500);
		setVisible(true);
	}
	
	Stock_Professor(String text, String id, String name, String subject, String score, String grade){
		this.id=id;
		this.subjectId=subject;
		jl_Title = new JLabel(text);
		jl_Id = new JLabel("학번:\t\t"+id);
		jl_Name = new JLabel("이름:\t\t"+name);
		jl_Subject = new JLabel("과목:\t\t"+subject);
		jl_Score = new JLabel("점수");
		jl_Grade = new JLabel("학점");
		jtf_Score = new JTextField(10);
		jtf_Score.setText(score);
		jtf_Grade = new JTextField(10);
		jtf_Grade.setText(grade);
		jb_Ok = new JButton(text);
		jb_Close = new JButton("취소");
		
		setLayout(null);
		display();
		setSize(400,500);
		setVisible(true);
		
		
	}
	
	public void display() {
		jl_Title.setHorizontalAlignment(JLabel.CENTER);
		jl_Title.setFont(jl_Title.getFont().deriveFont(22.0f));
		jl_Id.setFont(jl_Title.getFont().deriveFont(15.0f));
		jl_Name.setFont(jl_Title.getFont().deriveFont(15.0f));
		jl_Subject.setFont(jl_Title.getFont().deriveFont(15.0f));
		jl_Score.setFont(jl_Title.getFont().deriveFont(15.0f));
		jl_Grade.setFont(jl_Title.getFont().deriveFont(15.0f));
		
		jl_Title.setBounds(150, 0, 100, 80);
		jl_Id.setBounds(50, 70, 300, 40);
		jl_Name.setBounds(50, 110, 300, 40);
		jl_Subject.setBounds(50, 150, 300, 40);
		jl_Score.setBounds(50, 210, 100, 50);
		jl_Grade.setBounds(50, 270, 100, 50);
		jtf_Score.setBounds(90,210,240,50);
		jtf_Grade.setBounds(90,270,240,50);
		jb_Ok.setBounds(40,360,150,50);
		jb_Close.setBounds(210,360,150,50);
		
		jb_Ok.addActionListener(this);
		jb_Close.addActionListener(this);
	
		add(jl_Title);
		add(jl_Id);
		add(jl_Name);
		add(jl_Subject);
		add(jl_Score);
		add(jl_Grade);
		add(jtf_Score);
		add(jtf_Grade);
		add(jb_Ok);
		add(jb_Close);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="성적등록") {
			this.score=jtf_Score.getText();
			this.grade=jtf_Grade.getText();
			boolean isOk = ServiceDAO.getDAO().updateGrade(subjectId, id, score, grade);
			
			if(isOk) {
				JOptionPane.showMessageDialog(null,  "성적등록을 완료했습니다. ","성적등록 성공",JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				
			}else {
				JOptionPane.showConfirmDialog(null, "오류가 발생했습니다.","성적수정 실패",JOptionPane.WARNING_MESSAGE);
			}
			
			
			
		}else if(e.getActionCommand()=="성적수정") {

			this.score=jtf_Score.getText();
			this.grade=jtf_Grade.getText();
			boolean isOk = ServiceDAO.getDAO().updateGrade(subjectId, id, score, grade);
			
			if(isOk) {
				JOptionPane.showMessageDialog(null,  "성적수정을 완료했습니다. ","성적수정 성공",JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				
			}else {
				JOptionPane.showConfirmDialog(null, "오류가 발생했습니다.","성적수정 실패",JOptionPane.WARNING_MESSAGE);
			}
			
		}else if(e.getActionCommand()=="취소") {
			setVisible(false);
		}
		
	}

}
