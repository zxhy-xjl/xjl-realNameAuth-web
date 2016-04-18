package com.zxhy.xjl.rna.web.model;

/**
 * 消息提示实体类
 * @author Sway
 *
 */
public class Message {

	private String msg;//信息具体描述
	private String result;//信息成功与否
	private String result_phone;//返回电话号码
	
	public Message(){
		// do nothing
	}
	public Message(String msg, String result) {
		super();
		this.msg = msg;
		this.result = result;
	}
	public Message(String msg, String result, String result_phone) {
		super();
		this.msg = msg;
		this.result = result;
		this.result_phone = result_phone;
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
	public String getResult_phone() {
		return result_phone;
	}
	public void setResult_phone(String result_phone) {
		this.result_phone = result_phone;
	}
}
