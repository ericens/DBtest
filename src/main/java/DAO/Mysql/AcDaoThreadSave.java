package DAO.Mysql;

import DAO.Mysql.util.MysqlUtils;
import entity.AC;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ericens on 2017/3/26.
 * 把 事务提升到  service.
 */
public class AcDaoThreadSave {


    public void update_add(Object param[]) throws SQLException {
        try {
            String sql="update account set money=money + 1  where id =? ";
            QueryRunner queryRunner=new  QueryRunner();
            int insert = queryRunner.update(MysqlUtils.getMysqlConnection(), sql,param);
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }


    }

    public void update_delete(Object param[]) throws SQLException {
        try {
            String sql="update account set money=money - 1  where id =? ";
            QueryRunner queryRunner=new  QueryRunner();
            int insert = queryRunner.update( MysqlUtils.getMysqlConnection(), sql,param);
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }

    }


    public List getAll() throws SQLException {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from account";
        List list = (List) qr.query(MysqlUtils.getMysqlConnection(),sql, new BeanListHandler(AC.class));
        return list;
    }


}
