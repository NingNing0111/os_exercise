package me.pgthinker.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.pgthinker.common.BaseResponse;
import me.pgthinker.common.ErrorCode;
import me.pgthinker.common.ResultUtils;
import me.pgthinker.exception.BusinessException;
import me.pgthinker.model.dto.update.UpdateQuestion;
import me.pgthinker.model.entity.Question;
import me.pgthinker.model.enums.QuestionTypeEnum;
import me.pgthinker.model.vo.QuestionKnowledgeVO;
import me.pgthinker.model.vo.QuestionTypeVO;
import me.pgthinker.model.vo.QuestionVO;
import me.pgthinker.service.QuestionService;
import me.pgthinker.util.PageCovertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Project: me.pgthinker.controller
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/9/4 14:35
 * @Description:
 */
@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    @Value("${app.password}")
    private String password;

    @GetMapping("/{questionId}")
    public BaseResponse<QuestionVO> questionVOById(@PathVariable Long questionId){
        QuestionVO questionVO = questionService.getQuestionVOById(questionId);

        return ResultUtils.success(questionVO);
    }

    @GetMapping("/{current}/{pageSize}")
    public BaseResponse<IPage<QuestionVO>> questionVOPage( @PathVariable int current, @PathVariable int pageSize,@RequestParam(required = false) String type, @RequestParam(required = false) String knowledge){

        IPage<QuestionVO> questionVOIPage = questionService.questionsWithPage(current, pageSize, type, knowledge);

        return ResultUtils.success(questionVOIPage);
    }

    @PutMapping("/")
    public BaseResponse<String> updateQuestion(@RequestBody UpdateQuestion updateQuestion){
        if(ObjectUtils.isEmpty(updateQuestion)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userPassword = updateQuestion.getPassword();
        if(password != null){
            if(ObjectUtils.isEmpty(userPassword)){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            if(!password.equals(userPassword)){
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"密码错误");
            }
        }
        boolean isUpdated = questionService.updateQuestion(updateQuestion);
        if(!isUpdated){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新异常");
        }

        return ResultUtils.success("更新成功");
    }

    @GetMapping("/types")
    public BaseResponse<List<QuestionTypeVO>> types(){
        List<QuestionTypeVO> types = questionService.questionTypes();

        return ResultUtils.success(types);
    }

    @GetMapping("/knowledge")
    public BaseResponse<List<QuestionKnowledgeVO>> knowledge(){
        List<QuestionKnowledgeVO> questionKnowledgeVOS = questionService.questionKnowledge();
        return ResultUtils.success(questionKnowledgeVOS);
    }

}
