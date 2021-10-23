package pro.dimka.properties.models.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import pro.dimka.properties.models.AppProperties;
import pro.dimka.properties.models.ConfigName;
import pro.dimka.properties.models.Param;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ParamsRepositoryImplJdbc extends JdbcDaoSupport implements ParamRepositoryCustom {
    private static final Logger logger = LoggerFactory.getLogger(ParamsRepositoryImplJdbc.class);

    private static final String DEFAULT_TABLE_NAME = "apps_params";
    private String PARAMS_SQL = "SELECT key, value FROM apps_params WHERE application = '%s'";
    private String PARAMS_SQL_KEY_LIKE_EXPRESSION = "SELECT key, value FROM apps_params WHERE application='%s' and key like '%s'";

    private AppProperties appProperties;

    public ParamsRepositoryImplJdbc(AppProperties appProperties, DataSource dataSource) {
        this(appProperties, dataSource, DEFAULT_TABLE_NAME);
    }

    public ParamsRepositoryImplJdbc(AppProperties appProperties, DataSource dataSource, String tableName) {
        this.appProperties = appProperties;
        PARAMS_SQL = String.format(PARAMS_SQL, tableName);
        PARAMS_SQL_KEY_LIKE_EXPRESSION = String.format(PARAMS_SQL_KEY_LIKE_EXPRESSION, tableName);
        setDataSource(dataSource);
    }

    @Override
    public List<Param> getParamsByApplicationName() {
        String sql = String.format(PARAMS_SQL, getAppName());
        return makeRequest(sql);
    }

    @Override
    public List<Param> getParamsByKeyLikeExpression(String sqlKeyLikeExpression) {
        String sql = String.format(PARAMS_SQL_KEY_LIKE_EXPRESSION, getAppName(), sqlKeyLikeExpression);
        return makeRequest(sql);
    }


    private List<Param> makeRequest(String sql) {
        logger.debug("Params SQL: {}", sql);
        return getJdbcTemplate().query(sql, new ParamRowMapper());
    }

    private static class ParamRowMapper implements RowMapper<Param> {
        @Override
        public Param mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Param(rs.getString("key"), rs.getString("value"));
        }
    }

    private String getAppName() {
        return appProperties.getProperyValue(ConfigName.APPLICATION, "application");
    }
}
