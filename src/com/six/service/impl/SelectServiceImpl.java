package com.six.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.six.dao.*;
import com.six.service.SelectService;

/**
* @author gede
* @version date：2019年6月26日 下午6:10:04
* @description ：
*/
@Component
public class SelectServiceImpl implements SelectService {
	private AdminDao adminDao;
	private TeacherDao teacherDao;
	private ClazzDao clazzDao;
	private StudentDao studentDao;
	
	@Autowired
	public SelectServiceImpl(AdminDao adminDao, TeacherDao teacherDao, ClazzDao clazzDao, StudentDao studentDao) {
		super();
		this.adminDao = adminDao;
		this.teacherDao = teacherDao;
		this.clazzDao = clazzDao;
		this.studentDao = studentDao;
	}


	@Override
	public boolean select() {
		// TODO Auto-generated method stub
		System.out.println("开始查询");
//		adminDao.save();
//		teacherDao.save();
		clazzDao.save();
//		studentDao.save();
		studentDao.findByUsername(null);
		teacherDao.findByUsername(null);
		adminDao.findByUsername("admin");
		clazzDao.findByUsername(null);
		return false;
	}
	
}
