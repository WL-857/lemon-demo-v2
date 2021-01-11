package com.nhsoft.lemon.controller;

import com.nhsoft.lemon.dto.AvgMaxMinDTO;
import com.nhsoft.lemon.dto.StudentDTO;
import com.nhsoft.lemon.dto.TeacherDTO;
import com.nhsoft.lemon.model.Student;
import com.nhsoft.lemon.model.Teacher;
import com.nhsoft.lemon.model.extend.TeacherExtend;
import com.nhsoft.lemon.response.ResponseMessage;
import com.nhsoft.lemon.service.ScoreService;
import com.nhsoft.lemon.service.TeacherService;
import com.nhsoft.lemon.utils.CopyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wanglei
 */
@RestController
@Api("教师信息信息模块")
@Slf4j
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ScoreService scoreService;

    @ApiOperation("查询所有教师信息并分页")
    @GetMapping("/list/{pageNo}/{pageSize}")
    public ResponseMessage listAllTeacher(@PathVariable(name = "pageNo",required = true) int pageNo,
                                          @PathVariable(name = "pageSize",required = true) int pageSize) {
        log.info("listAllTeacher开始执行，参数为：pageNo=" + pageNo + ",pageSize=" + pageSize);

        if (pageNo <= 0) {
            pageNo = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        List<Teacher> teachers = teacherService.listAllTeacher(pageNo, pageSize);
        if (CollectionUtils.isEmpty(teachers)) {
            return ResponseMessage.error("数据为空");
        }
        List<TeacherDTO> teacherDTOS = CopyUtil.toList(teachers, TeacherDTO.class);
        return ResponseMessage.success("teacherDTOS", teacherDTOS);
    }

    @ApiOperation("教师查询其所教授科目的成绩最大值，最小值，平均值")
    @GetMapping("/score/{teachId}/{year}")
    public ResponseMessage readMaxAndMinAndAvgScore(@PathVariable(value = "teachId",required = true) Long teachId,
                                                    @PathVariable(value = "year",required = true) String year){
        log.info("readMaxAndMinAndAvgScore开始执行，参数为：teachId=" + teachId + ",year=" + year);
        List<TeacherExtend> scoreExtends = scoreService.listMaxMinAvgScore(teachId, year);
        if (CollectionUtils.isEmpty(scoreExtends)) {
            return ResponseMessage.error("数据为空");
        }
        List<AvgMaxMinDTO> avgMaxMinDTOS = CopyUtil.toList(scoreExtends, AvgMaxMinDTO.class);

        return ResponseMessage.success("avgMaxMinDTOS",avgMaxMinDTOS);

    }

    @ApiOperation("查询学年所有学生每门学科的成绩最大值，最小值，平均值")
    @GetMapping("/score/{year}")
    public ResponseMessage listAllMaxMinAvgScore(@PathVariable(value = "year",required = true) String year){
        log.info("listAllMaxMinAvgScore开始执行，参数为：year=" + year);
        List<TeacherExtend> teacherExtends = scoreService.listAllMaxMinAvgScore(year);
        if (CollectionUtils.isEmpty(teacherExtends)) {
            return ResponseMessage.error("数据为空");
        }

        List<AvgMaxMinDTO> avgMaxMinDTOS = CopyUtil.toList(teacherExtends, AvgMaxMinDTO.class);
        return ResponseMessage.success("avgMaxMinDTOS",avgMaxMinDTOS);
    }

    @ApiOperation("根据id查询教师信息")
    @GetMapping("/{id}")
    public ResponseMessage readTeacher(@PathVariable(name = "id",required = true) Long id) {
        log.info("readTeacher方法开始执行,参数为：id=" + id);
        if(ObjectUtils.isEmpty(id)){
            return ResponseMessage.error("参数为空");
        }
        Teacher teacher = teacherService.readTeacher(id);
        if (teacher == null) {
            return ResponseMessage.error("数据为空");
        }
        TeacherDTO teacherDTO = CopyUtil.to(teacher, TeacherDTO.class);
        log.info("readCourse方法执行结束，方法返回值为：" + teacherDTO);
        return ResponseMessage.success("studentDTO", teacherDTO);
    }

    @ApiOperation("添加教师")
    @PostMapping("/save")
    public ResponseMessage saveTeacher(@RequestBody TeacherDTO teacherDTO){
        log.info("saveTeacher方法开始执行,参数为：teacherDTO=" + teacherDTO);

        if(ObjectUtils.isEmpty(teacherDTO)){
            return ResponseMessage.error("参数为空，请输入参数");
        }
        Teacher teacher = CopyUtil.to(teacherDTO, Teacher.class);

        Teacher saveTeacher = teacherService.saveTeacher(teacher);
        if(saveTeacher == null){
            return ResponseMessage.error("添加失败");
        }
        log.info("saveTeacher方法执行结束");
        return ResponseMessage.success("添加成功");
    }

    @ApiOperation("批量添加教师")
    @PostMapping(value = "/batchSave")
    public ResponseMessage batchSaveTeacher(@RequestBody List<TeacherDTO> teacherDTOS){
        log.info("batchSaveTeacher方法开始执行,参数为：teacherDTOS=" + teacherDTOS);

        if(CollectionUtils.isEmpty(teacherDTOS)){
            return ResponseMessage.error("参数为空，请输入参数");
        }
        List<Teacher> teachers = CopyUtil.toList(teacherDTOS, Teacher.class);

        List<Teacher> teacherList = teacherService.batchSaveTeacher(teachers);
        if(CollectionUtils.isEmpty(teacherList)){
            return ResponseMessage.error("添加失败");
        }
        log.info("batchSaveTeacher方法执行结束");

        return ResponseMessage.success("添加成功");
    }

    @ApiOperation("删除教师")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseMessage deleteTeacher(@PathVariable(value = "id",required = true) Long id){
        log.info("deleteTeacher方法开始执行,参数为：id=" + id);

        if(ObjectUtils.isEmpty(id)){
            return ResponseMessage.error("参数为空，请输入参数");
        }

        teacherService.deleteTeacher(id);
        log.info("deleteStudent方法执行结束");

        return ResponseMessage.success("删除成功");
    }

    @ApiOperation("批量删除教师")
    @PostMapping(value = "/delete")
    public ResponseMessage batchDeleteTeacher(List<Long> ids){
        log.info("batchDeleteTeacher方法开始执行,参数为：ids=" + ids);

        if(ObjectUtils.isEmpty(ids)){
            return ResponseMessage.error("参数为空，请输入参数");
        }

        teacherService.batchDeleteTeacher(ids);
        log.info("batchDeleteTeacher方法执行结束");
        return ResponseMessage.success("删除成功");
    }
}
