package com.six.service.impl;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.six.dao.ClazzDao;
import com.six.dao.StudentDao;
import com.six.dao.TeacherDao;
import com.six.json.SessionUser;
import com.six.model.*;
import com.six.service.ClazzService;
import net.sf.json.*;

@Component
public class ClazzServiceImpl implements ClazzService {
	private ClazzDao clazzDao;
	private StudentDao studentDao;
	private TeacherDao teacherDao;
	
	
	@Autowired
	public ClazzServiceImpl(ClazzDao clazzDao,StudentDao studentDao,TeacherDao teacherDao) {
		super();
		this.clazzDao = clazzDao;
		this.studentDao = studentDao;
		this.teacherDao = teacherDao;
	}

	@Override
	public void editClazz(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Integer id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name"); 
		String info = request.getParameter("info");
		Clazz clazz = new Clazz();
		clazz.setName(name);
		clazz.setInfo(info);
		clazz.setId(id);
		if(clazzDao.editClazz(clazz)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void deleteClazz(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("clazzid");
		String idStr = "";
		for(String id : ids){
			idStr += id + ",";
		}
		idStr = idStr.substring(0, idStr.length()-1);
				
		try {
			if(clazzDao.deleteClazz(idStr)){
			response.getWriter().write("success");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addClazz(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String name = request.getParameter("name");
		String info = request.getParameter("info");
		Clazz clazz = new Clazz();
		
		clazz.setInfo(info);
		clazz.setName(name);
		
		if(clazzDao.addClazz(clazz)){
			try {
				response.getWriter().write("success");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}

	@Override
	public void getClazzList(HttpServletRequest request, HttpServletResponse response,SessionUser su) {
		// TODO Auto-generated method stub
		//String name = request.getParameter("clazzName");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Clazz clazz = new Clazz();
		
		if(su.getRole().equals("ROLE_STUDENT")){
			Student student = new Student();
			student.setId(0);
			
			Student s=studentDao.findByUsername(su.getUsername());
			student.setId(s.getId());
			clazz=s.getClazz();
			
		}else if(su.getRole().equals("ROLE_TEACHER")){
			Teacher teacher = new Teacher();
			teacher.setId(0);
			Teacher s=teacherDao.findByUsername(su.getUsername());
			teacher.setId(s.getId());
			clazz=s.getClazz();
		}else if(su.getRole().equals("ROLE_ADMIN")){
			String name = request.getParameter("clazzName");
			clazz.setName(name);
			
		}
		
		//clazz.setName(name);
		
	
		List<Clazz> clazzList = clazzDao.getClazzList(clazz, new Page(currentPage, pageSize));
		
		int total = clazzDao.getClazzListTotal();
		response.setCharacterEncoding("UTF-8");
		
		List<Clazz> clas=new ArrayList<Clazz>();
		for (Clazz clazz2 : clazzList) {
			Clazz c=new Clazz();
			c.setId(clazz2.getId());
			c.setInfo(clazz2.getInfo());
			c.setName(clazz2.getName());
			clas.add(c);
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", clas);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				JsonConfig jsonConfig = new JsonConfig();  //建立配置文件
		        jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
		        jsonConfig.setExcludes(new String[]{"clazz"});
				response.getWriter().write(JSONArray.fromObject(clas,jsonConfig).toString());
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

}
