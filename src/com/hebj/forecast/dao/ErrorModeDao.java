package com.hebj.forecast.dao;

import java.util.Date;
import java.util.List;

import com.hebj.forecast.entity.ErrorMode;

public interface ErrorModeDao {

	void save(ErrorMode ErrorModes);

	List<ErrorMode> getErrors(Date time, int hour);

}
