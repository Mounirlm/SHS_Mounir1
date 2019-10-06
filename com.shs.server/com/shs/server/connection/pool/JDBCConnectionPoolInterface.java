package com.shs.server.connection.pool;

import java.sql.Connection;
import java.sql.SQLException;


// Interface de la pool avec toute les methodes à implémenter dans la pool

public interface JDBCConnectionPoolInterface {
    public Connection getConnection() throws SQLException;
    public boolean releaseConnection(Connection connection)throws SQLException;
    public void shutdownConnections()throws SQLException;
}