package framework;

import java.sql.ResultSet;

/**
 * Created by ericens on 2017/3/25.
 */
public interface ResultHandler {

    public Object handle(ResultSet rs) ;
}
