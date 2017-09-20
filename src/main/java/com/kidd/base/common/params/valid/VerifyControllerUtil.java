package com.kidd.base.common.params.valid;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.kidd.base.common.KiddBaseReqDto;
import com.kidd.base.common.constant.KiddErrorCodes;
import com.kidd.base.common.exception.KiddControllerException;
import com.kidd.base.common.utils.KiddStringUtils;

/**
 * 验证Controller层DTO工具类
 *
 * @history
 */
public class VerifyControllerUtil {
    private static Logger logger = Logger.getLogger(VerifyControllerUtil.class);
    private final static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    /**
     * 验证是否为空字符串
     *
     * @param parameter 请求参数
     * @param message  错误描述
     */
    public static void assertNotBlank(String parameter, String message) throws KiddControllerException{
        if (KiddStringUtils.isBlank(parameter)) {
            throw new KiddControllerException(KiddErrorCodes.E_KIDD_NULL, message);
        }
    }


    /**
     * 校验客户端请求DTO
     *
     * @history
     */
    public static KiddValidResp validateReqDto(KiddBaseReqDto reqDto) {
        //校验Validator注解约束
    	KiddValidResp validResp = validateObject(reqDto);
        if(validResp.isSucc()){
            return reqDto.valid();
        }
        return validResp;
    }

    /**
     * 请求参数非空、格式验证，请求对象
     *
     * @history
     */
    public static KiddValidResp validateObject(Object object){
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (violations.size() == 0) return new KiddValidResp(true);

        //校验失败
        ConstraintViolation<Object> next = violations.iterator().next();
        Path propertyPath = next.getPropertyPath();
        Iterator<Path.Node> nodeIter = propertyPath.iterator();
        logger.warn("字段[" + nodeIter.next().getName() + "]校验失败");
        if(nodeIter.hasNext()){
            return new KiddValidResp(nodeIter.next().getName(), next.getMessage());
        }
        return new KiddValidResp(KiddErrorCodes.E_KIDD_NULL, next.getMessage());
    }

    /**
     * 多个对象 请求参数非空、格式验证，请求对象
     *
     * @param objects
     * @return
     */
    public static KiddValidResp validateObjects(Object... objects){
    	KiddValidResp resp = new KiddValidResp(true);
        for (Object obj: objects){
            resp = validateObject(obj);
            if(!resp.isSucc()){
                break;
            }
        }
        return resp;
    }

    /**
     * 请求参数校验 指定属性
     *
     * @param object
     * @param properties
     * @return
     */
    public static void validateProperty(Object object, String... properties){
        Validator validator = factory.getValidator();
        for (String property: properties){
            Set<ConstraintViolation<Object>> violations = validator.validateProperty(object, property);
            if (violations.size() == 0)  continue;
            throw new RuntimeException(violations.iterator().next().getMessage());
        }
    }
}