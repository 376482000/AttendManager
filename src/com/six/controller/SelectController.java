package com.six.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import com.six.service.impl.SelectServiceImpl;

/**
* @author gede
* @version date：2019年6月26日 下午6:13:37
* @description ：
*/
@Controller

public class SelectController {
	private SelectServiceImpl selectServiceImpl;

	@Autowired
	public SelectController(SelectServiceImpl selectServiceImpl) {
		super();
		this.selectServiceImpl = selectServiceImpl;
	}
	@RequestMapping(value="/select",method=GET)
	public void select(){
		if(selectServiceImpl.select()==true){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		
	}
	
}
