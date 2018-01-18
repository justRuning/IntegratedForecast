package com.hebj.forecast.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.hebj.forecast.entity.Deviation;
import com.hebj.forecast.entity.Station;

public interface DeviationDao {

	void save(List<Deviation> deviations);

	/**
	 * 计算误差，用最新的实况来计算前面各个时间各个模式的预报误差
	 * @param time 实况时间
	 * @param hour 时次
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws ParseException
	 */
	List<Deviation> readDeviation(Date time, int hour) throws UnsupportedEncodingException, IOException, ParseException;

	List<Deviation> getLastDeviation(Station station);

	List<String> getBestDeviation(Station station, Date time, int hour, int age);

	List<Deviation> getDeviation(Station station, String forecastType, int hour, int length);
	
	List<Deviation> getDeviation(Station station, String forecastType,Date time, int hour, int length);

	Deviation getDeviation(Station station, Date time, String forecastType, int hour);
}
