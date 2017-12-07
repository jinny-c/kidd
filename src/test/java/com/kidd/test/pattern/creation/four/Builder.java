package com.kidd.test.pattern.creation.four;

import java.util.ArrayList;
import java.util.List;

import com.kidd.test.pattern.creation.one.MailSender;
import com.kidd.test.pattern.creation.one.Sender;
import com.kidd.test.pattern.creation.one.SmsSender;

public class Builder {
	private List<Sender> list = new ArrayList<Sender>();

	public void produceMailSender(int count) {
		for (int i = 0; i < count; i++) {
			list.add(new MailSender());
		}
	}

	public void produceSmsSender(int count) {
		for (int i = 0; i < count; i++) {
			list.add(new SmsSender());
		}
	}
}
