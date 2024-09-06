package me.pgthinker.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.pgthinker.common.ErrorCode;
import me.pgthinker.common.ResultUtils;
import me.pgthinker.constant.CacheConstant;
import me.pgthinker.exception.BusinessException;
import me.pgthinker.model.dto.update.UpdateQuestion;
import me.pgthinker.model.entity.Question;
import me.pgthinker.model.enums.QuestionTypeEnum;
import me.pgthinker.model.vo.QuestionKnowledgeVO;
import me.pgthinker.model.vo.QuestionTypeVO;
import me.pgthinker.model.vo.QuestionVO;
import me.pgthinker.service.QuestionService;
import me.pgthinker.mapper.QuestionMapper;
import me.pgthinker.util.PageCovertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author pgthinker
* @description 针对表【question】的数据库操作Service实现
* @createDate 2024-09-04 13:50:20
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

    @Cacheable(cacheManager = CacheConstant.LOCAL_CACHE,keyGenerator = CacheConstant.KEY_GENERATOR,value = CacheConstant.QUESTION_INFO)
    @Override
    public QuestionVO getQuestionVOById(Long id) {
        QueryWrapper<Question> qw = new QueryWrapper<>();
        qw.eq("id", id);
        Question question = getOne(qw);
        if(ObjectUtils.isEmpty(question)){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question,questionVO);
        return questionVO;
    }

    @Cacheable(cacheManager = CacheConstant.LOCAL_CACHE,key = "'q_page_' + #current + '_' + #pageSize + '_' + #type + '_' + #knowledge",value = CacheConstant.QUESTION_INFO)
    @Override
    public IPage<QuestionVO> questionsWithPage(int current, int pageSize, String type, String knowledge) {
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
        Page<Question> page = page(questionPage, qw);
        IPage<QuestionVO> questionVOIPage = PageCovertUtils.pageVoCovert(page, QuestionVO.class);
        return questionVOIPage;
    }

    // 清除缓存
    @CacheEvict(cacheManager = CacheConstant.LOCAL_CACHE, value = CacheConstant.QUESTION_INFO, allEntries = true)
    @Override
    public boolean updateQuestion(UpdateQuestion updateQuestion) {
        Long id = updateQuestion.getId();
        Question storeQ = getById(id);
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
        boolean isUpdated = updateById(storeQ);
        if(!isUpdated){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return true;
    }

    @Cacheable(cacheManager = CacheConstant.LOCAL_CACHE,keyGenerator = CacheConstant.KEY_GENERATOR,value = CacheConstant.QUESTION_INFO)
    @Override
    public List<QuestionTypeVO> questionTypes() {
        QuestionTypeEnum[] values = QuestionTypeEnum.values();
        List<QuestionTypeVO> types = Arrays.stream(values).map(item -> {
            QuestionTypeVO questionTypeVO = new QuestionTypeVO();
            questionTypeVO.setLabel(item.getText());
            questionTypeVO.setValue(item.getValue());
            return questionTypeVO;
        }).collect(Collectors.toList());
        return types;
    }

    @Cacheable(cacheManager = CacheConstant.LOCAL_CACHE,keyGenerator = CacheConstant.KEY_GENERATOR,value = CacheConstant.QUESTION_INFO)
    @Override
    public List<QuestionKnowledgeVO> questionKnowledge() {
        List<Question> list = list();
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

        return res;
    }
}




