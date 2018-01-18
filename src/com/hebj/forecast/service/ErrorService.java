package com.hebj.forecast.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.hebj.forecast.entity.ErrorMode;

public interface ErrorService {

	List<ErrorMode> getError(Date time,int hour);
	
	void save(ErrorMode errorMode);
	
	void solveError(ErrorMode errorMode) throws ParseException;
}
