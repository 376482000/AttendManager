package com.six.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.six.json.SessionUser;

public interface ClazzService {
	public void editClazz(HttpServletRequest request,HttpServletResponse response);
	public void deleteClazz(HttpServletRequest request,HttpServletResponse response) ;
	public void addClazz(HttpServletRequest request,HttpServletResponse response);
	public void getClazzList(HttpServletRequest request, HttpServletResponse response, SessionUser su);

}
