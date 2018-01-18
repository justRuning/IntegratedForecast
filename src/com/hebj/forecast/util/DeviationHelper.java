package com.hebj.forecast.util;

import com.hebj.forecast.entity.Deviation;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.WeatherActual;

public class DeviationHelper {

	public static Deviation getDeviation(WeatherActual weatherActual, Forecast forecast) {

		if (weatherActual == null || forecast == null) {
			return null;
		}

		Deviation deviation = new Deviation();

		deviation.setBeginTime(forecast.getBeginTime());
		deviation.setAge(forecast.getAge());
		deviation.setForecastType(forecast.getForecastType());
		deviation.setHour(forecast.getHour());
		deviation.setStation(forecast.getStation());

		double value;

		value = Double.parseDouble(forecast.getMaxTemp()) - Double.parseDouble(weatherActual.getMaxTemp24()) / 10;
		deviation.setMaxTemp(String.valueOf((((int) (value * 100))) / 100.0));

		value = Double.parseDouble(forecast.getMaxTempRevised())
				- Double.parseDouble(weatherActual.getMaxTemp24()) / 10;
		deviation.setMaxTempRevised(String.valueOf((((int) (value * 100))) / 100.0));

		value = Double.parseDouble(forecast.getMinTemp()) - Double.parseDouble(weatherActual.getMinTemp24()) / 10;
		deviation.setMinTemp(String.valueOf((((int) (value * 100))) / 100.0));

		value = Double.parseDouble(forecast.getMinTempRevised())
				- Double.parseDouble(weatherActual.getMinTemp24()) / 10;
		deviation.setMinTempRevised(String.valueOf((((int) (value * 100))) / 100.0));

		if (weatherActual.getWater24() != "00000") {
			if (forecast.getRainDay() != null || forecast.getRainNight() != null) {
				deviation.setRainDay("1");
			} else {
				deviation.setRainDay("0");
			}
		} else if (weatherActual.getWater24() == "00000") {
			if (forecast.getRainDay() != null || forecast.getRainNight() != null) {
				deviation.setRainDay("0");
			} else {
				deviation.setRainDay("1");
			}
		}

		return deviation;
	}
}
