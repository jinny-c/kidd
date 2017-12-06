package com.kidd.test.proxy;

import org.springframework.stereotype.Service;

@Service(value = "helloWordService")
public class HelloWordServiceImpl implements HelloWordService {
	
	//@Override
	public void sayHello() {
		// TODO Auto-generated method stub
		System.out.println("say hello with service");
	}
	
}
