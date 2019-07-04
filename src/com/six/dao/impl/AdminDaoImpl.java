package com.six.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.six.dao.AdminDao;
import com.six.dao.BaseDao;
import com.six.model.Admin;

@Component
public class AdminDaoImpl implements AdminDao  {
	private BaseDao<Admin> baseDao;
	
	@Autowired
	public AdminDaoImpl(BaseDao<Admin> baseDao) {
		super();
		this.baseDao = baseDao;
	}

	public void save(){
		Admin admin=new Admin("abc","abc",1,"ROLE_ADMIN");
		baseDao.save(admin);
	}
	
	@Override
	public Admin findByUsername(String username) {
		// TODO Auto-generated method stub
		String hql="from com.six.model.Admin where username=?";
		Admin admin=baseDao.get(hql,new Object[]{username});
		return admin;
	}

	@Override
	public Admin findIdByUsernamePasswd(String username, String password) {
		// TODO Auto-generated method stub
		String hql="from com.six.model.Admin where username=? and password=?";
		List<Object> lo=new ArrayList<Object>();
		lo.add(username);
		lo.add(password);
		Admin admin=baseDao.get(hql, lo);
		return admin;
		
	}

	@Override
	public String getPassword(int id) {
		// TODO Auto-generated method stub
		String hql="from com.six.model.Admin where id=?";
		Admin admin=baseDao.get(hql, new Object[]{id});
		return admin.getPassword();
	}

	@Override
	public boolean editPassword(Admin admin, String newPassword) {
		// TODO Auto-generated method stub
		String sql = "update com.six.model.Admin set password = '"+newPassword+"' where id = " + admin.getId();
		if(baseDao.executeHql(sql)!=0){
			return true;
		}else
			return false;
	}

}
