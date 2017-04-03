package DAO.H2;

/**
 * Created by ericens on 2017/3/26.
 */
public class AcService_noTransaction {
    /*
    * tranfer the amt from ida to idb
    * */
    public void transer(int ida, int idb, double amt){
        AcDAO1 x=new AcDAO1();
        x.update_delete(new Object[]{ ida});
        x.update_add(new Object[]{ idb});

    }
}
