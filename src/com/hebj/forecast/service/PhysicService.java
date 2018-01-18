package com.hebj.forecast.service;

import java.text.ParseException;
import java.util.Date;

public interface PhysicService {

	void readPhysic(Date time,int hour,String physicType) throws ParseException;
}
