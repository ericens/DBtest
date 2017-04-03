package DAO.Mysql;

import DAO.Mysql.util.MysqlUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

/**
 * Created by ericens on 2017/3/26.
 * <p>
 * 在此进行事务 控制
 */
public class AcService_withTransaction {
    /*
    * tranfer the amt from ida to idb
    *  事务管理
    * */

    Logger logger= Logger.getLogger(AcService_withTransaction.class);

    public void transer(int ida, int idb, double amt) {
        logger.info("ida: "+ida+ "   idb:"+idb+"    amt: "+amt);

        Connection con = null;
        try {
            con = MysqlUtils.getMysqlConnection();
            con.setAutoCommit(false);
            AcDAO2 x = new AcDAO2(con);
            x.update_delete(new Object[]{ida});
//            int y=1/0;   //模拟 异常
            x.update_add(new Object[]{idb});
            con.commit();
        } catch (Exception e) {

            /*
            * 不写rollbak 则是自动回滚,也就是没有提交
            * 协商 rollbak  则 手动回滚
            * */

            e.printStackTrace();
        } finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

    }


    /*
   * tranfer the amt from ida to idb
   *  savepoint 测试
   * */
    public void transer_savepoint(int ida, int idb, int idc, double amt) {
        Connection con = null;
        Savepoint savepoint=null;
        try {
//            con = MysqlUtils.getConnnec();
            con= MysqlUtils.getMysqlConnection();
            con.setAutoCommit(false);

            AcDAO2 x = new AcDAO2(con);
            //sql  a
            x.update_delete(new Object[]{ida});
            savepoint = con.setSavepoint();

            // sqlb
            x.update_add(new Object[]{idb});
            int y = 1 / 0;   //模拟 异常

            //sql c
            x.update_add(new Object[]{idc});
            con.commit();

        } catch (Exception e) {

            /*
            * 不写rollbak 则是自动回滚,也就是没有提交
            * 协商 rollbak  则 手动回滚
            * */
            try {
                /*
                con.rollback();
                这样回滚是全部回滚,而不管是否是有savepoint

                */

                con.rollback(savepoint);
                //回滚了要记得通知数据库提交事务 , 不然也是没有提交
                con.commit();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
