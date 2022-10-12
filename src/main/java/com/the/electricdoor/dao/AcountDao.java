package com.the.electricdoor.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.the.electricdoor.Entity.Acount;

@Component
public class AcountDao {
    
    @Autowired
	private JdbcTemplate jdbcTemplate;

    public Acount selectAcountByKey(String user_name){

        // 数据库安全漏洞: sql注入 mybatis
        List<Acount> rs = jdbcTemplate.query("select * from acount where acount_name = ?", new RowMapper<Acount>() {
			@Override
			public Acount mapRow(ResultSet arg0, int arg1) throws SQLException {
				Acount acount = new Acount();
                acount.setAcount_name(arg0.getString("acount_name"));
                acount.setAcount_password(arg0.getString("acount_password"));
                acount.setNick_name(arg0.getString("nick_name"));
                acount.setSex(arg0.getInt("sex"));
				acount.setCreate_time(arg0.getDate("create_time"));
                return acount;
			}
		}, user_name);


        

        Acount acount = null;

        if(rs.size() > 0){
            acount = rs.get(0);
        }

        return acount;
    }
    
}
