package com.demo.common.valid.validator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.demo.common.valid.CheckType;

/**
 * 类描述: 自定义枚举类型校验器 
 * 创建人: baiqiufei 
 * 创建时间: 2017/9/30 14:36
 */
public class TypeValidator implements ConstraintValidator<CheckType, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(TypeValidator.class);

    private Class<?> typeClazz;

    /**
     * 系统中使用到的Interface枚举字段缓存，避免反射的性能消耗
     * static方法在系统初次构造TypeValidator对象时触发，有多线程的场景，故使用线程安全的Map
     */
    private static Map<String, List<String>> interfaceFields = new ConcurrentHashMap<String, List<String>>();

    static {
        initInterfaceFields();
    }

    @Override
    public void initialize(CheckType constraintAnnotation) {
        typeClazz = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintContext) {

        List<String> fields = interfaceFields.get(typeClazz.getName());

        if (CollectionUtils.isEmpty(fields)) {
            LOG.error("can't load typeClazz [{}], fieldValue={}", typeClazz.getName(), value);
            return false;
        }
        
        if (value == null) {
            // 是否可为空，由其他注解如NotNull等标记，该注解仅处理有值的场景
            return true;
        } else {
            String valueStr = null;
            if (value instanceof String) {
                valueStr = (String)value;
            } else if (value instanceof Integer) {
                valueStr = String.valueOf((Integer)value);
            } else if (value instanceof Character) {
                valueStr = String.valueOf((Character)value);
            } else {
                LOG.error("CheckType not support this field type, clazz={}, fieldValue={}", typeClazz.getName(), value);
            }
            
            if (StringUtils.isEmpty(valueStr)) {
                // 传入空字符串  也认为合法
                return true;
            } else {
                return fields.contains(valueStr);
            }
        }
        
    }

    /**
     * 遍历类路径下所有CheckType注解中的Interface类，缓存其静态变量
     */
    private static void initInterfaceFields() {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory();
        try {
            String path = ClassUtils.convertClassNameToResourcePath("com.migu.rstone");
            Resource[] res = resolver.getResources("classpath*:" + path + "/**/*.class");
            for (Resource r : res) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(r);
                ClassMetadata clazzMeta = metadataReader.getClassMetadata();

                Class<?> clazz;
                Class<?> interfaceClazz;
                try {
                    clazz = Class.forName(clazzMeta.getClassName());
                    // System.out.println(clazz);
                    for (Field field : clazz.getDeclaredFields()) {
                        if (field.isAnnotationPresent(CheckType.class)) {
                            interfaceClazz = field.getAnnotation(CheckType.class).type();

                            loadFields(interfaceClazz);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    LOG.error("fail to initInterfaceFields, ", e);
                }
            }
        } catch (IOException e) {
            LOG.error("fail to initInterfaceFields, ", e);
        }
    }

    /**
     * 
     * 加载Interface中定义的枚举值到缓存中
     */
    private static void loadFields(Class<?> interfaceClazz) {
        String clazzName = interfaceClazz.getName();
        if (interfaceFields.containsKey(clazzName)) {
            return;
        }

        Field[] fields = interfaceClazz.getDeclaredFields();

        List<String> fieldList = new ArrayList<String>(fields.length);
        for (Field field : fields) {
            try {
                Object value = field.get(null);
                if (value instanceof String) {
                    fieldList.add((String)value);
                } else if (value instanceof Integer) {
                    fieldList.add(String.valueOf((Integer)value));
                } else if (value instanceof Character) {
                    fieldList.add(String.valueOf((Character)value));
                } else {
                    LOG.error("invalid interface field type, fieldName={}, fieldValue={}", field, value);
                }
                
            } catch (Exception e) {
                LOG.error("fail to loadFields, ", e);
            }
        }

        interfaceFields.put(clazzName, fieldList);
    }
}