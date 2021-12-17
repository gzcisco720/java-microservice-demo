package com.demo.thrift.user.service.thrift;

import com.demo.thrift.user.service.api.UserService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ThriftServer {
    @Value("${server.port}")
    private int serverPort;

    private final UserService.Iface userService;

    public ThriftServer(UserService.Iface userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void start() {
        try {
            TNonblockingServerSocket socket = new TNonblockingServerSocket(serverPort);
            TProcessor processor = new UserService.Processor<>(userService);
            TNonblockingServer.Args args = new TNonblockingServer.Args(socket);
            args.processor(processor);
            args.transportFactory(new TFramedTransport.Factory());
            args.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TNonblockingServer(args);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
