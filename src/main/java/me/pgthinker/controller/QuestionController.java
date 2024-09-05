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
        QueryWrapper<Question> qw = new QueryWrapper<>();
        qw.eq("id", questionId);
        Question question = questionService.getOne(qw);
        System.out.println(questionId+" ========= "+question);
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question,questionVO);
        return ResultUtils.success(questionVO);
    }

    @GetMapping("/{current}/{pageSize}")
    public BaseResponse<IPage<QuestionVO>> questionVOPage( @PathVariable int current, @PathVariable int pageSize,@RequestParam(required = false) String type, @RequestParam(required = false) String knowledge){
        QueryWrapper<Question> qw = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(knowledge)){
            qw.like("knowledge", knowledge);
        }
        if(!ObjectUtils.isEmpty(type)){
            qw.eq("type",type);
        }
        if(pageSize > 50){
            pageSize = 20;
        }

        // 查出所有元素
        Page<Question> questionPage = new Page<>(current, pageSize);
        Page<Question> page = questionService.page(questionPage, qw);
        IPage<QuestionVO> questionVOIPage = PageCovertUtils.pageVoCovert(page, QuestionVO.class);
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
        Long id = updateQuestion.getId();
        Question storeQ = questionService.getById(id);
        if(ObjectUtils.isEmpty(storeQ)){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        String answer = updateQuestion.getAnswer();
        String explain = updateQuestion.getExplain();
        String content = updateQuestion.getContent();
        if(ObjectUtils.isEmpty(answer) || ObjectUtils.isEmpty(explain) || ObjectUtils.isEmpty(content)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        storeQ.setAnswer(answer);
        storeQ.setExplain(explain);
        storeQ.setContent(content);
        boolean isUpdated = questionService.updateById(storeQ);
        if(!isUpdated){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtils.success("修改成功");
    }

    @GetMapping("/types")
    public BaseResponse<List<QuestionTypeVO>> types(){
        QuestionTypeEnum[] values = QuestionTypeEnum.values();
        List<QuestionTypeVO> types = Arrays.asList(values).stream().map(item -> {
            QuestionTypeVO questionTypeVO = new QuestionTypeVO();
            questionTypeVO.setLabel(item.getText());
            questionTypeVO.setValue(item.getValue());
            return questionTypeVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(types);
    }

    @GetMapping("/knowledge")
    public BaseResponse<List<QuestionKnowledgeVO>> knowledge(){
        List<Question> list = questionService.list();
        HashSet<String> knowledgeSet = new HashSet<>();
        for(Question question: list){
            List<String> knowledge = question.getKnowledge();
            knowledgeSet.addAll(knowledge);
        }
        List<QuestionKnowledgeVO> res = knowledgeSet.stream().filter(StringUtils::hasText).map(item -> {
            QuestionKnowledgeVO questionKnowledgeVO = new QuestionKnowledgeVO();
            questionKnowledgeVO.setLabel(item);
            questionKnowledgeVO.setValue(item);
            return questionKnowledgeVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(res);
    }

}
