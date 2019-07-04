package com.six.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.six.dao.AttendanceDao;
import com.six.dao.ClazzDao;
import com.six.dao.CourseDao;
import com.six.dao.SelectedCourseDao;
import com.six.dao.StudentDao;
import com.six.json.JsonAttendance;
import com.six.json.JsonCourse;
import com.six.json.SessionUser;
import com.six.model.Attendance;
import com.six.model.Course;
import com.six.model.Page;
import com.six.model.SelectedCourse;
import com.six.model.Student;
import com.six.service.AttendanceService;
import com.six.util.DateFormatUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author gede
* @version date：2019年7月1日 下午8:15:47
* @description ：
*/
@Component
public class AttendanceServiceImpl implements  AttendanceService  {
	
	private ClazzDao clazzDao;
	private StudentDao studentDao;
	private AttendanceDao attendanceDao;
	private SelectedCourseDao selectedCourseDao;
	private CourseDao courseDao;
	
	@Autowired
	public AttendanceServiceImpl(ClazzDao clazzDao, StudentDao studentDao, AttendanceDao attendanceDao,
			SelectedCourseDao selectedCourseDao, CourseDao courseDao) {
		super();
		this.clazzDao = clazzDao;
		this.studentDao = studentDao;
		this.attendanceDao = attendanceDao;
		this.selectedCourseDao = selectedCourseDao;
		this.courseDao = courseDao;
	}
	
	@Override
	public void attendanceList(HttpServletRequest request, HttpServletResponse response,SessionUser su) {
		// TODO Auto-generated method stub
		int studentId = request.getParameter("studentid") == null ? 0 : Integer.parseInt(request.getParameter("studentid").toString());
		int courseId = request.getParameter("courseid") == null ? 0 : Integer.parseInt(request.getParameter("courseid").toString());
		String type = request.getParameter("type");
		String date = request.getParameter("date");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Attendance attendance = new Attendance();
		Course course=courseDao.findByid(courseId);
		Student student=null;
		if(su.getRole().equals("ROLE_STUDENT")){
			 student=studentDao.findByUsername(su.getUsername());
			
		}else
			student=studentDao.findById(studentId);
		attendance.setCourse(course);
		attendance.setStudent(student);
		attendance.setDate(date);
		attendance.setType(type);
		List<Attendance> attendanceList = attendanceDao.getSelectedCourseList(attendance, new Page(currentPage, pageSize));
		int total = attendanceDao.getAttendanceListTotal();
		
		List<JsonAttendance> la=new ArrayList<JsonAttendance>();
		for (Attendance attendance2 : attendanceList) {
			JsonAttendance ja=new JsonAttendance();
			ja.setCourseId(attendance2.getCourse().getId());
			ja.setDate(attendance2.getDate());
			ja.setId(attendance2.getId());
			ja.setStudentId(attendance2.getStudent().getId());
			ja.setType(attendance2.getType());
			la.add(ja);
		}
		
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", la);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				
				response.getWriter().write(JSONArray.fromObject(la).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addAttendance(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int studentId = request.getParameter("studentid") == null ? 0 : Integer.parseInt(request.getParameter("studentid").toString());
		int courseId = request.getParameter("courseid") == null ? 0 : Integer.parseInt(request.getParameter("courseid").toString());
		String type = request.getParameter("type").toString();
		Course course=courseDao.findByid(courseId);
		Student student=studentDao.findById(studentId);
		
		Attendance attendance = new Attendance();
		attendance.setCourse(course);
		attendance.setStudent(student);
		attendance.setType(type);
		attendance.setDate(DateFormatUtil.getFormatDate(new Date(), "yyyy-MM-dd"));
		String msg = "success";
		response.setCharacterEncoding("UTF-8");
		if(attendanceDao.isAttendanced(studentId, courseId, type,DateFormatUtil.getFormatDate(new Date(), "yyyy-MM-dd"))){
			msg = "已签到，请勿重复签到！";
		}else if(!attendanceDao.addAttendance(attendance)){
			msg = "系统内部出错。请联系管理员！";
		}
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void getStudentSelectedCourseList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int studentId = request.getParameter("studentid") == null ? 0 : Integer.parseInt(request.getParameter("studentid").toString());
		Student student=studentDao.findById(studentId);
		SelectedCourse selectedCourse = new SelectedCourse();
		selectedCourse.setStudent(student);
		List<SelectedCourse> selectedCourseList = selectedCourseDao.getSelectedCourseList(selectedCourse, new Page(1, 999));
		String courseId = "";
		if(selectedCourseList!=null){
			for(SelectedCourse sc : selectedCourseList){
				courseId += sc.getCourse().getId()+ ",";
			}
			courseId = courseId.substring(0,courseId.length()-1);
		}
		
	
		List<Course> courseList = courseDao.getCourse(courseId);
		List<JsonCourse> jc=new ArrayList<JsonCourse>();
		for (Course course : courseList) {
			JsonCourse c=new JsonCourse();
			c.setCourseDate(course.getCourseDate());
			c.setId(course.getId());
			c.setInfo(course.getInfo());
			c.setMaxNum(course.getMaxNum());
			c.setName(course.getName());
			c.setSelectedNum(course.getSelectedNum());
			c.setTeacherId(course.getTeacher().getId());
			jc.add(c);
		}
		
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(JSONArray.fromObject(jc).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAttendance(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		String msg = "success";
		if(!attendanceDao.deleteAttendance(id)){
			msg = "error";
		}
		response.getWriter().write(msg);
	}

}
