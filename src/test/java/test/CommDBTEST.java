package test;

import DAO.H2.AcDAO1;
import DAO.Mysql.AcDAO2;
import entity.AC;
import org.apache.log4j.Logger;
import org.junit.Test;
import DAO.Mysql.AcService_withTransaction;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ericens on 2017/3/26.
 */
public class CommDBTEST {

    Logger logger= Logger.getLogger(CommDBTEST.class);

    @Test
    public void Test() throws SQLException {
        AcDAO1 dao=new AcDAO1();
        Object x= dao.add();

        logger.info("x:"+x);
        System.out.println(x);

    }

    @Test
    public void Serverice1Test() throws SQLException {
        logger.info("Serverice1Test.......start");
        AcService_withTransaction dao=new AcService_withTransaction();
        dao.transer(1,2,222);
        logger.info("Serverice1Test.......end");

    }

    @Test
    public void Serverice2Test() throws SQLException {
        AcService_withTransaction dao=new AcService_withTransaction();
        dao.transer(1,2,222);

    }

    @Test
    public void Serverice2_savePointTest() throws SQLException {
        AcService_withTransaction dao=new AcService_withTransaction();
        List<AC> list=dao.getALL();
        for (AC ac:list
             ) {
            System.out.println(ac);

        }
        dao.transer_savepoint(1,2,3,1);

        list=dao.getALL();
        for (AC ac:list
                ) {
            System.out.println(ac);

        }

    }

    @Test
    public void threadSave_savePointTest() throws SQLException {
        AcDAO2.AcService_ThradSaveTransaction dao=new AcDAO2.AcService_ThradSaveTransaction();
        List<AC> list=dao.getALL();
        for (AC ac:list
                ) {
            System.out.println(ac);

        }
        dao.transer_savepoint(1,2,3,1);

        list=dao.getALL();
        for (AC ac:list
                ) {
            System.out.println(ac);

        }

    }
}
