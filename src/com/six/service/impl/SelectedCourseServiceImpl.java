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

import com.six.dao.CourseDao;
import com.six.dao.SelectedCourseDao;
import com.six.dao.StudentDao;
import com.six.json.JsonSelectedCourse;
import com.six.json.JsonTeacher;
import com.six.json.SessionUser;
import com.six.model.Page;
import com.six.model.SelectedCourse;
import com.six.model.Student;
import com.six.service.SelectedCourseService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
* @author gede
* @version date：2019年7月1日 下午9:05:53
* @description ：
*/
@Component
public class SelectedCourseServiceImpl implements SelectedCourseService {
	private SelectedCourseDao selectedCourseDao;
	private StudentDao studentDao;
	private CourseDao courseDao;
	
	@Autowired
	public SelectedCourseServiceImpl( CourseDao courseDao,SelectedCourseDao selectedCourseDao, StudentDao studentDao) {
		super();
		this.courseDao =courseDao;
		this.selectedCourseDao = selectedCourseDao;
		this.studentDao = studentDao;
	}

	@Override
	public void deleteSelectedCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		SelectedCourse selectedCourse = selectedCourseDao.getSelectedCourse(id);
		String msg = "success";
		if(selectedCourse == null){
			msg = "not found";
			response.getWriter().write(msg);
			return;
		}
		if(selectedCourseDao.deleteSelectedCourse(selectedCourse.getId())){
			courseDao.updateCourseSelectedNum(selectedCourse.getCourse().getId(), -1);
		}else{
			msg = "error";
		}
		response.getWriter().write(msg);
	}

	@Override
	public void addSelectedCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int studentId = request.getParameter("studentid") == null ? 0 : Integer.parseInt(request.getParameter("studentid").toString());
		int courseId = request.getParameter("courseid") == null ? 0 : Integer.parseInt(request.getParameter("courseid").toString());
		String msg = "success";
		if(courseDao.isFull(courseId)){
			msg = "courseFull";
			response.getWriter().write(msg);
			return;
		}
		
		if(selectedCourseDao.isSelected(studentId, courseId)){
			msg = "courseSelected";
			response.getWriter().write(msg);
			return;
		}
		SelectedCourse selectedCourse = new SelectedCourse();
		selectedCourse.setStudent(studentDao.findById(studentId));
		selectedCourse.setCourse(courseDao.findByid(courseId));
		if(selectedCourseDao.addSelectedCourse(selectedCourse)){
			msg = "success";
		}
		courseDao.updateCourseSelectedNum(courseId, 1);
		response.getWriter().write(msg);
	}

	@Override
	public void getSelectedCourseList(HttpServletRequest request, HttpServletResponse response,SessionUser su) {
		// TODO Auto-generated method stub
		int studentId = request.getParameter("studentid") == null ? 0 : Integer.parseInt(request.getParameter("studentid").toString());
		int courseId = request.getParameter("courseid") == null ? 0 : Integer.parseInt(request.getParameter("courseid").toString());
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		SelectedCourse selectedCourse = new SelectedCourse();
		System.out.println("此处"+studentId);
		Student student=null;
		if(su.getRole().equals("ROLE_STUDENT")){
			 student=studentDao.findByUsername(su.getUsername());
		}else 
			student=studentDao.findById(studentId);
		
		selectedCourse.setCourse(courseDao.findByid(courseId));
		selectedCourse.setStudent(student);
		List<SelectedCourse> courseList = selectedCourseDao.getSelectedCourseList(selectedCourse, new Page(currentPage, pageSize));
		int total = selectedCourseDao.getSelectedCourseListTotal();
		List<JsonSelectedCourse> jsc=new ArrayList<JsonSelectedCourse>();
		for (SelectedCourse selectedCourse2 : courseList) {
			JsonSelectedCourse sc=new JsonSelectedCourse();
			sc.setCourseId(selectedCourse2.getCourse().getId());
			sc.setId(selectedCourse2.getId());
			sc.setStudentId(selectedCourse2.getStudent().getId());
			jsc.add(sc);
		}
		
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", jsc);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				JsonConfig jsonConfig = new JsonConfig();  //建立配置文件
		        jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
		        jsonConfig.setExcludes(new String[]{"clazz"});
				response.getWriter().write(JSONArray.fromObject(jsc,jsonConfig).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
