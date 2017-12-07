package com.kidd.test.pattern.creation.one;

public class SendFactory3 {
	public static Sender produceMail() {
		return new MailSender();
	}

	public static Sender produceSms() {
		return new SmsSender();
	}
}
