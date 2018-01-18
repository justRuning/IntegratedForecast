package com.hebj.forecast.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.DeviationDao;
import com.hebj.forecast.dao.WeatherActualDao;
import com.hebj.forecast.entity.Deviation;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.entity.WeatherActual;
import com.hebj.forecast.service.WeatherActualService;

import jcifs.smb.SmbException;

@Service
public class WeatherActualServiceImpl implements WeatherActualService {

	@Autowired
	WeatherActualDao weatherActualDao;
	@Autowired
	DeviationDao deviationDao;

	@Override
	public String readWeather(Date time)
			throws MalformedURLException, SmbException, UnsupportedEncodingException, IOException, ParseException {
		List<WeatherActual> weatherActuals = weatherActualDao.readWeather(time);
		DateFormat dateFormat = new SimpleDateFormat("HH");
		if (weatherActuals != null) {
			this.save(weatherActuals);
			if (time.getHours() == 8 || time.getHours() == 20) {
				List<Deviation> deviations = deviationDao.readDeviation(time, time.getHours());
				if (deviations != null) {
					deviationDao.save(deviations);
				}
			}
			return "实况入库完成！！！！！！时间：" + time;
		} else {
			return "实况入库失败！！！！！！时间：" + time;
		}
	}

	@Override
	public void save(List<WeatherActual> weatherActuals) {
		weatherActualDao.save(weatherActuals);

	}

	@Override
	public List<WeatherActual> getWeather(String name) {
		List<WeatherActual> weatherActuals = weatherActualDao.getWeather(name);
		if (weatherActuals.size() < 1) {
			return null;
		} else {
			return weatherActuals;
		}
	}

	@Override
	public List<WeatherActual> getLastWeather() {
		return weatherActualDao.getLastWeather();
	}

	@Override
	public List<WeatherActual> get5Weather(Station station, Date time, int hour) {

		// 太tm饶了，这里hour是实况结点，与预报初始场正好相反
		hour = hour == 8 ? 20 : 8;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		if (calendar.get(Calendar.HOUR_OF_DAY) > 8 && hour == 8) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		} else if (hour == 8 && calendar.get(Calendar.HOUR_OF_DAY) <= 8) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		List<WeatherActual> weathers = new ArrayList<>();
		for (int i = 1; i < 8; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			weathers.add(0, weatherActualDao.getWeather(station, calendar.getTime(), hour));
		}
		return weathers;
	}

	@Override
	public List<WeatherActual> getWeather(Station station, Date time, int hour, int count) {
		hour = hour == 8 ? 20 : 8;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		if (calendar.get(Calendar.HOUR_OF_DAY) > 8 && hour == 8) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		} else if (hour == 8 && calendar.get(Calendar.HOUR_OF_DAY) <= 8) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		List<WeatherActual> weathers = new ArrayList<>();
		WeatherActual weather = new WeatherActual();
		calendar.add(Calendar.DAY_OF_MONTH, -count);
		for (int i = 0; i < count; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			weather = (WeatherActual) weatherActualDao.getWeather(station, calendar.getTime(), hour);
			weathers.add(weather);
		}
		 
		
		return weathers;
	}

}
