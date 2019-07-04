package com.six.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.six.dao.ClazzDao;
import com.six.dao.CourseDao;
import com.six.dao.SelectedCourseDao;
import com.six.dao.StudentDao;
import com.six.dao.TeacherDao;
import com.six.json.JsonCourse;
import com.six.json.JsonTeacher;
import com.six.json.SessionUser;
import com.six.model.Clazz;
import com.six.model.Course;
import com.six.model.Page;
import com.six.model.SelectedCourse;
import com.six.model.Teacher;
import com.six.service.CourseService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Component
public class CourseServiceImpl implements CourseService {
	private CourseDao courseDao;
	private TeacherDao teacherDao;
	private StudentDao studentDao;
	private SelectedCourseDao  selectedCourseDao;
	

	@Autowired
	public CourseServiceImpl(CourseDao courseDao, TeacherDao teacherDao, StudentDao studentDao,
			SelectedCourseDao selectedCourseDao) {
		super();
		this.courseDao = courseDao;
		this.teacherDao = teacherDao;
		this.studentDao = studentDao;
		this.selectedCourseDao = selectedCourseDao;
	}
	@Override
	public void addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		System.out.println("123");
		int teacherId = Integer.parseInt(request.getParameter("teacherid").toString());
		
		int maxNum = Integer.parseInt(request.getParameter("maxnum").toString());
		String courseDate = request.getParameter("course_date");
		String info = request.getParameter("info");
		
		Course course = new Course();
		course.setName(name);
		Teacher teacher = teacherDao.getTeacherById(teacherId);
		course.setTeacher(teacher);
		course.setSelectedNum(0);	
		course.setInfo(info);
		course.setMaxNum(maxNum);
		course.setCourseDate(courseDate);
	
		String msg = "error";
		if(courseDao.addCourse(course)){
			msg = "success";
		}
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteCourse(HttpServletRequest request,HttpServletResponse response) {
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("ids[]");
		String idStr = "";
		for(String id : ids){
			idStr += id + ",";
		}
		idStr = idStr.substring(0, idStr.length()-1);
		List<SelectedCourse> selectedCourse=selectedCourseDao.findSelectedCourseByCourseId(idStr);
		if(selectedCourse!=null){
			for (SelectedCourse selectedCourse2 : selectedCourse) {
				selectedCourseDao.deleteSelectedCourse(selectedCourse2.getId());
			}
		}
		
		if(courseDao.deleteCourse(idStr)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	@SuppressWarnings("null")
	@Override
	public void getCourseList(HttpServletRequest request,HttpServletResponse response, SessionUser su) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int teacherId = request.getParameter("teacherid") == null ? 0 : Integer.parseInt(request.getParameter("teacherid").toString());
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Course course = new Course();	
	
		Teacher teacher = teacherDao.getTeacherById(teacherId);		
		course.setTeacher(teacher);
		if(teacher!=null){
			course.setTeacher(teacher);
		}else 
			course.setTeacher(null);;
		
		course.setName(name);
		
		
		
		
		List<Course> courseList = courseDao.getCourseList(course, new Page(currentPage, pageSize));
		for (Course course2 : courseList) {
			course2.getName();
		}
		int total = courseDao.getCourseListTotal();	
		response.setCharacterEncoding("UTF-8");
		
		List<JsonCourse> crs=new ArrayList<JsonCourse>();
		for (Course course2 : courseList) {
			JsonCourse t=new JsonCourse();
			
			
			t.setCourseDate(course2.getCourseDate());
			t.setId(course2.getId());
			t.setInfo(course2.getInfo());
			t.setMaxNum(course2.getMaxNum());
			t.setName(course2.getName());
			
			t.setSelectedNum(course2.getSelectedNum());
			t.setTeacherId(course2.getTeacher().getId());
			crs.add(t);
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", crs);
		
		
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				JsonConfig jsonConfig = new JsonConfig();  //建立配置文件
		        jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
		        jsonConfig.setExcludes(new String[]{"teacher"});
				response.getWriter().write(JSONArray.fromObject(crs,jsonConfig).toString());
			}else{
				JsonConfig jsonConfig = new JsonConfig();  //建立配置文件
		         jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认,jsonConfig忽略
		         jsonConfig.setExcludes(new String[]{"teacher"});
				response.getWriter().write(JSONObject.fromObject(ret,jsonConfig).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void editCourse(HttpServletRequest request,HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int teacherId = Integer.parseInt(request.getParameter("teacherid").toString());
		int maxNum = Integer.parseInt(request.getParameter("maxnum").toString());
		int id = Integer.parseInt(request.getParameter("id").toString());
		String courseDate = request.getParameter("courseDate");
		String info = request.getParameter("info");
		Course course = new Course();
		Teacher teacher = teacherDao.getTeacherById(teacherId);
		course.setTeacher(teacher);
		course.setId(id);
		course.setName(name);
		//course.setTeacherId(teacher);
		course.setInfo(info);
		course.setCourseDate(courseDate);
		course.setMaxNum(maxNum);
		
		String msg = "error";
		if(courseDao.editCourse(course)){
			msg = "success";
		}
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
