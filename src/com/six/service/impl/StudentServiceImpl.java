package com.six.service.impl;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.six.dao.AttendanceDao;
import com.six.dao.ClazzDao;
import com.six.dao.LeaveDao;
import com.six.dao.SelectedCourseDao;
import com.six.dao.StudentDao;
import com.six.dao.TeacherDao;
import com.six.json.JsonStudent;
import com.six.json.SessionUser;
import com.six.model.Attendance;
import com.six.model.Clazz;
import com.six.model.Leave;
import com.six.model.Page;
import com.six.model.SelectedCourse;
import com.six.model.Student;
import com.six.model.Teacher;
import com.six.service.StudentService;
import com.six.util.SnGenerateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
* @author gede
* @version date：2019年6月27日 下午8:18:06
* @description ：
*/
@Component
public class StudentServiceImpl implements StudentService {

	private StudentDao studentDao;
	private ClazzDao clazzDao;
	private TeacherDao teacherDao;
	private SelectedCourseDao selectedCourseDao;
	private AttendanceDao attendanceDao;
	private LeaveDao leaveDao;
	@Autowired
	public StudentServiceImpl(LeaveDao leaveDao,StudentDao studentDao,ClazzDao clazzDao,TeacherDao teacherDao,SelectedCourseDao selectedCourseDao,AttendanceDao attendanceDao) {
		super();
		this.leaveDao=leaveDao;
		this.clazzDao =clazzDao;
		this.studentDao = studentDao;
		this.teacherDao = teacherDao;
		this.selectedCourseDao = selectedCourseDao;
		this.attendanceDao = attendanceDao;
	}

	@Override
	public void deleteStudent(HttpServletRequest request, HttpServletResponse response ) {
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("ids[]");
		String idStr = "";
		for(String id : ids){
			idStr += id + ",";
		}
		idStr = idStr.substring(0, idStr.length()-1);
		
		List<SelectedCourse> selectedCourse=selectedCourseDao.findSelectedCourseBystudentId(idStr);
		if(selectedCourse!=null){
			for (SelectedCourse selectedCourse2 : selectedCourse) {
				selectedCourseDao.deleteSelectedCourse(selectedCourse2.getId());
			}
		}
		
		List<Attendance> addendance=attendanceDao.findAttendanceBystudentId(idStr);
		if(addendance!=null){
			for (Attendance addendance2 : addendance) {
				attendanceDao.deleteAttendance(addendance2.getId());
			}
		}
		
		List<Leave> leave=leaveDao.findLeaveBystudentId(idStr);
		if(leave!=null){
			for (Leave leave2 : leave) {
				leaveDao.deleteLeaveById(leave2.getId());
			}
		}
		
		try {
			if (studentDao.deleteStudent(idStr)) {
				response.getWriter().write("success");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void editStudent(HttpServletRequest request, HttpServletResponse response)  {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		String sex = request.getParameter("sex");
		String mobile = request.getParameter("mobile");
		String qq = request.getParameter("qq");
		int clazzId = Integer.parseInt(request.getParameter("clazzid"));
		Clazz clazz=clazzDao.findById(clazzId);
		Student student = new Student();
		student.setClazz(clazz);
		student.setMobile(mobile);
		student.setName(name);
		student.setId(id);
		student.setQq(qq);
		student.setSex(sex);
		if(studentDao.editStudent(student)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	@Override
	public void getStudentList(HttpServletRequest request, HttpServletResponse response,SessionUser su) {
		// TODO Auto-generated method stub
		
		String name = request.getParameter("studentName");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		int cn = request.getParameter("clazzid") == null ? 0 : Integer.parseInt(request.getParameter("clazzid"));
		Clazz clazz=clazzDao.findById(cn);	
		
		Student student = new Student();
		student.setId(0);
		if(su.getRole().equals("ROLE_STUDENT")){
			
			Student s=studentDao.findByUsername(su.getUsername());
			student.setId(s.getId());
		}else if(su.getRole().equals("ROLE_TEACHER")){
			Teacher teacher = new Teacher();
			teacher.setId(0);
			Teacher s = teacherDao.findByUsername(su.getUsername());
			teacher.setId(s.getId());
		}
		if(clazz!=null){
			student.setClazz(clazz);
		}else 
			student.setClazz(null);
		
		student.setName(name);
		
		List<Student> studentList = studentDao.getStudentList(student, new Page(currentPage, pageSize));
		for (Student student2 : studentList) {
			student2.getName();
		}
		int total = studentDao.getStudentListTotal();
		response.setCharacterEncoding("UTF-8");
		List<JsonStudent> tea=new ArrayList<JsonStudent>();
		for (Student student2 : studentList) {
			JsonStudent t=new JsonStudent();
			t.setClazzId(student2.getClazz().getId());
			t.setAuthority(student2.getAuthority());
			t.setEnabled(student2.getEnabled());
			t.setId(student2.getId());
			t.setName(student2.getName());
			t.setMobile(student2.getMobile());
			t.setQq(student2.getQq());			
			t.setPassword(student2.getPassword());
			t.setSex(student2.getSex());
			t.setSn(student2.getSn());
			tea.add(t);
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", tea);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				JsonConfig jsonConfig = new JsonConfig();  //建立配置文件
		        jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
		        jsonConfig.setExcludes(new String[]{"clazz"});
				response.getWriter().write(JSONArray.fromObject(tea,jsonConfig).toString());
			}else{
				JsonConfig jsonConfig = new JsonConfig();  //建立配置文件
		         jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认,jsonConfig忽略
		         jsonConfig.setExcludes(new String[]{"clazz"});
				response.getWriter().write(JSONObject.fromObject(ret,jsonConfig).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Override
	public void addStudent(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		System.out.println(sex);
		String mobile = request.getParameter("mobile");
		String qq = request.getParameter("qq");
		int clazzId = Integer.parseInt(request.getParameter("clazzid"));
		Clazz c=clazzDao.findById(clazzId);
		
		Student student = new Student();
		student.setClazz(c);
		student.setMobile(mobile);
		student.setName(name);
		student.setPassword(password);
		student.setQq(qq);
		student.setSex(sex);
		student.setSn(SnGenerateUtil.generateTeacherSn(20));
		if(studentDao.addStudent(student)){
			try {
				response.getWriter().write("success");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}

	}

}

