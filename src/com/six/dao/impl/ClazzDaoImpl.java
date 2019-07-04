package com.six.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.six.dao.BaseDao;
import com.six.dao.ClazzDao;
import com.six.dao.StudentDao;
import com.six.dao.TeacherDao;
import com.six.model.Admin;
import com.six.model.Clazz;
import com.six.model.Page;
import com.six.model.Student;
import com.six.model.Teacher;
import com.six.util.StringUtil;

/**
* @author gede
* @version date：2019年6月26日 下午9:05:34
* @description ：
*/
@Component
public class ClazzDaoImpl implements ClazzDao {

	private BaseDao baseDao;
	private TeacherDao teacherDao;
	private StudentDao studentDao;
	
	@Autowired
	public ClazzDaoImpl(BaseDao baseDao,TeacherDao teacherDao,StudentDao studentDao) {
		super();
		this.baseDao = baseDao;
		this.studentDao = studentDao;
		this.teacherDao = teacherDao;
	}

	public void save(){
		Clazz admin=new Clazz("new 123");
		baseDao.save(admin);
	}
	
	/**
	 * 通过班级名称查询班级
	 */
	@Override
	public Clazz findByUsername(String username) {
		// TODO Auto-generated method stub
		String hql="from com.six.model.Clazz";
		List<Clazz> ad=baseDao.find(hql);
		for (Clazz admin : ad) {
			System.out.println("id :"+admin.getId()+"info :"+admin.getInfo());
		}
		return null;
	}
	
	/**
	 * 对班级进行分页查询
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Clazz> getClazzList(Clazz clazz,Page page){
		List<Clazz> ret = new ArrayList<Clazz>();
		String sql = "from com.six.model.Clazz where 1=?";
		if(!StringUtil.isEmpty(clazz.getName())){
			sql += "and name like '%" + clazz.getName() + "%'";
		}
		
		ret = baseDao.find(sql, new Object[]{1}, page.getCurrentPage(), page.getPageSize());
		return ret;
	}

	/**
	 * 根据班级id进行查询
	 */
	@Override
	public Clazz findById(int id) {
		// TODO Auto-generated method stub
		String hql="from com.six.model.Clazz where id=?";
		Clazz clazz= (Clazz) baseDao.get(hql,new Object[]{id});
		return clazz;
	}
	
	//通过ID找clazz
	
	
	
	/*
	 * 获取查询总数
	 */
	@Override
	public int getClazzListTotal(){
		int total = 0;
		String sql = "from com.six.model.Clazz where 1=1";
		List<Clazz> sl = baseDao.find(sql);
		total=sl.size();
		return total;
	}

	@Override
	public boolean deleteClazz(String idStr) {
		// TODO Auto-generated method stub
		String sql = "delete from com.six.model.Clazz where id in("+idStr+")";
		baseDao.executeHql(sql);
		return true;
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean addClazz(Clazz clazz) {
		// TODO Auto-generated method stub
		baseDao.save(clazz);
		return true;
	}

	@Override
	public boolean editClazz(Clazz clazz) {
		// TODO Auto-generated method stub
		String sql = "update com.six.model.Clazz set name = '"+clazz.getName()+"',info = '"+clazz.getInfo()+"' where id = " + clazz.getId();
		baseDao.executeHql(sql);
		return true;
	}


}
