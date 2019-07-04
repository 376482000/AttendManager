package com.six.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Leave entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "leave", catalog = "attend")

public class Leave implements java.io.Serializable {

	// Fields

	private Integer id;
	private Student student;
	private String info;
	private Integer status;
	private String remark;

	// Constructors

	/** default constructor */
	public Leave() {
	}

	/** minimal constructor */
	public Leave(Student student, Integer status) {
		this.student = student;
		this.status = status;
	}

	/** full constructor */
	public Leave(Student student, String info, Integer status, String remark) {
		this.student = student;
		this.info = info;
		this.status = status;
		this.remark = remark;
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
	@JoinColumn(name = "student_id", nullable = false)

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Column(name = "info", length = 512)

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "status", nullable = false)

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "remark", length = 512)

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}