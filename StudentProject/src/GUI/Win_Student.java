package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DB.Grade;
import DB.ServiceDAO;

public class Win_Student extends JFrame implements ActionListener {
	
	JButton jb_DB, jb_Logout;
	JLabel jl_Name, jl_Subject, jl_Info;
	int userid;
	
	JScrollPane scroll;	
	private JTable jtableStock=null;
	public DefaultTableModel data=null;
	
	public Win_Student(int userid, String name) {
		this.userid=userid;
		jl_Name = new JLabel("환영합니다!\t\t"+ name+" 학생");
		jl_Info = new JLabel("조회를 누르면 성적조회가 가능합니다. 점수 및 학점이 안보일 경우 해당 교수님께 문의드리기 바랍니다.");
		jb_DB = new JButton("조회");
		jb_Logout = new JButton("로그아웃");

		
		String names[] = {"과목코드","과목명","담당교수","점수","학점"};
		data = new DefaultTableModel(names, 0);
		jtableStock = new JTable(data);
		scroll = new JScrollPane(jtableStock);
		
		jb_DB.addActionListener(this);
		jb_Logout.addActionListener(this);
		
		setLayout(null);
		display();
		
		setSize(920,600);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand()=="조회") {
			Vector<Grade> grades = new Vector<>();
			grades = ServiceDAO.getDAO().getGrades(userid);
			
			int rows = data.getRowCount();		
			for(int i=rows-1; i>=0; i--) {
				data.removeRow(i);
			}
			
			for(Grade grade: grades) {
				String sub_Id =grade.getSub_Id();
				String sub_Name = grade.getSub_Name();
				String g_Score = String.valueOf(grade.getG_Score());
				String g_Grade = grade.getG_grade();
				String professor = ServiceDAO.getDAO().getProfessor(sub_Id);
				
				Vector<String> in = new Vector<>();
				in.add(sub_Id);
				in.add(sub_Name);
				in.add(professor);
				in.add(g_Score);
				in.add(g_Grade);
				
				data.addRow(in);
			}
			
		}else if(e.getActionCommand()=="로그아웃") {
			JOptionPane.showMessageDialog(null,  "로그아웃 합니다.\n안녕히가세요. ","로그아웃",JOptionPane.INFORMATION_MESSAGE);
			dispose();
			new Stock_Login();
			
		}
		
	}
	
	public void display() {
		jl_Name.setHorizontalAlignment(JLabel.CENTER);
		jl_Info.setHorizontalAlignment(JLabel.CENTER);
		jl_Name.setFont(jl_Name.getFont().deriveFont(22.0f));
		jl_Info.setFont(jl_Info.getFont().deriveFont(15.0f));
		jl_Name.setBounds(30,10,860,50);
		jl_Info.setBounds(30,70,860,50);
		jb_DB.setBounds(30,130,860,40);
		scroll.setBounds(30,190,860,350);
		jb_Logout.setBounds(790,15,100,40);
		
		
		add(jl_Name);
		add(jl_Info);
		add(jb_DB);
		add(scroll);
		add(jb_Logout);
	}
	
	
	

}
