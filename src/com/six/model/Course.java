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
 * Course entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "course", catalog = "attend")

public class Course implements java.io.Serializable {

	// Fields

	private Integer id;
	private Teacher teacher;
	private String name;
	private String courseDate;
	private Integer selectedNum;
	private Integer maxNum;
	private String info;
	private Set<Attendance> attendances = new HashSet<Attendance>(0);
	private Set<SelectedCourse> selectedCourses = new HashSet<SelectedCourse>(0);

	// Constructors

	/** default constructor */
	public Course() {
	}

	/** minimal constructor */
	public Course(Teacher teacher, String name, Integer selectedNum, Integer maxNum) {
		this.teacher = teacher;
		this.name = name;
		this.selectedNum = selectedNum;
		this.maxNum = maxNum;
	}

	/** full constructor */
	public Course(Teacher teacher, String name, String courseDate, Integer selectedNum, Integer maxNum, String info,
			Set<Attendance> attendances, Set<SelectedCourse> selectedCourses) {
		this.teacher = teacher;
		this.name = name;
		this.courseDate = courseDate;
		this.selectedNum = selectedNum;
		this.maxNum = maxNum;
		this.info = info;
		this.attendances = attendances;
		this.selectedCourses = selectedCourses;
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
	@JoinColumn(name = "teacher_id", nullable = false)

	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "name", nullable = false, length = 32)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "course_date", length = 32)

	public String getCourseDate() {
		return this.courseDate;
	}

	public void setCourseDate(String courseDate) {
		this.courseDate = courseDate;
	}

	@Column(name = "selected_num", nullable = false)

	public Integer getSelectedNum() {
		return this.selectedNum;
	}

	public void setSelectedNum(Integer selectedNum) {
		this.selectedNum = selectedNum;
	}

	@Column(name = "max_num", nullable = false)

	public Integer getMaxNum() {
		return this.maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	@Column(name = "info", length = 128)

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course")

	public Set<Attendance> getAttendances() {
		return this.attendances;
	}

	public void setAttendances(Set<Attendance> attendances) {
		this.attendances = attendances;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course")

	public Set<SelectedCourse> getSelectedCourses() {
		return this.selectedCourses;
	}

	public void setSelectedCourses(Set<SelectedCourse> selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

}