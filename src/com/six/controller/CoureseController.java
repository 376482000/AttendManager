package com.six.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.six.json.SessionUser;
import com.six.service.CourseService;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Controller
@RequestMapping(value="course",method=GET)
public class CoureseController {
	private CourseService courseService;
	
	@Autowired
	public CoureseController(CourseService courseService) {
		super();
		this.courseService = courseService;
	}



	@RequestMapping(value="/toCourseView",method=GET)
	public String toCourseView(HttpServletRequest request,HttpServletResponse response) throws IOException{
		return "courseList";
	}
	@RequestMapping(value="/courseList",method=POST)
	public void courseList(Authentication authentication,HttpServletRequest request,HttpServletResponse response) throws IOException{
		SessionUser su=new SessionUser();
		Collection<? extends GrantedAuthority> c=authentication.getAuthorities();
		su.setRole(c.toString().substring(1, c.toString().length()-1));
		su.setUsername(authentication.getName());
		courseService.getCourseList(request, response ,su);
	}
	@RequestMapping(value="/addCourse",method=POST)
	public void addCourse(HttpServletRequest request,HttpServletResponse response) throws IOException{
		courseService.addCourse(request, response);
		
	}
	
	@RequestMapping(value="/courseDele",method=POST)
	public void courseDele(HttpServletRequest request,HttpServletResponse response) throws IOException{
		courseService.deleteCourse(request, response);	
	}
	

	@RequestMapping(value="/editCourse",method=POST)
	public void editCourse(HttpServletRequest request,HttpServletResponse response) throws IOException{
		courseService.editCourse(request, response);	
	}
}
