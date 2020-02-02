package life.majiang.community.community.service;

import life.majiang.community.community.Mapper.UserMapper;
import life.majiang.community.community.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user1){
        User dbUser=userMapper.findByAccountId(user1.getAccountId());
        if (dbUser==null){
            userMapper.insert(user1);
        }else {
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user1.getAvatarUrl());
            dbUser.setName(user1.getName());
            dbUser.setToken(user1.getToken());
            userMapper.updateToken(dbUser);
        }
    }
}
