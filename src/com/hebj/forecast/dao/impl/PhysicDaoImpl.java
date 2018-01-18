package com.hebj.forecast.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hebj.forecast.dao.PhysicDao;
import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.entity.Physic;
import com.hebj.forecast.entity.Station;
import com.hebj.forecast.util.ReadMicapsData;

@Repository
public class PhysicDaoImpl implements PhysicDao {

	@Autowired
	StationDao stationDao;

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public void save(List<Physic> physics) {
		if (physics == null) {
			return;
		}
		String hql = "from Physic p where p.station=? and p.time =? and p.hour =? and p.physicType =? and p.high =?";
		Object[] params = new Object[5];
		for (Physic physic : physics) {
			params[0] = physic.getStation();
			params[1] = physic.getTime();
			params[2] = physic.getHour();
			params[3] = physic.getPhysicType();
			params[4] = physic.getHigh();
			List<?> physic2 = hibernateTemplate.find(hql, params);
			if (!physic2.isEmpty()) {
				hibernateTemplate.deleteAll(physic2);
			}
			hibernateTemplate.save(physic);
			hibernateTemplate.flush();
		}

	}

	@Override
	public List<Physic> readPhysic(Date time, int hour, String physicType) throws ParseException {

		List<Station> stations = stationDao.getPreparedStations();
		List<Physic> physics = new ArrayList<Physic>();
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		String value = null, lines = null;
		String[] values = null;
		String[] highs = { "999", "r3", "r6", "r12", "r24", "70", "100", "200", "300", "400", "500", "600", "700",
				"800", "850", "925", "1000" };

		for (int i = 0; i < highs.length; i++) {
			lines = ReadMicapsData.getMicapsData(physicType, time, highs[i], hour, 0);
			if (lines == null) {
				continue;
			}

			for (Station station : stations) {

				switch (physicType.toLowerCase()) {
				case "physic_cape":
				case "physic_delta_t58":
				case "physic_delta_thse58":
				case "physic_dh500_24":
				case "physic_div":
				case "physic_h0c":
				case "physic_h20c":
				case "physic_ki":
				case "physic_li":
				case "physic_qadv":
				case "physic_qfdiv":
				case "physic_qflux":
				case "physic_qq":
				case "physic_rh":
				case "physic_si":
				case "physic_ssi":
				case "physic_t_adv":
				case "physic_tadv58":
				case "physic_thetse":
				case "physic_tt":
				case "physic_ttadv":
				case "physic_vor":
				case "physic_vor_adv":
				case "physic_w":
					value = ReadMicapsData.getValueLikePhysic(lines, station);
					break;
				case "physic_uv":
					values = ReadMicapsData.getValueLikePhysicuv(lines, station);
					break;
				default:
					return null;
				}

				if (value != null || values != null) {
					Physic physic = new Physic();
					physic.setTime(dateFormat2.parse(dateFormat2.format(time)));
					physic.setHour(hour);
					physic.setPhysicType(physicType);
					physic.setHigh(highs[i]);
					physic.setStation(station);
					if (physicType.equalsIgnoreCase("physic_uv")) {
						physic.setValue(values[0]);
						physic.setValue2(values[1]);
					} else {
						physic.setValue(value);
					}
					physics.add(physic);
				}
			}
		}
		return physics;
	}

	@Override
	public List<Physic> getPhysic(Date time, int hour, String physicType, Station station) {
		// TODO Auto-generated method stub
		return null;
	}

}
