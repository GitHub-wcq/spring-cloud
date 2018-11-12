package com.wcq.springcloud.servicea.entity;


public class SysAdmin implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SysAdmin";
	public static final String ALIAS_ID = "主键id";
	public static final String ALIAS_USER_NAME = "用户名称";
	public static final String ALIAS_USER_PWD = "密码";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_STATUS = "状态（0可用，1禁用）";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
		
	//columns START
	private Integer id;
	private String userName;
	private String userPwd;
	private java.util.Date createTime;
	private Integer status;
	private java.util.Date updateTime;
	//columns END

	public SysAdmin(){
	}

	public SysAdmin(
		Integer id
	){
		this.id = id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setUserPwd(String value) {
		this.userPwd = value;
	}
	
	public String getUserPwd() {
		return this.userPwd;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

}

