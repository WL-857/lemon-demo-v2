package com.nhsoft.lemon.repository;

import com.nhsoft.lemon.dto.ScoreDTO;
import com.nhsoft.lemon.model.Course;
import com.nhsoft.lemon.model.Score;
import com.nhsoft.lemon.model.extend.ScoreExtend;
import com.nhsoft.lemon.model.extend.TeacherExtend;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wanglei
 */
@Repository
public interface ScoreDao{

    /**
     * 根据学号获取其所有的成绩
     * @param stuNo
     * @param year
     * @return
     */
    List<ScoreExtend> listStudentAllGrade(String stuNo, String year);

    /**
     * 根据教师编号查询其所教授课程的最大成绩、最小成绩、和平均成绩
     * @param teachId
     * @param year
     * @return
     */
    List<TeacherExtend> listMaxMinAvg(Long teachId, String year);

    /**
     * 查询学年所有学生每门学科的成绩最大值，最小值，平均值
     * @param year
     * @return
     */
    List<TeacherExtend> listAllMaxMinAvg(String year);

    /**
     * 查询所有的成绩并分页
     * @return
     */
    List<ScoreExtend> listAll(int pageNo,int pageSize);

    /**
     * 根据id查询分数
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