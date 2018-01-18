package com.hebj.forecast.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hebj.forecast.dao.ErrorModeDao;
import com.hebj.forecast.dao.ModeForecastDao;
import com.hebj.forecast.dao.PhysicDao;
import com.hebj.forecast.entity.ErrorMode;
import com.hebj.forecast.entity.ModeForecast;
import com.hebj.forecast.entity.Physic;
import com.hebj.forecast.service.ErrorService;

@Service
@Scope("prototype")
public class ErrorServiceImpl implements ErrorService {

	@Autowired
	ErrorModeDao errorModeDao;
	@Autowired
	PhysicDao physicDao;
	@Autowired
	ModeForecastDao modeForecastDao;

	@Override
	public List<ErrorMode> getError(Date time, int hour) {

		return errorModeDao.getErrors(time, hour);
	}

	@Override
	public void save(ErrorMode errorMode) {
		errorModeDao.save(errorMode);

	}

	@Override
	@Async
	public void solveError(ErrorMode errorMode) throws ParseException {

		List<ModeForecast> modeForecasts = new ArrayList<>();
		List<ModeForecast> modeForecasts2;
		if (errorMode.getType().contains("Physic")) {
			List<Physic> physics = physicDao.readPhysic(errorMode.getTime(), errorMode.getHour(), errorMode.getType());
			if (!physics.isEmpty()) {
				physicDao.save(physics);
				errorMode.setDo(true);
				errorModeDao.save(errorMode);
			}
		} else if (errorMode.getType().contains("Grapes") || errorMode.getType().contains("EC")
				|| errorMode.getType().contains("T639")) {
			for (int i = 0; i < 240; i = i + 3) {
				modeForecasts2 = modeForecastDao.readForecast(errorMode.getTime(), errorMode.getHour(), i,
						errorMode.getType());
				if (modeForecasts2 != null) {
					modeForecasts.addAll(modeForecasts2);
				}

			}
			if (!modeForecasts.isEmpty()) {
				modeForecastDao.save(modeForecasts);
				errorMode.setDo(true);
				errorModeDao.save(errorMode);
			}
		}

	}

}
