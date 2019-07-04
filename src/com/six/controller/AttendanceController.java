package com.six.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.six.json.SessionUser;
import com.six.service.AttendanceService;

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
@RequestMapping(value="attendance",method=GET)
public class AttendanceController {

	private AttendanceService attendanceService;
	
	@Autowired
	public AttendanceController(AttendanceService attendanceService) {
		super();
		this.attendanceService = attendanceService;
	}

	@RequestMapping(value="/toAttendanceView",method=GET)
	public String toAttendanceView(HttpServletRequest request,HttpServletResponse response) throws IOException{
		return "attendanceList";
	}
	
	@RequestMapping(value="/attendanceList",method=POST)
	public void attendanceList(Authentication authentication ,HttpServletRequest request,HttpServletResponse response) throws IOException{
		SessionUser su=new SessionUser();
		Collection<? extends GrantedAuthority> c=authentication.getAuthorities();
		su.setRole(c.toString().substring(1, c.toString().length()-1));
		su.setUsername(authentication.getName());
		attendanceService.attendanceList(request, response, su);
	}
	
	@RequestMapping(value="/getStudentSelectedCourseList",method=POST)
	public void getStudentSelectedCourseList(HttpServletRequest request,HttpServletResponse response){
		attendanceService.getStudentSelectedCourseList(request, response);
	}
	
	@RequestMapping(value="/attendanceAdd",method=POST)
	public void attendanceAdd(HttpServletRequest request,HttpServletResponse response){
		attendanceService.addAttendance(request, response);
	}
	@RequestMapping(value="/attendanceDele",method=POST)
	public void attendanceDele(HttpServletRequest request,HttpServletResponse response) throws IOException{
		attendanceService.deleteAttendance(request, response);
	}
}
