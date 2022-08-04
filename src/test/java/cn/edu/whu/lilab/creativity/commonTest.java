package cn.edu.whu.lilab.creativity;

import org.junit.jupiter.api.Test;

public class commonTest {

    @Test
    public  void testPublishDate() {
        String publish_date = "..";
        String[] strArray = publish_date.split("\\.");
        System.out.println(strArray==null);
        for (String s : strArray) {
            System.out.println(s);
        }
        // String str = "   01:???   ";
        // String[] strArray = str.split(":");
        // System.out.println(strArray[0].trim());
        // System.out.println(strArray[1].trim());
    }
}
