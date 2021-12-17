package com.demo.thrift.user.edge.service.client.filter;

import com.demo.thrift.user.edge.service.client.api.UserEdgeServiceApi;
import com.demo.thrift.user.service.api.UserDto;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class LoginFilter implements Filter {
    private final Retrofit retrofit;
    private static Cache<String, UserDto> cache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(3, TimeUnit.MINUTES)
            .build();

    public LoginFilter() {
         retrofit = new Retrofit.Builder()
                 .baseUrl("http://localhost:7912/user/auth/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        token = cookie.getValue();
                    }
                }
            }
        }
        UserDto userDto = null;
        if (StringUtils.isNoneBlank(token)) {
            userDto = cache.getIfPresent(token);
            if (userDto == null) {
                userDto = requestUserInfo(token);
                if (userDto!=null) {
                    cache.put(token, userDto);
                }
            }
        }
        if (userDto == null) {
            response.sendRedirect("http://localhost:7912/user/login");
            return;
        }

        login(request,response,userDto);

        filterChain.doFilter(request, response);
    }

    protected abstract void login(HttpServletRequest request, HttpServletResponse response, UserDto userDto);

    private UserDto requestUserInfo(String token) {
        UserEdgeServiceApi service = retrofit.create(UserEdgeServiceApi.class);
        Call<UserDto> authenticate = service.authenticate(token);
        UserDto body = null;
        try {
            Response<UserDto> response = authenticate.execute();
            body = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    @Override
    public void destroy() {}
}
