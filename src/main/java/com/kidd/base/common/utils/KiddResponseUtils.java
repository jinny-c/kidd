package com.kidd.base.common.utils;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kidd.base.common.constant.KiddConstants;
import com.kidd.base.common.serialize.KiddSerialTypeEnum;
import com.kidd.base.http.HttpHeader;
import com.kidd.base.spring.modelview.KiddModelAndView;
import com.kidd.base.spring.modelview.RespErr;

/**
 * 维护响应信息
 *
 * @history
 */
public class KiddResponseUtils {

	public static String toErr(HttpServletRequest request, String errorCode, String errorMsg) {
		KiddModelAndView<RespErr> errModel = KiddModelAndView.err(getSerialType(request), errorCode, errorMsg);
		KiddSerialTypeEnum mste = errModel.getTe();
		String msg = mste.toStr(errModel.getData());
		
		return msg;
	}


	/**
	 * 返回错误信息
	 *
	 * @history
	 */
	public static void writeToResponse(HttpServletResponse response, String msg)
			throws IOException {
		byte[] data = msg.getBytes(KiddConstants.CHARSET_DEF);
//		response.setHeader(KiddHttpConstants.NAME_HP_DATALENGTH, data.length + "");
//		response.setHeader(KiddHttpConstants.NAME_HP_CONTENTTYPE, KiddHttpConstants.HP_CONTENT_TYPE_TEXT);
		response.setContentType(KiddConstants.CONTENT_TYPE_TEXT);
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			outputStream.write(data);
			outputStream.flush();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}


	private static KiddSerialTypeEnum getSerialType(HttpServletRequest request) {
		Object header = request.getAttribute(HttpHeader.class.getName());
		if (header == null) {
			return KiddSerialTypeEnum.SERILAL_TYPE_FASTJSON;
		}

		HttpHeader httpHeader = (HttpHeader) header;
		if (httpHeader.getDataType() == null) {
			return KiddSerialTypeEnum.SERILAL_TYPE_FASTJSON;
		}

		return httpHeader.getDataType();
	}


}
