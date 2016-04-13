package com.zxhy.xjl.rna.web.model;


public class RealNameAuthTask {
	private String taskId;
	private String phone;
	private String processName;
	private String taskName;
	private String passwd;
	private String code;
	private String name;
	private String cardId;
	private String idCode;
	private String idName;
	private String idPhotoUrl;
	private String faceUrl;
	 
	public RealNameAuthTask() {
	}
	public RealNameAuthTask(String taskId, String phone, String processName, String taskName, String passwd,
			String code, String name, String cardId, String idCode, String idName, String idPhotoUrl, String faceUrl) {
		super();
		this.taskId = taskId;
		this.phone = phone;
		this.processName = processName;
		this.taskName = taskName;
		this.passwd = passwd;
		this.code = code;
		this.name = name;
		this.cardId = cardId;
		this.idCode = idCode;
		this.idName = idName;
		this.idPhotoUrl = idPhotoUrl;
		this.faceUrl = faceUrl;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public String getIdPhotoUrl() {
		return idPhotoUrl;
	}
	public void setIdPhotoUrl(String idPhotoUrl) {
		this.idPhotoUrl = idPhotoUrl;
	}
	public String getFaceUrl() {
		return faceUrl;
	}
	public void setFaceUrl(String faceUrl) {
		this.faceUrl = faceUrl;
	}
}
