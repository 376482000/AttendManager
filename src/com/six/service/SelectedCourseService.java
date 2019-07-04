package com.six.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.six.json.SessionUser;

/**
* @author gede
* @version date：2019年7月1日 下午9:03:25
* @description ：
*/
public interface SelectedCourseService {
	public void deleteSelectedCourse(HttpServletRequest request,
			HttpServletResponse response) throws IOException;
	public void addSelectedCourse(HttpServletRequest request,
			HttpServletResponse response) throws IOException;
	public void getSelectedCourseList(HttpServletRequest request,
			HttpServletResponse response,SessionUser su);
	
}
