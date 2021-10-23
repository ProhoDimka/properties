package pro.dimka.properties.models;

import java.util.Map;
import java.util.Properties;

public interface AppProperties {
    Properties getProperties(ConfigName configName);
    Map<ConfigName,Properties> getAllProperties();
    void setProperies(ConfigName configName, Properties properties);
    void setProperyValue(ConfigName configName, String key, String value);
    String getProperyValue(ConfigName configName, String key);
    void removeProperties(ConfigName configName);
    void removePropertyValue(ConfigName configName, String key);
    boolean isPropertiesSet(ConfigName configName);
}
