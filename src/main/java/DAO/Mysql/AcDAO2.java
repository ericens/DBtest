package DAO.Mysql;

import DAO.Mysql.util.MysqlUtils;
import entity.AC;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

/**
 * Created by ericens on 2017/3/26.
 * 把 事务提升到  service.
 */
public class AcDAO2 {
    Connection con;

    Logger logger= Logger.getLogger(AcDAO2.class);

    public  AcDAO2(Connection con){
        this.con=con;
    }


    public void update_add(Object param[]) throws SQLException {
        try {
            String sql="update account set money=money + 1  where id =? ";
            QueryRunner queryRunner=new  QueryRunner();
            int insert = queryRunner.update(con, sql,param);
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }


    }

    public void update_delete(Object param[]) throws SQLException {
        try {
            String sql="update account set money=money - 1  where id =? ";
            QueryRunner queryRunner=new  QueryRunner();
            int insert = queryRunner.update(con, sql,param);
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }

    }


    public List getAll() throws SQLException {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from account";
        List list = (List) qr.query(con,sql, new BeanListHandler(AC.class));
        return list;
    }


    /**
     * Created by ericens on 2017/3/26.
     * <p>
     * 在此进行事务 控制
     */
    public static class AcService_ThradSaveTransaction {
        /*
        * tranfer the amt from ida to idb
        *  事务管理
        * */
        public void transer(int ida, int idb, double amt) {
            try {
                MysqlUtils.startTransaction();
                AcDaoThreadSave x = new AcDaoThreadSave();
                x.update_delete(new Object[]{ida});
    //            int y=1/0;   //模拟 异常
                x.update_add(new Object[]{idb});
                MysqlUtils.commit();
            } catch (Exception e) {

                /*
                * 不写rollbak 则是自动回滚,也就是没有提交
                * 协商 rollbak  则 手动回滚
                * */

                e.printStackTrace();
            } finally {
                MysqlUtils.close();
            }

        }


        /*
       * tranfer the amt from ida to idb
       *  savepoint 测试
       * */
        public void transer_savepoint(int ida, int idb, int idc, double amt) {
            Savepoint savepoint = null;
            try {
                MysqlUtils.startTransaction();

                AcDaoThreadSave x = new AcDaoThreadSave();
                //sql  a
                x.update_delete(new Object[]{ida});
                savepoint = MysqlUtils.getMysqlConnection().setSavepoint();

                // sqlb
                x.update_add(new Object[]{idb});
                int y = 1 / 0;   //模拟 异常

                //sql c
                x.update_add(new Object[]{idc});
                MysqlUtils.getMysqlConnection().commit();

            } catch (Exception e) {


                MysqlUtils.rollback(savepoint);
                //回滚了要记得通知数据库提交事务 , 不然也是没有提交
                MysqlUtils.commit();
                e.printStackTrace();
            } finally {
                MysqlUtils.close();
            }

        }

        public List getALL() {
            Connection con = MysqlUtils.getMysqlConnection();
            AcDAO2 x = new AcDAO2(con);
            try {
                return x.getAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            MysqlUtils.close();

            return null;
        }
    }
}
