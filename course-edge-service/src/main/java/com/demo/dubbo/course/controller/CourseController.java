package com.demo.dubbo.course.controller;

import com.demo.dubbo.course.service.api.CourseDTO;
import com.demo.dubbo.course.service.api.ICourseService;
import com.demo.thrift.user.service.api.UserDto;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CourseController {
    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:20880")
    private ICourseService courseService;

    @GetMapping("/courseList")
    public List<CourseDTO> courseList(HttpServletRequest request) {
        UserDto userDto = (UserDto) request.getAttribute("user");
        System.out.println(userDto.getUsername());
        return courseService.courseList();
    }
}
