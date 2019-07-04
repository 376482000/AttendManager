package com.six.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.six.json.SessionUser;

public interface StudentService {
	public void deleteStudent(HttpServletRequest request,HttpServletResponse response);
	public void editStudent(HttpServletRequest request,HttpServletResponse response);
	public void getStudentList(HttpServletRequest request,HttpServletResponse response,SessionUser su);
	public void addStudent(HttpServletRequest request,HttpServletResponse response) throws IOException;

}
