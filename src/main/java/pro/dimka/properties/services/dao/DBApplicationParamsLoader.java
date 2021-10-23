package pro.dimka.properties.services.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.dimka.properties.models.*;
import pro.dimka.properties.models.repositories.ParamRepositoryCustom;

import java.util.List;

public class DBApplicationParamsLoader implements ApplicationParamsLoader {
    private static final Logger logger = LoggerFactory.getLogger(DBApplicationParamsLoader.class);

    private final AppProperties appProperties;

    private final ParamRepositoryCustom paramsRepository;

    public DBApplicationParamsLoader(ParamRepositoryCustom paramsRepository, AppProperties appProperties) {
        this.paramsRepository = paramsRepository;
        this.appProperties = appProperties;
    }

    public void loadApplicationParams() {
        List<Param> params = paramsRepository.getParamsByApplicationName();
        for (Param param : params) {
            appProperties.setProperyValue(ConfigName.APPLICATION
                    , param.getKey()
                    , param.getValue()
            );
        }
        /*
            Этот параметр используется для проверки актуатора опеншифт.
            После старта контейнера опеншифт проверяет приложение на готовность балансировать на него нагрузку
            Если этот параметр false, то это одна из причин не включать контейнер в балансировку
        */
        appProperties.setProperyValue(ConfigName.APPLICATION
                , "isDbParamsLoaded", "true");
    }

    public void loadApplicationParams(String sqlKeyLikeExpression) {
        List<Param> params = paramsRepository.getParamsByKeyLikeExpression(sqlKeyLikeExpression);
        for (Param param : params) {
            appProperties.setProperyValue(ConfigName.APPLICATION
                    , param.getKey()
                    , param.getValue()
            );
        }
    }
}
