package com.six.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.six.dao.BaseDao;
import com.six.dao.CourseDao;
import com.six.model.Course;
import com.six.model.Page;
import com.six.model.Student;
import com.six.model.Teacher;
@Repository
@Transactional
public class CourseDaoImpl implements CourseDao {
	
	private BaseDao baseDao;
	
	@Autowired
	public CourseDaoImpl(BaseDao baseDao) {
		super();
		this.baseDao = baseDao;
	}

	@Override
	public List<Course> getCourseList(Course course, Page page) {
		// TODO Auto-generated method stub
		List<Course> ret = new ArrayList<Course>();
		String sql = "from com.six.model.Course where 1=?";
		ret = baseDao.find(sql, new Object[]{1}, page.getCurrentPage(), page.getPageSize());
		return ret;
	}

	@Override
	public int getCourseListTotal() {
		int total = 0;
		String sql = "from com.six.model.Course where 1=1";
		List<Course> tl = baseDao.find(sql);
		total=tl.size();
		return total;
	}

	@Override
	public boolean isFull(int courseId) {
		// TODO Auto-generated method stub
		boolean ret = false;
		String sql = " from com.six.model.Course where selected_num >= max_num and id = " + courseId;
		List<Course> query = baseDao.find(sql);
		if (query.size() >= 1) {
			return true;
		}
		return ret;
	}

	@Override
	public Course findByid(int courseId) {
		// TODO Auto-generated method stub
		String hql="from com.six.model.Course where id=?";
		Course course= (Course) baseDao.get(hql,new Object[]{courseId});
		return course;
	}

	@Override
	public void updateCourseSelectedNum(int courseId, int i) {
		// TODO Auto-generated method stub
		String sql = "";
		if(i > 0){
			sql = "update com.six.model.Course set selected_num = selected_num + "+ i + " where id = " + courseId;
		}else{
			sql = "update com.six.model.Course set selected_num = selected_num - " + Math.abs(i) + " where id = " + courseId;
		}
		baseDao.executeHql(sql);
	}

	@Override
	public boolean addCourse(Course course) {
		// TODO Auto-generated method stub
		baseDao.save(course);
		return true;
	}

	@Override
	public boolean deleteCourse(String ids) {
		// TODO Auto-generated method stub
		String sql = "delete com.six.model.Course where id in("+ids+")";
		baseDao.executeHql(sql);
		return true;
	}
	
	@Override
	public boolean editCourse(Course course) {
		// TODO Auto-generated method stub
		String hql = "update com.six.model.Course set name = '"+course.getName()+"',teacher_id = "+course.getTeacher().getId()+",course_date = '"+course.getCourseDate()+"',max_num = "+course.getMaxNum()+" ,info = '"+course.getInfo()+"' where id = " + course.getId();
		 baseDao.executeHql(hql);
		 return true;
	}

	@Override
	public List<Course> getCourse(String courseId) {
		// TODO Auto-generated method stub
		List<Course> rt=new ArrayList<Course>();
		String sql = "from com.six.model.Course where id in("+courseId+")";
		rt=baseDao.find(sql);
		return rt;
	}

}
