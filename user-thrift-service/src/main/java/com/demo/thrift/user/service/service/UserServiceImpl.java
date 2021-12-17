package com.demo.thrift.user.service.service;

import com.demo.thrift.user.service.api.UserInfo;
import com.demo.thrift.user.service.api.UserService;
import com.demo.thrift.user.service.mapper.UserMapper;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService.Iface {
    private final UserMapper mapper;

    public UserServiceImpl(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UserInfo getUserById(int id) throws TException {
        return mapper.getUserById(id);
    }

    @Override
    public UserInfo getTeacherById(int id) throws TException {
        return mapper.getTeacherById(id);
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        return mapper.getUserByName(username);
    }

    @Override
    public void registerUser(UserInfo userinfo) throws TException {
        mapper.registerUser(userinfo);
    }
}
