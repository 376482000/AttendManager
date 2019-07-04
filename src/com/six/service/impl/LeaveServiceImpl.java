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
import com.six.dao.LeaveDao;
import com.six.dao.SelectedCourseDao;
import com.six.dao.StudentDao;
import com.six.dao.TeacherDao;
import com.six.json.JsonLeave;
import com.six.json.SessionUser;
import com.six.model.Clazz;
import com.six.model.Leave;
import com.six.model.Page;
import com.six.model.SelectedCourse;
import com.six.model.Student;
import com.six.service.LeaveService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Component
public class LeaveServiceImpl implements LeaveService {
	private LeaveDao leaveDao;
	private CourseDao courseDao;
	private TeacherDao teacherDao;
	private StudentDao studentDao;
	private SelectedCourseDao  selectedCourseDao;
	

	@Autowired
	public LeaveServiceImpl(CourseDao courseDao, TeacherDao teacherDao, StudentDao studentDao,SelectedCourseDao selectedCourseDao,LeaveDao leaveDao) {
		super();
		this.courseDao = courseDao;
		this.teacherDao = teacherDao;
		this.studentDao = studentDao;
		this.leaveDao = leaveDao;
		this.selectedCourseDao = selectedCourseDao;
	}
	
	
	@Override	
	public void getLeaveList(HttpServletRequest request,HttpServletResponse response, SessionUser su) throws IOException{
		// TODO Auto-generated method stub
		int studentId = request.getParameter("studentid") == null ? 0 : Integer.parseInt(request.getParameter("studentid").toString());
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		
		Leave leave = new Leave();
		//获取当前登录用户类型
		Student student = new Student();
		if(su.getRole().equals("ROLE_STUDENT")){
			//如果是学生，只能查看自己的信息	
			student=studentDao.findByUsername(su.getUsername());
			leave.setStudent(student);;
		}else{
			student=studentDao.findById(studentId);
		}
		leave.setStudent(student);
		List<Leave> leaveList = leaveDao.getLeaveList(leave, new Page(currentPage, pageSize));
		int total = leaveDao.getLeaveListTotal();
		
		response.setCharacterEncoding("UTF-8");
		List<JsonLeave> lea=new ArrayList<JsonLeave>();
		for (Leave leave2 : leaveList) {
			JsonLeave jl=new JsonLeave();
			jl.setId(leave2.getId());
			jl.setInfo(leave2.getInfo());
			jl.setRemark(leave2.getRemark());
			jl.setStatus(leave2.getStatus());
			jl.setStudentId(leave2.getStudent().getId());
			lea.add(jl);
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", lea);
		
		
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(lea).toString());
			}else{
				
				response.getWriter().write(JSONObject.fromObject(ret ).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void addLeave(HttpServletRequest request,HttpServletResponse response) throws IOException{
		// TODO Auto-generated method stub
		int studentId = Integer.parseInt(request.getParameter("studentid"));
		String info = request.getParameter("info");
		Student student = studentDao.findById(studentId);
		Leave leave = new Leave();
		leave.setStudent(student);
		leave.setInfo(info);
		leave.setRemark("");
		leave.setStatus(0);
		
		String msg = "error";
		if(leaveDao.addLeave(leave)){
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
	public void editLeave(HttpServletRequest request,HttpServletResponse response)throws IOException {
		// TODO Auto-generated method stub
		int studentId = Integer.parseInt(request.getParameter("studentid"));
		int id = Integer.parseInt(request.getParameter("id"));
		String info = request.getParameter("info");
		Student student = studentDao.findById(studentId);
		Leave leave = new Leave();
		leave.setStudent(student);
		leave.setInfo(info);
		leave.setRemark("");
		leave.setStatus(JsonLeave.LEAVE_STATUS_WAIT);
		leave.setId(id);
		
		String msg = "error";
		if(leaveDao.editLeave(leave)){
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
	public void deleteLeave(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("id");		
		String idStr = "";
		for(String id : ids){
			idStr += id + ",";
		}
		idStr = idStr.substring(0, idStr.length()-1);
		String msg = "success";
		if(!leaveDao.deleteLeave(idStr)){
			msg = "error";
		}
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkLeave(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int studentId = Integer.parseInt(request.getParameter("studentid"));
		int id = Integer.parseInt(request.getParameter("id"));
		int status = Integer.parseInt(request.getParameter("status"));
		String info = request.getParameter("info");
		String remark = request.getParameter("remark");
		
		System.out.println(status);
		System.out.println(info);
		System.out.println(remark);
		Student student=studentDao.findById(studentId);
		Leave leave = new Leave();
		leave.setStudent(student);
		leave.setInfo(info);
		leave.setRemark(remark);
		leave.setStatus(status);
		leave.setId(id);
		String msg = "error";
		if(leaveDao.editLeave(leave)){
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
	





