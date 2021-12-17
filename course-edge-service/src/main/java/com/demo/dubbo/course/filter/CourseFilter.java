package com.demo.dubbo.course.filter;

import com.demo.thrift.user.edge.service.client.filter.LoginFilter;
import com.demo.thrift.user.service.api.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CourseFilter extends LoginFilter {
    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response, UserDto userDto) {
        request.setAttribute("user", userDto);
    }
}
