package com.nhsoft.lemon.controller;

import com.nhsoft.lemon.dto.CourseDTO;
import com.nhsoft.lemon.dto.ScoreDTO;
import com.nhsoft.lemon.exception.GlobalException;
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
    public ResponseMessage listAllScore(@PathVariable(name = "pageNo", required = true) int pageNo,
                                        @PathVariable(name = "pageSize", required = true) int pageSize) {

        List<ScoreExtend> scoreExtends = scoreService.listAll(pageNo, pageSize);
        if (CollectionUtils.isEmpty(scoreExtends)) {
            throw new GlobalException("参数为空");
        }
        List<ScoreDTO> scoreDTOS = CopyUtil.toList(scoreExtends, ScoreDTO.class);
        return ResponseMessage.success("courseDTOS", scoreDTOS);
    }

    @ApiOperation("根据课程编号查询分数")
    @GetMapping("/{id}")
    public ResponseMessage readScore(@PathVariable(name = "id", required = true) Long id) {

        if (ObjectUtils.isEmpty(id)) {
            throw new GlobalException("参数为空");
        }
        ScoreExtend score = scoreService.readScore(id);
        if (score == null) {
            throw new GlobalException("参数为空");
        }
        ScoreDTO scoreDTO = CopyUtil.to(score, ScoreDTO.class);
        return ResponseMessage.success("courseDTOS", scoreDTO);
    }

    @ApiOperation("添加分数")
    @PostMapping("/save")
    public ResponseMessage saveScore(@RequestBody ScoreDTO scoreDTO) {

        if (ObjectUtils.isEmpty(scoreDTO)) {
            throw new GlobalException("参数为空");
        }
        Score score = CopyUtil.to(scoreDTO, Score.class);
        Score saveScore = scoreService.saveScore(score);
        if (saveScore == null) {
            throw new GlobalException("添加失败");
        }
        return ResponseMessage.success("添加成功");
    }

    @ApiOperation("批量添加分数")
    @PostMapping(value = "/batchSave")
    public ResponseMessage batchSaveScore(@RequestBody List<ScoreDTO> scoreDTOS) {

        if (CollectionUtils.isEmpty(scoreDTOS)) {
            throw new GlobalException("参数为空");
        }
        List<Score> scores = CopyUtil.toList(scoreDTOS, Score.class);
        List<Score> saveScore = scoreService.batchSaveScore(scores);
        if (CollectionUtils.isEmpty(saveScore)) {
            throw new GlobalException("添加失败");
        }
        return ResponseMessage.success("添加成功");
    }

    @ApiOperation("删除分数")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseMessage deleteScore(@PathVariable(value = "id", required = true) Long id) {

        if (ObjectUtils.isEmpty(id)) {
            throw new GlobalException("参数为空");
        }
        scoreService.deleteScore(id);
        return ResponseMessage.success("删除成功");
    }

    @ApiOperation("批量删除分数")
    @PostMapping(value = "/delete")
    public ResponseMessage batchDeleteScore(List<Long> ids) {

        if (ObjectUtils.isEmpty(ids)) {
            throw new GlobalException("参数为空");
        }
        scoreService.batchDeleteScore(ids);
        return ResponseMessage.success("删除成功");
    }


}
