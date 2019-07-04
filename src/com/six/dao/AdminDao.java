package com.six.dao;

import java.util.List;

import com.six.model.Admin;

public interface AdminDao {
	public Admin findByUsername(String username);
	public void save();
	public Admin findIdByUsernamePasswd(String username,String password);
	public String getPassword(int id);
	public boolean editPassword(Admin admin, String newPassword);
}
