package com.hebj.forecast.dao.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hebj.forecast.dao.DeviationDao;
import com.hebj.forecast.dao.ForecastDao;
import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.dao.WeatherActualDao;
import com.hebj.forecast.entity.Deviation;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.entity.WeatherActual;
import com.hebj.forecast.util.DeviationHelper;

@Repository
public class DeviationDaoImpl implements DeviationDao {

	@Autowired
	StationDao stationDao;

	@Autowired
	WeatherActualDao weatherActualDao;
	@Autowired
	ForecastDao forecastDao;

	@Autowired
	HibernateTemplate hibernateTemplate;

	
	
	@Override
	public List<Deviation> readDeviation(Date time, int hour)
			throws UnsupportedEncodingException, IOException, ParseException {

		List<WeatherActual> weatherActuals = weatherActualDao.getWeather(time, hour);
		if (weatherActuals == null) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		String[] types = new String[] { "EC", "T639", "Grapes", "中国天气网", "American", "中央指导", "local", "T7Online",
				"集成" };
		List<Deviation> deviations = new ArrayList<>();

		for (String type : types) {
			for (WeatherActual weatherActual : weatherActuals) {
				calendar.setTime(time);
				calendar.add(Calendar.HOUR_OF_DAY, -12);
				for (int i = 1; i < 8; i++) {
					calendar.add(Calendar.HOUR_OF_DAY, -24);
					List<Forecast> forecasts = new ArrayList<Forecast>();
					Forecast forecast = forecastDao.getForecast(weatherActual.getStation(), calendar.getTime(),
							hour == 8 ? 20 : 8, type, i * 24);
					if (forecast == null) {
						continue;
					}
					forecasts.add(forecast);
					Deviation deviation = DeviationHelper.getDeviation(weatherActual, forecasts.get(0));
					deviations.add(deviation);
				}
			}
		}
		return deviations;
	}

	@Override
	public List<Deviation> getLastDeviation(Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(List<Deviation> deviations) {
		if (deviations == null) {
			return;
		}
		for (Deviation deviation : deviations) {

			String hql = "from Deviation f where f.station=? and f.age=? and f.beginTime =? and f.hour =? and f.forecastType =?";
			Object[] params = new Object[5];
			params[0] = deviation.getStation();
			params[1] = deviation.getAge();
			params[2] = deviation.getBeginTime();
			params[3] = deviation.getHour();
			params[4] = deviation.getForecastType();
			List<?> deviations2 = hibernateTemplate.find(hql, params);
			if (!deviations2.isEmpty()) {
				hibernateTemplate.deleteAll(deviations2);
			}
			hibernateTemplate.save(deviation);
		}
		hibernateTemplate.flush();

	}

	@Override
	public List<String> getBestDeviation(Station station, Date time, int hour, int age) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String[] types = new String[] { "EC", "T639", "Grapes", "中国天气网", "American", "中央指导", "T7Online", "集成" };
		TreeMap<Double, String> map = new TreeMap<Double, String>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);

		calendar.add(Calendar.DAY_OF_MONTH, -4);
		String hql = "select AVG(ABS(maxTemp * 1.0)) from Deviation f where f.station=? and f.age=? and f.beginTime >? and f.hour =? and forecastType=?";
		Object[] params = new Object[5];
		params[0] = station;
		params[1] = age;
		try {
			params[2] = dateFormat.parse(dateFormat.format(calendar.getTime()));
		} catch (ParseException e) {
		}
		params[3] = hour;
		hibernateTemplate.setMaxResults(10);
		for (String type : types) {
			params[4] = type;
			List<Double> value = (List<Double>) hibernateTemplate.find(hql, params);
			if (value.get(0) != null) {
				map.put(value.get(0), type);
			}
		}
		List<String> result = new ArrayList<>();
		for (String string : map.values()) {
			result.add(string);
		}

		return result;
	}

	@Override
	public List<Deviation> getDeviation(Station station, String forecastType, int hour, int length) {

		length = length + 2;
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		calendar.add(Calendar.DAY_OF_MONTH, -length);

		hibernateTemplate.setMaxResults(10);
		String hql = "from Deviation f where f.station=? and f.forecastType=? and age='24' and hour=? and beginTime between ? and ? order by f.beginTime";
		Object[] params = new Object[5];
		params[0] = station;
		params[1] = forecastType;
		params[2] = hour;
		try {
			params[3] = dateFormat.parse(dateFormat.format(calendar.getTime()));
			calendar.add(Calendar.DAY_OF_MONTH, length);
			params[4] = dateFormat.parse(dateFormat.format(calendar.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<Deviation> deviations = (List<Deviation>) hibernateTemplate.find(hql, params);

		int size = deviations.size();
		if (size < 1) {
			return null;
		} else if (size < (length + 1)) {
			return deviations.subList(0, size - 1);
		}
		return deviations.subList(deviations.size() - length - 1, deviations.size() - 1);
	}

	@Override
	public Deviation getDeviation(Station station, Date time, String forecastType, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		hibernateTemplate.setMaxResults(10);
		String hql = "from Deviation f where f.station=? and f.forecastType=? and age='24' and hour=? and beginTime = ? order by f.beginTime";
		Object[] params = new Object[5];
		params[0] = station;
		params[1] = forecastType;
		params[2] = hour;
		try {
			params[3] = dateFormat.parse(dateFormat.format(calendar.getTime()));

			params[4] = dateFormat.parse(dateFormat.format(calendar.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<Deviation> deviations = (List<Deviation>) hibernateTemplate.find(hql, params);

		int size = deviations.size();
		if (size < 1) {
			return null;
		} else {
			return deviations.get(0);
		}
	}

	@Override
	public List<Deviation> getDeviation(Station station, String forecastType, Date time, int hour, int length) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		calendar.add(Calendar.DAY_OF_MONTH, -length);
		List<Deviation> deviations = new ArrayList<>();
		hibernateTemplate.setMaxResults(10);
		for (int i = 0; i < length; i++) {

			String hql = "from Deviation f where f.station=? and f.forecastType=? and age='24' and hour=? and beginTime=? order by f.beginTime";
			Object[] params = new Object[4];
			params[0] = station;
			params[1] = forecastType;
			params[2] = hour;
			try {
				params[3] = dateFormat.parse(dateFormat.format(calendar.getTime()));
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				// params[4] =
				// dateFormat.parse(dateFormat.format(calendar.getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			List<Deviation> deviations2 = (List<Deviation>) hibernateTemplate.find(hql, params);

			int size = deviations2.size();
			if (size < 1) {
				Deviation deviation = new Deviation();
				deviations.add(deviation);
			} else if (size < (length + 1)) {
				deviations.addAll(deviations2);
			}
		}

		return deviations;
	}

}
