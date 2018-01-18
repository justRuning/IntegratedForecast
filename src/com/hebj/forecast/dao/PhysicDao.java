package com.hebj.forecast.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.hebj.forecast.entity.ModeForecast;
import com.hebj.forecast.entity.Physic;
import com.hebj.forecast.entity.Station;

public interface PhysicDao {

	/**
	 * 物理场数据存入数据库
	 * @param forecasts
	 */
	void save(List<Physic> physics);

	/**
	 * 从micaps格点数据读取站点数据
	 * @param time 初始场时间
	 * @param hour 初始场时次 
	 * @param physicType  无立场类型
	 * @return
	 * @throws ParseException 
	 */
	List<Physic> readPhysic(Date time, int hour, String physicType) throws ParseException;

	List<Physic> getPhysic(Date time, int hour, String physicType, Station station);
}
