package com.kidd.test.excel;

import com.kidd.base.common.utils.readFile.JsonFileUtil;
import com.kidd.base.common.utils.readFile.XmlFileUtil;


public class ReadFileTest {

	public static void main(String[] args) {
		String path ="D:/workSpace/20170531-micro/trunk/hpay/kidd/src/main/resources/analogData/";
		String name1 ="format.xml";
		String name2 ="format.json";
		
		String str1 = XmlFileUtil.readXmlFile(path, name1);
		System.out.println("======"+str1);
		String str2 = JsonFileUtil.readJsonFile(path, name2);
		System.out.println("======"+str2);
		
		//GetValidateCodeReq req1 = XmlFileUtil.readXmlFile(GetValidateCodeReq.class,path, name1);
		//System.out.println("======"+req1);
		//GetValidateCodeReq req = new GetValidateCodeReq();
		//req.setMobile("mobile");
		//req.setChannel("channel");
		//String res = XmlFileUtil.convertToXml(req);
		//System.out.println("======"+res);
		//GetValidateCodeReq req2 = JsonFileUtil.readJsonFile(GetValidateCodeReq.class,path, name2);
		//System.out.println("======"+req2);
	}
}
