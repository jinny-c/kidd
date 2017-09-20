
package com.kidd.base.common.enums;

import java.io.File;

public enum KiddSymbolEnum {

	/** #号 **/
	Well("#", "#号"),
	/** 空字符串 **/
	Blank("", "空字符串"),
	/** 空格 **/
	Space(" ", "空格"),
	/** 短横线 **/
	Line("-", "短横线"),
	/** 中括号加短横线 **/
	BracketAndLine("[-]", "中括号加短横线"),
	/** 竖线 **/
	Colon("|", "竖线"),
	/** \\|-斜杠+竖线 **/
	ColonCheck("\\|", "斜杠+竖线"),
	/** [|]-中括号加竖线 **/
	BracketAndColon("[|]", "中括号+竖线"),
	/** *号 **/
	Star("*", "*号"),
	/** 点 **/
	Point(".", "点"),
	/** 左中括号 **/
	LeftMidBracket("[", "左中括号"),
	/** 右中括号 **/
	RightMidBracket("]", "右中括号"),
	/** 冒号 **/
	MaoHao(":", "冒号"),
	/** 逗号 **/
	Comma(",", "逗号"),
	/** 句号 **/
	StopCode("。", "句号"),
	/** 下划线 **/
	Underline("_", "下划线"),
	/** 加号 **/
	Add("+", "加号"),
	/** 双竖线 **/
	DoubleColon("||", "双竖线"),
	/** 斜杠 **/
	Slash("/", "斜杠"),
	/** 反斜杠 **/
	UntiSlash("\\", "反斜杠"),
	/** 左括号 **/
	LeftBracket("(", "左括号"),
	/** 右括号 **/
	RightBracket(")", "右括号"),
	/** 等于号 **/
	Equals("=", "等于号"),
	/** 不等于号 **/
	NotEquals("!=", "不等于号"),
	/** 分号 **/
	Semicolon(";", "分号"),
	/** 左大括号 **/
	LeftBigBracket("{", "左大括号"),
	/** 右大括号 **/
	RightBigBracket("}", "右大括号"),
	/** &号 **/
	Ampersand("&", "&号"),
	/** 百分号 **/
	Percentage("%", "百分号"),
	/** @号 **/
	EmailCode("@", "@号"),
	/** ￥符号 **/
	RmbCode("￥", "￥符号"),
	/** $符号 **/
	UsCode("$", "$符号"),
	/** 感叹号 **/
	Exclamation("!", "感叹号"),
	/** 双引号 **/
	DubboQuotaCode("\"", "双引号"),
	/** 单引号 **/
	QuotaCode("\'", "单引号"),
	/** 单引号 **/
	SigleRefer("'", "单引号"),
	/** 问号 **/
	QuestionCode("?", "问号"),
	/** 换行 **/
	WRAP("\n", "换行"),
	/** 回车 **/
	ENTER("\r", "回车"),
	/** 文件分割符 **/
	FILE_SEPARATOR(File.separator, "文件分割符 "),

	;

	/** 符号 **/
	private String symbol;
	/** 描述 **/
	private String desc;

	private KiddSymbolEnum(String symbol, String desc) {
		this.symbol = symbol;
		this.desc = desc;
	}

	public String symbol() {
		return symbol;
	}

	public String desc() {
		return desc;
	}

}
