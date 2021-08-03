package studentManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class StudentManagementDao {
	static final String DB_URL = "jdbc:mysql://localhost:3306/schoolmanagement";
	static final String DB_USER = "root";
	static final String DB_PASSWORD = "pass@Word1";
	
	static final String INSERT_STUDENT_QUERY= "insert into students (studentEmail,studentPassword) values (?,?)";
	static final String SELECT_STUDENT_BY_ID_QUERY = "select * from students where studentId = ?";
	static final String SELECT_ALL_STUDENT_QUERY = "select * from students";
	static final String DELETE_STUDENT_QUERY = "delete from students where studentId = ?";
	static final String UPDATE_STUDENT_QUERY = "update students set studentEmail = ?, studentPassword= ?, where id = ?";
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		return connection;
	}
	
	public static String getEncodedString(String password)
	{
		return Base64.getEncoder().encodeToString(password.getBytes());
	}
	
	public static String getDecodedString(String encryptedPassword)
	{
		return new String(Base64.getMimeDecoder().decode(encryptedPassword));
	}
	
	// create/insert a student
	public void insertStudent(StudentManagementModel student) throws SQLException
	{
		try
		{
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_QUERY);
			preparedStatement.setString(1, student.getStudentEmail());
			preparedStatement.setString(2, getEncodedString(student.getStudentPassword()));
			preparedStatement.executeUpdate();
		}
		catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		
	}
	
	//update student details
	public boolean updateStudent(StudentManagementModel student) throws SQLException
	{
		boolean rowUpdated = true ;
		try
		{
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT_QUERY);
			preparedStatement.setString(1, student.getStudentEmail());
			preparedStatement.setString(2, getEncodedString(student.getStudentPassword()));
			preparedStatement.setInt(3, student.getStudentId());
			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		return rowUpdated;
	}
	
	// select student by id
	public StudentManagementModel selectStudent(int id) throws SQLException
	{
		StudentManagementModel student = null;
		try 
		{
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID_QUERY);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			
			while (result.next())
			{
				String studentEmail = result.getString("studentEmail");
				String studentPassword = getDecodedString(result.getString("studentPassword"));
				student = new StudentManagementModel(studentEmail,studentPassword);
			}
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return student;
	}
	
//	//select all students
	public List<StudentManagementModel> selectAllStudents() throws SQLException
	{
		 List<StudentManagementModel> studentList = new ArrayList();
		try 
		{
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENT_QUERY);
			ResultSet result = preparedStatement.executeQuery();
			
			while (result.next())
			{
				int studentId = result.getInt("studentId");
				String studentEmail = result.getString("studentEmail");
				String studentPassword= getDecodedString(result.getString("studentPassword"));
				studentList.add(new StudentManagementModel(studentId,studentEmail,studentPassword));
			}
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return studentList;
	}
	
	//delete student by id
	public boolean deleteStudent(int id) throws SQLException
	{
		boolean studentDeleted = false;
		try
		{
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT_QUERY);
			preparedStatement.setInt(1, id);
			studentDeleted = preparedStatement.executeUpdate() > 0;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return studentDeleted;		
	}
}
