package com.hebj.forecast.service;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.hebj.forecast.entity.Deviation;
import com.hebj.forecast.entity.Station;

public interface DeviationService {

	List<String> getBestDeviation(Station station, Date time, int hour, int age);

	TreeMap<String, List<String>> get7Deviation(Station station, int hour, String type);

	TreeMap<String, Integer> getDeviationCounts(Station station, int hour, String type);
	
	TreeMap<String, List<String>> get7Deviation(Station station, Date time, int hour, String type);

	List<String> get7DeviationDate(Station station, int hour);
	
	void readDeviation(Date time, int hour);
}
