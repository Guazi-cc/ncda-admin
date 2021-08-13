package com.ncda.ncdaadminbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class NcdaAdminBackendApplicationTests {

    @Test
    void contextLoads() {

        String yearss = "2021年8月";
        String year = null;
        String month = null;
        char[] yearStrArr = yearss.toCharArray();
        for (int i = 0; i < yearss.length(); i++) {
            if (yearss.charAt(i) == '2' && yearss.charAt(i+1) == '0') {
                year = yearss.substring(i, i+4);
                System.out.println(year);
            }
        }

//        for (int i = yearss.length() - 1; i >=0; i--) {
//            System.out.println(yearss.charAt(i));
//            if(yearss.charAt(i) == '月') {
//
//            }
//        }
    }

    @Test
    void test2() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date parse = sdf.parse("2021.7.2");
        System.out.println(parse);

    }

}
