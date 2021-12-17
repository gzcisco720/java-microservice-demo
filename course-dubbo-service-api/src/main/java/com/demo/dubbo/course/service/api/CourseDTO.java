package com.demo.dubbo.course.service.api;

import com.demo.thrift.user.service.api.TeacherDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO implements Serializable {
    private int id;
    private String title;
    private String description;
    private TeacherDTO teacher;
}
