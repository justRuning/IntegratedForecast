package com.hebj.forecast.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hebj.forecast.entity.ErrorMode;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.service.DeviationService;
import com.hebj.forecast.service.ErrorService;
import com.hebj.forecast.service.ForecastService;
import com.hebj.forecast.service.ModeForecastService;
import com.hebj.forecast.service.PhysicService;
import com.hebj.forecast.service.WeatherActualService;
import com.sun.xml.internal.ws.api.Cancelable;

@Service
public class Task {

	private static Logger logger = Logger.getLogger(Task.class);
	@Autowired
	WeatherActualService weatherActualService;
	@Autowired
	ForecastService forecastService;
	@Autowired
	PhysicService physicService;
	@Autowired
	ModeForecastService modeForecastService;
	@Autowired
	DeviationService deviationService;

	@Scheduled(cron = "0 10 * * * ?")
	public void weahterAct() {

		Calendar calendar = Calendar.getInstance();

		String msg;
		try {
			msg = weatherActualService.readWeather(calendar.getTime());
			logger.info(msg);
		} catch (IOException | ParseException e) {
			logger.error("读取实况出错，时间：" + calendar.getTimeInMillis());
		}

	}

	@Scheduled(cron = "0 0/15 4,5,6,7,8,9,10,14,15,16,17,18 * * ?")
	public void readEC() {
		// String[] ECTypes = new String[] { "EC_2D", "EC_2T", "EC_10FG3",
		// "EC_10FG6", "EC_10uv", "EC_CAPE", "EC_CP",
		// "EC_D", "EC_dh", "EC_dp", "EC_D", "EC_dh", "EC_dp", "EC_dt",
		// "EC_FAL", "EC_GH", "EC_LCC", "EC_LSP",
		// "EC_mx2t6", "EC_mn2t6", "EC_Q", "EC_R", "EC_SF", "EC_SKT", "EC_T",
		// "EC_TCC", "EC_TP", "EC_tcw",
		// "EC_tcwv", "EC_W" };
		String[] ECTypes = new String[] { "EC_10uv", "EC_mx2t6", "EC_R", "EC_mn2t6", "EC_TP", "EC_TCC", "EC_W" };

		// context = new
		// ClassPathXmlApplicationContext("config/applicationContext.xml");
		// ModeForecastService modeForecastService = (ModeForecastService)
		// context.getBean("ModeForecast");
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 13) {
			hour = 8;
		} else {
			hour = 20;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		for (String type : ECTypes) {
			try {
				modeForecastService.reaadForecast(calendar.getTime(), hour, type);
			} catch (ParseException e) {
				logger.error("EC入库失败，时间：" + calendar.getTime() + hour + type);
				continue;
			}
		}
	}

	// public void readGrapes() {
	// String[] GrapesTypes = new String[] { "Grapes_H", "Grapes_T", "Grapes_W",
	// "Grapes_VOR", "Grapes_DIV",
	// "Grapes_Q", "Grapes_RH", "Grapes_T2M", "Grapes_Ts", "Grapes_PSL",
	// "Grapes_RH2M", "Grapes_RAIN",
	// "Grapes_RAIN03", "Grapes_RAIN06", "Grapes_RAIN12", "Grapes_RAIN24",
	// "Grapes_T_ADV", "Grapes_TD",
	// "Grapes_Q_FLUX", "Grapes_Q_DIV", "Grapes_THETA", "Grapes_K",
	// "Grapes_C_DBZ", "Grapes_WIND",
	// "Grapes_WIND10M" };
	@Scheduled(cron = "0 0/15 4,5,6,7,8,9,10,14,15,16,17,18 * * ?")
	public void readGrapes() {
		String[] GrapesTypes = new String[] { "Grapes_T2M", "Grapes_RH", "Grapes_RAIN12", "Grapes_WIND10M" };

		// context = new
		// ClassPathXmlApplicationContext("config/applicationContext.xml");
		// ModeForecastService modeForecastService = (ModeForecastService)
		// context.getBean("ModeForecast");
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 13) {
			hour = 8;
		} else {
			hour = 20;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		for (String type : GrapesTypes) {
			try {
				modeForecastService.reaadForecast(calendar.getTime(), hour, type);
			} catch (ParseException e) {
				logger.error("Grapes入库失败，时间：" + calendar.getTime() + hour + type);
				continue;
			}
			System.out.println(type);
		}
	}

	@Scheduled(cron = "0 0/15 4,5,6,7,8,9,10,14,15,16,17,18 * * ?")
	public void readT639() {

		// context = new
		// ClassPathXmlApplicationContext("config/applicationContext.xml");
		// ModeForecastService modeForecastService = (ModeForecastService)
		// context.getBean("ModeForecast");
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 13) {
			hour = 8;
		} else {
			hour = 20;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		// String[] T639Types = new String[] { "T639_DIV", "T639_H", "T639_ki",
		// "T639_OMEGA", "T639_PSL", "T639_Q",
		// "T639_Q_DIV", "T639_Q_FLUX", "T639_RAIN03", "T639_RAIN06",
		// "T639_RAIN12", "T639_RAIN24", "T639_RAIN",
		// "T639_RH2M", "T639_RH", "T639_T2M", "T639_T", "T639_T_ADV",
		// "T639_TD", "T639_THETA_SE", "T639_VOR",
		// "T639_VOR_ADV", "T639_WIND10M", "T639_WIND" };
		String[] T639Types = new String[] { "T639_RH", "T639_RAIN12", "T639_T2M", "T639_WIND10M" };
		for (String type : T639Types) {
			try {
				modeForecastService.reaadForecast(calendar.getTime(), hour, type);
			} catch (ParseException e) {
				logger.error("T639入库失败，时间：" + calendar.getTime() + hour + type);
				continue;
			}
			System.out.println(type);
		}
	}

	// @Scheduled(cron = "0 10,25,40,55 4,5,14,15 * * ?")
	public void readPhysic() {
		String[] physicTypes = new String[] { "Physic_tt", "Physic_rh", "Physic_w" };

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 13) {
			hour = 8;
		} else {
			hour = 20;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		for (String type : physicTypes) {
			try {
				physicService.readPhysic(calendar.getTime(), hour, type);
			} catch (ParseException e) {
				logger.error("Physic入库失败，时间：" + calendar.getTime() + hour + type);
				continue;
			}
			System.out.println(type);

		}
	}

	@Scheduled(cron = "0 12,25,40,55 4,5,6,7,8,9,10,14,15,16,17,18 * * ?")
	public void T639Forecast() {

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 14) {
			hour = 8;
		} else {
			hour = 20;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		try {
			forecastService.readForecast(calendar.getTime(), hour, "t639");
		} catch (IOException | ParseException e) {
			logger.error("T639天气预报入库失败，时间：" + calendar.getTime() + hour);
		}
	}

	@Scheduled(cron = "0 12,25,40,55 4,5,6,7,8,9,10,14,15,16,17,18 * * ?")
	public void GrapesForecast() {

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 14) {
			hour = 8;
		} else {
			hour = 20;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		try {
			forecastService.readForecast(calendar.getTime(), hour, "grapes");
		} catch (IOException | ParseException e) {
			logger.error("Grapes天气预报入库失败，时间：" + calendar.getTime() + hour);
		}
	}

	@Scheduled(cron = "0 12,25,40,55 4,5,6,7,8,9,10,14,15,16,17,18 * * ?")
	public void EcForecast() {

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 14) {
			hour = 8;
		} else {
			hour = 20;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		try {
			forecastService.readForecast(calendar.getTime(), hour, "ec");
			// forecastService.readForecast(calendar.getTime(), hour, "t639");
			// forecastService.readForecast(calendar.getTime(), hour, "grapes");
		} catch (IOException | ParseException e) {
			logger.error("EC天气预报入库失败，时间：" + calendar.getTime() + hour);
		}
	}

	// @Scheduled(cron = "0 40 * * * ?")
	// public void readError() {
	//
	// Calendar calendar = Calendar.getInstance();
	// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// Date time = new Date();
	// int hour = calendar.get(Calendar.HOUR_OF_DAY);
	// if (hour >= 13) {
	// hour = 8;
	// } else {
	// hour = 20;
	// calendar.add(Calendar.DAY_OF_MONTH, -1);
	// }
	// try {
	// time = dateFormat.parse(dateFormat.format(calendar.getTime()));
	// } catch (ParseException e) {
	// System.out.println();
	// }
	// List<ErrorMode> errorModes = errorService.getError(time, hour);
	// if (errorModes != null) {
	// for (ErrorMode errorMode : errorModes) {
	// try {
	// errorService.solveError(errorMode);
	// } catch (ParseException e) {
	// System.out.println(errorMode.toString());
	// }
	// }
	// }
	//
	// }

	@Scheduled(cron = "0 5,15,30,40,58 4,5,6,8,9,10,14,15,16 * * ?")
	public void readT7() {

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 14) {
			hour = 8;
		} else {
			hour = 20;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		try {
			forecastService.readForecast(calendar.getTime(), hour, "t7online");
			// forecastService.readForecast(calendar.getTime(), hour,
			// "american");
			// forecastService.readForecast(calendar.getTime(), hour, "china");
		} catch (IOException | ParseException e) {
			logger.error("天气在线天气预报入库失败，时间：" + calendar.getTime() + hour);
		}
	}

	@Scheduled(cron = "0 5,15,40,55 3,4,5,6,7,8,13,14,15,16,17 * * ?")
	public void readSEVP() {

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 14) {
			hour = 8;
		} else {
			hour = 20;
		}

		try {
			forecastService.readForecast(calendar.getTime(), hour, "中央指导");
		} catch (IOException | ParseException e) {
			logger.error("中央指导天气预报入库失败，时间：" + calendar.getTime() + hour);
		}
	}

	@Scheduled(cron = "0 5,15,30,40,58 4,5,6,7,12,13,14,15,16,17 * * ?")
	public void readAmerican() {

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 14) {
			hour = 8;
		} else {
			hour = 20;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		try {
			forecastService.readForecast(calendar.getTime(), hour, "american");
		} catch (IOException | ParseException e) {
			logger.error("美国天气天气预报入库失败，时间：" + calendar.getTime() + hour);
		}
	}

	@Scheduled(cron = "0 12,30,50 6,16 * * ?")
	public void localForecast() {

		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);

		if (hours == 8 || hours == 6 || hours == 10 || hours == 16) {
			try {
				forecastService.readForecast(calendar.getTime(), hours, "local");
			} catch (IOException | ParseException e) {
				logger.error("本地预报入库失败，时间：" + calendar.getTime() + hours);
			}
		}
	}

	@Scheduled(cron = "0 15,40,58 5,6,7,12,13,14,15 * * ?")
	public void chinaForecast() {

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 14) {
			hour = 8;
		} else {
			hour = 20;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		try {
			forecastService.readForecast(calendar.getTime(), hour, "china");
		} catch (IOException | ParseException e) {
			logger.error("中国天气网预报入库失败，时间：" + calendar.getTime() + hour);
		}
	}

	@Scheduled(cron = "0 1,35 5,6,7,8,9,10,11,14,15,16,17,18,19,20 * * ?")
	public void integratedForecast() {

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 14) {
			hour = 8;
		} else {
			hour = 20;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		try {
			forecastService.readForecast(calendar.getTime(), hour, "集成");
		} catch (IOException | ParseException e) {

		}
	}

	@Scheduled(cron = "0 56 12 * * ?")
	public void deleteModeForecast() {

		modeForecastService.deleteForecast(new Date());
	}

	@Scheduled(cron = "0 22 9,10,21,22 * * ?")
	public void readDeviation() {

		Calendar time = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int hour = time.get(Calendar.HOUR_OF_DAY);
		if (hour >= 14) {
			hour = 20;
		} else {
			hour = 8;
		}

		deviationService.readDeviation(time.getTime(), hour);
	}
}
