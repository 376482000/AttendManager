package com.six.dao;

import java.util.List;

import com.six.model.Attendance;
import com.six.model.Page;

/**
* @author gede
* @version date：2019年7月1日 下午8:27:39
* @description ：
*/
public interface AttendanceDao {
	public boolean addAttendance(Attendance attendance);
	
	public boolean isAttendanced(int studentId,int courseId,String type,String date);
	public List<Attendance> getSelectedCourseList(Attendance attendace,Page page);
	public int getAttendanceListTotal( );
	public boolean deleteAttendance(int id);

	public List<Attendance> findAttendanceBystudentId(String idStr);
}
