package com.kidd.base.common;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kidd.base.http.HttpHeader;
import com.kidd.base.modelview.KiddModelAndView;
import com.kidd.base.modelview.RespErr;
import com.kidd.base.serialize.KiddSerialTypeEnum;

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
		byte[] data = msg.getBytes("UTF-8");
//		response.setHeader(KiddHttpConstants.NAME_HP_DATALENGTH, data.length + "");
//		response.setHeader(KiddHttpConstants.NAME_HP_CONTENTTYPE, KiddHttpConstants.HP_CONTENT_TYPE_TEXT);
		response.setContentType("text/plain; charset=UTF-8");
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
