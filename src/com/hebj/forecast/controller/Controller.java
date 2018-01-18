package com.hebj.forecast.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.entity.WeatherActual;
import com.hebj.forecast.service.DeviationService;
import com.hebj.forecast.service.ForecastService;
import com.hebj.forecast.service.WeatherActualService;

@org.springframework.stereotype.Controller
@RequestMapping("/forecast")
public class Controller {

	@Autowired
	ForecastService forecastService;
	@Autowired
	WeatherActualService weatherActualservice;
	@Autowired
	DeviationService deviationService;
	@Autowired
	StationDao stationDao;

	@RequestMapping("/hello")
	public ModelAndView hello(@RequestParam(value = "station", required = false) String stationName,
			@RequestParam(value = "time", required = false) String date,
			@RequestParam(value = "hour", required = false) String shour) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (stationName == null) {
			stationName = "临河";
			Calendar time = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			int hour = time.get(Calendar.HOUR_OF_DAY);
			if (hour >= 14) {
				shour = "8";
				date = sdf.format(Calendar.getInstance().getTime());
			} else {
				shour = "20";
				time.add(Calendar.DAY_OF_MONTH, -1);
				date = sdf.format(time.getTime());
			}
			shour = "8";
			date = sdf.format(Calendar.getInstance().getTime());
		}
		ModelAndView model = new ModelAndView("table.jsp");

		TreeMap<String, List<Forecast>> everyTypeForecasts = new TreeMap<>();
		Station station = stationDao.getStationByName(stationName);
		TreeMap<String, List<String>> deviation7Max;
		TreeMap<String, List<String>> deviation7Min;

		TreeMap<String, List<double[]>> wucha = new TreeMap<>();

		Date time = new Date();
		try {
			time = sdf.parse(date);
		} catch (ParseException e) {
			System.out.println("传入时间格式有误！");
		}

		int hour = Integer.parseInt(shour);
		Calendar calendar = Calendar.getInstance();
		Calendar fCalendar = Calendar.getInstance();
		int fHour = hour == 8 ? 20 : 8;
		calendar.setTime(time);
		if (hour == 8) {
			fCalendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		List<WeatherActual> weathers = weatherActualservice.getWeather(station, calendar.getTime(), fHour, 7);

		everyTypeForecasts = forecastService.getEveryTypeForecasts(station, calendar.getTime(), hour);

		deviation7Max = deviationService.get7Deviation(station, time, hour, "Max");

		deviation7Min = deviationService.get7Deviation(station, time, hour, "Min");

		model.addObject("everyTypeForecasts", everyTypeForecasts);
		model.addObject("max", deviation7Max);
		model.addObject("min", deviation7Min);
		model.addObject("weathers", weathers);
		model.addObject("station", stationName);
		model.addObject("time", date);
		model.addObject("hour", shour);
		return model;

	}

	@RequestMapping("/index2")
	public ModelAndView index2(String name) {

		ModelAndView model = new ModelAndView("index2.jsp");
		TreeMap<String, List<Forecast>> map = new TreeMap<>();
		TreeMap<String, List<Forecast>> map48 = new TreeMap<>();
		TreeMap<String, List<Forecast>> map72 = new TreeMap<>();
		HashMap<String, TreeMap<String, List<String>>> forecast5DayMax = new HashMap<>();
		HashMap<String, TreeMap<String, List<String>>> forecast5DayMin = new HashMap<>();
		HashMap<String, TreeMap<String, List<String>>> deviation7Max = new HashMap<>();
		HashMap<String, TreeMap<String, List<String>>> deviation7Min = new HashMap<>();
		HashMap<String, TreeMap<String, Integer>> deviationMax2 = new HashMap<>();
		HashMap<String, TreeMap<String, Integer>> deviationMax1 = new HashMap<>();
		HashMap<String, TreeMap<String, Integer>> deviationMin2 = new HashMap<>();
		HashMap<String, TreeMap<String, Integer>> deviationMin1 = new HashMap<>();
		Station station = stationDao.getStationByName(name);
		List<String> bfDays = new ArrayList<>();
		List<String> bf7Days = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		Calendar fCalendar = Calendar.getInstance();
		int hour;
		if (calendar.get(Calendar.HOUR_OF_DAY) > 14) {
			hour = 8;
		} else {
			hour = 20;
			fCalendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		deviation7Max.put(station.getStationName(), deviationService.get7Deviation(station, hour, "Max"));
		deviation7Min.put(station.getStationName(), deviationService.get7Deviation(station, hour, "Min"));

		map.put(station.getStationName(), forecastService.getLastForecast(station, fCalendar.getTime(), hour, 24));
		map48.put(station.getStationName(), forecastService.getLastForecast(station, fCalendar.getTime(), hour, 48));
		map72.put(station.getStationName(), forecastService.getLastForecast(station, fCalendar.getTime(), hour, 72));
		List<Forecast> forecasts = forecastService.getLastForecast(station, fCalendar.getTime(), hour);
		if (forecasts == null) {
			model = new ModelAndView("404.jsp");
			return model;
		}
		List<String> ecMax = new ArrayList<>();
		List<String> t639Max = new ArrayList<>();
		List<String> americanMax = new ArrayList<>();
		List<String> sevpMax = new ArrayList<>();
		List<String> t7Max = new ArrayList<>();
		List<String> grapesMax = new ArrayList<>();
		List<String> jiMax = new ArrayList<>();
		List<String> chinaMax = new ArrayList<>();
		List<String> ecMin = new ArrayList<>();
		List<String> t639Min = new ArrayList<>();
		List<String> americanMin = new ArrayList<>();
		List<String> sevpMin = new ArrayList<>();
		List<String> t7Min = new ArrayList<>();
		List<String> grapesMin = new ArrayList<>();
		List<String> jiMin = new ArrayList<>();
		List<String> chinaMin = new ArrayList<>();

		for (int i = 0; i < forecasts.size(); i++) {
			String type = forecasts.get(i).getForecastType();
			switch (forecasts.get(i).getForecastType()) {
			case "EC":
				ecMax.add(forecasts.get(i).getMaxTemp());
				ecMin.add(forecasts.get(i).getMinTemp());
				break;
			case "T639":
				t639Max.add(forecasts.get(i).getMaxTemp());
				t639Min.add(forecasts.get(i).getMinTemp());
				break;
			case "American":
				americanMax.add(forecasts.get(i).getMaxTemp());
				americanMin.add(forecasts.get(i).getMinTemp());
				break;
			case "T7Online":
				t7Max.add(forecasts.get(i).getMaxTemp());
				t7Min.add(forecasts.get(i).getMinTemp());
				break;
			case "Grapes":
				grapesMax.add(forecasts.get(i).getMaxTemp());
				grapesMin.add(forecasts.get(i).getMinTemp());
				break;
			case "中央指导":
				sevpMax.add(forecasts.get(i).getMaxTemp());
				sevpMin.add(forecasts.get(i).getMinTemp());
				break;
			case "集成":
				jiMax.add(forecasts.get(i).getMaxTemp());
				jiMin.add(forecasts.get(i).getMinTemp());
				break;
			case "中国天气网":
				chinaMax.add(forecasts.get(i).getMaxTemp());
				chinaMin.add(forecasts.get(i).getMinTemp());
				break;
			default:
				break;
			}
		}
		deviationMax2.put(station.getStationName(), deviationService.getDeviationCounts(station, hour, "Max2"));
		deviationMax1.put(station.getStationName(), deviationService.getDeviationCounts(station, hour, "Max1"));
		deviationMin2.put(station.getStationName(), deviationService.getDeviationCounts(station, hour, "Min2"));
		deviationMin1.put(station.getStationName(), deviationService.getDeviationCounts(station, hour, "Min1"));

		List<WeatherActual> weatherActuals = weatherActualservice.get5Weather(station, Calendar.getInstance().getTime(),
				hour);
		List<String> max = new ArrayList<>();
		List<String> min = new ArrayList<>();
		SimpleDateFormat ft = new SimpleDateFormat("M月d日");

		for (WeatherActual weatherActual : weatherActuals) {
			if (weatherActual == null) {
				max.add(null);
				min.add(null);
				bfDays.add("''");
				continue;
			}
			max.add(String.valueOf(Double.parseDouble(weatherActual.getMaxTemp24()) / 10));
			min.add(String.valueOf(Double.parseDouble(weatherActual.getMinTemp24()) / 10));
			bfDays.add("'" + ft.format(weatherActual.getTime()) + "'");
		}
		for (int i = 1; i < 8; i++) {
			bfDays.add("'" + String.valueOf(i * 24) + "'");
		}

		TreeMap<String, List<String>> list = new TreeMap<>();
		list.put("EC最高气温", ecMax);
		list.put("T639最高气温", t639Max);
		list.put("美国最高气温", americanMax);
		list.put("天气在线最高气温", t7Max);
		list.put("中央指导最高气温", sevpMax);
		list.put("Grapes最高气温", grapesMax);
		list.put("实况(高温)", max);
		list.put("中国天气最高气温", chinaMax);
		list.put("集成最高气温", jiMax);
		forecast5DayMax.put(station.getStationName(), list);

		TreeMap<String, List<String>> list2 = new TreeMap<>();
		list2.put("EC最低气温", ecMin);
		list2.put("T639最低气温", t639Min);
		list2.put("美国最低气温", americanMin);
		list2.put("天气在线最低气温", t7Min);
		list2.put("中央指导最低气温", sevpMin);
		list2.put("Grapes最低气温", grapesMin);
		list2.put("实况", min);
		list2.put("中国天气最低气温", chinaMin);
		list2.put("集成最低气温", jiMin);
		forecast5DayMin.put(station.getStationName(), list2);
		bf7Days = deviationService.get7DeviationDate(station, hour);

		model.addObject("bfDays", bfDays);
		model.addObject("bf7Days", bf7Days);
		model.addObject("hour", hour);
		model.addObject("map", map);
		model.addObject("map48", map48);
		model.addObject("map72", map72);
		model.addObject("forecasts7DayMax", forecast5DayMax);
		model.addObject("forecasts7DayMin", forecast5DayMin);
		model.addObject("deviationMax", deviation7Max);
		model.addObject("deviationMin", deviation7Min);
		model.addObject("deviationMax2", deviationMax2);
		model.addObject("deviationMax1", deviationMax1);
		model.addObject("deviationMin2", deviationMin2);
		model.addObject("deviationMin1", deviationMin1);

		return model;
	}

	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("index.jsp");
		List<Forecast> bestForecasts = new ArrayList<Forecast>();
		TreeMap<String, List<Forecast>> everyTypeForecasts = new TreeMap<>();
		List<Station> stations = stationDao.getPreparedStations();

		List<WeatherActual> weathers = weatherActualservice.getLastWeather();

		Calendar calendar = Calendar.getInstance();
		Calendar fCalendar = Calendar.getInstance();
		int hour;
		if (calendar.get(Calendar.HOUR_OF_DAY) > 14) {
			hour = 8;
		} else {
			hour = 20;
			fCalendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		everyTypeForecasts = forecastService.getEveryTypeForecasts(fCalendar.getTime(), hour);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormat.format(fCalendar.getTime()) + " " + hour + ":00";

		for (Station station : stations) {

			List<String> bestString = deviationService.getBestDeviation(station, calendar.getTime(), hour, 24);
			for (String string : bestString) {
				Forecast forecast = forecastService.getForecast(station, fCalendar.getTime(), hour, 24, string);
				if (forecast != null) {
					bestForecasts.add(forecast);
					break;
				}
			}
		}

		// model.addObject("msg", msg);
		model.addObject("everyTypeForecasts", everyTypeForecasts);
		model.addObject("hour", hour);
		model.addObject("beginTime", time);
		model.addObject("bestForecasts", bestForecasts);
		model.addObject("weathers", weathers);
		// List<Forecast> forecasts = forecastService
		return model;
	}

	@RequestMapping("/forecast")
	public ModelAndView forecast(String name) {

		List<Forecast> forecasts = forecastService.getLastForecast(name);
		List<WeatherActual> weathers = weatherActualservice.getWeather(name);
		List<Integer> highTems = new ArrayList<Integer>();
		List<String> days = new ArrayList<String>();
		List<Integer> lowTems = new ArrayList<Integer>();
		List<String> tems = new ArrayList<String>();
		List<String> hours = new ArrayList<String>();

		if (forecasts.get(0).getHour() == 16) {
			highTems.add(0, null);
			highTems.remove(highTems.size() - 1);
		}
		for (WeatherActual weatherActual : weathers) {
			tems.add(String.valueOf(Double.parseDouble(weatherActual.getTem()) / 10));
			hours.add("'" + String.valueOf(weatherActual.getHour()) + "时" + "'");
		}
		ModelAndView model = new ModelAndView("forecast.jsp");

		model.addObject("forecasts", forecasts);
		model.addObject("weathers", weathers);
		model.addObject("highTems", highTems);
		model.addObject("lowTems", lowTems);
		model.addObject("tems", tems);
		model.addObject("hours", hours);
		model.addObject("days", days);
		return model;

	}

	@RequestMapping("/test")
	public void test() {

		Calendar time = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		time.set(Calendar.YEAR, 2017);
		time.set(Calendar.MONTH, 5);
		time.set(Calendar.DAY_OF_MONTH, 10);

		for (int i = 0; i < 25; i++) {
			time.add(Calendar.DAY_OF_MONTH, 1);
			System.out.println(dateFormat.format(time.getTime()));

			deviationService.readDeviation(time.getTime(), 8);
			deviationService.readDeviation(time.getTime(), 20);
		}

	}

}
