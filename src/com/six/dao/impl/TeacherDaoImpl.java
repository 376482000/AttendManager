package com.six.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import com.six.dao.BaseDao;
import com.six.dao.TeacherDao;
import com.six.model.Admin;
import com.six.model.Page;
import com.six.model.Student;
import com.six.model.Teacher;
import com.six.util.StringUtil;

/**
* @author gede
* @version date：2019年6月26日 下午9:06:16
* @description ：
*/
@Repository
@Transactional
public class TeacherDaoImpl implements TeacherDao {
	private BaseDao<Teacher> baseDao;
	
	@Autowired
	public TeacherDaoImpl(BaseDao<Teacher> baseDao) {
		super();
		this.baseDao = baseDao;
	}

	public void save(){
		
//		Teacher admin=new Teacher(20, "1121", "test", "test", "男");
//		baseDao.save(admin);
	}
	
	@Override
	public Teacher findByUsername(String username) {
		// TODO Auto-generated method stub
		String hql="from com.six.model.Teacher where name=?";
		Teacher teacher=baseDao.get(hql,new Object[]{username});
		return teacher;
	}

	
	@Override
	public  boolean addTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		baseDao.save(teacher);
		return true;
	}

	@Override
	public boolean editTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		
		String hql = "update com.six.model.Teacher set name = '"+teacher.getName()+"',sex = '" + teacher.getSex() +"',mobile = '" + teacher.getMobile() + "',qq = '" + teacher.getQq() + "',clazz_id = '" + teacher.getClazz().getId()+"' where id = '" + teacher.getId()+"'";
		 baseDao.executeHql(hql);
		 return true;
	}

	@Override
	public Teacher getTeacherById(int id) {
		// TODO Auto-generated method stub
		String hql="from com.six.model.Teacher where id=?";
		Teacher teacher=baseDao.get(hql,new Object[]{id});
		return teacher;
	}

	@Override
	public List<Teacher> getTeacherList(Teacher teacher, Page page) {
		// TODO Auto-generated method stub
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "from com.six.model.Teacher where 1=?";
		if(!StringUtil.isEmpty(teacher.getName())){
			sql += "and name like '%" + teacher.getName() + "%'";
		}		
		if(teacher.getClazz()!=null){
			sql += " and clazz_id = " + teacher.getClazz().getId();
		}
		if(teacher.getId()!= 0){
			sql += " and id = " + teacher.getId();
		}
		ret = baseDao.find(sql, new Object[]{1}, page.getCurrentPage(), page.getPageSize());
		return ret;
	}
	
	@Override
	public int getTeacherListTotal(){
		int total = 0;
		String sql = "from com.six.model.Teacher where 1=1";
		List<Teacher> tl = baseDao.find(sql);
		total=tl.size();
		return total;
	}

	@Override
	public void deleteTeacher(String idStr) {
		// TODO Auto-generated method stub
		String sql = "delete from com.six.model.Teacher where id in("+idStr+")";
		baseDao.executeHql(sql);
	}

	@Override
	public Teacher findIdByUsernamePasswd(String username, String password) {
		// TODO Auto-generated method stub
		String hql="from com.six.model.Teacher where name=? and password=?";
		List<Object> lo=new ArrayList<Object>();
		lo.add(username);
		lo.add(password);
		Teacher teacher=baseDao.get(hql, lo);
		return teacher;
	}

	@Override
	public boolean editPassword(Teacher teacher, String newPassword) {
		// TODO Auto-generated method stub
		String sql = "update com.six.model.Teacher set password = '"+newPassword+"' where id = " + teacher.getId();
		if(baseDao.executeHql(sql)!=0){
			return true;
		}else
			return false;
	}
}
