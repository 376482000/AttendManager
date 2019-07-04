package com.six.dao;

import java.util.List;

import com.six.model.Course;
import com.six.model.Page;

public interface CourseDao {
	public List<Course> getCourseList(Course course, Page page);
	public int getCourseListTotal();
	public boolean isFull(int courseId);
	public Course findByid(int courseId);
	public void updateCourseSelectedNum(int courseId, int i);
	public boolean addCourse(Course course);
	public boolean deleteCourse(String ids);
	public boolean editCourse(Course course);
	public List<Course> getCourse(String courseId);
}
