package com.luv2code.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// set up the printwriter
		PrintWriter out=response.getWriter();
		//get a database connection
		Connection myconn=null;
		Statement mystmt=null;
		ResultSet myrs=null;
		try {
			//create a sql statement
			myconn=dataSource.getConnection();
			String sql="select * from student";
			mystmt=myconn.createStatement();
			//execute sql query
			myrs=mystmt.executeQuery(sql);
			//process the result set
			while(myrs.next())
			{
				String firstname=myrs.getString("first_name");
				out.println(firstname);
			}
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
	}

}
