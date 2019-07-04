package com.six.json;

import java.io.InputStream;

/** 
 *教师实体表设计
 */
public class JsonTeacher {
	private int id;
	private String sn;//教师工号
	private String name;
	private String password;
	private int clazzId;
	private String sex;
	private String mobile;
	private String qq;
	private Integer enabled;
	private String authority;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getClazzId() {
		return clazzId;
	}
	public void setClazzId(int clazzId) {
		this.clazzId = clazzId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public JsonTeacher(int id, String sn, String name, String password, int clazzId, String sex, String mobile,
			String qq, Integer enabled, String authority) {
		super();
		this.id = id;
		this.sn = sn;
		this.name = name;
		this.password = password;
		this.clazzId = clazzId;
		this.sex = sex;
		this.mobile = mobile;
		this.qq = qq;
		this.enabled = enabled;
		this.authority = authority;
	}
	public JsonTeacher() {
		super();
	}
	
	
	
	
	
}
