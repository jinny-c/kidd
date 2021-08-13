package com.kidd.test;

import com.google.common.base.Joiner;
import com.kidd.base.common.serialize.KiddFastJsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class MyJavaTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		tsJoin();
	}

	/**
	 * 字符串拼接
	 */
	public static void tsJoin(){
		Map<String,String> mp = new HashMap<>();
		mp.put("a","111");
		mp.put("b","222");
		mp.put("c","333");
		String sj = Joiner.on("&").withKeyValueSeparator("=").join(mp);
		System.out.println(sj);

		System.out.println(KiddFastJsonUtils.toStr("1234"));

		Map<String,String> mp1 = new HashMap<>();
		mp1.put("a",KiddFastJsonUtils.toStr("111"));
		mp1.put("b",KiddFastJsonUtils.toStr("222"));
		mp1.put("c",KiddFastJsonUtils.toStr("333"));

		System.out.println(Joiner.on("&").withKeyValueSeparator("=").join(mp1));

	}

	public static void tsStJoin(){
		StringJoiner sjn = new StringJoiner("=","","");
		System.out.println(sjn.add("123").add("456"));

		System.out.println(StringUtils.join("11","22","33"));
		System.out.println(StringUtils.join(new Object[]{"11","22","33"},"-"));

	}
}
