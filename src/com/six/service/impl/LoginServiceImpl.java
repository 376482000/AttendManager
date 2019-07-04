package com.six.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.six.dao.AdminDao;
import com.six.dao.ClazzDao;
import com.six.dao.StudentDao;
import com.six.dao.TeacherDao;
import com.six.model.Admin;
import com.six.model.Student;
import com.six.model.Teacher;

/**
* @author gede
* @version date：2019年6月26日 下午11:51:06
* @description ：
*/
@Component

public class LoginServiceImpl implements com.six.service.LoginService {
	private AdminDao adminDao;
	private TeacherDao teacherDao;
	private StudentDao studentDao;
	
	@Autowired
	public LoginServiceImpl(AdminDao adminDao, TeacherDao teacherDao, StudentDao studentDao) {
		super();
		this.adminDao = adminDao;
		this.teacherDao = teacherDao;
		this.studentDao = studentDao;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		System.out.println(username);
		
		Admin admin=adminDao.findByUsername(username);
		Teacher teacher=teacherDao.findByUsername(username);
		Student student=studentDao.findByUsername(username);
		if(admin!=null){
			List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(admin.getAuthority()));
			return new User(admin.getUsername(),admin.getPassword(),authorities);
		}else if(teacher!=null){
			List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(teacher.getAuthority()));
			return new User(teacher.getName(),teacher.getPassword(),authorities);
		}else if(student!=null){
			List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(student.getAuthority()));
			return new User(student.getName(),student.getPassword(),authorities);
		}else
			throw new UsernameNotFoundException("asad");
		
	}

}
