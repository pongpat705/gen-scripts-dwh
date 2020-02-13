package com.dwh.genscripts.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.PreDestroy;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class BaseDao {

    public JdbcTemplate jdbcTemplate;

    public BaseDao() {
        jdbcTemplate = new JdbcTemplate(this.getDataSource());
    }

    public javax.sql.DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@ip:port:sid");
        dataSource.setUsername("username");
        dataSource.setPassword("password");

        return dataSource;
    }

    public String getSql(String tableName){
        // BuildMyString.com generated code. Please enjoy your string responsibly.

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT LISTAGG((case when t.DATA_TYPE = 'DATE' then 'TO_CHAR('||t.column_name||', ''YYYYMMDDHH24MISS'')' else t.column_name end), '|| ''|'' || ') ");
        sb.append("         WITHIN GROUP (ORDER BY t.COLUMN_ID) as sql ");
        sb.append("from USER_TAB_COLS t ");
        sb.append("where t.TABLE_NAME = upper('"+tableName+"') ");

        log.info("sql for getAll : {}", sb.toString());

        String tableNames = jdbcTemplate.queryForObject(sb.toString(), new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("sql");
            }
        });
        log.info("data {}", tableNames);

        return  tableNames;
    }

    @PreDestroy
    private void afterUsed(){
        try {
            log.info("closing connection!");
            this.jdbcTemplate.getDataSource().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
