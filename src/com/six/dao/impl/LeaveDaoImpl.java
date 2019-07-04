package com.six.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.six.dao.BaseDao;
import com.six.dao.LeaveDao;
import com.six.model.Clazz;
import com.six.model.Leave;
import com.six.model.Page;
import com.six.model.SelectedCourse;
import com.six.model.Student;
import com.six.model.Teacher;
@Component
public class LeaveDaoImpl implements LeaveDao{
	private BaseDao<Leave> baseDao;
	
	@Autowired
	public LeaveDaoImpl(BaseDao<Leave> baseDao) {
		super();
		this.baseDao = baseDao;
	}
	

	@Override
	public List<Leave> getLeaveList(Leave leave, Page page) {
		// TODO Auto-generated method stub
		List<Leave> ret = new ArrayList<Leave>();
		String sql = "from com.six.model.Leave where 1=?";
		if(leave.getStudent()!=null){
			sql += " and student_id = " + leave.getStudent().getId();
		}
		ret = baseDao.find(sql, new Object[]{1}, page.getCurrentPage(), page.getPageSize());
		return ret;
		
	}

	@Override
	public int getLeaveListTotal() {
		// TODO Auto-generated method stub
		int total = 0;
		String sql = "from com.six.model.Leave where 1=1";
		List<Leave> sl = baseDao.find(sql);
		total=sl.size();
		return total;
	}


	@Override
	public boolean addLeave(Leave leave) {
		// TODO Auto-generated method stub
		baseDao.save(leave);
		return true;
	}


	@Override
	public boolean editLeave(Leave leave) {
		// TODO Auto-generated method stub
		String sql = "update com.six.model.Leave set id = "+leave.getStudent().getId()+", info = '"+leave.getInfo()+"',status = "+leave.getStatus()+",remark = '"+leave.getRemark()+"' where id = " + leave.getId();
		baseDao.executeHql(sql);
		 return true;
	}


	@Override
	public boolean deleteLeave(String ids) {
		// TODO Auto-generated method stub
		String sql = "delete com.six.model.Leave where id in("+ids+")";
		baseDao.executeHql(sql);
		return true;
	}


	@Override
	public List<Leave> findLeaveBystudentId(String idStr) {
		// TODO Auto-generated method stub
		List<Leave> ret = null;
		String sql = "from com.six.model.Leave where student_id =?" ;
		ret =  baseDao.find(sql,new Object[]{idStr});
		return ret;
	}


	@Override
	public void deleteLeaveById(Integer id) {
		// TODO Auto-generated method stub
		String sql = "delete com.six.model.Leave where id ="+id;
		baseDao.executeHql(sql);
	}


}
