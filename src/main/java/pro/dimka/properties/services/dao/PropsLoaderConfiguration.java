package pro.dimka.properties.services.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Read properties from properties file
 * */

public class PropsLoaderConfiguration {

    /**
     * Returns properties from properties file whose readed from resources
     * @param file  file in resources
     * @return  properties from file
     * */
    public static Properties getPropertiesFromResources(String file) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try(InputStream resourceStream = loader.getResourceAsStream(file)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            throw e;
        }
        return properties;
    }

    /**
     * Returns properties from file in filesystem
     * @param file  file in filesystem
     * @return  properties from file
     * */
    public static Properties getPropertiesFromFileSystem(String file) throws IOException {
        Properties properties = new Properties();
        try(InputStream resourceStream = new FileInputStream(file)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            throw e;
        }
        return properties;
    }
}
