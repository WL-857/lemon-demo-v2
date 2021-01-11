package com.nhsoft.lemon.service;


import com.nhsoft.lemon.dto.ScoreDTO;
import com.nhsoft.lemon.model.Course;
import com.nhsoft.lemon.model.Score;
import com.nhsoft.lemon.model.Student;
import com.nhsoft.lemon.model.extend.ScoreExtend;
import com.nhsoft.lemon.model.extend.TeacherExtend;

import java.util.List;

/**
 * @author wanglei
 */
public interface ScoreService {

    /**
     *  查询所有分数并且分页
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<ScoreExtend> listAllScore(int pageNo, int pageSize);

    /**
     * 根据学号获取学生所有的课程成绩
     *
     * @param stuNo
     * @param time
     * @return
     */
    List<ScoreExtend> listStudentAllGrade(String stuNo, String time);

    /**
     * 教师根据其主键和学年查询其所教授科目的成绩最大值，最小值，平均值
     *
     * @param teachId
     * @param year
     * @return
     */
    List<TeacherExtend> listMaxMinAvgScore(Long teachId, String year);

    /**
     * 查询学年所有学生每门学科的成绩最大值，最小值，平均值
     *
     * @param year
     * @return
     */
    List<TeacherExtend> listAllMaxMinAvgScore(String year);

    /**
     * 根据id查询分数信息
     * @param id
     * @return
     */
    ScoreExtend readScore(Long id);

    /**
     * 添加分数
     * @param score
     * @return
     */
    Score saveScore(Score score);

    /**
     * 批量添加分数
     * @param scores
     * @return
     */
    List<Score> batchSaveScore(List<Score> scores);

    /**
     * 根究id删除分数
     * @param id
     * @return
     */
    int deleteScore(Long id);

    /**
     * 批量删除分数
     * @param ids
     */
    void batchDeleteScore(List<Long> ids);

    /**
     * 修改分数
     * @param score
     * @return
     */
    Score updateScore(Score score);
}