package com.hebj.forecast.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.DeviationDao;
import com.hebj.forecast.entity.Deviation;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.service.DeviationService;

import lombok.val;

@Service
public class DeviationServiceImpl implements DeviationService {

	@Autowired
	DeviationDao deviationDao;

	@Override
	public List<String> getBestDeviation(Station station, Date time, int hour, int age) {

		return deviationDao.getBestDeviation(station, time, hour, age);
	}

	@Override
	public TreeMap<String, List<String>> get7Deviation(Station station, int hour, String type) {

		String[] types = new String[] { "EC", "T639", "Grapes", "中国天气网", "American", "中央指导", "T7Online", "集成" };
		TreeMap<String, List<String>> mapMax = new TreeMap<>();
		TreeMap<String, List<String>> mapMin = new TreeMap<>();
		for (String string : types) {
			List<Deviation> deviations = deviationDao.getDeviation(station, string, hour, 7);
			List<String> values = new ArrayList<>();
			List<String> values1 = new ArrayList<>();
			if (deviations == null || deviations.size() < 1) {
				continue;
			}
			for (int i = 0; i < deviations.size(); i++) {
				values.add(deviations.get(i).getMaxTemp());
				values1.add(deviations.get(i).getMinTemp());
			}
			mapMax.put(string, values);
			mapMin.put(string, values1);
		}
		return type == "Max" ? mapMax : mapMin;
	}

	@Override
	public TreeMap<String, Integer> getDeviationCounts(Station station, int hour, String type) {
		String[] types = new String[] { "EC", "T639", "Grapes", "中国天气网", "American", "中央指导", "T7Online", "集成" };
		TreeMap<String, Integer> mapMax = new TreeMap<>();
		TreeMap<String, Integer> mapMin = new TreeMap<>();
		TreeMap<String, Integer> mapMax1 = new TreeMap<>();
		TreeMap<String, Integer> mapMin1 = new TreeMap<>();
		for (String string : types) {
			List<Deviation> deviations = deviationDao.getDeviation(station, string, hour, 7);
			if (deviations == null || deviations.isEmpty()) {
				continue;
			}
			int countMax2 = 0;
			int countMin2 = 0;
			int countMax1 = 0;
			int countMin1 = 0;
			for (int i = 0; i < deviations.size(); i++) {
				if (Math.abs(Double.parseDouble(deviations.get(i).getMaxTemp())) <= 2) {
					countMax2++;
					if (Math.abs(Double.parseDouble(deviations.get(i).getMaxTemp())) <= 1) {
						countMax1++;
					}
				}
				if (Math.abs(Double.parseDouble(deviations.get(i).getMinTemp())) <= 2) {
					countMin2++;
					if (Math.abs(Double.parseDouble(deviations.get(i).getMinTemp())) <= 1) {
						countMin1++;
					}
				}
			}
			mapMax.put(string, countMax2);
			mapMin.put(string, countMin2);
			mapMax1.put(string, countMax1);
			mapMin1.put(string, countMin1);
		}
		switch (type) {
		case "Max2":
			return mapMax;
		case "Min2":
			return mapMin;
		case "Max1":
			return mapMax1;
		case "Min1":
			return mapMin1;
		default:
			return null;
		}
	}

	@Override
	public List<String> get7DeviationDate(Station station, int hour) {

		List<String> times = new ArrayList<>();
		List<Deviation> deviations = deviationDao.getDeviation(station, "中国天气网", hour, 7);

		SimpleDateFormat dateFormat = new SimpleDateFormat("M月d日");
		for (Deviation deviation : deviations) {
			times.add("'" + dateFormat.format(deviation.getBeginTime()) + "'");
		}
		return times;
	}

	@Override
	public TreeMap<String, List<String>> get7Deviation(Station station, Date time, int hour, String type) {
		String[] types = new String[] { "EC", "T639", "Grapes", "中国天气网", "American", "中央指导", "T7Online", "集成" };
		TreeMap<String, List<String>> mapMax = new TreeMap<>();
		TreeMap<String, List<String>> mapMin = new TreeMap<>();
		for (String string : types) {
			List<Deviation> deviations = deviationDao.getDeviation(station, string, time, hour, 7);
			List<String> values = new ArrayList<>();
			List<String> values1 = new ArrayList<>();
			if (deviations == null || deviations.size() < 1) {
				continue;
			}
			for (int i = 0; i < deviations.size(); i++) {
				values.add(deviations.get(i).getMaxTemp());
				values1.add(deviations.get(i).getMinTemp());
			}
			double d = 0, d1 = 0, j = 0;
			for (int i = values.size() - 1; i >= 0; i--) {
				if (values.get(i) != null) {
					j++;
					d = d + Math.abs(Double.parseDouble(values.get(i)));
					if (j == 3) {
						values.add(String.format("%.1f", d / j));
					}
				}
				if (values1.get(i) != null) {
					d1 = d1 + Math.abs(Double.parseDouble(values1.get(i)));
					if (j == 3) {
						values1.add(String.format("%.1f", d1 / j));
					}
				}
			}
			values.add(String.format("%.1f", d / j));
			values1.add(String.format("%.1f", d1 / j));

			mapMax.put(string, values);
			mapMin.put(string, values1);
		}
		return type == "Max" ? mapMax : mapMin;
	}

	@Override
	public void readDeviation(Date time, int hour) {

		try {
			List<?> deviations = deviationDao.readDeviation(time, hour);
			deviationDao.save((List<Deviation>) deviations);
		} catch (IOException | ParseException e) {
			System.out.println(time.toGMTString() + hour + "误差计算出错!");
		}

	}

}
