package com.hebj.forecast.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ErrorMode {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String type;
	private Date time;
	private int hour;
	private boolean isDo;

	public ErrorMode() {
	}

	@Override
	public String toString() {
		return "ErrorMode [id=" + id + ", physicType=" + type + ", time=" + time + ", hour=" + hour + ", isDo="
				+ isDo + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public boolean isDo() {
		return isDo;
	}

	public void setDo(boolean isDo) {
		this.isDo = isDo;
	}

}
