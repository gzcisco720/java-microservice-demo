package com.demo.thrift.user.edge.service.client.api;

import com.demo.thrift.user.service.api.UserDto;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserEdgeServiceApi {
    @POST("/user/auth")
    Call<UserDto> authenticate(@Header("token") String token);
}
