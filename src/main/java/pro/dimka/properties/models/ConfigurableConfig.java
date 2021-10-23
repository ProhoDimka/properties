package pro.dimka.properties.models;

public interface ConfigurableConfig {
    String getName();

    /**
     * Returns a config filename.
     * Put files to resources with postfix ".properties"
     * @return  properties file name
     * */
    default String getResourcesFileName() {
        return getName().concat(".properties");
    }

    /**
     * If you set EnvVars with Path to file name it will be read from there
     * @return  name of EnvVar with Path to properties file in file system
     */
    default String getSystemVariableWithFileName() {
        return getName().toUpperCase() + "_CONFIG_FILE";
    }
}
