package pro.dimka.properties.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;

public abstract class AbstractAppProperties implements AppProperties {
    private static final Logger logger = LoggerFactory.getLogger(AbstractAppProperties.class);

    public abstract Map<ConfigName, Properties> getPropertiesMap();

    @Override
    public Properties getProperties(ConfigName configName) {
        return getPropertiesMap().get(configName);
    }

    @Override
    public Map<ConfigName, Properties> getAllProperties() {
        return getPropertiesMap();
    }

    @Override
    public void setProperies(ConfigName configName, Properties properties) {
        if (getPropertiesMap().containsKey(configName)) {
            getPropertiesMap().remove(configName);
        }
        getPropertiesMap().put(configName, properties);
    }

    @Override
    public void setProperyValue(ConfigName configName, String key, String value) {
        //TODO: надо написать тесты
        if (getPropertiesMap().containsKey(configName)) {
            if (getPropertiesMap().get(configName).getProperty(key) == null) {
                getPropertiesMap().get(configName).setProperty(key, value);
            }  else {
                if (!getPropertiesMap().get(configName).getProperty(key).equals(value)) {
                    getPropertiesMap().get(configName).remove(key);
                    getPropertiesMap().get(configName).setProperty(key, value);
                    logger.info("Для конфигурации '{}' обновлен ключ '{}' со значнием '{}'", configName, key, value);
                }
            }
        } else {
            Properties properties = new Properties();
            properties.put(key, value);
            getPropertiesMap().put(configName, properties);
            logger.info("Добавлена конфигурация '{}' и ключ '{}' со значением '{}'", configName, key, value);
        }
    }

    @Override
    public String getProperyValue(ConfigName configName, String key) {
        String value = null;
        return getPropertiesMap().get(configName).getProperty(key);
    }

    @Override
    public void removeProperties(ConfigName configName) {
        getPropertiesMap().remove(configName);
    }

    @Override
    public void removePropertyValue(ConfigName configName, String key) {
        if (getPropertiesMap().containsKey(configName)) {
            if (getPropertiesMap().get(configName).getProperty(key) != null)
                getPropertiesMap().get(configName).remove(key);
        }
    }

    @Override
    public boolean isPropertiesSet(ConfigName configName) {
        return getPropertiesMap().containsKey(configName);
    }
}
