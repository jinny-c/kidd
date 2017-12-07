package com.kidd.test.pattern.creation.two;

import com.kidd.test.pattern.creation.one.Sender;
import com.kidd.test.pattern.creation.one.SmsSender;

public class SendSmsFactory implements Provider {

	@Override
	public Sender produce() {
		// TODO Auto-generated method stub
		return new SmsSender();
	}

}
