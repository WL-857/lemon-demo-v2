package com.nhsoft.lemon.service;

import com.nhsoft.lemon.model.TeacherCourse;
import com.nhsoft.lemon.model.extend.TeacherExtend;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author wanglei
 */
public interface TeacherCourseService {
    /**
     * 查询所有老师所教授的课程并分页
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<TeacherExtend> list(int pageNo,int pageSize);


    /**
     * 添加新的教师课程
     * @param teacherCourse
     * @return
     */
    TeacherCourse save(TeacherCourse teacherCourse);

    /**
     * 批量添加新的教师课程
     * @param teacherCourses
     * @return
     */
    List<TeacherCourse> batchSave(List<TeacherCourse> teacherCourses);

    /**
     * 删除教师课程
     * @param teacherCourse
     */
    void delete(TeacherCourse teacherCourse);

    /**
     * 批量删除教师课程
     * @param teacherCourses
     */
    void batchDelete(List<TeacherCourse> teacherCourses);
}
