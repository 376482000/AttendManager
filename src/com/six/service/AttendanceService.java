package com.six.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.six.json.SessionUser;

/**
* @author gede
* @version date：2019年7月1日 下午8:10:53
* @description ：
*/
public interface AttendanceService {
	public void attendanceList(HttpServletRequest request,HttpServletResponse response,SessionUser su) ;
	public void addAttendance(HttpServletRequest request,HttpServletResponse response);
	public void getStudentSelectedCourseList(HttpServletRequest request,HttpServletResponse response);
	public void deleteAttendance(HttpServletRequest request,HttpServletResponse response) throws IOException;
}
