package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class ServiceDAO {
	private ServiceDAO() {
		
	}
	
	public static ServiceDAO serviceDAO = new ServiceDAO();
	
	public static ServiceDAO getDAO() {
		return serviceDAO;
	}
	

	
	// 해당과목의 성적목록 가져오기
	public Vector<Grade> getGrades(String sub_id){
		Vector<Grade> grades = new Vector<>();
		
		try {
			Connection conn = DBConnect.connect();
			String sql = "select * from grade where g_sub_Id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, sub_id);
			
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Grade grade = new Grade();
				grade.setSub_Id(rs.getString(1));
				grade.setSub_Name(rs.getString(2));
				grade.setS_id(rs.getInt(3));
				grade.setS_Name(rs.getString(4));
				grade.setG_Score(rs.getInt(5));
				grade.setG_grade(rs.getString(6));
				
				grades.add(grade);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnect.close();
		}
		return grades;
	}
	
	// 해당학생의 성적목록 가져우기 
	public Vector<Grade> getGrades(int s_id){
		Vector<Grade> grades = new Vector<>();
		
		try {
			Connection conn = DBConnect.connect();
			String sql = "select * from grade where g_s_Id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, String.valueOf(s_id));
			
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Grade grade = new Grade();
				grade.setSub_Id(rs.getString(1));
				grade.setSub_Name(rs.getString(2));
				grade.setS_id(rs.getInt(3));
				grade.setS_Name(rs.getString(4));
				grade.setG_Score(rs.getInt(5));
				grade.setG_grade(rs.getString(6));
				
				grades.add(grade);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnect.close();
		}
		return grades;
	}
	
	//담당교수 찾기 
	public String getProfessor(String sub_Id) {
		
		String professor="";
		try {
			Connection conn = DBConnect.connect();
			String sql = "select professor.p_Name from professor, subject where professor.p_Id = subject.sub_p_Id and subject.sub_Id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, sub_Id);
			
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				professor=rs.getString(1);
		
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnect.close();
		}
		
		return professor;
	}

	//성적등록 및 수정
	public boolean updateGrade(String subject, String id, String score, String grade) {
		//과목번호와 학생번호를 가지고 grade 테이블 업데이트해야함.
		boolean isOk = false;
		int r = 0;
		try {
			Connection conn = DBConnect.connect();
			String sql = "update grade set g_Score=?, g_Grade=? where g_sub_Name=? and g_s_Id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, score);
			stmt.setString(2, grade);
			stmt.setString(3, subject);
			stmt.setInt(4, Integer.parseInt(id));

			r=stmt.executeUpdate();
			if(r>0) {
				isOk = true;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnect.close();
		}
		
		return isOk;
	}
	//성적삭제(성적 -> Null값으로 만들기)
	public boolean updateGradeNull(String subject, String id) {
		boolean isOk = false;
		
		int r=0;
		try {
			Connection conn = DBConnect.connect();
			String sql = "update grade set g_Score=null, g_Grade=null where g_sub_Name=? and g_s_Id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, subject);
			stmt.setInt(2, Integer.parseInt(id));
			
			r=stmt.executeUpdate();
			if(r>0) {
				isOk = true;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnect.close();
		}
		
		return isOk;
		
	}
	public void deleteGrade() {
		
	}
	
	//해당 교수의 담당과목 가지고 오기 
	public HashMap<String, String> getSubject(int id){
		
		HashMap<String, String> subjects = new HashMap<>();
		String subject="";
		String subjectId;
		
		try {
			Connection conn = DBConnect.connect();
			String sql = "select sub_Id, sub_Name from subject where sub_p_Id =?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				subjectId=rs.getString(1);
				subject = rs.getString(2);
				subjects.put(subjectId, subject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnect.close();
		}
		
		return subjects;
	}
	

}
