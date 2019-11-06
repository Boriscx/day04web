package cn.bao.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {
    private static Connection connection;
    private PreparedStatement statementUpdate;
    private PreparedStatement statementQuery;

    private static ComboPooledDataSource pool = new ComboPooledDataSource();

    public Connection getConnection() {
        try {
            connection = pool.getConnection();
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PreparedStatement getStatement(String sql, Object... objects) {
        PreparedStatement statement = null;
        try {
            if (connection == null || connection.isClosed()) {
                return getConnection() == null ? null : getStatement(sql, objects);
            } else {
                statement = connection.prepareStatement(sql);
                if (objects != null && objects.length > 0) {
                    for (int i = 0; i < objects.length; i++) {
                        statement.setObject(i + 1, objects[i]);
                    }
                    return statement;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getResultSet(String sql, Object... objects) {
        if (statementQuery == null) {
            statementQuery = getStatement(sql, objects);
            return statementQuery == null ? null : getResultSet(sql, objects);
        } else {
            try {
                return statementQuery.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int update(String sql, Object... objects) {
        if (statementUpdate == null) {
            statementUpdate = getStatement(sql, objects);
            return statementUpdate == null ? null : update(sql, objects);
        } else {
            try {
                return statementUpdate.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public void closeStatement(PreparedStatement statement) {
        if (statement == null) return;
        try {
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement = null;
        }
    }
}
