package com.rheno.androiddb;

public class DBModel {
	private long id;
	private String content;
	public DBModel(){}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
