package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="/jdbc/web_student_tracker")
	private DataSource dataSource;
	private StudentDbUtil studentDbUtil;

	
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
		studentDbUtil= new StudentDbUtil(dataSource);
		}
		catch(Exception exc)
		{
			throw new ServletException(exc);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			//if the command is null
			if(theCommand==null)
			{
				theCommand="LIST";
			}
			//route to the appropriate method
			switch(theCommand)
			{
			case "LIST":
				listStudents(request, response);
			break;
			case "ADD":
				addStudents(request, response);
			break;
			case "LOAD":
				loadStudents(request, response);
				break;
			case "UPDATE":
				updateStudents(request, response);
				break;
			case "DELETE":
				deleteStudent(request, response);
				break;
			case "SEARCH":
				searchStudent(request, response);
				break;
			default: 
				listStudents(request, response);
			}
			
		}
		catch(Exception exc)
		{
			throw new ServletException(exc);
		}
	}

	private void searchStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
	 //get the search name
		String theSearchName=request.getParameter("theSearchName");
		//search student from database util
		List<Student> students=studentDbUtil.searchStudents(theSearchName);
		//set attribute
		request.setAttribute("STUDENT_LIST", students);
		//list the students
		RequestDispatcher dispatcher= request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request,  response);
		
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		// read the student id from data
		String theStudentId=request.getParameter("studentId");
		
		//delete the student from database
		studentDbUtil.deleteStudent(theStudentId);
		
		//display the list
		listStudents(request, response);
		
		
	}

	private void updateStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read the data from form
		int id=Integer.parseInt(request.getParameter("studentId"));
		String firstname=request.getParameter("firstname");
		String lastname= request.getParameter("lastname");
		String email= request.getParameter("email");
		//create a new student
		Student theStudent=new Student(id, firstname, lastname, email);
		//update student to the database
		studentDbUtil.updateStudent(theStudent);
		//list the students
		listStudents(request, response);
		
	}

	private void loadStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read student id from data
		String theStudentId= request.getParameter("studentId");
		//get student from db( db util)
		Student theStudent= studentDbUtil.getStudent(theStudentId);
		//place student in the attribute value
		request.setAttribute("THE_STUDENT", theStudent);
		//send to jsp
		RequestDispatcher dispatcher=
				request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
		
		
	}

	private void addStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		//read the student info from data
		String firstname=request.getParameter("firstname");
		String lastname=request.getParameter("lastname");
		String email=request.getParameter("email");
		// create the new Student object
		Student theStudent= new Student(firstname, lastname, email);
		// add student to the database
		studentDbUtil.addStudent(theStudent);
		//send back to the main page
		listStudents(request, response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		//get the students
		List<Student> Students= studentDbUtil.getStudents();
		//add students to the request
		request.setAttribute("STUDENT_LIST", Students);
		//add jsp view
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request,  response);
		
	}

}
