package com.kidd.base.factory.message;


import com.kidd.base.factory.message.dto.IPubNumMessage;

public interface IMsgHandlerDispatcher {
	void dispatch(IPubNumMessage pubNumMessage);
}
