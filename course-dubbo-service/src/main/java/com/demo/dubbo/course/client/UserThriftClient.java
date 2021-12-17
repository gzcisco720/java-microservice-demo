package com.demo.dubbo.course.client;

import com.demo.thrift.user.service.api.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.thrift.transport.TTransport;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserThriftClient {
    private TTransport transport;
    private UserService.Client client;
}
