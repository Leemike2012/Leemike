package com.the.electricdoor;

import org.springframework.cache.interceptor.AbstractCacheInvoker;

public class Test1 {
    public static void main(String[] args) {

        String name = "abc";
        String address = "zhongxin";


        StringBuffer sql = new StringBuffer();

        sql.append("select *  from hotel  where");

        if(!name.trim().equals("")){
            sql.append(" and name = '").append(name).append("' ");

        }

        if(!address.trim().equals("")){
            sql.append("and address like '%" + address + "%'");
        }
        

        System.out.println(sql.toString());


        
    }

    
}