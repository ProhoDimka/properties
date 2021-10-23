package pro.dimka.properties.models;

import pro.dimka.properties.models.bpp.InjectAppProperties;

import java.util.Map;
import java.util.Properties;

public class CustomAppProperties extends AbstractAppProperties {

    @InjectAppProperties(configs = {ConfigName.APPLICATION})
    private Map<ConfigName, Properties> propertiesMap;

    @Override
    public Map<ConfigName, Properties> getPropertiesMap() {
        return this.propertiesMap;
    }
}
