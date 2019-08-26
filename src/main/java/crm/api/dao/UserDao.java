package crm.api.dao;

import crm.api.entity.User;

public interface UserDao {
    User findByUserName(String userName);
}
