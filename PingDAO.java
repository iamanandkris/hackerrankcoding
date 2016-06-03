package com.websystique.springmvc.persistance;
import org.springframework.stereotype.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.websystique.springmvc.model.User;


@Repository
public class PingDAO
{

    public static Log LOG = LogFactory.getLog(PingDAO.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(User user) {
        LOG.info("Inserting USERTBL : " + user);
        jdbcTemplate.update("INSERT INTO USERTBL(NAME, AGE, SALARY) VALUES(?, ?, ?)", user.getName(),user.getAge(),user.getSalary());
    }

    public void update(User user) {
        LOG.info("Updating USERTBL : " + user);

        Object[] params = { user.getName(), user.getAge(),user.getSalary(), user.getId()};
        int[] types = {Types.VARCHAR, Types.INTEGER,Types.DOUBLE,Types.INTEGER};
        jdbcTemplate.update("UPDATE USERTBL SET NAME = ?, AGE = ?, SALARY=? WHERE id = ?", params,types );
    }

    public List<Map<String, Object>> findAllUsers() {
        LOG.info("GETTING ALL USERS");
        return jdbcTemplate.queryForList("SELECT * FROM USERTBL ORDER BY ID");
    }

    public Map<String, Object> findUserById(long id) {
        LOG.info("GETTING USER WITH ID : " + id);
        List<Map<String, Object>> mappedVal =  jdbcTemplate.queryForList("SELECT * FROM USERTBL WHERE ID= ? ORDER BY ID",id );

        if (null != mappedVal && !mappedVal.isEmpty()){
            if (null != mappedVal.get(0)){
                return mappedVal.get(0);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public Map<String, Object> findUserByName(String name) {
        LOG.info("GETTING USER WITH NAME : " + name);
        List<Map<String, Object>> mappedVal = jdbcTemplate.queryForList("SELECT * FROM USERTBL WHERE NAME= ? ORDER BY ID",name );

        if (null != mappedVal && !mappedVal.isEmpty()){
            if (null != mappedVal.get(0)){
                return mappedVal.get(0);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public boolean doesUserAlreadyExist(User user) {
        LOG.info("GETTING USER WITH NAME : " + user);
        List<Map<String, Object>> mappedVal =  jdbcTemplate.queryForList("SELECT * FROM USERTBL WHERE NAME= ? AND AGE = ? AND SALARY = ? ORDER BY ID", user.getName(), user.getAge(), user.getSalary());
        LOG.info("GOT INFO: " + mappedVal);
        if (null != mappedVal && !mappedVal.isEmpty()){
            if (null != mappedVal.get(0)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public void deleteById(long id)
    {
        LOG.info("DELETING USERTBL WITH ID : " + id);
    }

    public void deleteAllUsers()
    {
        LOG.info("DELETING ALL THE USERTBL ");
    }
}
