package com.zxhy.xjl.rna.web.model;

public class Message {

	private String msg;//信息具体描述
	private String result;//信息成功与否
	
	public Message(){
		// do nothing
	}
	public Message(String msg, String result) {
		super();
		this.msg = msg;
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
