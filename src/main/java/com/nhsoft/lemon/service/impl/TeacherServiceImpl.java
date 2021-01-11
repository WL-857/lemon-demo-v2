package com.nhsoft.lemon.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhsoft.lemon.config.RedisKey;
import com.nhsoft.lemon.exception.GlobalException;
import com.nhsoft.lemon.repository.TeacherDao;
import com.nhsoft.lemon.dto.TeacherDTO;

import com.nhsoft.lemon.model.Teacher;
import com.nhsoft.lemon.service.TeacherService;
import com.nhsoft.lemon.utils.PageUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wanglei
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherDao teacherDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource(name = "objectMapper")
    private ObjectMapper objectMapper;

    @Override
    public List<Teacher> listAllTeacher(int pageNo, int pageSize) {
        PageUtil pageUtil = new PageUtil();
        PageUtil check = pageUtil.check(pageNo, pageSize);
        List<Teacher> teachers = teacherDao.listAllTeacher(check.getOffset(),check.getRows());
        return teachers;
    }

    @Override
    public Teacher readTeacher(Long id) {
        return teacherDao.readTeacher(id);
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        Teacher save = teacherDao.saveTeacher(teacher);
        if(save == null){
            throw new GlobalException("数据库中已存在该条记录");
        }
        Long teachId = save.getTeacherId();
        String teacherKey = RedisKey.TEACHER_KEY + teachId;
        Object teacherValue = redisTemplate.opsForValue().get(teacherKey);

        try {
            String teacherJson = objectMapper.writeValueAsString(teacher);
            if(teacherValue == null){
                redisTemplate.opsForValue().set(teacherKey,teacherJson);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return save;
    }

    @Override
    public List<Teacher> batchSaveTeacher(List<Teacher> teachers) {
        List<Teacher> teacherList = teacherDao.batchSaveTeacher(teachers);

        teachers.forEach(teacher -> {
            String teacherKey = RedisKey.TEACHER_KEY + teacher.getTeacherId();
            Object value = redisTemplate.opsForValue().get(teacherKey);

            try {
                String teacherJson = objectMapper.writeValueAsString(teacher);
                if (value == null) {
                    redisTemplate.opsForValue().set(teacherKey, teacherJson);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return teacherList;
    }

    @Override
    public void deleteTeacher(Long id) {
        String  teacherKey = RedisKey.TEACHER_KEY + id;
        Object value = redisTemplate.opsForValue().get(teacherKey);
        if (value != null) {
            redisTemplate.delete(teacherKey);
        }
        if (teacherDao.readTeacher(id) != null) {

            teacherDao.deleteTeacher(id);
        }else {
            throw new GlobalException("数据库中没有该条记录");
        }
    }

    @Override
    public void batchDeleteTeacher(List<Long> ids) {
        ids.forEach(id -> {
            String teacherKey = RedisKey.TEACHER_KEY + id;
            Object value = redisTemplate.opsForValue().get(teacherKey);
            if (value != null) {
                redisTemplate.delete(teacherKey);
            }
        });
        teacherDao.batchDeleteTeacher(ids);
    }
}
