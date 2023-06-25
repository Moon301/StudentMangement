package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class MemberDAO {
	private MemberDAO() {
		
	}
	
	public static MemberDAO memberDAO = new MemberDAO();
	
	public static MemberDAO getDAO() {
		return memberDAO;
	}
	
	
	//Join(Sign up)기능  
	//1. 교수테이블 또는 학생테이블에 있는지 확인해보고(->사전에 등록된 사람만 가입이 가능하게)
	//2. 없으면 등록되지 않은 사람이다 학교 관리자에 문의해주세요 전화번호 표시
	//3. 있으면 사용자인증(Member) 테이블에 등록시키
	
	public boolean check_JoinID(String role, int userId) {
		String sql;
		boolean isOk = false;
		try {
			Connection conn = DBConnect.connect();
			if(role=="교수") {
				sql = "select p_Id from professor where p_Id=?";
			}else {
				sql = "select s_Id from student where s_Id=?";
			}
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				isOk = true;
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		}finally{
			DBConnect.close();
		}
		
		
		return isOk;
	}
	public String[] check_Login(int userId, String pwd) {
		String[] info = new String[3];
		
		try {
			Connection conn = DBConnect.connect();
			String sql = "select userPwd, userRole, userName from member where userId=?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				info[0]= rs.getString(1);
				info[1]= rs.getString(2);
				info[2]= rs.getString(3);
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		}finally{
			DBConnect.close();
		}
		
		
		return info;
	}
	
	public String[] getUserInfo(String role, int userId){
		
		String[] info= new String[2];
		
		try {
			Connection conn = DBConnect.connect();
			
			
			String sql="";
			if(role.equals("교수")){
				sql = "select p_Name, p_Birth from professor where p_Id=?";
			}else {
				sql="select s_Name, s_Birth from student where s_Id=?";
			}
			PreparedStatement stmt = conn.prepareStatement(sql);		
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				info[0]= rs.getString(1);
				info[1]= rs.getString(2);
				
			}

		}catch (SQLException e){
			e.printStackTrace();
			
		}finally {
			DBConnect.close();
		}
		
		return info;
		
		
	}
	
	public boolean checkMember(int userId) {
		boolean isOk =false;
		try {
			Connection conn = DBConnect.connect();
			String sql = "select userId from member where userId=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString(1));
				isOk=true;
			}
		}catch(SQLException e){
			e.printStackTrace();
			
		}finally {
			DBConnect.close();
		}
		
		
		return isOk;
	}
	
	
	public String memberRole(int id) {
		String role="";
		
		try {
			Connection conn = DBConnect.connect();
			String sql = "select userRole from member where userId=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				role=rs.getString(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
			
		}finally {
			DBConnect.close();
		}
		
		
		return role;
	}
	
	//Join -> Member Insert 
	public boolean insertMember(Member user) {
		boolean isOk = false;
		int r=0;
		try {
			Connection conn = DBConnect.connect();
			String sql = "insert into member values(?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user.getUserId());
			stmt.setString(2, user.getUserPwd());
			stmt.setString(3, user.getUserName());
			stmt.setString(4, user.getUserRole());
			
			r = stmt.executeUpdate();
			if(r>0) {
				isOk = true;
			}
		}catch(SQLException e){
			e.printStackTrace();
			
		}finally {
			DBConnect.close();
		}
		
		return isOk;
	}
}
