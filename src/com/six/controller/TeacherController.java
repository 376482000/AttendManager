package com.six.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.six.json.SessionUser;
import com.six.service.TeacherService;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Controller
@RequestMapping(value="teacher",method=GET)
public class TeacherController {
	private TeacherService teacherService;
	
	@Autowired
	public TeacherController(TeacherService teacherService) {
		super();
		this.teacherService = teacherService;
	}

	@RequestMapping(value="/toTeacherView",method=GET)
	public String toTeacherView(HttpServletRequest request,HttpServletResponse response) throws IOException{
		return "teacherList";
	}
	
	@RequestMapping(value="/teacherList",method=POST)
	public void teacherList(Authentication authentication ,HttpServletRequest request,HttpServletResponse response) throws IOException{
		SessionUser su=new SessionUser();
		Collection<? extends GrantedAuthority> c=authentication.getAuthorities();
		su.setRole(c.toString().substring(1, c.toString().length()-1));
		su.setUsername(authentication.getName());
		teacherService.getTeacherList(request, response,su);
		
	}
	
	@RequestMapping(value="/teacherAdd",method=POST)
	public void teacherAdd(HttpServletRequest request,HttpServletResponse response) throws IOException{
		teacherService.addTeacher(request, response);
	}
	
	@RequestMapping(value="/teacherEdit",method=POST)
	public void teacherEdit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		teacherService.editTeacher(request, response);	
	}
	
	@RequestMapping(value="/teacherDele",method=POST)
	public void teacherDele(HttpServletRequest request,HttpServletResponse response) throws IOException{
		teacherService.deleteTeacher(request, response);	
	}

}
