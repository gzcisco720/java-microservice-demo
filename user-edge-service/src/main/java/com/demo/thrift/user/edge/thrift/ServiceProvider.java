package com.demo.thrift.user.edge.thrift;

import com.demo.thrift.user.service.api.UserService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServiceProvider {

    @Value("${thrift.user-service.ip}")
    private String serverIp;
    @Value("${thrift.user-service.port}")
    private int serverPort;

    public UserThriftClient getUserThriftClient() {
        TTransport transport;
        try {
            TSocket socket = new TSocket(serverIp, serverPort);
            socket.setSocketTimeout(3000);
            transport = new TFramedTransport(socket);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            UserService.Client client = new UserService.Client(protocol);
            return new UserThriftClient(transport, client);
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
    }
}
