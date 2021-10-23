package pro.dimka.properties.models.repositories;

import java.util.List;

import pro.dimka.properties.models.Param;

public interface ParamRepositoryCustom {
    List<Param> getParamsByApplicationName();
    List<Param> getParamsByKeyLikeExpression(String sqlKeyLikeExpression);
}
