package DAO.H2;

import framework.ResultHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Created by ericens on 2017/3/25.
 */
public class H2Utils {
    //使用ThreadLocal存储当前线程中的Connection对象
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    static String driver1 = "org.h2.Driver";
    static String url="jdbc:h2:tcp://localhost/~/test";
    static String name="sa";
    static String pwd="";
    static {


        try {
            Class.forName(driver1);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    public static Connection getConnnec() throws SQLException {
        Connection conn = threadLocal.get();
        if(conn==null) {
            conn = DriverManager.getConnection(url, name, pwd);
            threadLocal.set(conn);

        }
        return conn;
    }



    /*
               获取属性名
               int type=ps.getParameterMetaData().getParameterType(i);
               String typxe=ps.getParameterMetaData().getParameterTypeName(i);
               //获取的不是属性名, 而是varchar  类型
               而且 i应该是 1 开始,而不是 0 开始
              */
    public static void update(String sql, Object param[]){
        Connection con=null;
        try {
            con=getConnnec();
            PreparedStatement ps=con.prepareStatement(sql);
            int count= ps.getParameterMetaData().getParameterCount();
            for(int i=0;i<count;i++){
                ps.setObject(i+1,param[i]);
            }
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public  static Object query(String sql, Object para[], ResultHandler rs){
        Connection con=null;
        try {
            con=getConnnec();
            PreparedStatement ps=con.prepareStatement(sql);
            int count= ps.getParameterMetaData().getParameterCount();
            for(int i=0;i<count;i++){
                ps.setObject(i+1,para[i]);
            }
            ps.execute();
            return rs.handle(ps.getResultSet());

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }


}
