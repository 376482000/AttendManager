package com.six.dao;

import java.util.List;

import com.six.model.Clazz;
import com.six.model.Page;
import com.six.model.Student;

public interface StudentDao {
	public Student findByUsername(String username);

	public boolean deleteStudent(String idStr);
	List<Student> getStudentList(Student student, Page page);
	public int getStudentListTotal();
	public Student findIdByUsernamePasswd(String username,String password);
	public boolean editPassword(Student student, String newPassword);
	public boolean addStudent(Student student);
	public Student findById(int id);
	public boolean editStudent(Student student);
}
