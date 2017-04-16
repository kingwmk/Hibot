package cn.ybz21.hibotvoice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private final static String FORMAT = "yyyy-MM-dd HH:mm:ss";

	public int computeSecondFromNow(String time) {
		Date oldDate = getDateFromString(time);
		Date now = new Date();
		return (int) ((now.getTime() - oldDate.getTime()) / 1000);
	}

	public String getNowFormatDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		Date now = new Date();
		String abcValue = sdf.format(now);
		return abcValue;
	}
	public String getNowFormatDate(String fromat) {
		SimpleDateFormat sdf = new SimpleDateFormat(fromat);
		Date now = new Date();
		String abcValue = sdf.format(now);
		return abcValue;
	}

	public String getFormatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);

		String abcValue = sdf.format(date);
		return abcValue;
	}

	public Date getDateFromString(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}
