package com.nhsoft.lemon.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhsoft.lemon.config.RedisKey;
import com.nhsoft.lemon.exception.GlobalException;
import com.nhsoft.lemon.repository.CourseDao;

import com.nhsoft.lemon.model.Course;
import com.nhsoft.lemon.service.CourseService;
import com.nhsoft.lemon.utils.PageUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author wanglei
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseDao courseDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource(name = "objectMapper")
    private ObjectMapper objectMapper;

    @Override
    public List<Course> listAllCourse(int pageNo, int pageSize) {
        PageUtil pageUtil = new PageUtil();
        PageUtil check = pageUtil.check(pageNo, pageSize);
        List<Course> courses = courseDao.listAllCourse(check.getOffset(), check.getRows());
        return courses;
    }

    @Override
    public Course readCourse(Long id) {
        Course course = courseDao.readCourse(id);
        if(ObjectUtils.isEmpty(course)){
            throw new GlobalException("数据库中没有该记录");
        }
        return course ;
    }

    @Override
    public Course saveCourse(Course course) {
        Course save = courseDao.saveCourse(course);
        if (save == null) {
            throw  new GlobalException("数据库中已存在该记录");
        }
        Long couId = save.getCourseId();
        String courseKey = RedisKey.COURSE_KEY + couId;
        Object courseValue = redisTemplate.opsForValue().get(courseKey);

        try {
            String courseJson = objectMapper.writeValueAsString(course);
            if (courseValue == null) {
                redisTemplate.opsForValue().set(courseKey, courseJson);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return save;
    }

    @Override
    public List<Course> batchSaveCourse(List<Course> courses) {
        List<Course> list = courseDao.batchSaveCourse(courses);
        list.forEach(course -> {
            String courseKey = RedisKey.COURSE_KEY + course.getCourseId();
            Object value = redisTemplate.opsForValue().get(courseKey);
            try {
                String courseJson = objectMapper.writeValueAsString(course);
                if (value == null) {
                    redisTemplate.opsForValue().set(courseKey, courseJson);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        return list;
    }

    @Override
    public void deleteCourse(Long id) {
        String courseKey = RedisKey.COURSE_KEY + id;
        Object value = redisTemplate.opsForValue().get(courseKey);
        if (value != null) {
            redisTemplate.delete(courseKey);
        }
        if (courseDao.readCourse(id) != null) {
            courseDao.deleteCourse(id);
        }else{
            throw new GlobalException("数据库中不存在该条记录");
        }
    }

    @Override
    public void batchDeleteCourse(List<Long> ids) {
        ids.forEach(id -> {
            String courseKey = RedisKey.COURSE_KEY + id;
            Object value = redisTemplate.opsForValue().get(courseKey);
            if (value != null) {
                redisTemplate.delete(courseKey);
            }
        });
        courseDao.batchDeleteCourse(ids);
    }

    @Override
    public Course updateCourse(Course course) {
        String courseKey = RedisKey.COURSE_KEY + course.getCourseId();
        redisTemplate.opsForValue().set(courseKey,course);
        return courseDao.updateCourse(course);
    }
}
