package com.six.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.six.json.SessionUser;

public interface CourseService {

	public void addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public void deleteCourse(HttpServletRequest request, HttpServletResponse response);
	public void editCourse(HttpServletRequest request, HttpServletResponse response);
	public void getCourseList(HttpServletRequest request, HttpServletResponse response, SessionUser su);

}
