package cn.bao.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sun.org.apache.xpath.internal.objects.XNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {
    private static Connection connection;
    private static PreparedStatement statement;

    private static ComboPooledDataSource pool = new ComboPooledDataSource();

    public static Connection getConnection() {
        try {
            connection = pool.getConnection();
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PreparedStatement getStatement(String sql,Object... objects) {
        try {
            if (connection == null || connection.isClosed()){
                return getConnection()==null?null:getStatement(sql,objects);
            }else{
                statement = connection.prepareStatement(sql);
                if (objects!=null&&objects.length>0){
                    for (int i =0 ;i < objects.length;i++){
                        statement.setObject(i+1,objects[i]);
                    }
                    return statement;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getResultSet(String sql,Object... objects){
        if (statement == null ){
            return getStatement(sql,objects)==null?null:getResultSet(sql,objects);
        }else {
            try {
                return statement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int update(String sql,Object... objects){
        if (statement == null ){
            return getStatement(sql,objects)==null?null:update(sql,objects);
        }else {
            try {
                return statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public void close(){
        if (connection == null)return;

        try {
            if (!connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
