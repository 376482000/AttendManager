package com.six.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.six.json.SessionUser;
import com.six.service.ClazzService;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="clazz",method=GET)
public class ClazzController {
	
	private ClazzService clazzService;
	
	@Autowired	
	public ClazzController(ClazzService clazzService) {
		super();
		this.clazzService = clazzService;
	}

	@RequestMapping(value="/toClazzView",method=GET)
	public String toClazzView(HttpServletRequest request,HttpServletResponse response) throws IOException{
		return "clazzList";
	}
	@RequestMapping(value="/clazzList",method=POST)
	public void clazzList(Authentication authentication ,HttpServletRequest request,HttpServletResponse response) throws IOException{
		SessionUser su=new SessionUser();
		Collection<? extends GrantedAuthority> c=authentication.getAuthorities();
		su.setRole(c.toString().substring(1, c.toString().length()-1));
		su.setUsername(authentication.getName());
		
		clazzService.getClazzList(request, response,su);
		
	}
	@RequestMapping(value="/deleClazz",method=POST)
	public void deleClazz(HttpServletRequest request,HttpServletResponse response) throws IOException{
		clazzService.deleteClazz(request, response);
		
	}
	@RequestMapping(value="/addClazz",method=POST)
	public void addClazz(HttpServletRequest request,HttpServletResponse response) throws IOException{
		clazzService.addClazz(request, response);
		
	}
	
	@RequestMapping(value="/editClazz",method=POST)
	public void editClazz(HttpServletRequest request,HttpServletResponse response) throws IOException{
		clazzService.editClazz(request, response);
		
	}
	
}
