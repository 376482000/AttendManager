package com.six.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;

@Controller
public class HomeController {
	
	@RequestMapping(value="/",method=GET)
	public String home(Model model){
		//model.addAttribute("name", user.getUsername());
		return "system";
	}
}
