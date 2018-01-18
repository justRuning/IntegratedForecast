package com.hebj.forecast.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hebj.forecast.dao.ModeForecastDao;
import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.entity.ModeForecast;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.util.ReadMicapsData;

@Repository
public class ModeForecastDaoImpl implements ModeForecastDao {

	@Autowired
	StationDao stationDao;

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public List<ModeForecast> readForecast(Date time, int hour, int age, String forecastType) throws ParseException {

		List<ModeForecast> forecasts = new ArrayList<ModeForecast>();
		List<Station> stations = stationDao.getPreparedStations();
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		String value = null, lines = null;
		String[] values = null;
		// String[] highs = { "999", "70", "100", "200", "225", "250", "275",
		// "300", "350", "400", "450", "500", "550",
		// "600", "650", "700", "750", "800", "850", "925", "1000" };
		String[] highs = { "999", "500", "700", "850" };

		for (String high : highs) {
			lines = ReadMicapsData.getMicapsData(forecastType, time, high, hour, age);
			if (lines == null) {
				continue;
			}

			for (Station station : stations) {

				switch (forecastType.toLowerCase()) {
				case "ec_2d":
				case "ec_2t":
				case "ec_10fg3":
				case "ec_10fg6":
				case "ec_cape":
				case "ec_cp":
				case "ec_d":
				case "ec_dh":
				case "ec_dp":
				case "ec_dt":
				case "ec_fal":
				case "ec_gh":
				case "ec_lcc":
				case "ec_lsp":
				case "ec_msl":
				case "ec_mn2t6":
				case "ec_mx2t6":
				case "ec_q":
				case "ec_r":
				case "ec_sf":
				case "ec_skt":
				case "ec_t":
				case "ec_tcc":
				case "ec_tp":
				case "ec_tcw":
				case "ec_tcwv":
				case "ec_w":
					value = ReadMicapsData.getValueLikeEc(lines, station);
					break;
				case "ec_10uv":
					values = ReadMicapsData.getValueLikeEcuv(lines, station);
					break;
				case "t639_div":
				case "t639_h":
				case "t639_ki":
				case "t639_omega":
				case "t639_psl":
				case "t639_q":
				case "t639_q_div":
				case "t639_q_flux":
				case "t639_rain03":
				case "t639_rain06":
				case "t639_rain12":
				case "t639_rain24":
				case "t639_rain":
				case "t639_rh":
				case "t639_rh2m":
				case "t639_t2m":
				case "t639_t":
				case "t639_t_adv":
				case "t639_td":
				case "t639_theta":
				case "t639_theta_se":
				case "t639_vor":
				case "t639_vor_adv":
				case "grapes_c_dbz":
				case "grapes_div":
				case "grapes_h":
				case "grapes_k":
				case "grapes_w":
				case "grapes_psl":
				case "grapes_q":
				case "grapes_q_div":
				case "grapes_q_flux":
				case "grapes_rain03":
				case "grapes_rain06":
				case "grapes_rain12":
				case "grapes_rain24":
				case "grapes_rain":
				case "grapes_rh":
				case "grapes_rh2m":
				case "grapes_t2m":
				case "grapes_t":
				case "grapes_ts":
				case "grapes_t_adv":
				case "grapes_td":
				case "grapes_theta":
				case "grapes_theta_se":
				case "grapes_vor":
				case "grapes_vor_adv":
					value = ReadMicapsData.getValueLikeT639(lines, station);
					break;
				case "t639_wind10m":
				case "t639_wind":
				case "grapes_wind10m":
				case "grapes_wind":
					values = ReadMicapsData.getValueLikeT639Wind(lines, station);
					break;
				default:
					return null;
				}

				if (value != null || values != null) {
					ModeForecast forecast = new ModeForecast();
					forecast.setBeginTime(dateFormat2.parse(dateFormat2.format(time)));
					forecast.setAge(age);
					forecast.setHour(hour);
					forecast.setForecastType(forecastType);
					forecast.setHigh(high);
					forecast.setStation(station);
					if (forecastType.equalsIgnoreCase("ec_10uv") || forecastType.equalsIgnoreCase("t639_wind")
							|| forecastType.equalsIgnoreCase("t639_wind10m")
							|| forecastType.equalsIgnoreCase("grapes_wind")
							|| forecastType.equalsIgnoreCase("grapes_wind10m")) {
						forecast.setValue(values[0]);
						forecast.setValue2(values[1]);
					} else {
						forecast.setValue(value);
					}
					forecasts.add(forecast);
				}
			}
		}

		return forecasts;
	}

	@Override
	public List<ModeForecast> getForecast(Date time, int second, int age, String forecastType, Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(List<ModeForecast> forecasts) {
		if (forecasts == null) {
			return;
		}
		String hql = "from ModeForecast f where f.station=? and f.age=? and f.beginTime =? and f.hour =? and f.forecastType =? and f.high =?";
		Object[] params = new Object[6];
		for (ModeForecast forecast : forecasts) {
			params[0] = forecast.getStation();
			params[1] = forecast.getAge();
			params[2] = forecast.getBeginTime();
			params[3] = forecast.getHour();
			params[4] = forecast.getForecastType();
			params[5] = forecast.getHigh();
			hibernateTemplate.setMaxResults(10);
			List<?> forecast2 = hibernateTemplate.find(hql, params);
			if (!forecast2.isEmpty()) {
				hibernateTemplate.deleteAll(forecast2);
				// hibernateTemplate.flush();
			}
			hibernateTemplate.save(forecast);
		}

	}

	@Override
	public void deleteForecast(Date time) {
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		String hql = "from ModeForecast f where f.beginTime<?";
		Object[] params = new Object[1];
		try {
			params[0] = dateFormat2.parse(dateFormat2.format(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<?> modeForecasts = hibernateTemplate.find(hql, params);
		if (!modeForecasts.isEmpty()) {
			hibernateTemplate.deleteAll(modeForecasts);
		}

	}

}
