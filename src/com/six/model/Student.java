package com.six.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Student entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "student", catalog = "attend")

public class Student implements java.io.Serializable {

	// Fields

	private Integer id;
	private Clazz clazz;
	private String sn;
	private String name;
	private String password;
	private String sex;
	private String mobile;
	private String qq;
	private Integer enabled;
	private String authority;
	private Set<Attendance> attendances = new HashSet<Attendance>(0);
	private Set<SelectedCourse> selectedCourses = new HashSet<SelectedCourse>(0);
	private Set<Leave> leaves = new HashSet<Leave>(0);

	// Constructors

	/** default constructor */
	public Student() {
	}

	/** minimal constructor */
	public Student(Clazz clazz, String sn, String name, String password, String sex) {
		this.clazz = clazz;
		this.sn = sn;
		this.name = name;
		this.password = password;
		this.sex = sex;
	}

	/** full constructor */
	public Student(Clazz clazz, String sn, String name, String password, String sex, String mobile, String qq,
			Integer enabled, String authority, Set<Attendance> attendances, Set<SelectedCourse> selectedCourses,
			Set<Leave> leaves) {
		this.clazz = clazz;
		this.sn = sn;
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.mobile = mobile;
		this.qq = qq;
		this.enabled = enabled;
		this.authority = authority;
		this.attendances = attendances;
		this.selectedCourses = selectedCourses;
		this.leaves = leaves;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clazz_id", nullable = false)

	public Clazz getClazz() {
		return this.clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

	@Column(name = "sn", nullable = false, length = 32)

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name = "name", nullable = false, length = 32)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "password", nullable = false, length = 32)

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "sex", nullable = false, length = 5)

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "mobile", length = 12)

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "qq", length = 18)

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "enabled")

	public Integer getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	@Column(name = "authority")

	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")

	public Set<Attendance> getAttendances() {
		return this.attendances;
	}

	public void setAttendances(Set<Attendance> attendances) {
		this.attendances = attendances;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")

	public Set<SelectedCourse> getSelectedCourses() {
		return this.selectedCourses;
	}

	public void setSelectedCourses(Set<SelectedCourse> selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")

	public Set<Leave> getLeaves() {
		return this.leaves;
	}

	public void setLeaves(Set<Leave> leaves) {
		this.leaves = leaves;
	}

}