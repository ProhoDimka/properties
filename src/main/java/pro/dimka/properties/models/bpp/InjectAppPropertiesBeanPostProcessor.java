package pro.dimka.properties.models.bpp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import pro.dimka.properties.models.ConfigName;
import pro.dimka.properties.models.ConfigurableConfig;
import pro.dimka.properties.services.dao.PropsLoaderConfiguration;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class InjectAppPropertiesBeanPostProcessor implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(InjectAppPropertiesBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        Map<ConfigurableConfig, Properties> propertiesMap = new ConcurrentHashMap<>();

        for (Field field : fields) {
            InjectAppProperties annotation = field.getAnnotation(InjectAppProperties.class);
            if (annotation != null) {
                ConfigName[] configNames = annotation.configs();
                for (ConfigName configName : configNames) {
                    propertiesMap.put(configName, getProperties(configName));
                }
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, propertiesMap);
            }
        }

        return bean;
    }

    private Properties getProperties(ConfigName configName) {
        // Get Custom config files, example for ift,lt,psi,prod
        Properties properties = null;
        String applicationFileName = System.getProperty(configName.getSystemVariableWithFileName());
        logger.info("Попробовали получить настройку из системной переменной {}", applicationFileName);
        try {
            String applicationPropsFileName = configName.getResourcesFileName();
            if (applicationFileName != null && !applicationFileName.isEmpty()) {
                applicationPropsFileName = applicationFileName;
                properties = PropsLoaderConfiguration.getPropertiesFromFileSystem(applicationPropsFileName);
            } else {
                logger.info("Для конфигурации {} используется настройка по умолчанию {}", configName, applicationPropsFileName);
                properties = PropsLoaderConfiguration.getPropertiesFromResources(applicationPropsFileName);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e.fillInStackTrace());
        }

        return properties;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
