package com.example.util;

import java.sql.*;

public abstract class ConnectionPool {  //应该至少3种连接池，一种是缓冲线程池，一种指定最大数量，最后一种是单连接池。类似于线程池
    private int connectionNum;
    private Connection[] pool;

    ConnectionPool(int connectionNum){
        this.connectionNum=connectionNum>1?connectionNum:1;  //连接数至少为1
    }

    public Connection getConnection(){
        return null;
    }
    
}
