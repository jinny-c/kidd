package com.kidd.base.modelview;

import java.io.Serializable;

import com.kidd.base.serialize.KiddSerialTypeEnum;


public class KiddModelAndView<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private KiddSerialTypeEnum te;

	private T data;

	public KiddModelAndView(KiddSerialTypeEnum te, T data) {
		this.te = te;
		this.data = data;
	}

	public KiddSerialTypeEnum getTe() {
		return te;
	}

	public T getData() {
		return data;
	}

	public static KiddModelAndView<RespSucc> succ(KiddSerialTypeEnum te) {
		return new KiddModelAndView<RespSucc>(te, new RespSucc());
	}

	public static KiddModelAndView<RespErr> err(KiddSerialTypeEnum te,
			String code, String msg) {
		return new KiddModelAndView<RespErr>(te, new RespErr(code, msg));
	}

}