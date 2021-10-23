package pro.dimka.properties.services.dao;

public interface ApplicationParamsLoader {

    /**
     * Load params from source to properties
     * */
    void loadApplicationParams();

    /**
     * Load params from source to properties
     * @param keyLike   for example, "select key, value from app_properties where app = 'myApp' and key like '%keyLike%'"
     * */
    void loadApplicationParams(String keyLike);

}
