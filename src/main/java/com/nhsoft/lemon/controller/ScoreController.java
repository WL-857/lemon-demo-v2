package com.nhsoft.lemon.controller;

import com.nhsoft.lemon.dto.CourseDTO;
import com.nhsoft.lemon.dto.ScoreDTO;
import com.nhsoft.lemon.model.Course;
import com.nhsoft.lemon.model.Score;
import com.nhsoft.lemon.model.extend.ScoreExtend;
import com.nhsoft.lemon.response.ResponseMessage;
import com.nhsoft.lemon.service.CourseService;
import com.nhsoft.lemon.service.ScoreService;
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
@Api("分数信息模块")
@Slf4j
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @ApiOperation("查询所有课程信息并分页")
    @GetMapping("/list/{pageNo}/{pageSize}")
    public ResponseMessage listAllScore(@PathVariable(name = "pageNo",required = true) int pageNo,
                                         @PathVariable(name = "pageSize",required = true) int pageSize) {
        log.info("listAllScore方法开始执行,参数为：pageNo=" + pageNo + ",pageSize=" + pageSize);

        if (pageNo < 0) {
            pageNo = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        List<ScoreExtend> scoreExtends = scoreService.listAllScore(pageNo,pageSize);
        if (CollectionUtils.isEmpty(scoreExtends)) {
            return ResponseMessage.error("数据为空");
        }
        List<ScoreDTO> scoreDTOS = CopyUtil.toList(scoreExtends, ScoreDTO.class);
        log.info("方法执行结束，方法返回值为：" + scoreDTOS);
        return ResponseMessage.ok().put("courseDTOS", scoreDTOS);
    }

    @ApiOperation("根据课程编号查询分数")
    @GetMapping("/{id}")
    public ResponseMessage readScore(@PathVariable(name = "id",required = true) Long id) {
        log.info("readScore方法开始执行,参数为：id=" + id);
        if(ObjectUtils.isEmpty(id)){
            return ResponseMessage.error("参数为空");
        }
        ScoreExtend score = scoreService.readScore(id);
            if (score == null) {
            return ResponseMessage.error("数据为空");
        }
        ScoreDTO scoreDTO = CopyUtil.to(score, ScoreDTO.class);
        log.info("readCourse方法执行结束，方法返回值为：" + scoreDTO);
        return ResponseMessage.ok().put("courseDTOS", scoreDTO);
    }

    @ApiOperation("添加分数")
    @PostMapping("/save")
    public ResponseMessage saveScore(@RequestBody ScoreDTO scoreDTO){
        log.info("saveScore方法开始执行,参数为：scoreDTO=" + scoreDTO);
        if(ObjectUtils.isEmpty(scoreDTO)){
            return ResponseMessage.error("参数为空，请输入参数");
        }
        Score score = CopyUtil.to(scoreDTO, Score.class);

        Score saveScore = scoreService.saveScore(score);
        if(saveScore == null){
            return ResponseMessage.error("添加失败");
        }
        log.info("saveScore方法执行结束");
        return ResponseMessage.ok("添加成功");
    }

    @ApiOperation("批量添加分数")
    @PostMapping(value = "/batchSave")
    public ResponseMessage batchSaveScore(@RequestBody List<ScoreDTO> scoreDTOS){
        log.info("batchSaveCourse方法开始执行,参数为：scoreDTOS=" + scoreDTOS);

        if(CollectionUtils.isEmpty(scoreDTOS)){
            return ResponseMessage.error("参数为空，请输入参数");
        }
        List<Score> scores = CopyUtil.toList(scoreDTOS, Score.class);

        List<Score> saveScore = scoreService.batchSaveScore(scores);
        if(CollectionUtils.isEmpty(saveScore)){
            return ResponseMessage.error("添加失败");
        }
        log.info("batchSaveScore方法执行结束");

        return ResponseMessage.ok("添加成功");
    }

    @ApiOperation("删除分数")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseMessage deleteScore(@PathVariable(value = "id",required = true) Long id){
        log.info("deleteScore方法开始执行,参数为：id=" + id);

        if(ObjectUtils.isEmpty(id)){
            return ResponseMessage.error("参数为空，请输入参数");
        }

        scoreService.deleteScore(id);
        log.info("deleteScore方法执行结束");

        return ResponseMessage.ok("删除成功");
    }

    @ApiOperation("批量删除分数")
    @PostMapping(value = "/delete")
    public ResponseMessage batchDeleteScore(List<Long> ids){
        log.info("batchDeleteScore方法开始执行,参数为：ids=" + ids);

        if(ObjectUtils.isEmpty(ids)){
            return ResponseMessage.error("参数为空，请输入参数");
        }

        scoreService.batchDeleteScore(ids);
        log.info("batchDeleteScore方法执行结束");
        return ResponseMessage.ok("删除成功");
    }


}
