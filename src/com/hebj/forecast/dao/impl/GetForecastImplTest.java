package com.hebj.forecast.dao.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import jcifs.smb.SmbException;

public class GetForecastImplTest {

	@Test
	public void test() throws SmbException {
		GetForecastImpl getForecastImpl = new GetForecastImpl();
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			getForecastImpl.getSEVPForecast(calendar.getTime(), 20);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
