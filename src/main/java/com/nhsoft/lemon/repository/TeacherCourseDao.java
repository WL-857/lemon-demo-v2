package com.nhsoft.lemon.repository;

import com.nhsoft.lemon.model.TeacherCourse;
import com.nhsoft.lemon.model.extend.TeacherExtend;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

/**
 * @author wanglei
 */
public interface TeacherCourseDao {

    /**
     * 查询所有老师所教授的课程并分页
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<TeacherExtend> listTeacherCourse(int pageNo,int pageSize);


    /**
     * 添加新的教师课程
     * @param teacherCourse
     * @return
     */
    TeacherCourse saveTeacherCourse(TeacherCourse teacherCourse);

    /**
     * 批量添加新的教师课程
     * @param teacherCourses
     * @return
     */
    List<TeacherCourse> batchSaveTeacherCourse(List<TeacherCourse> teacherCourses);

    /**
     * 删除教师课程
     * @param teacherCourse
     */
    int deleteTeacherCourse(TeacherCourse teacherCourse);

    /**
     * 批量删除教师课程
     * @param teacherCourses
     */
    void batchDeleteTeacherCourse(List<TeacherCourse> teacherCourses);

}
