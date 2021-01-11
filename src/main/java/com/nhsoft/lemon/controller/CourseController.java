package com.nhsoft.lemon.controller;

import com.nhsoft.lemon.dto.CourseDTO;
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
    public ResponseMessage listAllCourse(@PathVariable(name = "pageNo",required = true) int pageNo,
                                         @PathVariable(name = "pageSize",required = true) int pageSize) {

        if (pageNo < 0) {
            pageNo = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        List<Course> courses = courseService.listAllCourse(pageNo,pageSize);
        if (CollectionUtils.isEmpty(courses)) {
            return ResponseMessage.error("数据为空");
        }
        List<CourseDTO> courseDTOS = CopyUtil.toList(courses, CourseDTO.class);
        return ResponseMessage.success("courseDTOS", courseDTOS);
    }

    @ApiOperation("根据课程编号查询课程信息")
    @GetMapping("/{couId}")
    public ResponseMessage readCourse(@PathVariable(name = "couId",required = true) Long couId) {
        if(ObjectUtils.isEmpty(couId)){
            return ResponseMessage.error("参数为空");
        }
        Course course = courseService.readCourse(couId);
            if (course == null) {
            return ResponseMessage.error("数据为空");
        }
        CourseDTO courseDTO = CopyUtil.to(course, CourseDTO.class);
        log.info("readCourse方法执行结束，方法返回值为：" + courseDTO);
        return ResponseMessage.success("courseDTOS", courseDTO);
    }

    @ApiOperation("添加课程")
    @PostMapping("/save")
    public ResponseMessage saveCourse(@RequestBody CourseDTO courseDTO){
        if(ObjectUtils.isEmpty(courseDTO)){
            return ResponseMessage.error("参数为空，请输入参数");
        }
        Course course = CopyUtil.to(courseDTO, Course.class);

        Course saveCourse = courseService.saveCourse(course);
        if(saveCourse == null){
            return ResponseMessage.error("添加失败");
        }
        return ResponseMessage.success("添加成功");
    }

    @ApiOperation("批量添加课程")
    @PostMapping(value = "/batchSave")
    public ResponseMessage batchSaveCourse(@RequestBody List<CourseDTO> courseDTOs){

        if(CollectionUtils.isEmpty(courseDTOs)){
            return ResponseMessage.error("参数为空，请输入参数");
        }
        List<Course> courses = CopyUtil.toList(courseDTOs, Course.class);

        List<Course> saveCourse = courseService.batchSaveCourse(courses);
        if(CollectionUtils.isEmpty(saveCourse)){
            return ResponseMessage.error("添加失败");
        }

        return ResponseMessage.success("添加成功");
    }

    @ApiOperation("删除课程")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseMessage deleteCourse(@PathVariable(value = "id",required = true) Long id){
        log.info("deleteCourse方法开始执行,参数为：id=" + id);

        if(ObjectUtils.isEmpty(id)){
            return ResponseMessage.error("参数为空，请输入参数");
        }

        courseService.deleteCourse(id);
        log.info("deleteCourse方法执行结束");

        return ResponseMessage.success("删除成功");
    }

    @ApiOperation("批量删除课程")
    @PostMapping(value = "/delete")
    public ResponseMessage batchDeleteCourse(List<Long> ids){
        log.info("batchDeleteCourse方法开始执行,参数为：ids=" + ids);

        if(ObjectUtils.isEmpty(ids)){
            return ResponseMessage.error("参数为空，请输入参数");
        }

        courseService.batchDeleteCourse(ids);
        log.info("batchDeleteCourse方法执行结束");
        return ResponseMessage.success("删除成功");
    }


}
