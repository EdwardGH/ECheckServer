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

import com.whut.core.BaseForm;
import com.whut.core.utils.ResponseUtils;
import com.whut.dao.impl.UserDao;
import com.whut.entity.UserEntity;
import com.whut.service.UserService;




@Controller
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody
	Map<String,Object> list(HttpServletRequest request,String username) {
		BaseForm form = new BaseForm();
		UserEntity entity = userService.findById(1);
		//UserEntity entity1 = userService.findUniqueByProperty("username", "edward");
		UserEntity entity2 = new UserEntity();
		entity2.setUsername("hello");
		entity2.setPassword("world");
		try {
			userService.saveUser(entity2);
			form.setResult("success");
		} catch (Exception e) {
			form.setResult("failure");
		}
		List<Integer> testList = new ArrayList<Integer>();
		testList.add(1);
		testList.add(2);
		return  ResponseUtils.sendBaseForm(form);
	}
}
