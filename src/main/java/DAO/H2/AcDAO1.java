package DAO.H2;

import entity.AC;
import framework.ResultHandlerBean;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ericens on 2017/3/26.
 *
 * 所有方法 都是单独的处理,方法开启连接,然后关闭连接, 消耗大
 * 没有事务,
 */
public class AcDAO1 {

    public void insertTest(){
        String sql="insert into account(name,money) values(?,?)";
        H2Utils.update(sql,new Object[]{"zlx",123.2f});

    }


    public Object select(int id){
        String sql="select * from account  where id= ?";

        ResultHandlerBean handler=new ResultHandlerBean(AC.class);
        AC ac = (AC) H2Utils.query(sql,new Object[]{id},handler);
        return  ac;


    }
//
//    所有方法 都是单独的处理,方法开启连接,然后关闭连接, 消耗大
//    没有事务,
//
    public int add() throws SQLException {
        String sql="insert into account(name,money) values(?,?)";

        QueryRunner queryRunner=new  QueryRunner();
        Connection con= H2Utils.getConnnec();

        int insert = queryRunner.update(con, sql, new Object[]{"zlx2", 123.4f});
        System.out.print("key  "+insert);
        con.close();
        return insert;

    }

    public void update_add(Object param[])  {
        Connection con=null;
        try {
            String sql="update account set money=money + 222  where id =? ";
            QueryRunner queryRunner=new  QueryRunner();
            con= H2Utils.getConnnec();

            int insert = queryRunner.update(con, sql,param);
            System.out.print("key  "+insert);
            con.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if(con!=null)

                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    public void update_delete(Object param[]) {
        Connection con=null;
        try {
            String sql="update account set money=money - 222  where id =? ";
            QueryRunner queryRunner=new  QueryRunner();
            con= H2Utils.getConnnec();

            int insert = queryRunner.update(con, sql,param);
            con.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if(con!=null)
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    public int delete() throws SQLException {
        String sql="insert into account where id = ?)";

        QueryRunner queryRunner=new  QueryRunner();
        Connection con= H2Utils.getConnnec();

        int insert = queryRunner.update(con, sql, new Object[]{14});
        con.close();
        return insert;

    }
}
