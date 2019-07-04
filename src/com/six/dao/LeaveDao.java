package com.six.dao;

import java.util.List;

import com.six.model.Leave;
import com.six.model.Page;
import com.six.model.SelectedCourse;

public interface LeaveDao {

	public List<Leave> getLeaveList(Leave leave, Page page);
	public int getLeaveListTotal();
	public boolean addLeave(Leave leave);
	public boolean editLeave(Leave leave);
	public boolean deleteLeave(String ids);
	public List<Leave> findLeaveBystudentId(String idStr);
	public void deleteLeaveById(Integer id);

}
