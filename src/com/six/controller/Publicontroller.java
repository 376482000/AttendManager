package com.six.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.six.json.SessionUser;
import com.six.service.PublicService;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller

public class Publicontroller {
	private PublicService publicService;
	
	@Autowired
	public Publicontroller(PublicService publicService) {
		super();
		this.publicService = publicService;
	}

	@RequestMapping(value="SystemServlet",method=GET)
	public String redirect(HttpServletRequest request,HttpServletResponse reponse) 
			throws IOException{		
		//publicService.rediret(request, reponse);
		return "system";
	}
	
	/**
	 * 进入修改密码界面
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/SystemServlet/editPwd",method=GET)
	public String editPwd(HttpServletRequest request,HttpServletResponse reponse) 
			throws IOException{		
		//publicService.rediret(request, reponse);
		return "personalView";
	}
	
	/*
	 **
	 *修改密码
	 */
	@RequestMapping(value="/SystemServlet/editPasswod",method=POST)
	public String editPasswod(Authentication authentication ,HttpServletRequest request,HttpServletResponse reponse) 
			throws IOException{	
		SessionUser su=new SessionUser();
		String username=authentication.getName();
		Collection<? extends GrantedAuthority> c=authentication.getAuthorities();
		String role=c.toString().substring(1, c.toString().length()-1);
		su.setRole(role);
		su.setUsername(username);
		publicService.editPassword(request, reponse,username);
		return "personalView";
	}
	
	@RequestMapping(value="/SystemServlet/logout",method=GET)
	public String logout(HttpServletRequest request,HttpServletResponse reponse) 
			throws IOException{	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, reponse, auth);
		}
		return "redirect:/login?logout";
	}

}
