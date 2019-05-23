package com.demo.common.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.demo.common.consts.CommonResultCode;
import com.demo.common.exception.BizException;

/**
 * 类描述: 本类是JaxbUtil工具类
 */
public class JaxbUtil {
	
	/**
     * xml前缀
     */
    public static final String XML_PREFIX = "<?xml";

    private static final String XML_REGEX = "(?<=>)\\s+(?=<)";

	private static final String ENTER_REGEX = "\r|\n";

    private static final String EMPTY = "";

	static Map<String, JAXBContext> jaxbContextMap = new HashMap<String, JAXBContext>();
	
	private static final Logger LOG = LoggerFactory.getLogger(JaxbUtil.class);
	/**
	 * JavaBean 转换成 xml 默认编码UTF-8
	 * @param obj 需要转换的 JavaBean
	 * @return 转换后的xml
	 */
	public static String convertToXml(final Object obj) {
		return convertToXml(obj, "UTF-8", true);
	}

	/**
	 * JavaBean 转换成 xml 默认编码UTF-8，不格式化
	 * @param obj 需要转换的 JavaBean
	 * @return 转换后的xml
	 */
	public static String convertToNonFormattedXml(final Object obj) {
		return convertToXml(obj, "UTF-8", false);
	}

	/**
	 * 获得 class 对应的 JAXBContext
	 * 
	 * @param c class
	 * @return 对应的 JAXBContext
	 * @throws JAXBException
	 */
	private static <T> JAXBContext getJAXBContextFor(final Class<T> c) throws JAXBException {
		String className = c.getName();
		
		JAXBContext jaxbContext = jaxbContextMap.get(className);
		if (jaxbContext == null) {
			jaxbContext = JAXBContext.newInstance(c);
			jaxbContextMap.put(className, jaxbContext);
		}
		
		return jaxbContext;
	}

	/**
	 * JavaBean 转换成 xml
	 * @param obj 需要转换的 JavaBean
	 * @param encoding 编码
	 * @param needFormat 是否需要格式化
	 * @return
	 */
	public static String convertToXml(final Object obj, final String encoding, boolean needFormat) {
		String result = null;
		try {
			JAXBContext jaxbContext = getJAXBContextFor(obj.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			if (needFormat) {
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			}
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (Exception e) {
			LOG.error("JaxbUtil.convertToXml error, obj={}",  obj);
			throw new BizException(CommonResultCode.SYSTEM_ERROR.getCode(), CommonResultCode.SYSTEM_ERROR.getMsg());
		}
		return result;
	}

	/**
	 * xml 转换成 JavaBean
	 *
	 * @param xml 需要转换的 xml
	 * @param c 转换的 JavaBean class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T converyToJavaBean(final String xml, final Class<T> c) {
		T t = null;
		try {
			JAXBContext jaxbContext = getJAXBContextFor(c);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			LOG.error("JaxbUtil.converyToJavaBean error, obj={}",  xml);
			throw new BizException(CommonResultCode.SYSTEM_ERROR.getCode(), CommonResultCode.SYSTEM_ERROR.getMsg());
		}
		return t;
	}

	/**
	 * 去除xml空格和换行符
	 * @author yangliu
	 * @param xmlString xml字符串
	 * @return 格式化xml
	 */
	public static String removeBlank(String xmlString) {
	    if (StringUtils.isEmpty(xmlString)) {
	        return xmlString;
	    }
		String newStr = xmlString.replaceAll(XML_REGEX, EMPTY);
	    //移除换行和回车字符
		return newStr.replaceAll(ENTER_REGEX, EMPTY);
	}
}

