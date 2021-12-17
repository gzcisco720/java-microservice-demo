package com.demo.dubbo.course.service;

import com.alibaba.spring.util.BeanUtils;
import com.demo.dubbo.course.client.ServiceProvider;
import com.demo.dubbo.course.mapper.CourseMapper;
import com.demo.dubbo.course.service.api.CourseDTO;
import com.demo.dubbo.course.service.api.ICourseService;
import com.demo.thrift.user.service.api.TeacherDTO;
import com.demo.thrift.user.service.api.UserInfo;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DubboService(version = "1.0.0")
public class CourseServiceImpl implements ICourseService {

    private final CourseMapper courseMapper;

    private final ServiceProvider serviceProvider;

    public CourseServiceImpl(CourseMapper courseMapper, ServiceProvider serviceProvider) {
        this.courseMapper = courseMapper;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public List<CourseDTO> courseList() {
        List<CourseDTO> courses = courseMapper.listCourses();
        if (courses != null) {
            for (CourseDTO cours : courses) {
                Integer teacherId = courseMapper.getCourseTeacher(cours.getId());
                if (teacherId != null) {
                    try {
                        UserInfo teacherById = serviceProvider.getUserThriftClient().getClient().getTeacherById(teacherId);
                        cours.setTeacher(trans2Teacher(teacherById));
                    } catch (TException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return courses;
    }

    private TeacherDTO trans2Teacher(UserInfo teacherById) {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setUsername(teacherById.getUsername());
        teacherDTO.setPassword(teacherById.getPassword());
        teacherDTO.setEmail(teacherById.getEmail());
        teacherDTO.setMobile(teacherById.getMobile());
        teacherDTO.setNickname(teacherById.getNickname());
        teacherDTO.setIntro(teacherById.getIntro());
        teacherDTO.setRate(teacherById.getRate());
        return teacherDTO;
    }
}
