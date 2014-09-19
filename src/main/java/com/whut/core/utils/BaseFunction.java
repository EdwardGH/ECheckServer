package com.whut.core.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public final class BaseFunction {
	private static boolean SET_TIMEZONE = false;

	public static void clearHash(Hashtable hash) {
		if (hash != null) {
			hash.clear();
		}
		hash = null;
	}

	public static void clearMap(HashMap hash) {
		if (hash != null) {
			hash.clear();
		}
		hash = null;
	}

	public static void clearMap(Map hash) {
		if (hash != null) {
			hash.clear();
		}
		hash = null;
	}

	public static void clearList(List list) {
		if (list != null) {
			list.clear();
		}
		list = null;
	}

	public static boolean isEmpty(String str) {
		return (str == null) || (str.trim().length() == 0);
	}

	public static boolean isEmpty(Long str) {
		return (str == null) || (str.longValue() == 0L);
	}

	public static boolean isNull(String str) {
		boolean result = false;
		if (str == null) {
			result = true;
		}
		return result;
	}

	public static String checkNull(String str, String value) {
		if ((isEmpty(str) == true) && (value != null)) {
			str = value;
		}

		return str;
	}

	public static String checkNull(String str) {
		if (str == null) {
			str = "";
		}
		return str;
	}

	public static String removeChar(String str) {
		String result = str;
		StringBuffer temp = null;
		if ((result == null) || (result.trim().length() == 0)) {
			return result;
		}
		temp = new StringBuffer("");
		for (int i = 0; i < result.length(); i++) {
			if (result.charAt(i) == ' ')
				continue;
			temp.append(result.charAt(i));
		}

		result = temp.toString();

		return result;
	}

	public static String javaConvert(String str) {
		if (!isEmpty(str)) {
			str = str.replaceAll("\\\\", "\\\\\\\\");
		}
		return str;
	}

	public static long getSecond(long begintime, long endtime) {
		long diff = endtime - begintime;
		long result = diff / 1000L;
		return result;
	}

	public static String getLineIdBySequenceName(String fatherPathcode,
			String currentMaxValue, int fixLength, boolean beforeFill) {
		String resultLineIdStr = null;
		long tempLineIdLong = 0L;
		try {
			if (isEmpty(fatherPathcode) == true) {
				if (isEmpty(currentMaxValue) == true) {
					tempLineIdLong = 1L;
				} else {
					tempLineIdLong = Long.parseLong(currentMaxValue) + 1L;
				}

			} else if (isEmpty(currentMaxValue) == true) {
				tempLineIdLong = 1L;
			} else {
				int posInt = -1;
				posInt = currentMaxValue.indexOf(fatherPathcode);
				currentMaxValue = currentMaxValue
						.substring(posInt + fatherPathcode.length() + 1,
								currentMaxValue.length());
				tempLineIdLong = Long.parseLong(currentMaxValue) + 1L;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		resultLineIdStr = Long.toString(tempLineIdLong);
		if (fixLength < 0) {
			fixLength = 4;
		}

		if (resultLineIdStr.length() < fixLength) {
			for (int i = resultLineIdStr.length(); i < fixLength; i++) {
				if (beforeFill == true) {
					resultLineIdStr = "0" + resultLineIdStr;
				} else {
					resultLineIdStr = resultLineIdStr + "0";
				}
			}
		}
		if (isEmpty(fatherPathcode) != true) {
			resultLineIdStr = fatherPathcode + "." + resultLineIdStr;
		}
		return resultLineIdStr;
	}

	public static String getPropertyName(Object object, String propertyName) {
		String result = null;
		try {
			if (!isEmpty(propertyName))
				result = BeanUtils.getProperty(object, propertyName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String getServerCurrentDateAndTime(Date date, String pattern) {
		String result = null;
		SimpleDateFormat df = null;
		try {
			if (isEmpty(pattern) == true) {
				pattern = "yyyy-MM-dd";
			}
			df = new SimpleDateFormat(pattern);
			if (!SET_TIMEZONE) {
				TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
				TimeZone.setDefault(tz);
				SET_TIMEZONE = true;
			}
			if (date != null) {
				result = df.format(date);
			}
			if (isEmpty(result) == true)
				result = "";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String getServerCurrentDateAndTime(Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
		return getServerCurrentDateAndTime(date, pattern);
	}

	public static String getServerCurrentDateAndTime() {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date date = new Date();
		return getServerCurrentDateAndTime(date, pattern);
	}

	public static String getYearFromDateStr(String dateStr) {
		String result = null;
		dateStr = checkNull(dateStr, "");
		if (dateStr.length() == 0) {
			result = getServerSysCurrentYear();
		} else if (dateStr.length() > 4) {
			result = dateStr.substring(0, 4);
		} else {
			result = getServerSysCurrentYear();
		}

		return result;
	}

	public static String getServerSysCurrentYear() {
		Date date = null;
		String pattern = "yyyy";
		date = new Date();
		return getServerCurrentDateAndTime(date, pattern);
	}

	public static String getServerCurrentDate(String current_time) {
		String result = "";
		long currentLong = 0L;
		try {
			if (!isEmpty(current_time)) {
				try {
					currentLong = Long.parseLong(current_time);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				result = getServerCurrentDate(currentLong);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String getServerCurrentDate(long current) {
		String result = "";
		try {
			String pattern = "yyyy-MM-dd HH:mm:ss";
			Date date = new Date(current);
			if (date != null)
				result = getServerCurrentDateAndTime(date, pattern);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String getServerCurrentYearMonthDay() {
		String pattern = "yyyy-MM-dd";
		Date date = new Date();
		return getServerCurrentDateAndTime(date, pattern);
	}

	public static String[] listToArr(List list) {
		String[] result = null;
		if (emptyList(list) == true) {
			int size = list.size();
			result = new String[size];
			for (int i = 0; i < size; i++) {
				result[i] = ((String) list.get(i));
			}
		}
		return result;
	}

	public static String listToStr(List list, String splitStr) {
		StringBuffer result = new StringBuffer("");
		String temp_splitStr = null;
		if (emptyList(list) == true) {
			if (isEmpty(splitStr) == true) {
				temp_splitStr = ":";
			} else {
				temp_splitStr = splitStr;
			}
			int size = list.size();
			for (int i = 0; i < size; i++) {
				if (i > 0) {
					result.append(temp_splitStr);
				}
				result.append((String) list.get(i));
			}
		}
		return result.toString();
	}

	public static String getServerCurrentSysDate() {
		String pattern = "yyyy-MM-dd";
		Date date = new Date();
		return getServerCurrentDateAndTime(date, pattern);
	}

	public static String getServerCurrentSysDateChStr() {
		String pattern = "yyyy年MM月dd日";
		Date date = new Date();
		return getServerCurrentDateAndTime(date, pattern);
	}

	public static String getTime(long time) {
		String result = "";
		time /= 1000L;
		if (time <= 86400L) {
			if (time <= 3600L)
				;
		}

		return result;
	}

	public static String getRequestUrl(HttpServletRequest request) {
		StringBuffer result = new StringBuffer("");
		result.append("http://" + request.getServerName());
		result.append(":" + request.getServerPort());
		result.append("/" + request.getContextPath() + "/"
				+ request.getServletPath());

		return result.toString();
	}

	public static String getRequestData(HttpServletRequest request) {
		StringBuffer result = new StringBuffer("");
		result.append(getRequestUrl(request));
		Enumeration enumType = request.getParameterNames();
		int count = 0;
		while (enumType.hasMoreElements()) {
			String fieldName = (String) enumType.nextElement();
			String fieldValue = request.getParameter(fieldName);
			fieldValue = fieldValue == null ? "" : request
					.getParameter(fieldName);
			if (count > 0) {
				result.append("&" + fieldName + "=" + fieldValue);
			} else {
				result.append("?" + fieldName + "=" + fieldValue);
				count = 1;
			}
		}

		return result.toString();
	}

	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static StringBuffer getRequestHead(HttpServletRequest request) {
		StringBuffer result = new StringBuffer("");
		String headerName = null;
		if (request != null) {
			Enumeration enumType = request.getHeaderNames();
			if (enumType != null) {
				while (enumType.hasMoreElements() == true) {
					headerName = (String) enumType.nextElement();
					result.append(headerName + ":"
							+ request.getHeader(headerName) + ";");
				}
			}
		}

		return result;
	}

	public static String arrToSqlStr(String[] arr, String entityName)
			throws Exception {
		StringBuffer result = new StringBuffer("");
		try {
			if ((arr != null) && (arr.length > 0))
				for (int i = 0; i < arr.length; i++) {
					if (i > 0) {
						result.append(" or ");
					}
					result.append(entityName + "='" + arr[i] + "'");
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result.toString();
	}

	public static String arrToSqlStr(String[] arr, String entityName, List param)
			throws Exception {
		StringBuffer result = new StringBuffer("");
		String temp = null;
		try {
			if ((arr != null) && (arr.length > 0))
				for (int i = 0; i < arr.length; i++) {
					if (i > 0) {
						result.append(" or ");
					}
					result.append(entityName + "=?");
					temp = arr[i];
					param.add(checkNull(temp));
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result.toString();
	}

	public static String listToSqlStr(List list, String entityName, List param)
			throws Exception {
		StringBuffer result = new StringBuffer("");
		Long objectLong = null;
		Integer objectInt = null;
		try {
			if ((list != null) && (list.size() > 0)) {
				for (int i = 0; i < list.size(); i++) {
					if (i > 0) {
						result.append(" or ");
					}
					result.append(entityName + "=?");
					if ((list.get(i) instanceof String)) {
						param.add((String) list.get(i));
					} else if ((list.get(i) instanceof Long)) {
						objectLong = (Long) list.get(i);
						if (objectLong != null) {
							param.add(Long.valueOf(objectLong.longValue()));
						} else {
							param.add("0");
						}

					} else if ((list.get(i) instanceof Integer)) {
						objectInt = (Integer) list.get(i);
						if (objectInt != null) {
							param.add(Integer.valueOf(objectInt.intValue()));
						} else {
							param.add("0");
						}
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result.toString();
	}

	public static String listToSqlStr(List list, String entityName)
			throws Exception {
		StringBuffer result = new StringBuffer("");
		try {
			if ((list != null) && (list.size() > 0)) {
				int size = list.size();
				for (int i = 0; i < size; i++) {
					if (i > 0) {
						result.append(" or ");
					}
					result.append(entityName + "=?");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result.toString();
	}

	public static int getCountInt(String ids) {
		String[] temp_arr = null;
		int result = 0;
		if (!isEmpty(ids)) {
			temp_arr = strToArray(ids);
			if (temp_arr != null) {
				for (int i = 0; i < temp_arr.length; i++) {
					if ((temp_arr[i] != ":") && (!isEmpty(temp_arr[i]))) {
						result++;
					}
				}
			}
		}
		return result;
	}

	public static boolean emptyList(List dataList) {
		boolean result = true;
		if ((dataList != null) && (dataList.size() > 0)) {
			result = false;
		}
		return result;
	}

	public static boolean isDate(String dateStr, String pattern) {
		boolean result = false;
		if (!isEmpty(dateStr)) {
			SimpleDateFormat formatter = null;
			Date date = null;
			try {
				if (isEmpty(pattern) == true) {
					pattern = "yyyy-MM-dd";
				}
				formatter = new SimpleDateFormat(pattern);
				date = formatter.parse(dateStr);
				result = true;
			} catch (Exception ex) {
				date = null;
				result = false;
				ex.printStackTrace();
			}
			if (date == null) {
				result = false;
			}
		}
		return result;
	}

	public static boolean validYidongCellId(String[] arr) {
		boolean result = true;
		if ((arr != null) && (arr.length == 4) && ("0".equals(arr[0]) == true)) {
			result = false;
		}

		return result;
	}

	public static boolean validDianxingCellId(String[] arr, String cell_id) {
		boolean result = true;
		if ((arr != null)
				&& (("0".equals(arr[0]) == true)
						|| ("-1--1--1".equals(cell_id) == true)
						|| ("[-1,-1]".equals(cell_id) == true)
						|| (arr.length == 6) || (arr.length == 3))) {
			result = false;
		}

		return result;
	}

	public static String[] strToArray(String str) {
		String[] result = null;
		String splitStr = null;
		try {
			if (isEmpty(str) == true) {
				return result;
			}

			if (str.indexOf(":") >= 0) {
				splitStr = ":";
			} else if (str.indexOf(";") >= 0) {
				splitStr = ";";
			} else if (str.indexOf(",") >= 0) {
				splitStr = ",";
			}

			result = strToArray(str, splitStr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String[] strToArray(String str, String splitStr) {
		String[] result = null;
		List tempList = null;
		try {
			if (isEmpty(str) == true) {
				return result;
			}
			if (splitStr == null) {
				splitStr = "$$";
			}
			StringTokenizer st = new StringTokenizer(str, splitStr);
			tempList = new ArrayList();
			while (st.hasMoreElements() == true) {
				tempList.add(st.nextToken());
			}
			if (tempList.size() > 0) {
				int size = tempList.size();
				result = new String[size];
				for (int i = 0; i < size; i++) {
					result[i] = ((String) tempList.get(i));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		String[] arr = strToArray(
				";0.0;0.0;0.0;0.0;1309496708121:;0.0;0.0;0.0;0.0;1309497127401",
				":");
		if ((arr != null) && (arr.length > 0)) {
			int size = arr.length;
			for (int i = 0; i < size; i++)
				if (!isEmpty(arr[i])) {
					String[] temp = strToArray(arr[i], ";");
					System.out.println(temp[0]);
					System.out.println(temp[1]);
					System.out.println(temp[2]);
					System.out.println(temp[3]);
				}
		}
	}
}