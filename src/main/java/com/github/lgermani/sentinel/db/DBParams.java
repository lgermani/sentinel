package com.github.lgermani.sentinel.db;

import org.apache.commons.lang.StringUtils;

public class DBParams {

    private String user;
    private String password;
    private String address;
    private String port;
    private String dbName;

    private DBParams() {
        if (StringUtils.isNotBlank(System.getenv("db.user"))) {
            user = System.getenv("db.user");
        } else {
            user = "root";
        }

        if (StringUtils.isNotBlank(System.getenv("db.password"))) {
            password = System.getenv("db.password");
        } else {
            password = "password";
        }

        if (StringUtils.isNotBlank(System.getenv("db.address"))) {
            address = System.getenv("db.address");
        } else {
            address = "localhost";
        }

        if (StringUtils.isNotBlank(System.getenv("db.port"))) {
            port = System.getenv("db.port");
        } else {
            port = "3306";
        }

        if (StringUtils.isNotBlank(System.getenv("db.dbName"))) {
            dbName = System.getenv("db.dbName");
        } else {
            dbName = "Employees";
        }

    }

    //Singleton implementation
    private static DBParams instance;

    public static DBParams getInstance(){
        if (instance == null) {
            instance = new DBParams();
        }
        return instance;
    }



    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPort() {
        return port;
    }

    public String getDbName() {
        return dbName;
    }
}
