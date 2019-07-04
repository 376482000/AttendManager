package com.six.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.six.dao.AttendanceDao;
import com.six.dao.BaseDao;
import com.six.model.Admin;
import com.six.model.Attendance;
import com.six.model.Page;
import com.six.model.SelectedCourse;
import com.six.model.Teacher;
import com.six.util.StringUtil;

/**
* @author gede
* @version date：2019年7月1日 下午8:30:22
* @description ：
*/
@Component
public class AttendanceDaoImpl implements AttendanceDao {
	private BaseDao baseDao;
	
	@Autowired
	public AttendanceDaoImpl(BaseDao baseDao) {
		super();
		this.baseDao = baseDao;
	}
	
	@Override
	public boolean addAttendance(Attendance attendance) {
		// TODO Auto-generated method stub
		baseDao.save(attendance);
		return true;
	}

	@Override
	public boolean isAttendanced(int studentId, int courseId, String type, String date) {
		// TODO Auto-generated method stub
		boolean ret = false;
		String sql = "from com.six.model.Attendance where student_id = " + studentId + " and course_id = " + courseId + " and type = '" + type + "' and date = '" + date + "'";
	
		if (baseDao.find(sql).size()>=1) {
			return true;
		}
		return ret;
	}

	/**
	 * 获取指定的考勤信息列表
	 * @param attendace
	 * @param page
	 * @return
	 */
	@Override
	public List<Attendance> getSelectedCourseList(Attendance attendace, Page page) {
		// TODO Auto-generated method stub
		List<Attendance> ret = new ArrayList<Attendance>();
		String sql = "from com.six.model.Attendance where 1=?";
		
		if(attendace.getStudent() != null){
			sql += " and student_id = " + attendace.getStudent().getId();
		}
		if(attendace.getCourse() != null){
			sql += " and course_id = " + attendace.getCourse().getId();
		}
		if(!StringUtil.isEmpty(attendace.getType())){
			sql += " and type = '" + attendace.getType() + "'";
		}
		if(!StringUtil.isEmpty(attendace.getDate())){
			sql += " and date = '" + attendace.getDate() + "'";
		}
		ret = baseDao.find(sql, new Object[]{1}, page.getCurrentPage(), page.getPageSize());
		return ret;
		
	}

	@Override
	public int getAttendanceListTotal() {
		// TODO Auto-generated method stub
		int total = 0;
		String sql = "from com.six.model.Attendance where 1=1";
		List<Teacher> tl = baseDao.find(sql);
		total=tl.size();
		return total;
	}

	@Override
	public boolean deleteAttendance(int id) {
		// TODO Auto-generated method stub
		String sql = "delete from com.six.model.Attendance where id = " + id;
		baseDao.executeHql(sql);
		return true;
	}

	@Override
	public List<Attendance> findAttendanceBystudentId(String idStr) {
		// TODO Auto-generated method stub
		List<Attendance> ret = null;
		String sql = "from com.six.model.Attendance where student_id =?" ;
		ret =  baseDao.find(sql,new Object[]{idStr});
		return ret;
	}

}
