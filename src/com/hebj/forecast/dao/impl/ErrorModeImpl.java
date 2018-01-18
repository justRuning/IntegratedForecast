package com.hebj.forecast.dao.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hebj.forecast.dao.ErrorModeDao;
import com.hebj.forecast.entity.ErrorMode;

@Repository
public class ErrorModeImpl implements ErrorModeDao {


	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public void save(ErrorMode errorModes) {

		if (errorModes == null) {
			return;
		}
		String hql = "from ErrorMode e where e.type=? and e.time =? and e.hour =?";
		Object[] params = new Object[3];
		params[0] = errorModes.getType();
		params[1] = errorModes.getTime();
		params[2] = errorModes.getHour();
		List<?> errors = hibernateTemplate.find(hql, params);
		if (errors.size() < 1 || errors.get(0) == null) {
			hibernateTemplate.save(errorModes);
			hibernateTemplate.flush();
		}

	}

	@Override
	public List<ErrorMode> getErrors(Date time, int hour) {
		String hql = "from ErrorMode e where e.isDo=? and e.time=? and e.hour=?";
		Object[] params = new Object[3];
		params[0] = false;
		params[1] = time;
		params[2] = hour;
		List<?> list = hibernateTemplate.find(hql, params);
		if (list.size() < 1) {
			return null;
		}
		Collections.reverse(list);
		return (List<ErrorMode>) list;
	}

}
