package com.kidd.test.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.kidd.base.common.utils.ToStringUtils;
/**
 * @description bean的toString
 *
 * @auth chaijd
 * @date 2021/12/24
 */
public class TestBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private String stId;
	@Getter
	@Setter
	private Integer intId;
	@Getter
	@Setter
	private Long LnId;

	@Override
	public String toString() {
		ToStringUtils builder = new ToStringUtils(this);
		builder.add("stId", stId).add("intId", intId)
				.add("LnId", LnId);
		return builder.toString();
	}

}
