package com.demo.thrift.user.edge.service;

import com.demo.thrift.user.edge.dto.UserLoginDto;
import com.demo.thrift.user.edge.thrift.ServiceProvider;
import com.demo.thrift.user.edge.thrift.UserThriftClient;
import com.demo.thrift.user.edge.redis.RedisClient;
import com.demo.thrift.user.service.api.UserDto;
import com.demo.thrift.user.service.api.UserInfo;
import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class UserService {

    private final ServiceProvider serviceProvider;
    private final RedisClient redisClient;

    public UserService(ServiceProvider serviceProvider, RedisClient redisClient) {
        this.serviceProvider = serviceProvider;
        this.redisClient = redisClient;
    }

    public ResponseEntity<String> login(UserLoginDto userLoginDto) {
        UserInfo userInfo;
        UserThriftClient client = null;
        try {
            client = serviceProvider.getUserThriftClient();
            userInfo = client.getClient().getUserByName(userLoginDto.getUsername());
            client.getTransport().close();
        } catch (TException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    new HttpHeaders(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (client != null) {
                client.getTransport().close();
            }
        }
        if (userInfo == null) {
            return new ResponseEntity<>("user not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        if (!userInfo.getPassword().equalsIgnoreCase(md5(userLoginDto.getPassword()))) {
            return new ResponseEntity<>("Invalid password", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }
        String token = genToken();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userInfo, userDto);
        redisClient.set(token, userDto, 3600);

        return ResponseEntity.ok(token);
    }

    public ResponseEntity<String> register(UserDto userDto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userDto.getUsername());
        userInfo.setPassword(md5(userDto.getPassword()));
        userInfo.setMobile(userDto.getMobile());
        userInfo.setEmail(userDto.getEmail());
        userInfo.setNickname(userDto.getNickname());
        UserThriftClient client = null;
        try {
            client = serviceProvider.getUserThriftClient();
            client.getClient().registerUser(userInfo);
            client.getTransport().close();
            return ResponseEntity.ok("register successfully");
        } catch (TException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (client != null) {
                client.getTransport().close();
            }
        }
    }

    public UserDto authenticate(String token) {
        Object o = redisClient.get(token);
        return (UserDto)o;
    }

    private String md5(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(password.getBytes(StandardCharsets.UTF_8));
            return HexUtils.toHexString(md5Bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String genToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
