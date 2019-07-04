package com.six.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.six.dao.BaseDao;
import com.six.dao.StudentDao;
import com.six.model.Admin;
import com.six.model.Clazz;
import com.six.model.Page;
import com.six.model.Student;
import com.six.model.Teacher;
import com.six.util.StringUtil;

/**
* @author gede
* @version date：2019年6月26日 下午9:05:56
* @description ：
*/
@Component
public class StudentDaoImpl implements StudentDao {
	
	private BaseDao<Student> baseDao;
	
	@Autowired
	public StudentDaoImpl(BaseDao<Student> baseDao) {
		super();
		this.baseDao = baseDao;
	}

	/*
	 * 按名字查询
	 * @see com.six.dao.StudentDao#findByUsername(java.lang.String)
	 */
	@Override
	public Student findByUsername(String username) {
		// TODO Auto-generated method stub
		String hql="from com.six.model.Student where name=?";
		Student student=baseDao.get(hql,new Object[]{username});
		return student;
	}
	
	
	@Override
	public Student findById(int id) {
		// TODO Auto-generated method stub
		String hql="from com.six.model.Student where id=?";
		Student student=baseDao.get(hql,new Object[]{id});
		return student;
	}

	@Override
	public boolean deleteStudent(String ids) {
		// TODO Auto-generated method stub
		String sql = "delete com.six.model.Student where id in("+ids+")";
		baseDao.executeHql(sql);
		return true;
	}
	
	/*
	 * 分页查询
	 * @see com.six.dao.StudentDao#getStudentList(com.six.model.Student, com.six.model.Page)
	 */
	@Override
	public List<Student> getStudentList(Student student, Page page) {
		List<Student> ret = new ArrayList<Student>();
		String sql = "from com.six.model.Student where 1=?";
		if(!StringUtil.isEmpty(student.getName())){
			sql += "and name like '%" + student.getName() + "%'";
		}
		if(student.getClazz()!=null){
			sql += " and clazz_id = " + student.getClazz().getId();
		}
		if(student.getId() != 0){
			sql += " and id = " + student.getId();
		}
		ret = baseDao.find(sql, new Object[]{1}, page.getCurrentPage(), page.getPageSize());
		return ret;
	}
	
	/*
	 * 获取查询总数
	 */
	@Override
	public int getStudentListTotal(){
		int total = 0;
		String sql = "from com.six.model.Student where 1=1";
		List<Student> sl = baseDao.find(sql);
		total=sl.size();
		return total;
	}

	@Override
	public Student findIdByUsernamePasswd(String username, String password) {
		// TODO Auto-generated method stub
		String hql="from com.six.model.Student where name=? and password=?";
		List<Object> lo=new ArrayList<Object>();
		lo.add(username);
		lo.add(password);
		Student student=baseDao.get(hql, lo);
		return student;
	}

	@Override
	public boolean editPassword(Student student, String newPassword) {
		// TODO Auto-generated method stub
		String sql = "update com.six.model.Student set password = '"+newPassword+"' where id = " + student.getId();if(baseDao.executeHql(sql)!=0){
			return true;
		}else
			return false;
	}

	@Override
	public boolean addStudent(Student student) {
		// TODO Auto-generated method stub
		baseDao.save(student);
		return true;
		
	}

	@Override
	public boolean editStudent(Student student) {
		// TODO Auto-generated method stub
		String sql = "update com.six.model.Student set name = '"+student.getName()+"'";
		sql += ",sex = '" + student.getSex() + "'";
		sql += ",mobile = '" + student.getMobile() + "'";
		sql += ",qq = '" + student.getQq() + "'";
		sql += ",clazz_id = " + student.getClazz().getId();
		sql += " where id = " + student.getId();
		baseDao.executeHql(sql);
		return true;
	}



}
