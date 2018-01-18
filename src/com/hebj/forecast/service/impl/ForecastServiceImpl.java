package com.hebj.forecast.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.DeviationDao;
import com.hebj.forecast.dao.ForecastDao;
import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.entity.Deviation;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.service.ForecastService;

@Service
public class ForecastServiceImpl implements ForecastService {

	@Autowired
	ForecastDao forecastDao;
	@Autowired
	StationDao station;
	@Autowired
	DeviationDao deviationDao;

	@Override
	@Async
	public String readForecast(Date time, int second, String forecastType)
			throws UnsupportedEncodingException, IOException, ParseException {
		List<Forecast> forecasts;
		forecasts = forecastDao.readForecast(time, second, forecastType);
		if (forecasts != null) {
			forecastDao.save(forecasts);
			return forecastType + "入库完成，时间：" + time;
		}
		return forecastType + "入库失败！！！！！！时间：" + time;
	}

	@Override
	public List<Forecast> getLastForecast(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Forecast> getLastForecast(Station station, Date time, int hour, int age) {
		List<Forecast> forecasts = (List<Forecast>) forecastDao.getforecast(station, time, hour, age);

		for (int i = 0; i < forecasts.size(); i++) {
			if (forecasts.get(i).getForecastType().equals("Local")) {
				forecasts.remove(i);
				i--;
			}
		}
		return forecasts;
	}

	@Override
	public Forecast getForecast(Station station, Date time, int hour, int age, String type) {
		Forecast forecasts = forecastDao.getForecast(station, time, hour, type, age);
		if (forecasts == null) {
			return null;
		}
		return forecasts;
	}

	@Override
	public List<Forecast> getLastForecast(Station station, Date time, int hour) {
		List<Forecast> forecasts = (List<Forecast>) forecastDao.getForecast(station, time, hour);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		int second = calendar.get(Calendar.HOUR_OF_DAY);
		if (second > 8 && hour == 8) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		} else if (second > 8 && hour == 20) {

		} else {
			calendar.add(Calendar.DAY_OF_MONTH, -2);
		}

		String[] types = { "EC", "T639", "American", "T7Online", "Grapes", "中央指导", "中国天气网", "集成" };
		for (int i = 0; i < 7; i++) {

			calendar.add(Calendar.DAY_OF_MONTH, -1);
			for (String string : types) {
				Forecast forecasts2 = forecastDao.getForecast(station, calendar.getTime(), hour, string, 24);
				if (forecasts2 == null) {
					Forecast forecast = new Forecast();
					forecast.setForecastType(string);
					forecasts.add(0, forecast);
					continue;
				}
				forecasts.add(0, forecasts2);
			}

		}

		return forecasts;
	}

	@Override
	public TreeMap<String, List<Forecast>> getEveryTypeForecasts(Date time, int hour) {
		TreeMap<String, List<Forecast>> everyTypeForecasts = new TreeMap<>();
		String[] types = { "EC", "T639", "American", "T7Online", "中国天气网", "Grapes", "中央指导", "集成" };

		for (String type : types) {
			everyTypeForecasts.put(type, forecastDao.getForecast(time, hour, type));
		}

		return everyTypeForecasts;
	}

	@Override
	public TreeMap<String, List<Forecast>> getEveryTypeForecasts(Station station, Date time, int hour) {
		TreeMap<String, List<Forecast>> everyTypeForecasts = new TreeMap<>();
		String[] types = { "EC", "T639", "American", "T7Online", "中国天气网", "Grapes", "中央指导", "集成" };

		for (String type : types) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			List<Forecast> forecasts = new ArrayList<Forecast>();
			List<Forecast> fs = forecastDao.getForecast(station, time, hour, type);
			if (fs != null) {
				forecasts.addAll(fs);
			}
			Forecast f = new Forecast();
			forecasts.add(0, f);
			forecasts.add(0, f);

			for (int i = 0; i < 7; i++) {
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				Forecast forecast = new Forecast();
				forecast = ((Forecast) forecastDao.getForecast(station, calendar.getTime(), hour, type, 24));
				forecasts.add(0, forecast);

			}

			everyTypeForecasts.put(type, forecasts);
		}

		return everyTypeForecasts;
	}

	@Override
	public void suppleForecast(Date time, int hour, String type) {

		List<Station> stations = station.getPreparedStations();

		for (Station station : stations) {
			List<Forecast> forecasts = forecastDao.getForecast(station, time, hour, type);
			if (forecasts == null || forecasts.size() < 10) {
				List<Deviation> deviations = deviationDao.getDeviation(station, type, hour, 3);
				double max = 0, min = 0;
				for (Deviation deviation : deviations) {
					max = Double.parseDouble(deviation.getMaxTemp());
					min = Double.parseDouble(deviation.getMinTemp());
				}
				max = Math.round((max / deviations.size()) * 100) / 100;
				min = min / deviations.size();
				switch (type) {
				case "EC":

				case "T639":
				case "Grapes":

					break;

				default:
					break;
				}
			}
		}

	}

}
