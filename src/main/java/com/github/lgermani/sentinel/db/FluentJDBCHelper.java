package com.github.lgermani.sentinel.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;

import javax.sql.DataSource;


/**
 * Helper class for use Fluent-jdbc: https://github.com/zsoltherpai/fluent-jdbc
 */
public class FluentJDBCHelper {

    private FluentJdbc fluentJdbc;

    public FluentJDBCHelper() {
        fluentJdbc = new FluentJdbcBuilder()
                .connectionProvider(createDataSource())
                .build();
    }

    public FluentJDBCHelper(DataSource dataSource) {
        fluentJdbc = new FluentJdbcBuilder()
                .connectionProvider(dataSource)
                .build();
    }

    public Query query(){
        return fluentJdbc.query();
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
