package com.lazywg.assembly.sql.demo;



import com.lazywg.assembly.sql.annotation.SQLField;
import com.lazywg.assembly.sql.annotation.SQLKeyField;
import com.lazywg.assembly.sql.annotation.SQLTable;
import com.lazywg.assembly.sql.enums.SQLKeyFieldType;

import java.util.Date;

/**
 * @author gaowang
 * @tableName 用户表
 **/
@SQLTable("Users")
public class Entity {
	/**
	 * id
	 */
	@SQLKeyField(SQLKeyFieldType.PRIMARY)
	@SQLField(value = "id")
	private String id;

	/**
	 * 姓名
	 */
	@SQLField(value = "userName")
	private String userName;

	/**
	 * 登录账户
	 */
	@SQLField(value = "account")
	private String account;

	/**
	 * 登录密码
	 */
	@SQLField(value = "password")
	private String password;

	/**
	 * 性别：男、女、保密
	 */
	@SQLField(value = "gender")
	private String gender;

	/**
	 * 年龄
	 */
	@SQLField(value = "age")
	private int age;

	/**
	 * 身份证号
	 */
	@SQLField(value = "idCard")
	private String idCard;

	/**
	 * 邮箱
	 */
	@SQLField(value = "mail")
	private String mail;

	/**
	 * 办公电话：多个号码以逗号分隔
	 */
	@SQLField(value = "officePhones")
	private String officePhones;

	/**
	 * 个人电话
	 */
	@SQLField(value = "phone")
	private String phone;

	/**
	 * 最后登录时间
	 */
	@SQLField(value = "lastLoginTime")
	private Date lastLoginTime;

	/**
	 * 用户状态:启用、禁用
	 */
	@SQLField(value = "status")
	private String status;

	/**
	 * 备注
	 */
	@SQLField(value = "remark")
	private String remark;

	/**
	 * creater
	 */
	@SQLField(value = "creater")
	private String creater;

	/**
	 * createrId
	 */
	@SQLField(value = "createrId")
	private String createrId;

	/**
	 * createTime
	 */
	@SQLField(value = "createTime", supportCmdTypes = 7)
	private Date createTime;

	/**
	 * modifier
	 */
	@SQLField(value = "modifier")
	private String modifier;

	/**
	 * modifierId
	 */
	@SQLField(value = "modifierId")
	private String modifierId;

	/**
	 * modifyTime
	 */
	@SQLField(value = "modifyTime")
	private Date modifyTime;

	/**
	 * isDel
	 */
	@SQLField(value = "isDel")
	private boolean isDel;

	/**
	 * 获取 id
	 * 
	 * @return id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 设置 id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 姓名
	 * 
	 * @return userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * 设置 姓名
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取 登录账户
	 * 
	 * @return account
	 */
	public String getAccount() {
		return this.account;
	}

	/**
	 * 设置 登录账户
	 * 
	 * @param account
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 获取 登录密码
	 * 
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * 设置 登录密码
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取 性别：男、女、保密
	 * 
	 * @return gender
	 */
	public String getGender() {
		return this.gender;
	}

	/**
	 * 设置 性别：男、女、保密
	 * 
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * 获取 年龄
	 * 
	 * @return age
	 */
	public int getAge() {
		return this.age;
	}

	/**
	 * 设置 年龄
	 * 
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * 获取 身份证号
	 * 
	 * @return idCard
	 */
	public String getIdCard() {
		return this.idCard;
	}

	/**
	 * 设置 身份证号
	 * 
	 * @param idCard
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	/**
	 * 获取 邮箱
	 * 
	 * @return mail
	 */
	public String getMail() {
		return this.mail;
	}

	/**
	 * 设置 邮箱
	 * 
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * 获取 办公电话：多个号码以逗号分隔
	 * 
	 * @return officePhones
	 */
	public String getOfficePhones() {
		return this.officePhones;
	}

	/**
	 * 设置 办公电话：多个号码以逗号分隔
	 * 
	 * @param officePhones
	 */
	public void setOfficePhones(String officePhones) {
		this.officePhones = officePhones;
	}

	/**
	 * 获取 个人电话
	 * 
	 * @return phone
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 设置 个人电话
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取 最后登录时间
	 * 
	 * @return lastLoginTime
	 */
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	/**
	 * 设置 最后登录时间
	 * 
	 * @param lastLoginTime
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * 获取 用户状态:启用、禁用
	 * 
	 * @return status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 用户状态:启用、禁用
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取 备注
	 * 
	 * @return remark
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 设置 备注
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取 creater
	 * 
	 * @return creater
	 */
	public String getCreater() {
		return this.creater;
	}

	/**
	 * 设置 creater
	 * 
	 * @param creater
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}

	/**
	 * 获取 createrId
	 * 
	 * @return createrId
	 */
	public String getCreaterId() {
		return this.createrId;
	}

	/**
	 * 设置 createrId
	 * 
	 * @param createrId
	 */
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	/**
	 * 获取 createTime
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 设置 createTime
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 modifier
	 * 
	 * @return modifier
	 */
	public String getModifier() {
		return this.modifier;
	}

	/**
	 * 设置 modifier
	 * 
	 * @param modifier
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * 获取 modifierId
	 * 
	 * @return modifierId
	 */
	public String getModifierId() {
		return this.modifierId;
	}

	/**
	 * 设置 modifierId
	 * 
	 * @param modifierId
	 */
	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	/**
	 * 获取 modifyTime
	 * 
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return this.modifyTime;
	}

	/**
	 * 设置 modifyTime
	 * 
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 获取 isDel
	 * 
	 * @return isDel
	 */
	public boolean getIsDel() {
		return this.isDel;
	}

	/**
	 * 设置 isDel
	 * 
	 * @param isDel
	 */
	public void setIsDel(boolean isDel) {
		this.isDel = isDel;
	}

}