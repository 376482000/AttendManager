package com.six.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.six.json.SessionUser;
import com.six.service.SelectedCourseService;

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
@RequestMapping(value="selectCourse",method=GET)
public class SelectCourseController {
	private SelectedCourseService selectedCourseService;

	@Autowired
	public SelectCourseController(SelectedCourseService selectedCourseService) {
		super();
		this.selectedCourseService = selectedCourseService;
	}
	
	@RequestMapping(value="/toSelectedCourseView",method=GET)
	public String toSelectedCourseView(HttpServletRequest request,HttpServletResponse response) throws IOException{
		return "selectedCourseList";
	}
	@RequestMapping(value="/selectedCourseList",method=POST)
	public void selectedCourseList(Authentication authentication,HttpServletRequest request,HttpServletResponse response) throws IOException{
		SessionUser su=new SessionUser();
		Collection<? extends GrantedAuthority> c=authentication.getAuthorities();
		su.setRole(c.toString().substring(1, c.toString().length()-1));
		su.setUsername(authentication.getName());
		selectedCourseService.getSelectedCourseList(request, response, su);;
		
	}
	@RequestMapping(value="/selectedCourseAdd",method=POST)
	public void selectedCourseAdd(HttpServletRequest request,HttpServletResponse response) throws IOException{
		selectedCourseService.addSelectedCourse(request, response);
		
	}
	@RequestMapping(value="/selectedCourseDele",method=POST)
	public void selectedCourseDele(HttpServletRequest request,HttpServletResponse response) throws IOException{
		selectedCourseService.deleteSelectedCourse(request, response);
		
	}
}
