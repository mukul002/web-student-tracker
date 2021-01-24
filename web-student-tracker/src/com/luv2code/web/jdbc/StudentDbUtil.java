package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


public class StudentDbUtil {
	private DataSource dataSource;
	public StudentDbUtil(DataSource thedataSource)
	{
		dataSource=thedataSource;
	}
	public List<Student> getStudents() throws Exception
	{
		List<Student> Students= new ArrayList<>();
		Connection myconn=null;
		Statement mystmt=null;
		ResultSet myrs= null;
		
		try {
		//get a connection
			myconn=dataSource.getConnection();
		
		//create statement
			mystmt=myconn.createStatement();
			String sql="select * from student order by last_name";
		
		//execute query
			myrs=mystmt.executeQuery(sql);
			//get  resultset
			while(myrs.next())
			{	//retrieve data from result set
				int id= myrs.getInt("id");
				String firstname=myrs.getString("first_name");
				String lastname=myrs.getString("last_name");
				String email=myrs.getString("email");
				//create a new student object
				Student tempStudent= new Student(id, firstname, lastname, email);
				//add data to
				Students.add(tempStudent);
			}
			return Students;
		}
			finally {
				//close jdbc object
				close(myconn, mystmt, myrs);
				
			}
		}
	private void close(Connection myconn, Statement mystmt, ResultSet myrs) {
		// TODO Auto-generated method stub
		try {
			if(myrs!=null)
				myrs.close();
			if(mystmt!=null)
				mystmt.close();
			if(myconn!=null)
				myconn.close();
			
			
		
			
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		
	}
	public void addStudent(Student theStudent) throws Exception {
		// TODO Auto-generated method stub
		Connection myconn= null;
		PreparedStatement mystmt= null;
		try {
		// get db connection
		myconn= dataSource.getConnection();
		// create sql statement
		String sql= "insert into student"
				+"(first_name,last_name,email)"
				+"values(?, ?, ?)";	
		mystmt= myconn.prepareStatement(sql);
		//get the param values
		mystmt.setString(1, theStudent.getFirstname());	
		mystmt.setString(2, theStudent.getLastname());
		mystmt.setString(3, theStudent.getEmail());
		//execute
		mystmt.execute();
	
		
	}
		finally {
			//clean up jdbc objects
			close(myconn, mystmt, null);
		}
			
		}
	public Student getStudent(String theStudentId) throws Exception {
		// TODO Auto-generated method stub
		Student theStudent=null;
		Connection myconn=null;
		PreparedStatement mystmt=null;
		ResultSet myrs=null;
		int studentId;
		try {
			//convert student id to int
			studentId=Integer.parseInt(theStudentId);
			
			//connection to the database
			myconn=dataSource.getConnection();
			
			//create sql query
			String sql="select * from student where id=?";
			
			//create prepared statements
			mystmt=myconn.prepareStatement(sql);
			//request params
			 mystmt.setInt(1, studentId);
			
			//execute 
			myrs=mystmt.executeQuery();
			
			//retrieve data from resultset
			if(myrs.next())
			{
				String firstname=myrs.getString("first_name");
				String lastname=myrs.getString("last_name");
				String email=myrs.getString("email");
				
				//construct the  Student using id
				theStudent= new Student(studentId, firstname, lastname, email);
			}
			else
			{
				throw new Exception("could not fing the student id"+studentId);
			}
			
		return theStudent;
		}
		finally {
			//clear the jdbc objects
			close(myconn,mystmt,myrs);
		}
		
	}
	public void updateStudent(Student theStudent) throws Exception {
		// TODO Auto-generated method stub
		Connection myconn=null;
		PreparedStatement mystmt=null;
		
		try {
			myconn=dataSource.getConnection();
			String sql="update student "
					+ "set first_name=?, last_name=?, email=? "
					+ "where id=?";
			mystmt=myconn.prepareStatement(sql);
			mystmt.setString(1, theStudent.getFirstname());
			mystmt.setString(2, theStudent.getLastname());
			mystmt.setString(3, theStudent.getEmail());
			mystmt.setInt(4, theStudent.getId());
			mystmt.execute();
		}
		finally {
			close(myconn, mystmt, null);
		}
	}
	public void deleteStudent(String theStudentId) throws Exception {
		Connection myconn=null;
		PreparedStatement mystmt=null;
		try {
			// get the student id
			int studentId=Integer.parseInt(theStudentId);
			
			//get the connection
			myconn=dataSource.getConnection();
			 
			//create sql
			String sql="delete from student where id=?";
			
			//prepare statemnt
			mystmt=myconn.prepareStatement(sql);
			
			//set params
			mystmt.setInt(1, studentId);
			
			//execute query
			mystmt.execute();
			
		}
		finally {
			close(myconn, mystmt, null);
		}
	}
	public List<Student> searchStudents(String theSearchName) throws Exception {
		List<Student> students= new ArrayList<>();
		
		Connection myconn=null;
		PreparedStatement mystmt=null;
		ResultSet myrs=null;
		int studentId;
		
		try {
			//get the db connection
			myconn= dataSource.getConnection();
			// only search is the thesearchname is not empty
			if(theSearchName!=null && theSearchName.trim().length() > 0) {
				//create sql to search for student
				String sql="select * from student where lower(first_name) like ? or lower(last_name) like ?";
				//create prepared statement
				mystmt=myconn.prepareStatement(sql);
				//set params
				String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%";
				mystmt.setString(1, theSearchNameLike);
				mystmt.setString(2, theSearchNameLike);
			}
			else {
				String sql="select * from student order by last_name";
				mystmt=myconn.prepareStatement(sql);
				
			}
			myrs=mystmt.executeQuery();
			
			while(myrs.next()) {
				int id=myrs.getInt("id");
				String firstname=myrs.getString("first_name");
				String lastname=myrs.getString("last_name");
				String email=myrs.getString("email");
				
				//create new student object
				Student tempStudent=new Student(id, firstname, lastname, email);
				//add the student
				students.add(tempStudent);
				
			}
			return students;
		}
		finally {
			close(myconn, mystmt, myrs);
		}

	}
}	
		
		
		

