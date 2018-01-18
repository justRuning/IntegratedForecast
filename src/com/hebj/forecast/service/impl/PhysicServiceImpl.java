package com.hebj.forecast.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.ErrorModeDao;
import com.hebj.forecast.dao.PhysicDao;
import com.hebj.forecast.entity.ErrorMode;
import com.hebj.forecast.entity.Physic;
import com.hebj.forecast.service.PhysicService;

import antlr.PythonCharFormatter;

@Service
public class PhysicServiceImpl implements PhysicService {

	private static Logger logger = Logger.getLogger(PhysicServiceImpl.class);
	
	@Autowired
	PhysicDao physicDao;
	@Autowired
	ErrorModeDao errorModeDao;

	@Override
	@Async
	public void readPhysic(Date time, int hour, String physicType) throws ParseException {

		List<Physic> forecasts = new ArrayList<>();
		List<Physic> physics = physicDao.readPhysic(time, hour, physicType);
		if (physics!=null) {
			forecasts.addAll(physics);
		}
		

		if (!forecasts.isEmpty()) {
			physicDao.save(forecasts);
			logger.info(time.getTime() + "  " + hour + "时" + physicType + "入库" + forecasts.size() + "条");
		} else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			logger.info(time.getTime() + "  " + hour + "时" + physicType + "入库失败了");
			ErrorMode errorMode = new ErrorMode();
			errorMode.setDo(false);
			errorMode.setHour(hour);
			errorMode.setType(physicType);
			try {
				errorMode.setTime(dateFormat.parse(dateFormat.format(time)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			errorModeDao.save(errorMode);
		}

	}

}
