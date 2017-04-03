import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by ericens on 2017/3/27.
 */
public class zipTest {

    public void bruteForceCompress(String infileStr,String outFileStr) throws Exception {
        long start=System.currentTimeMillis();
        System.out.println("the gzip operaton is begin");
        System.out.println("infileStr :"+infileStr);
        System.out.println("outFileStr :"+ outFileStr);


        FileInputStream fileInputStream=new FileInputStream(infileStr);

        FileOutputStream fileOutputStream=new FileOutputStream(outFileStr);

        GZIPOutputStream gzipOutputStream=new GZIPOutputStream(fileOutputStream);
        byte buffer[]=new byte[8096];
        int count=0;
        while( (count=fileInputStream.read(buffer)) >0){
            gzipOutputStream.write(buffer,0,count);
        }
        long end=System.currentTimeMillis();
        System.out.println("the gzip operaton is done" + (end-start));
    }
    @Test
    public void testzip() throws Exception {
        String in="/Users/ericens/Documents/youdao_note_20160820.enex";
        String out=in+"xxx.zip";
        bruteForceCompress(in,out);
    }

}
