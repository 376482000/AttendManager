package com.six.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.six.json.SessionUser;
import com.six.model.Leave;

public interface LeaveService {

	public void getLeaveList(HttpServletRequest request, HttpServletResponse response, SessionUser su) throws IOException;

	public void addLeave(HttpServletRequest request, HttpServletResponse response) throws IOException;

	public void editLeave(HttpServletRequest request, HttpServletResponse response) throws IOException;

	public void deleteLeave(HttpServletRequest request, HttpServletResponse response) throws IOException;

	public void checkLeave(HttpServletRequest request,HttpServletResponse response) ;
}
