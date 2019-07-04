 package com.six.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.six.dao.ClazzDao;
import com.six.dao.TeacherDao;
import com.six.json.JsonTeacher;
import com.six.json.SessionUser;
import com.six.model.Clazz;
import com.six.model.Page;
import com.six.model.Teacher;
import com.six.service.TeacherService;
import com.six.util.SnGenerateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


/**
* @author gede
* @version date：2019年6月29日 下午1:19:57
* @description ：
*/
@Component
public class TeacherServiceImpl implements TeacherService {

	private TeacherDao teacherDao;
	private ClazzDao clazzDao;
	
	@Autowired
	public TeacherServiceImpl(TeacherDao teacherDao,ClazzDao clazzDao) {
		super();
		this.teacherDao = teacherDao;
		this.clazzDao = clazzDao;
	}

	
	@Override
	public void getTeacherList(HttpServletRequest request, HttpServletResponse response,SessionUser su) {
		
				String name = request.getParameter("teacherName");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				int cn = request.getParameter("clazzid") == null ? 0 : Integer.parseInt(request.getParameter("clazzid"));
				Clazz clazz=clazzDao.findById(cn);	
				//获取当前登录用户类型
				Teacher teacher = new Teacher();
				teacher.setId(0);
				if(su.getRole().equals("ROLE_TEACHER")){
					Teacher s = teacherDao.findByUsername(su.getUsername());
					teacher.setId(s.getId());
				}
				if(clazz!=null){
					teacher.setClazz(clazz);
				}else 
					teacher.setClazz(null);
				
				teacher.setName(name);
				
				List<Teacher> teacherList = teacherDao.getTeacherList(teacher, new Page(currentPage, pageSize));
				for (Teacher teacher2 : teacherList) {
					teacher2.getName();
				}
				int total = teacherDao.getTeacherListTotal();
				response.setCharacterEncoding("UTF-8");
				
				
				List<JsonTeacher> tea=new ArrayList<JsonTeacher>();
				for (Teacher teacher2 : teacherList) {
					JsonTeacher t=new JsonTeacher();
					t.setAuthority(teacher2.getAuthority());
					t.setClazzId(teacher2.getClazz().getId());
					t.setEnabled(teacher2.getEnabled());
					t.setId(teacher2.getId());
					t.setMobile(teacher2.getMobile());
					t.setName(teacher2.getName());
					t.setQq(teacher2.getQq());
					t.setPassword(teacher2.getPassword());
					t.setSex(teacher2.getSex());
					t.setSn(teacher2.getSn());
					tea.add(t);
				}		
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", tea);
				try {
					
					String from = request.getParameter("from");
					if("combox".equals(from)){
						JsonConfig jsonConfig = new JsonConfig();  //建立配置文件
				         jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
				         jsonConfig.setExcludes(new String[]{"clazz"});
						response.getWriter().write(JSONArray.fromObject(tea,jsonConfig).toString());
					}else{
						JsonConfig jsonConfig = new JsonConfig();  //建立配置文件
				         jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认,jsonConfig忽略
				         jsonConfig.setExcludes(new String[]{"clazz"});
						response.getWriter().write(JSONObject.fromObject(ret,jsonConfig).toString());
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	@Override
	public void addTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		System.out.println(sex);
		String mobile = request.getParameter("mobile");
		String qq = request.getParameter("qq");
		int clazzId = Integer.parseInt(request.getParameter("clazzid"));
		Clazz c=clazzDao.findById(clazzId);
		
		Teacher teacher = new Teacher();
		teacher.setClazz(c);
		teacher.setMobile(mobile);
		teacher.setName(name);
		teacher.setPassword(password);
		teacher.setQq(qq);
		teacher.setSex(sex);
		teacher.setSn(SnGenerateUtil.generateTeacherSn(20));
		if(teacherDao.addTeacher(teacher)){
			try {
				response.getWriter().write("success");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}

	@Override
	public void deleteTeacher(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("ids[]");
		String idStr = "";
		for(String id : ids){
			idStr += id + ",";
		}
		idStr = idStr.substring(0, idStr.length()-1);
		System.out.println(idStr);
		try {
			teacherDao.deleteTeacher(idStr);
			response.getWriter().write("success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void editTeacher(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		String sex = request.getParameter("sex");
		String mobile = request.getParameter("mobile");
		String qq = request.getParameter("qq");
		int clazzId = Integer.parseInt(request.getParameter("clazzid"));
		
		Clazz c=clazzDao.findById(clazzId);
		Teacher teacher = new Teacher();
		teacher.setId(id);
		teacher.setClazz(c);
		teacher.setMobile(mobile);
		teacher.setName(name);
		teacher.setQq(qq);
		teacher.setSex(sex);
		if(teacherDao.editTeacher(teacher)){
			try {
				response.getWriter().write("success");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}

}
