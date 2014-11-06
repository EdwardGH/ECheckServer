package com.whut.core.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.whut.core.BaseForm;

public class ResponseUtils
{
  static final String RESPONSE_JSON = "application/json;charset=UTF-8";
  static final String RESPONSE_XML = "text/xml;charset=utf-8";
  static final String RESPONSE_HTML = "text/html;charset=UTF-8";
  static final String ENCODING = "UTF-8";

  
  public static <T> Map<String, Object> sendList(List<T> T)
  {
    return sendList(T, true);
  }

  public static <T> Map<String, Object> sendList(List<T> T, boolean success)
  {
    Map map = getInstanceMap();
    map.put("dataList", T);

    if (success == true) {
      map.put("success", Boolean.valueOf(true));
      map.put("result", Boolean.valueOf(true));
    }
    else {
      map.put("success", Boolean.valueOf(false));
      map.put("result", Boolean.valueOf(false));
    }
    return map;
  }

  public static Map<String, Object> getInstanceMap()
  {
    return new HashMap();
  }

  public static <T> Map<String, Object> sendBaseForm(BaseForm form)
  {
    return sendBaseForm(form, true);
  }

  public static <T> Map<String, Object> sendBaseForm(BaseForm form,boolean success){
	  Map map = getInstanceMap();
	    if (form != null) {
	      map.put("model", form);
	      map.put("success", Boolean.valueOf(true));
	      //map.put("result", Boolean.valueOf(true));
	    } 
	    return map;
  }

  public static void send(HttpServletResponse response, String jsonStr)
  {
    response.setContentType("application/json;charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Cache-Control", "no-cache, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    PrintWriter pw = null;
    try {
      pw = response.getWriter();
      pw.print(jsonStr);
      pw.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (pw != null) {
        pw.close();
      }
      pw = null;
    }
  }

  public static void sendHtml(HttpServletResponse response, String jsonStr)
  {
    response.setContentType("text/html;charset=UTF-8");
    response.setCharacterEncoding("UTF-8");

    PrintWriter pw = null;
    try {
      pw = response.getWriter();
      pw.print(jsonStr);
      pw.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (pw != null) {
        pw.close();
      }
      pw = null;
    }
  }
}
