package com.nhsoft.lemon.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhsoft.lemon.config.RedisKey;
import com.nhsoft.lemon.dto.ScoreDTO;
import com.nhsoft.lemon.exception.GlobalException;
import com.nhsoft.lemon.model.Course;
import com.nhsoft.lemon.model.Score;
import com.nhsoft.lemon.model.Student;
import com.nhsoft.lemon.model.extend.ScoreExtend;
import com.nhsoft.lemon.model.extend.TeacherExtend;
import com.nhsoft.lemon.repository.ScoreDao;
import com.nhsoft.lemon.service.ScoreService;
import com.nhsoft.lemon.utils.CopyUtil;
import com.nhsoft.lemon.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanglei
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ScoreServiceImpl implements ScoreService {
    @Resource
    private ScoreDao scoreDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource(name = "objectMapper")
    private ObjectMapper objectMapper;

    @Override
    public List<ScoreExtend> listAll(int pageNo, int pageSize) {
        PageUtil pageUtil = new PageUtil();
        PageUtil check = pageUtil.check(pageNo, pageSize);
        List<ScoreExtend> scoreExtends = scoreDao.listAll(check.getOffset(), check.getRows());
        return scoreExtends;
    }

    @Override
    public List<ScoreExtend> listStudentAllGrade(String stuNo, String time) {
        if (StringUtils.isEmpty(stuNo) || StringUtils.isEmpty(time)) {
            throw new GlobalException("参数为空");
        }
        List<ScoreExtend> scoreExtends = scoreDao.listStudentAllGrade(stuNo, time);
        if (CollectionUtils.isEmpty(scoreExtends)) {
            throw new GlobalException("数据库中没有记录");
        }
        return scoreExtends;
    }

    @Override
    public List<TeacherExtend> listMaxMinAvg(Long teachId, String year) {

        if (ObjectUtils.isEmpty(teachId) || StringUtils.isEmpty(year)) {
            throw new GlobalException("参数为空");
        }
        List<TeacherExtend> scoreExtends = scoreDao.listMaxMinAvg(teachId, year);
        if (CollectionUtils.isEmpty(scoreExtends)) {
            throw new GlobalException("数据库中没有记录");
        }
        return scoreExtends;

    }


    @Override
    public List<TeacherExtend> listAllMaxMinAvg(String year) {
        if (StringUtils.isEmpty(year)) {
            throw new GlobalException("参数为空");
        }
        List<TeacherExtend> teacherExtends = scoreDao.listAllMaxMinAvg(year);
        if (CollectionUtils.isEmpty(teacherExtends)) {
            throw new GlobalException("数据库中没有记录");
        }
        return teacherExtends;
    }

    @Override
    public Score saveScore(Score score) {
        Score save = scoreDao.saveScore(score);
        if (save == null) {
            throw new GlobalException("数据库中已存在该记录");
        }
        Long scoId = save.getScoreId();
        String scoreKey = RedisKey.SCORE_KEY + scoId;
        Object scoreValue = redisTemplate.opsForValue().get(scoreKey);

        try {
            String scoreJson = objectMapper.writeValueAsString(score);
            if (scoreValue == null) {
                redisTemplate.opsForValue().set(scoreKey, scoreJson);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return save;
    }

    @Override
    public List<Score> batchSaveScore(List<Score> scores) {
        List<Score> list = scoreDao.batchSaveScore(scores);
        list.forEach(course -> {
            String scoreKey = RedisKey.SCORE_KEY + course.getCourseId();
            Object value = redisTemplate.opsForValue().get(scoreKey);
            try {
                String scoreJson = objectMapper.writeValueAsString(course);
                if (value == null) {
                    redisTemplate.opsForValue().set(scoreKey, scoreJson);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        return list;
    }

    @Override
    public int deleteScore(Long id) {
        int i = 0;
        String scoreKey = RedisKey.SCORE_KEY + id;
        Object value = redisTemplate.opsForValue().get(scoreKey);
        if (value != null) {
            redisTemplate.delete(scoreKey);
        }
        if (scoreDao.readScore(id) != null) {
            i = scoreDao.deleteScore(id);
        } else {
            throw new GlobalException("数据库中不存在该条记录");
        }
        return i;
    }

    @Override
    public void batchDeleteScore(List<Long> ids) {
        ids.forEach(id -> {
            String scoreKey = RedisKey.SCORE_KEY + id;
            Object value = redisTemplate.opsForValue().get(scoreKey);
            if (value != null) {
                redisTemplate.delete(scoreKey);
            }
            scoreDao.batchDeleteScore(ids);
        });
    }

    @Override
    public Score updateScore(Score score) {
        String scoreKey = RedisKey.SCORE_KEY + score.getCourseId();
        redisTemplate.opsForValue().set(scoreKey, score);
        return scoreDao.updateScore(score);
    }

    @Override
    public ScoreExtend readScore(Long id) {
        ScoreExtend scoreExtend = scoreDao.readScore(id);
        if (ObjectUtils.isEmpty(scoreExtend)) {
            throw new GlobalException("数据库中没有该条记录");
        }
        return scoreExtend;
    }
}
