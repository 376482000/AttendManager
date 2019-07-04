package com.six.dao;

import java.io.Serializable;
import java.util.List;

import com.six.model.Page;
import com.six.model.Teacher;

public interface TeacherDao {
	public Teacher findByUsername(String username);
	public void save();
	public boolean addTeacher(Teacher teacher);
	public boolean editTeacher(Teacher teacher);
	public List<Teacher> getTeacherList(Teacher teacher,Page page);
	int getTeacherListTotal();
	public void deleteTeacher(String idStr);
	public Teacher findIdByUsernamePasswd(String username,String password);
	public boolean editPassword(Teacher teacher, String newPassword);
	public Teacher getTeacherById(int teacherId);
}
