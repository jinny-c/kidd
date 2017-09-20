package com.kidd.base.spring.redefine;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.kidd.base.annotation.proc.KiddSecureProcessor;
import com.kidd.base.exception.KiddGlobalValidException;
import com.kidd.base.http.RequestResponseContext;
import com.kidd.base.serialize.KiddSerialTypeEnum;
import com.kidd.base.spring.modelview.KiddModelAndView;
import com.kidd.base.utils.KiddObjectTypeUtils;
import com.kidd.base.utils.KiddStringUtils;

@SuppressWarnings("rawtypes")
public class KiddObjHttpMsgConvert extends
		AbstractHttpMessageConverter<KiddModelAndView> {
	
	private static Logger logger = LoggerFactory.getLogger(KiddObjHttpMsgConvert.class);

	@Autowired
	private KiddSecureProcessor kiddSecureProcessor;

	private static String CHARSET = "UTF-8";

	public KiddObjHttpMsgConvert() {
		/*this(new MediaType("text", "plain", Charset.forName("UTF-8")),
				new MediaType("json", "application", Charset.forName("UTF-8")),
				new MediaType("xml", "application", Charset.forName("UTF-8")));*/
		this(MediaType.TEXT_PLAIN,
				MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_XML);
	}

	public KiddObjHttpMsgConvert(MediaType... supportedMediaTypes) {
		super(supportedMediaTypes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.http.converter.AbstractHttpMessageConverter#supports
	 * (java.lang.Class)
	 */
	@Override
	protected boolean supports(Class<?> clazz) {
		return KiddModelAndView.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.http.converter.AbstractHttpMessageConverter#readInternal
	 * (java.lang.Class, org.springframework.http.HttpInputMessage)
	 */
	@Override
	protected KiddModelAndView readInternal(
			Class<? extends KiddModelAndView> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		return super.read(clazz, inputMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.http.converter.AbstractHttpMessageConverter#writeInternal
	 * (java.lang.MicroModelAndView, org.springframework.http.HttpOutputMessage)
	 */
	@Override
	protected void writeInternal(KiddModelAndView model,
			HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		logger.info("KiddObjHttpMsgConvert.writeInternal start");
		
		if (model == null ) {
			logger.error("the httpobj or type is nll,url={}",
					RequestResponseContext.getRequest().getRequestURL());
			throw new HttpMessageNotWritableException("response error");
		}
		Object data = model.getData();
		// 加密处理
		try {
			logger.debug("resp encr judge start");
			this.encr(data);
		} catch (KiddGlobalValidException e) {
			/*String respMsg = MicroResponseUtils.toErr(RequestResponseContext.getRequest(),
					e.getErrorCode(), e.getErrorMsg());
			outputMessage.getBody().write(respMsg.getBytes(HpayCharset.UTF8.getValue()));*/
			return;
		}
		List<MediaType> mList = getSupportedMediaTypes();
		logger.debug("MediaType list={}",mList);
		
		KiddSerialTypeEnum mste = model.getTe();
		String msg = mste.toStr(data);

		if (KiddStringUtils.isBlank(msg)) {
			logger.error("the http data is null,url={}", RequestResponseContext
					.getRequest().getRequestURL());
			throw new HttpMessageNotWritableException("response null");
		}

		/*if (mste.isJson() || mste.isFastJson()) {
			//HINT 打印返回参数
			//logger.info("clientResponseData: " + model);
			JSONObject json = (JSONObject) JSONObject.toJSON(model);//将java对象转换为json对象
			String str = json.toString();//将json对象转换为字符串
			logger.info("clientResponseData:={}", str);
			//outputMessage.getBody().write(dataCompile(data));
			//outputMessage.getBody().write(msg.getBytes(CHARSET));
			outputMessage.getBody().write(str.getBytes(CHARSET));
            return;
		}*/
		//HINT 打印返回参数(JSON)
		logger.info("serialType={},clientResponseData:={}", mste.serilalType(),
				msg);
		outputMessage.getBody().write(msg.getBytes(CHARSET));
	}

	/**
	 * 加密处理
	 * 
	 * @param data
	 * @throws MicroGlobalValidException
	 */
	private void encr(Object data) throws KiddGlobalValidException {
        encr(data, data.getClass());
	}


    /**
     * 加密处理
     *
     * @param data
     * @throws MicroGlobalValidException
     */
    private void encr(Object data, Class<? extends Object> clazz) throws KiddGlobalValidException {
        // 2016-09-20 处理父类中需要加密的属性字段
        for(; clazz != Object.class; clazz = clazz.getSuperclass()) {
            if (!kiddSecureProcessor.isSecure(clazz)) {
                return;
            }
            PropertyDescriptor pd = null;
            Method reader = null;
            Method writer = null;
            /*MicroHttpHeader header = KiddSecureProcessor.getHeader();
            UserKeyBean userKey = KiddSecureProcessor.getUserKey(
                    header.getVersion(), header.getTerminalUserID());*/
            try {
                Object v = null;
                for (Field field : clazz.getDeclaredFields()) {
                    if (field == null) {
                        continue;
                    }

                    if (!kiddSecureProcessor.isEncr(field)) {
                        continue;
                    }
                    // 2016-09-20 递归处理被MicroEncrAnno注解标识的集合
                    if(KiddObjectTypeUtils.isCollections(field.getType())){
                        field.setAccessible(true);
                        List<?> list = (List<?>) field.get(data);
                        for (Object o : list) {
                            encr(o);
                        }
                    }else{
                        pd = new PropertyDescriptor(field.getName(), clazz);
                        reader = pd.getReadMethod();
                        if (reader == null) {
                            continue;
                        }
                        v = reader.invoke(data);
                        if (v == null) {
                            continue;
                        }
                        logger.info("KiddSecureProcessor encrypt field=[{}],value=[{}]",field.getName(),String.valueOf(v));
                        v = kiddSecureProcessor.encrypt("key", field.getName(), String.valueOf(v),
                                "version", "sessionId");
                        writer = pd.getWriteMethod();
                        if (writer == null) {
                            continue;
                        }
                        writer.invoke(data, v);
                    }
                }
            } catch (Exception e) {
                logger.error("resp data encr fail", e);
            }
        }
    }
}
