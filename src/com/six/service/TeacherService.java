package com.six.service;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.six.json.SessionUser;
/**
* @author gede
* @version date：2019年6月29日 下午1:19:12
* @description ：
*/
public interface TeacherService {

	public void getTeacherList(HttpServletRequest request,HttpServletResponse response,SessionUser su);
	public void addTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public void deleteTeacher(HttpServletRequest request, HttpServletResponse response);
	public void editTeacher(HttpServletRequest request, HttpServletResponse response);
}
