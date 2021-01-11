package com.nhsoft.lemon.controller;

import com.nhsoft.lemon.dto.CourseDTO;
import com.nhsoft.lemon.exception.GlobalException;
import com.nhsoft.lemon.model.Course;
import com.nhsoft.lemon.response.ResponseMessage;
import com.nhsoft.lemon.service.CourseService;
import com.nhsoft.lemon.utils.CopyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wanglei
 */
@RestController
@Api("课程信息模块")
@Slf4j
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("查询所有课程信息并分页")
    @GetMapping("/list/{pageNo}/{pageSize}")
    public ResponseMessage listAll(@PathVariable(name = "pageNo", required = true) int pageNo,
                                   @PathVariable(name = "pageSize", required = true) int pageSize) {

        if (pageNo < 0) {
            pageNo = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        List<Course> courses = courseService.listAll(pageNo, pageSize);
        if (CollectionUtils.isEmpty(courses)) {
            throw new GlobalException("数据为空");
        }
        List<CourseDTO> courseDTOS = CopyUtil.toList(courses, CourseDTO.class);
        return ResponseMessage.success("courseDTOS", courseDTOS);
    }

    @ApiOperation("根据课程编号查询课程信息")
    @GetMapping("/{couId}")
    public ResponseMessage readCourse(@PathVariable(name = "couId", required = true) Long couId) {
        if (ObjectUtils.isEmpty(couId)) {
            throw new GlobalException("数据为空");
        }
        Course course = courseService.readCourse(couId);
        if (course == null) {
            throw new GlobalException("数据为空");
        }
        CourseDTO courseDTO = CopyUtil.to(course, CourseDTO.class);
        log.info("readCourse方法执行结束，方法返回值为：" + courseDTO);
        return ResponseMessage.success("courseDTOS", courseDTO);
    }

    @ApiOperation("添加课程")
    @PostMapping("/save")
    public ResponseMessage saveCourse(@RequestBody CourseDTO courseDTO) {
        if (ObjectUtils.isEmpty(courseDTO)) {
            throw new GlobalException("数据为空");
        }
        Course course = CopyUtil.to(courseDTO, Course.class);

        Course saveCourse = courseService.saveCourse(course);
        if (saveCourse == null) {
            throw new GlobalException("添加失败");
        }
        return ResponseMessage.success("添加成功");
    }

    @ApiOperation("批量添加课程")
    @PostMapping(value = "/batchSave")
    public ResponseMessage batchSaveCourse(@RequestBody List<CourseDTO> courseDTOs) {

        if (CollectionUtils.isEmpty(courseDTOs)) {
            throw new GlobalException("数据为空");
        }
        List<Course> courses = CopyUtil.toList(courseDTOs, Course.class);

        List<Course> saveCourse = courseService.batchSaveCourse(courses);
        if (CollectionUtils.isEmpty(saveCourse)) {
            throw new GlobalException("添加失败");
        }

        return ResponseMessage.success("添加成功");
    }

    @ApiOperation("删除课程")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseMessage deleteCourse(@PathVariable(value = "id", required = true) Long id) {

        if (ObjectUtils.isEmpty(id)) {
            throw new GlobalException("数据为空");
        }

        courseService.deleteCourse(id);
        log.info("deleteCourse方法执行结束");

        return ResponseMessage.success("删除成功");
    }

    @ApiOperation("批量删除课程")
    @PostMapping(value = "/delete")
    public ResponseMessage batchDeleteCourse(List<Long> ids) {

        if (ObjectUtils.isEmpty(ids)) {
            throw new GlobalException("数据为空");
        }

        courseService.batchDeleteCourse(ids);

        return ResponseMessage.success("删除成功");
    }


}
