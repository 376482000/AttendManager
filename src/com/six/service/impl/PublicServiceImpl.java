package com.six.service.impl;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.six.dao.AdminDao;
import com.six.dao.StudentDao;
import com.six.dao.TeacherDao;
import com.six.model.Admin;
import com.six.model.Student;
import com.six.model.Teacher;
import com.six.service.PublicService;

@Component
public class PublicServiceImpl implements PublicService {
	private AdminDao adminDao;
	private StudentDao studentDao;
	private TeacherDao teahcerDao;
		
	@Autowired
	public PublicServiceImpl(AdminDao adminDao, StudentDao studentDao, TeacherDao teahcerDao) {
		super();
		this.adminDao = adminDao;
		this.studentDao = studentDao;
		this.teahcerDao = teahcerDao;
	}
	
	@Override
	public void editPassword(HttpServletRequest request, HttpServletResponse response,String username ) {
		// TODO Auto-generated method stub
		
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newpassword");
		response.setCharacterEncoding("UTF-8");
		Admin admin=adminDao.findIdByUsernamePasswd(username, password);
		Teacher teacher=teahcerDao.findIdByUsernamePasswd(username, password);
		Student student=studentDao.findIdByUsernamePasswd(username, password);
		if(admin!=null){
			if(!admin.getPassword().equals(password)){
				try {
					response.getWriter().write("success");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			adminDao.editPassword(admin, newPassword);
			
		}else if(teacher!=null){
			if(!teacher.getPassword().equals(password)){
				try {
					response.getWriter().write("原密码错误！");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			teahcerDao.editPassword(teacher, newPassword);

		}else if(student!=null){
			if(!student.getPassword().equals(password)){
				try {
					response.getWriter().write("原密码错误！");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			studentDao.editPassword(student, newPassword);
			
		}
	}
}
