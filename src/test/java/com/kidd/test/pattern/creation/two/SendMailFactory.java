package com.kidd.test.pattern.creation.two;

import com.kidd.test.pattern.creation.one.MailSender;
import com.kidd.test.pattern.creation.one.Sender;

public class SendMailFactory implements Provider {

	@Override
	public Sender produce() {
		// TODO Auto-generated method stub
		return new MailSender();
	}

}
