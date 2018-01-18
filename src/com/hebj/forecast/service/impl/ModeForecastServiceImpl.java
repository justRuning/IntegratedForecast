package com.hebj.forecast.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.ErrorModeDao;
import com.hebj.forecast.dao.ModeForecastDao;
import com.hebj.forecast.entity.ErrorMode;
import com.hebj.forecast.entity.ModeForecast;
import com.hebj.forecast.service.ModeForecastService;

@Service("ModeForecast")
public class ModeForecastServiceImpl implements ModeForecastService {

	private static Logger logger = Logger.getLogger(ModeForecastServiceImpl.class);

	@Autowired
	ModeForecastDao modeForecastDao;
	@Autowired
	ErrorModeDao errorModeDao;

	@SuppressWarnings("unused")
	@Override
	@Async
	public void reaadForecast(Date time, int hour, String forecastType) throws ParseException {

		List<ModeForecast> forecasts = new ArrayList<>();
		for (int age = 12; age < 241; age = age + 3) {
			List<ModeForecast> forecasts2 = modeForecastDao.readForecast(time, hour, age, forecastType);
			if (forecasts2!=null) {
				forecasts.addAll(forecasts2);
			}
		}

		if (!forecasts.isEmpty()) {
			modeForecastDao.save(forecasts);
			
			logger.info(time.getTime() + "  " + hour + "时" + forecastType + "入库" + forecasts.size() + "条");

		} else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			logger.info(time.getTime() + "  " + hour + "时" + forecastType + "入库出错");
			ErrorMode errorMode = new ErrorMode();
			errorMode.setDo(false);
			errorMode.setHour(hour);
			errorMode.setType(forecastType);
			try {
				errorMode.setTime(dateFormat.parse(dateFormat.format(time)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			errorModeDao.save(errorMode);
		}
	}

	@Override
	public void deleteForecast(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.DAY_OF_YEAR, -5);
		modeForecastDao.deleteForecast(calendar.getTime());
		
	}
	
	

}
