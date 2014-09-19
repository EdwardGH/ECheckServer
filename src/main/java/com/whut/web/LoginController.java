package com.whut.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.whut.core.utils.ResponseUtils;
import com.whut.dao.impl.UserDao;
import com.whut.entity.UserEntity;




@Controller
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody
	Map<String,Object> list(HttpServletRequest request) {
		UserEntity entity = userDao.get(1);
		List<Integer> testList = new ArrayList<Integer>();
		testList.add(1);
		testList.add(2);
		return  ResponseUtils.sendList(testList);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	
	
}
