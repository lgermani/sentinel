package com.github.lgermani.sentinel.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;


/**
 * Helper class for use Fluent-jdbc: https://github.com/zsoltherpai/fluent-jdbc
 */
public class FluentJDBCHelper {

    private FluentJdbc fluentJdbc;
    public Query query;

    public FluentJDBCHelper() {
        fluentJdbc = new FluentJdbcBuilder()
                .connectionProvider(createDataSource())
                .build();

        query = fluentJdbc.query();
    }

    public Query query(){
        return query;
    }

    private MysqlDataSource createDataSource(){
        MysqlDataSource dataSource = new MysqlDataSource();

        DBParams dbParams = DBParams.getInstance();
        dataSource.setUser(dbParams.getUser());
        dataSource.setPassword(dbParams.getPassword());

        dataSource.setURL("jdbc:mysql://"+ dbParams.getAddress() +":"+ dbParams.getPort() +"/"+ dbParams.getDbName());
        return dataSource;
    }
}
