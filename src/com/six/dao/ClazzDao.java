package com.six.dao;

import java.util.List;

import com.six.model.Clazz;
import com.six.model.Page;
import com.six.model.Student;
import com.six.model.Teacher;

public interface ClazzDao {
	public Clazz findByUsername(String username);
	public void save();
	
	public Clazz findById(int id);
	int getClazzListTotal();
	public boolean deleteClazz(String idStr);
	public boolean addClazz(Clazz clazz);
	public List<Clazz> getClazzList(Clazz clazz, Page page);
	public boolean editClazz(Clazz clazz);
	
	
}
