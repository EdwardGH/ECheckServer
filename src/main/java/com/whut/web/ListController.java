package com.whut.web;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.whut.bean.BeanOne;
import com.whut.common.page.PageModel;
import com.whut.core.BaseForm;
import com.whut.core.utils.ResponseUtils;
import com.whut.dao.impl.UserDao;
import com.whut.entity.UserEntity;
import com.whut.service.UserService;




@Controller
@RequestMapping(value = "/list")
public class ListController {

	@Autowired
	private UserService userService;
	
    @RequestMapping(value = "/loadList", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> loadOption(HttpServletRequest request,String username) {
        List<BeanOne> list = new ArrayList<BeanOne>();
        BeanOne one = new BeanOne();
        one.setName("one");
        one.setPassword("onepass");
        BeanOne two = new BeanOne();
        two.setName("two");
        two.setPassword("two");
        BeanOne three = new BeanOne();
        three.setName("three");
        three.setPassword("three");
        list.add(one);
        list.add(two);
        list.add(three);
        PageModel pageModel = new PageModel<BeanOne>();
        pageModel.setRecords(list);
        pageModel.setRecordCount(3);
        pageModel.setPage(1);
        Map map = ResponseUtils.getInstanceMap();
        map.put("pageModel",pageModel);
        return  map;
    }
}
