package com.six.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.six.json.SessionUser;
import com.six.service.LeaveService;

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
@RequestMapping(value="leave",method=GET)
public class LeaveController {
	
	private LeaveService leaveService;
	
	@Autowired
	public LeaveController(LeaveService leaveService) {
		super();
		this.leaveService = leaveService;
	}


	@RequestMapping(value="/toLeaveView",method=GET)
	public String toLeaveView(HttpServletRequest request,HttpServletResponse response) throws IOException{
		return "leaveList";
	}
	
	
	@RequestMapping(value="/leaveList",method=POST)
	public void leaveList(Authentication authentication,HttpServletRequest request,HttpServletResponse response) throws IOException{
		SessionUser su=new SessionUser();
		Collection<? extends GrantedAuthority> c=authentication.getAuthorities();
		su.setRole(c.toString().substring(1, c.toString().length()-1));
		su.setUsername(authentication.getName());
		leaveService.getLeaveList(request, response,su);
	}
	
	@RequestMapping(value="/addLeave",method=POST)
	public void addLeave(HttpServletRequest request,HttpServletResponse response) throws IOException{
		leaveService.addLeave(request, response);
		
	}
	
	@RequestMapping(value="/editLeave",method=POST)
	public void editLeave(HttpServletRequest request,HttpServletResponse response) throws IOException{
		leaveService.editLeave(request, response);
	}
	
	@RequestMapping(value="/checkLeave",method=POST)
	public void checkLeave(HttpServletRequest request,HttpServletResponse response) throws IOException{
		leaveService.checkLeave(request, response);
		
	}
	
	@RequestMapping(value="/deleteLeave",method=POST)
	public void deleteLeave(HttpServletRequest request,HttpServletResponse response) throws IOException{
		leaveService.deleteLeave(request, response);
	}
}
