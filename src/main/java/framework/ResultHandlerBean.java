package framework;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * Created by ericens on 2017/3/25.
 */
public class ResultHandlerBean implements ResultHandler {
    Class c;

    public  ResultHandlerBean(Class c){
        this.c=c;
    }
    public Object handle(ResultSet rs)  {
        Object bean=null;
        try {
            if(!rs.next())
                return  null;
            bean=c.newInstance();
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            // i 从1 开始
            for (int i=1;i<=numberOfColumns;i++){
                String columnName=rsmd.getColumnName(i).toLowerCase();
                System.out.println("colname:  "+columnName);
                Object o=rs.getObject(i);
                // 这个才可以获取  所有定义的field,     c.getField()  只是获取了 public 的方法
                Field f=c.getDeclaredField(columnName);
                f.setAccessible(true);

                f.set(bean,o);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bean;
    }
}
