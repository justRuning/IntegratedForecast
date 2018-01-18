package com.hebj.forecast.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.hebj.forecast.entity.ModeForecast;
import com.hebj.forecast.entity.Station;

public interface ModeForecastDao {

	/**
	 * 模式站点预报存入数据库
	 * @param forecasts
	 */
	void save(List<ModeForecast> forecasts);

	/**
	 * 从micaps格点数据读取站点数据
	 * @param time 初始场时间
	 * @param hour 初始场时次 
	 * @param age  预报时效
	 * @param forecastType  数值预报类型
	 * @return
	 * @throws ParseException 
	 */
	List<ModeForecast> readForecast(Date time, int hour, int age, String forecastType) throws ParseException;

	List<ModeForecast> getForecast(Date time, int second, int age, String forecastType, Station station);
	
	void deleteForecast(Date time);
}
