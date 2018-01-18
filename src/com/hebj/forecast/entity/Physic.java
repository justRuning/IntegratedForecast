package com.hebj.forecast.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.context.annotation.Lazy;

import lombok.ToString;

@Entity
@ToString
public class Physic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn
	private Station station;

	private String physicType;
	private Date time;
	private int hour;
	private String high;
	private String value;
	private String value2;

	public Physic() {
	}

	@Override
	public String toString() {
		return "Physic [id=" + id + ", station=" + station + ", physicType=" + physicType + ", time=" + time + ", hour="
				+ hour + ", high=" + high + ", value=" + value + ", value2=" + value2 + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public String getPhysicType() {
		return physicType;
	}

	public void setPhysicType(String physicType) {
		this.physicType = physicType;
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

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

}
