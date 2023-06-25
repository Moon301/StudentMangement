package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DB.Grade;
import DB.ServiceDAO;

public class Win_Professor extends JFrame implements ActionListener {
	ServiceDAO serviceDAO = ServiceDAO.getDAO();
	
	JButton jb_DB;
	JLabel jl_Name, jl_Subject;
	JComboBox jc_Subject;
	JButton jb_Add, jb_Modify, jb_Delete, jb_Logout;
	JScrollPane scroll;
	HashMap<String, String> subjects;
	
	private JTable jtableStock=null;
	public DefaultTableModel data=null;
	
	public Win_Professor(int userId, String userName) {
		jl_Name = new JLabel("환영합니다. " + userName+"교수님");
		jl_Subject = new JLabel("과목선택");
		jc_Subject = new JComboBox();
		jb_DB = new JButton("조회");
		jb_Add = new JButton("성적등록");
		jb_Modify = new JButton("성적수정");
		jb_Delete = new JButton("성적삭제");
		jb_Logout = new JButton("로그아웃");
		
		String name[] = {"과목명","학번","이름","점수","학점"};
		data = new DefaultTableModel(name, 0);
		jtableStock = new JTable(data);
		scroll = new JScrollPane(jtableStock);
		
		
		setLayout(null);
		display();
		comboBoxSet(userId);
		
		jb_DB.addActionListener(this);
		jb_Add.addActionListener(this);
		jb_Modify.addActionListener(this);
		jb_Delete.addActionListener(this);
		jb_Logout.addActionListener(this);
		
		
		setSize(920,600);
		setVisible(true);
	}
	
	public void comboBoxSet(int userId) {
		subjects = new HashMap<>();
		subjects = serviceDAO.getSubject(userId);

		for(String subject : subjects.values()) {
			jc_Subject.addItem(subject);
		}
		
	}
	
	
	public void display() {
		jl_Name.setHorizontalAlignment(JLabel.CENTER);
		jl_Subject.setHorizontalAlignment(JLabel.RIGHT);
		jl_Name.setFont(jl_Name.getFont().deriveFont(17.0f));

		
		
		jl_Name.setBounds(30,10,200,40);
		jl_Subject.setBounds(200,10,100,40);
		jc_Subject.setBounds(310, 10, 200,40);
		jb_Logout.setBounds(790,10,100,40);

		
		jb_DB.setBounds(30, 60, 200, 40);
		jb_Add.setBounds(250, 60, 200, 40);
		jb_Modify.setBounds(470, 60, 200, 40);
		jb_Delete.setBounds(690, 60, 200, 40);
		scroll.setBounds(30,150,860,400);
		
		
		
		add(jl_Name);
		add(jl_Subject);
		add(jc_Subject);
		add(jb_DB);
		add(jb_Add);
		add(jb_Modify);
		add(jb_Delete);
		add(scroll);
		add(jb_Logout);
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand()=="조회") {
			
			String subjectId="";
			
			for(String key: subjects.keySet()) {
				String value = subjects.get(key);
				
				if(value.equals(jc_Subject.getSelectedItem().toString())) {
					subjectId=key;
				}
			}
			
			Vector<Grade> grades = new Vector<>();
			grades= serviceDAO.getGrades(subjectId);
			
	
			
			int rows = data.getRowCount();		
			for(int i=rows-1; i>=0; i--) {
				data.removeRow(i);
			}
			
			for(Grade grade: grades) {
				String sub_Name = grade.getSub_Name();
				String s_Id = String.valueOf(grade.getS_id());
				String s_Name = grade.getS_Name();
				String g_Score = String.valueOf(grade.getG_Score());
				String g_Grade = grade.getG_grade();
				
				Vector<String> in = new Vector<>();
				in.add(sub_Name);
				in.add(s_Id);
				in.add(s_Name);
				in.add(g_Score);
				in.add(g_Grade);
				
				data.addRow(in);
			}
			
		}else if(e.getActionCommand()=="성적등록") {
			int row = jtableStock.getSelectedRow();
			if(row==-1) {
				JOptionPane.showMessageDialog(null, "등록할 성적 데이터를 선택하지 않았습니다.","주의!",JOptionPane.WARNING_MESSAGE);
			}else {
				String[] info = selectRow(e.getActionCommand(), row);
				new Stock_Professor(info[0],info[1],info[2],info[3]);
				
			}
			
			
		}else if(e.getActionCommand()=="성적수정") {
			int row = jtableStock.getSelectedRow();
			if(row==-1) {
				JOptionPane.showMessageDialog(null, "수정할 성적 데이터를 선택하지 않았습니다.","주의!",JOptionPane.WARNING_MESSAGE);
			}else {
				String[] info = selectRow(e.getActionCommand(), row);
				new Stock_Professor(info[0],info[1],info[2],info[3],info[4],info[5]);
				
			}
			
		}else if(e.getActionCommand()=="성적삭제") {
			int row = jtableStock.getSelectedRow();
			if(row==-1) {
				JOptionPane.showMessageDialog(null, "삭제할 성적을 데이터를 선택하지 않았습니다.","주의!",JOptionPane.WARNING_MESSAGE);
				
			}else {
				int result = JOptionPane.showConfirmDialog(null, "해당학생의 성적을 정말로 삭제하시겠습니까?","경고!",JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					String[] info = selectRow(e.getActionCommand(), row);
					ServiceDAO.getDAO().updateGradeNull(info[3], info[1]);	
				}
				

			}
			
		}else if(e.getActionCommand()=="로그아웃") {
			JOptionPane.showMessageDialog(null,  "로그아웃 합니다.\n안녕히가세요. ","로그아웃",JOptionPane.INFORMATION_MESSAGE);
			dispose();
			new Stock_Login();
		}
		
	}
	
	public String[] selectRow(String e, int row) {
		//info[0]= title, info[1]= id, info[2]=name, info[3]=subject, info[4]=score, info[5]=grade, info[6]= subjectId
		String[] info = new String[6];
		
		info[0]=e;
		info[1] =(String)jtableStock.getValueAt(row, 1);
		info[2] =(String)jtableStock.getValueAt(row, 2);
		info[3] =(String)jtableStock.getValueAt(row, 0);
		info[4] =(String)jtableStock.getValueAt(row, 3);
		info[5] =(String)jtableStock.getValueAt(row, 4);
		
		return info;
		
	}

}
