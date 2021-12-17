# java-microservice-demo

## Architecture
![image](https://user-images.githubusercontent.com/11530457/146609872-2b0cb1bd-12fa-4444-9ef8-acf30bc64735.png)

## Serivces

- Zuul Gateway
    - module: api-gateway-zuul
    - endpoints
        - /api/user/*
        - /api/course/*
    - about: API gateway
    - port: 8080
  

- Eureka Registry
    - module: edge-service-eureka-registry
    - about: service registry for edge services and api gateway
    - port: 8081


- Course Edge Service
    - module: course-edge-service
    - about: edge service for providing course related restful api
    - auth: api protected by user-edge-service-client
    - endpoint
      - /course/courseList
    - port: 7914


- User Edge Service
    - module: user-edge-service
    - about: edge service for providing user related restful api
    - endpoint
        - /user/login
        - /user/register
        - /user/auth
    - port: 7912


- Course Service
    - module: course-dubbo-service
    - about: service using dubbo rpc for providing course related data
    - defination: course-dubbo-service-api
    - port: 7913


- User Service
    - module: user-thrift-service
    - about: service using thrift rpc for providing user related data
    - defination: user-thrift-service-api
    - port: 7911

## Best Running order

1. docker-compose
2. user-thrift-service
3. course-dubbo-service
4. edge-service-eureka-registry
5. user-edge-service
6. course-edge-service
7. api-gateway-zuul

