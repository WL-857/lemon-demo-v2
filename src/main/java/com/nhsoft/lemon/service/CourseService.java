package com.nhsoft.lemon.service;

import com.nhsoft.lemon.dto.CourseDTO;
import com.nhsoft.lemon.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanglei
 */
public interface CourseService {

    /**
     * 查询所有的课程并分页
     * @param pageNo
     * @param pageSize
     * @return
     */

    List<Course> listAll(int pageNo, int pageSize);

    /**
     * 根据课程编号查询课程信息
     * @param id
     * @return
     */
    Course readCourse(Long id);

    /**
     * 添加课程
     * @param course
     * @return
     */
    Course saveCourse(Course course);

    /**
     * 批量添加课程
     * @param courses
     * @return
     */
    List<Course> batchSaveCourse(List<Course> courses);

    /**
     * 根据id删除课程
     * @param id
     */
    void deleteCourse(Long id);

    /**
     * 批量删除
     * @param ids
     */
    void batchDeleteCourse(List<Long> ids);

    /**
     * 修改课程
     * @param course
     * @return
     */
    Course updateCourse(Course course);
}
