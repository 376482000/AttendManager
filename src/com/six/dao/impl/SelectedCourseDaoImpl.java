package com.six.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.six.dao.BaseDao;
import com.six.dao.SelectedCourseDao;
import com.six.model.Page;
import com.six.model.SelectedCourse;
import com.six.model.Student;

/**
* @author gede
* @version date：2019年7月1日 下午9:01:41
* @description ：
*/
@Component
public class SelectedCourseDaoImpl implements SelectedCourseDao {
	private BaseDao baseDao;
	
	@Autowired
	public SelectedCourseDaoImpl(BaseDao baseDao) {
		super();
		this.baseDao = baseDao;
	}
	
	@Override
	public List<SelectedCourse> getSelectedCourseList(SelectedCourse selectedCourse, Page page) {
		// TODO Auto-generated method stub
		List<SelectedCourse> ret = new ArrayList<SelectedCourse>();
		String sql = "from com.six.model.SelectedCourse where 1=? ";
		if(selectedCourse.getStudent()!= null){
			System.out.println(selectedCourse.getStudent().getId());
			sql += " and student_id = " + selectedCourse.getStudent().getId();
		}
		if(selectedCourse.getCourse() != null){
			System.out.println(selectedCourse.getCourse().getId());
			sql += " and course_id = " + selectedCourse.getCourse().getId();
		}
		ret = baseDao.find(sql, new Object[]{1}, page.getCurrentPage(), page.getPageSize());
		return ret;
	}

	@Override
	public int getSelectedCourseListTotal() {
		// TODO Auto-generated method stub
		int total = 0;
		String sql = "from com.six.model.SelectedCourse where 1=1";
		List<Student> sl = baseDao.find(sql);
		total=sl.size();
		return total;
	}

	@Override
	public boolean isSelected(int studentId, int courseId) {
		// TODO Auto-generated method stub
		boolean ret = false;
		String sql = "from com.six.model.SelectedCourse where student_id = " + studentId + " and course_id = " + courseId;
		List<SelectedCourse> query = baseDao.find(sql);
			if(query.size()>=1){
				return true;
			}
		return ret;
	}

	@Override
	public boolean addSelectedCourse(SelectedCourse selectedCourse) {
		// TODO Auto-generated method stub
		baseDao.save(selectedCourse);
		return true;
	}

	@Override
	public boolean deleteSelectedCourse(int id) {
		// TODO Auto-generated method stub
		String sql = "delete from com.six.model.SelectedCourse where id = " + id;
		baseDao.executeHql(sql);
		return true;
	}

	@Override
	public SelectedCourse getSelectedCourse(int id) {
		// TODO Auto-generated method stub
		SelectedCourse ret = null;
		String sql = "from com.six.model.SelectedCourse where id =?" ;
		ret = (SelectedCourse) baseDao.get(sql,new Object[]{id});
		return ret;
	}

	@Override
	public List<SelectedCourse> findSelectedCourseByCourseId(String idStr) {
		// TODO Auto-generated method stub
		List<SelectedCourse> ret = null;
		String sql = "from com.six.model.SelectedCourse where course_id =?" ;
		ret =  baseDao.find(sql,new Object[]{idStr});
		return ret;
	}

	@Override
	public List<SelectedCourse> findSelectedCourseBystudentId(String idStr) {
		// TODO Auto-generated method stub
		List<SelectedCourse> ret = null;
		String sql = "from com.six.model.SelectedCourse where student_id =?" ;
		ret =  baseDao.find(sql,new Object[]{idStr});
		return ret;
	}

}
