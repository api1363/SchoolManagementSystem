package adminManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import bookManagement.model.Books;

public class AdminManagementDao {
	static final String DB_URL = "jdbc:mysql://localhost:3306/schoolmanagement";
	static final String DB_USER = "root";
	static final String DB_PASSWORD = "pass@Word1";
	
	static final String INSERT_ADMIN_QUERY= "insert into admin (adminEmail,adminPassword) values (?,?)";
	static final String SELECT_ADMIN_BY_ID_QUERY = "select adminId,adminEmail,adminPassword from admin where adminId = ?";
	static final String SELECT_ALL_ADMIN_QUERY = "select * from admin";
	static final String DELETE_ADMIN_QUERY = "delete from admin where adminId = ?";
	static final String UPDATE_ADMIN_QUERY = "update admin set adminEmail = ?, adminPassword= ?, where id = ?";
	
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
	
	// create/insert an admin
	public void insertAdmin(AdminManagementModel admin) throws SQLException
	{
		try
		{
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADMIN_QUERY);
			preparedStatement.setString(1, admin.getAdminEmail());
			preparedStatement.setString(2, getEncodedString(admin.getAdminPassword()));
			preparedStatement.executeUpdate();
		}
		catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		
	}
	
	//update admin details
	public boolean updateAdmin(AdminManagementModel admin) throws SQLException
	{
		boolean rowUpdated = true ;
		try
		{
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADMIN_QUERY);
			preparedStatement.setString(1, admin.getAdminEmail());
			preparedStatement.setString(2, getEncodedString(admin.getAdminPassword()));
			preparedStatement.setInt(3, admin.getAdminId());
			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		return rowUpdated;
	}
	
	// select admin by id
	public AdminManagementModel selectAdmin(int id) throws SQLException
	{
		AdminManagementModel admin = null;
		try 
		{
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ADMIN_BY_ID_QUERY);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			
			while (result.next())
			{
				String adminEmail = result.getString("adminEmail");
				String adminPassword = getDecodedString(result.getString("adminPassword"));
				admin = new AdminManagementModel(adminEmail,adminPassword);
			}
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return admin;
	}
	
//	//select all books
//	public List<Books> selectAllBooks() throws SQLException
//	{
//		 List<Books> bookList = new ArrayList();
//		try 
//		{
//			Connection connection = getConnection();
//			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALLBOOKS_QUERY);
//			ResultSet result = preparedStatement.executeQuery();
//			
//			while (result.next())
//			{
//				int id = result.getInt("id");
//				String title = result.getString("title");
//				String author = result.getString("author");
//				float price = result.getFloat("price");
//				bookList.add(new Books(id,title,author,price));
//			}
//		}
//		catch (Exception e) 
//		{
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return bookList;
//	}
//	
	//delete admin by id
	public boolean deleteAdmin(int id) throws SQLException
	{
		boolean adminDeleted = false;
		try
		{
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADMIN_QUERY);
			preparedStatement.setInt(1, id);
			adminDeleted = preparedStatement.executeUpdate() > 0;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return adminDeleted;		
	}
}