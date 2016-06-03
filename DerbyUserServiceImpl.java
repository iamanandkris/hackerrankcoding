package com.websystique.springmvc.service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.model.User;
import com.websystique.springmvc.persistance.PingDAO;

@Service("userServiceDerby")
@Transactional
public class DerbyUserServiceImpl implements UserService
{

    public static Log LOG = LogFactory.getLog(DerbyUserServiceImpl.class);

    @Autowired
    PingDAO pingDAO;

    public List<User> findAllUsers() {
        List<Map<String, Object>> allUsers = pingDAO.findAllUsers();
        List<User> outList = new ArrayList<User>();
        for(Map<String, Object> mp : allUsers){
            User usr = new User();

            Integer id = (Integer)mp.get("ID");
            usr.setId(id);

            String name = (String)mp.get("NAME");
            usr.setName(name);

            Integer age = (Integer)mp.get("AGE");
            usr.setAge(age);

            Double salary = (Double)mp.get("SALARY");
            usr.setSalary(salary);

            LOG.info("Current User To SEND BACK : " + usr);

            outList.add(usr);
        }

        return outList;
    }

    public User findById(long id) {
        Map<String, Object> userMap = pingDAO.findUserById(id);

        if (null != userMap && !userMap.isEmpty())
        {
            User usr = new User();

            Integer idd = (Integer) userMap.get("ID");
            usr.setId(idd);

            String name = (String) userMap.get("NAME");
            usr.setName(name);

            Integer age = (Integer) userMap.get("AGE");
            usr.setAge(age);

            Double salary = (Double) userMap.get("SALARY");
            usr.setSalary(salary);

            return usr;
        }else{
            return null;
        }
    }

    public User findByName(String name) {
        Map<String, Object> userMap = pingDAO.findUserByName(name);

        if (null != userMap && !userMap.isEmpty())
        {
            User usr = new User();

            Integer idd = (Integer) userMap.get("ID");
            usr.setId(idd);

            String namee = (String) userMap.get("NAME");
            usr.setName(namee);

            Integer age = (Integer) userMap.get("AGE");
            usr.setAge(age);

            Double salary = (Double) userMap.get("SALARY");
            usr.setSalary(salary);

            return usr;
        }else{
            return null;
        }
    }

    public void saveUser(User user) {
        pingDAO.insert(user);
    }

    public void updateUser(User user) {
        pingDAO.update(user);
    }

    public void deleteUserById(long id) {}

    public void deleteAllUsers() {}

    public boolean isUserExist(User user){
        return pingDAO.doesUserAlreadyExist(user);
    }
}
