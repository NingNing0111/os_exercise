package me.pgthinker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.pgthinker.model.entity.Question;
import me.pgthinker.service.QuestionService;
import me.pgthinker.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author pgthinker
* @description 针对表【question】的数据库操作Service实现
* @createDate 2024-09-04 13:50:20
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




