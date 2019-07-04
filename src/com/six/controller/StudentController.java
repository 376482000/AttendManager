package com.six.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.six.dao.StudentDao;
import com.six.json.SessionUser;
import com.six.model.Admin;
import com.six.service.StudentService;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Controller
@RequestMapping(value="student",method=GET)
public class StudentController {

	private StudentDao studentDao;
	private StudentService studentService;
	
	@Autowired
	public StudentController(StudentDao studentDao, StudentService studentService) {
		super();
		this.studentDao = studentDao;
		this.studentService = studentService;
	}

	@RequestMapping(value="/toStudentView",method=GET)
	public String toStudentView(HttpServletRequest request,HttpServletResponse response) throws IOException{
		return "studentList";
	}
	
	@RequestMapping(value="/studentList",method=POST)
	public void studentList(Authentication authentication ,HttpServletRequest request,HttpServletResponse response) throws IOException{
		SessionUser su=new SessionUser();
		Collection<? extends GrantedAuthority> c=authentication.getAuthorities();
		su.setRole(c.toString().substring(1, c.toString().length()-1));
		su.setUsername(authentication.getName());
		studentService.getStudentList(request, response,su);
		
	}
	@RequestMapping(value="/studentAdd",method=POST)
	public void studentAdd(HttpServletRequest request,HttpServletResponse response) throws IOException{
		studentService.addStudent(request, response);	
	}
	@RequestMapping(value="/studentDele",method=POST)
	public void studentDele(HttpServletRequest request,HttpServletResponse response) throws IOException{
		studentService.deleteStudent(request, response);	
	}
	@RequestMapping(value="/studentEdit",method=POST)
	public void studentEdit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		studentService.editStudent(request, response);;	
	}
	
}
