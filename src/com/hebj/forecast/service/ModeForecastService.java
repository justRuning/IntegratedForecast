package com.hebj.forecast.service;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Future;

public interface ModeForecastService {

	void reaadForecast(Date time,int hour,String forecastType) throws ParseException;
	
	void deleteForecast(Date time);
}
