package com.kidd.test.excel;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.base.common.utils.ReadOrWriteFileUtil;

public class FileImportTest {

	public static void main(String[] args) {
		method1();
	}
	
	private static void method1() {
		String path ="E:/rtx接收文件/";
		String name ="20171012_user.xls";
		StringBuffer stb = new StringBuffer();
		stb.append("insert into hpay.micro_merch_recommend_info(login_user_id,merchant_code,recommender,recommend_type,recommend_pub_id,create_time,upt_time) ");
		stb.append("values({},'{}','WKZF@WK13564439536',0,'gh_fa29902d73ce',sysdate,sysdate);");
		
		Map<Integer, List<String>> res = ReadOrWriteFileUtil.readExcelFile(path, name);
		System.out.println("==============" + res.size());
		Iterator<Entry<Integer, List<String>>> iter = res.entrySet().iterator();
		
		String userId = null;
		String merCode = null;
		StringBuffer need = new StringBuffer();
		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next();
			Integer key = (Integer) entry.getKey();
			if (1 == key) {
				continue;
			}
			@SuppressWarnings("unchecked")
			List<String> rowV = (List<String>) entry.getValue();
			userId = rowV.get(1);
			merCode = rowV.get(45);
			need.append(KiddStringUtils.replace(stb.toString(),userId,merCode));
			need.append("\r\n");
		}
		
		ReadOrWriteFileUtil.writeStr(path, need.toString(), "need.sql");
	}
}
