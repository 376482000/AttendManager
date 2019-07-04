package com.six.dao;

import java.util.List;

import com.six.model.Page;
import com.six.model.SelectedCourse;

/**
* @author gede
* @version date：2019年7月1日 下午8:57:10
* @description ：
*/
public interface SelectedCourseDao {
	public List<SelectedCourse> getSelectedCourseList(SelectedCourse selectedCourse,Page page);
	public int getSelectedCourseListTotal();
	public boolean isSelected(int studentId,int courseId);
	public boolean addSelectedCourse(SelectedCourse selectedCourse);
	public boolean deleteSelectedCourse(int id);
	public SelectedCourse getSelectedCourse(int id);
	public List<SelectedCourse> findSelectedCourseByCourseId(String idStr);
	public List<SelectedCourse> findSelectedCourseBystudentId(String idStr);
	
}

