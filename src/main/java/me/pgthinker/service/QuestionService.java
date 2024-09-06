package me.pgthinker.service;

import me.pgthinker.model.dto.update.UpdateQuestion;
import me.pgthinker.model.entity.Question;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import me.pgthinker.model.vo.QuestionKnowledgeVO;
import me.pgthinker.model.vo.QuestionTypeVO;
import me.pgthinker.model.vo.QuestionVO;

import java.util.List;

/**
* @author pgthinker
* @description 针对表【question】的数据库操作Service
* @createDate 2024-09-04 13:50:20
*/
public interface QuestionService extends IService<Question> {
    QuestionVO getQuestionVOById(Long id);

    IPage<QuestionVO> questionsWithPage(int current, int pageSize, String type, String knowledge);

    boolean updateQuestion(UpdateQuestion updateQuestion);

    List<QuestionTypeVO> questionTypes();

    List<QuestionKnowledgeVO> questionKnowledge();



}
