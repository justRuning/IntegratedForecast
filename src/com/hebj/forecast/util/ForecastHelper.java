package com.hebj.forecast.util;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder.Case;

import com.hebj.forecast.entity.Forecast;

/**
 * 预报类帮助类，主要计算订正预报数据、由天气现象计算降雨量等
 * 
 * @author hebj
 *
 */
public class ForecastHelper {

	/**
	 * 对预报对象进行调整，如降水量变降水等级
	 * 
	 * @param forecast
	 * @return
	 */
	public static Forecast correctForecast(Forecast forecast) {
		if (forecast.getMaxTemp() == null || forecast.getMinTemp() == null) {
			return null;
		}
		double maxTem = Double.parseDouble(forecast.getMaxTemp());
		double minTem = Double.parseDouble(forecast.getMinTemp());
		forecast.setMinTempRevised(forecast.getMinTemp());

		if (forecast.getForecastType() == "American") {
			forecast = ForecastHelper.americForecast(forecast);
		}
		if (forecast.getForecastType() == "T7Online") {
			forecast = ForecastHelper.T7onlineForecast(forecast);
		}

		switch (forecast.getForecastType()) {

		case "American":
		case "T7Online":
		case "中国天气网":
		case "中央指导":
			maxTem = maxTem + ForecastHelper.windDirection(forecast.getWindDirectionDay())
					- ForecastHelper.windVelocity(forecast.getWindVelocityDay())
					- ForecastHelper.sky(forecast.getSkyDay()) + ForecastHelper.rain(forecast.getRainDay());
			minTem = minTem + ForecastHelper.windDirection(forecast.getWindDirectionNight())
					+ ForecastHelper.windVelocity(forecast.getWindVelocityNight())
					+ ForecastHelper.sky(forecast.getSkyNight()) + ForecastHelper.rain(forecast.getRainNight());
			break;
		case "Ec":
		case "T639":
		case "Grapes":
			maxTem = maxTem + ForecastHelper.windDirection(forecast.getWindDirectionDay())
					- ForecastHelper.windVelocity(forecast.getWindVelocityDay())
					- ForecastHelper.RH(forecast.getRHDay()) + ForecastHelper.rain(forecast.getRainDay());
			minTem = minTem + ForecastHelper.windDirection(forecast.getWindDirectionNight())
					+ ForecastHelper.windVelocity(forecast.getWindVelocityNight())
					+ ForecastHelper.RH(forecast.getRHNight()) + ForecastHelper.rain(forecast.getRainNight());
			break;
		case "local":
			forecast.setWindDirectionNight(forecast.getWindDirectionDay());
			forecast.setWindVelocityNight(forecast.getWindVelocityDay());
			maxTem = maxTem + ForecastHelper.windDirection(forecast.getWindDirectionDay())
					- ForecastHelper.windVelocity(forecast.getWindVelocityDay())
					- ForecastHelper.sky(forecast.getSkyDay());
			minTem = maxTem + ForecastHelper.windDirection(forecast.getWindDirectionNight())
					+ ForecastHelper.windVelocity(forecast.getWindVelocityNight())
					+ ForecastHelper.sky(forecast.getSkyNight());
			break;
		default:
			break;
		}
		forecast.setMaxTempRevised(String.valueOf(((int) (maxTem * 10)) / 10.0));
		forecast.setMinTempRevised(String.valueOf(((int) (minTem * 10)) / 10.0));
		return forecast;
	}

	/**
	 * 降雨等级对温度的影响
	 * 
	 * @param value
	 * @return
	 */
	public static double rain(String value) {

		double result;
		if (value == null) {
			return 0;
		}
		Matcher matcher = Pattern.compile("^[-\\+]?[\\d]*\\.[\\d]*$").matcher(value);
		if (matcher.find()) {
			double douValue = Double.parseDouble(value);
			if (douValue < 5) {
				result = 0;
			} else if (douValue < 10) {
				result = 0.2;
			} else if (douValue < 15) {
				result = 0.5;
			} else if (douValue < 30) {
				result = 1;
			} else if (douValue < 70) {
				result = 2;
			} else {
				result = 3;
			}

			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH);
			if (month <= 11 && month >= 2) {
				result = -result;
			}
		}

		else {
			switch (value) {
			case "微量":
			case "阵雨":
			case "小阵雨":
			case "阵雪":
				result = 0;
				break;
			case "小雨":
				result = -0.2;
				break;
			case "小雪":
				result = 0.2;
				break;
			case "中雨":
				result = -0.5;
				break;
			case "中雪":
				result = 0.5;
				break;
			case "大雨":
				result = -1;
				break;
			case "大雪":
				result = 1;
				break;
			case "暴雨":
				result = -2;
				break;
			case "暴雪":
				result = 2;
				break;
			case "大暴雨":
				result = -3;
				break;
			case "大暴雪":
				result = 3;
				break;
			default:
				result = 0;
				break;
			}
		}

		return result;
	}

	/**
	 * 天空状况对温度的影响——cloud
	 * 
	 * @param value
	 * @return
	 */
	private static double sky(String value) {
		double result;
		if (value == null) {
			return 0;
		}
		switch (value) {
		case "晴":
		case "晴间少云":
			result = 0;
			break;
		case "晴间多云":
			result = 0.2;
			break;
		case "多云间晴":
			result = 0.3;
			break;
		case "多云":
		case "轻雾":
		case "浮尘":
			result = 0.5;
			break;
		case "多云间阴":
		case "霾":
		case "扬沙":
			result = 0.8;
			break;
		case "阴":
		case "大雾":
		case "沙尘暴":
			result = 1;
			break;
		default:
			result = 0;
			break;
		}
		return result;
	}

	/**
	 * 风向对温度的影响
	 * 
	 * @param value
	 * @return
	 */
	private static double windDirection(String value) {
		double result;
		if (value == null) {
			return 0;
		}
		switch (value) {
		case "N":
		case "NNE":
		case "NNW":
			result = -0.3;
			break;
		case "NW":
		case "NE":
			result = -0.2;
			break;
		case "E":
		case "ENE":
		case "ESE":
			result = -0.1;
			break;
		case "W":
		case "WNW":
		case "WSW":
			result = 0.1;
			break;
		case "SW":
		case "SE":
			result = 0.2;
			break;
		case "S":
		case "SSW":
		case "SSE":
			result = 0.3;
			break;
		default:
			result = 0;
			break;
		}
		return result;
	}

	/**
	 * 风速对温度的影响
	 * 
	 * @param value
	 * @return
	 */
	private static double windVelocity(String value) {
		double result;
		if (value == null) {
			return 0;
		}
		switch (value) {
		case "C":
		case "1":
		case "2":
		case "1-2":
			result = 0;
			break;
		case "3":
		case "2-3":
			result = 0.2;
			break;
		case "4":
		case "3-4":
			result = 0.3;
			break;
		case "5":
		case "4-5":
			result = 0.5;
			break;
		case "6":
		case "5-6":
			result = 0.8;
			break;
		case "7":
		case "8":
		case "9":
		case "10":
		case "11":
		case "6-7":
		case "7-8":
		case "8-9":
			result = 1;
			break;
		default:
			result = 0;
			break;
		}
		return result;
	}

	/**
	 * 湿度对温度的影响
	 * 
	 * @param value
	 * @return
	 */
	private static double RH(String value) {
		if (value == null) {
			return 0;
		}

		double result;
		double douValue = Double.parseDouble(value);
		if (douValue <= 40) {
			result = 0;
		} else if (douValue <= 60) {
			result = 0.2;
		} else if (douValue <= 70) {
			result = 0.3;
		} else if (douValue <= 80) {
			result = 0.5;
		} else if (douValue <= 90) {
			result = 0.8;
		} else if (douValue <= 100) {
			result = 1;
		} else {
			result = 0;
		}

		return result;
	}

	/**
	 * 通过天气在线天气描述，得到天空状况和降水
	 * 
	 * @param forecast
	 * @return
	 */
	private static Forecast T7onlineForecast(Forecast forecast) {

		forecast.setWindDirectionNight(forecast.getWindDirectionDay());
		forecast.setWindVelocityNight(forecast.getWindVelocityDay());

		String[] valueDay = ForecastHelper.T7Rain(forecast.getSkyDay());
		String[] valueNight = ForecastHelper.T7Rain(forecast.getSkyNight());

		forecast.setSkyDay(valueDay[0]);
		forecast.setRainDay(valueDay[1]);

		forecast.setSkyNight(valueNight[0]);
		forecast.setRainNight(valueNight[1]);

		return forecast;
	}

	/**
	 * 通过天气在线天气描述，得到天空状况和降水
	 * 
	 * @param forecast
	 * @return
	 */
	private static String[] T7Rain(String value) {
		String[] result = new String[2];
		value = value.replace("有", "");
		if (value.contains(",")) {

			result[0] = value.split(",")[0].trim();
			result[1] = value.split(",")[1].trim();
		} else {
			result[0] = value.trim();
			result[1] = null;
		}
		return result;
	}

	/**
	 * 通过美国天气网站天气描述，得到天空状况和降水
	 * 
	 * @param forecast
	 * @return
	 */
	private static Forecast americForecast(Forecast forecast) {
		String[] valueDay = ForecastHelper.getAmericRain(forecast.getSkyDay());
		String[] valueNight = ForecastHelper.getAmericRain(forecast.getSkyNight());
		forecast.setSkyDay(valueDay[0]);
		forecast.setRainDay(valueDay[1]);
		forecast.setSkyNight(valueNight[0]);
		forecast.setRainNight(valueNight[1]);

		return forecast;
	}

	/**
	 * 通过美国天气网站天气描述，得到天空状况和降水
	 * 
	 * @param value
	 * @return
	 */
	private static String[] getAmericRain(String value) {
		
		String[] result = new String[2];
		result[1] = null;
		if (value == null) {
			result[1] = result[0] = null;
			return result;
		}
		switch (value) {
		case "Sun":
		case "Sunny":
		case "Clear":
			result[0] = "晴";
			break;
		case "Mostly Sunny":
		case "Mostly Clear":
			result[0] = "晴间少云";
			break;
		case "Partly Cloudy":
			result[0] = "晴间多云";
			break;
		case "Mostly Cloudy":
		case "Clouds Early / Clearing Late":
		case "AM Clouds / PM. Clear":
		case "AM Clouds / PM Sun":
			result[0] = "多云间晴";
			break;
		case "Cloudy":
		case "Clearing Early / Clouds Late":
		case "AM Clear / PM Clouds":
			result[0] = "多云";
			break;
		case "Cloudy Day":
			result[0] = "阴";
			break;
		case "Wind":
		case "Showers/ Wind Late":
		case "PM Showers/ Wind":
			result[0] = "大风";
			break;
		case "Light Rain / Wind":
		case "PM Rain / Wind":
		case "Rain":
		case "Rain Early":
		case "Light Rain Early":
		case "Rain / Wind Early":
		case "Rain / Wind Late":
		case "Rain / Wind":
		case "AM Rain":
		case "PM Rain":
		case "AM Clouds Rain":
		case "AM Rain / Wind":
		case "PM Clouds Rain":
		case "AM Light Rain":
		case "AM Light Rain / Wind":
		case "PM Light Rain":
		case "Light Rain":
		case "Light Rain Late":
		case "Rain Late":
		case "Clouds Day Rain":
			result[1] = "小雨";
			result[0] = "阴";
			break;
		case "Showers Late": // 晚些时候的阵雨
		case "Showers Early": // 早期阵雨
		case "Showers / Wind": // 阵雨/风
		case "Showers / Wind Early":
		case "Showers":
		case "AM Showers":
		case "AM Showers / Wind":
		case "PM Showers":
		case "Few Showers":
			result[0] = "阴";
			result[1] = "阵雨";
			break;
		case "Isolated Thunderstorms": // 孤立的雷暴
		case "Scattered Thunderstorms": // 分散的雷暴
		case "Rain / Thunder": // 雨伴有雷声
		case "Thunderstorms": // 雷暴
		case "Thunderstorms Early": // 早期的雷暴
		case "Thunderstorms Late": // 晚些时候的雷暴
		case "PM Thunderstorms": // 下午雷暴
		case "AM Thunderstorms": // 上午雷暴
			result[0] = "雷暴";
			break;
		case "Snow": // 雪
		case "Snow Showers": // 阵雪
		case "AM Snow Showers": // 上午阵雪
		case "PM Snow Showers": // 下午阵雪
		case "Snow Showers Early": // 早期阵雪
		case "Snow Showers Late": // 晚些时候的阵雪
			result[0] = "阴";
			result[1] = "小雪";
			break;
		case "Rain / Snow": // 雨或雪
		case "Rain / Snow Early":
		case "Rain / Snow Showers Early":
		case "AM Rain / Snow":
		case "PM Rain / Snow":
		case "PM Rain / Snow Showers":
		case "Rain / Snow Showers":
		case "Ice Late": // 后期冻雨
		case "Ice Early": // 早期冻雨
		case "Rain / Ice Early": // 雨/早期冻雨
		case "Rain to Snow": // 雨转雪
		case "Snow to Rain": // 雪转雨
		case "Rain to Ice": // 雨转冻雨
		case "Ice to Rain": // 冻雨转雨
			result[0] = "阴";
			result[1] = "雨夹雪";
			break;
		case "Mostly Clear / Wind":
		case "Mostly Sunny / Wind":
		case "Cloudy Early / Clearing Late / Wind":
		case "Sunny / Wind":
		case "Cloudy / Wind":
		case "Partly Cloudy / Wind":
		case "Clear / Wind":
		case "Mostly Cloudy / Wind":
		case "AM Clouds / PM Sun / Wind":
			result[0] = "晴间多云";
			break;
		default:
			result[0] = value;
			break;
		}
		return result;
	}
}
