package com.yf.model;

import com.yf.annotation.ExportCellName;
import com.yf.annotation.OrderNumber;

public class Student {
	@ExportCellName("学号")
	@OrderNumber(1)
	private String id;
	@ExportCellName("姓名")
	@OrderNumber(2)
	private String name;
	@ExportCellName("性别")
	@OrderNumber(3)
	private String sex;
	@ExportCellName("年龄")
	@OrderNumber(4)
	private Integer age;
	@ExportCellName("语文")
	@OrderNumber(5)
	private Integer score1;
	@OrderNumber(7)
	@ExportCellName("数学")
	private Integer score2;
	@OrderNumber(6)
	@ExportCellName("英语")
	private Integer socre3;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getScore1() {
		return score1;
	}
	public void setScore1(Integer score1) {
		this.score1 = score1;
	}
	public Integer getScore2() {
		return score2;
	}
	public void setScore2(Integer score2) {
		this.score2 = score2;
	}
	public Integer getSocre3() {
		return socre3;
	}
	public void setSocre3(Integer socre3) {
		this.socre3 = socre3;
	}
	
	
}
