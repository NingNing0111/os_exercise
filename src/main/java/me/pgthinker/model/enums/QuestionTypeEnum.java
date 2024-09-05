package me.pgthinker.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Project: me.pgthinker.model.enums
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/9/4 14:09
 * @Description:
 */
@AllArgsConstructor
@Getter
public enum QuestionTypeEnum {

    SINGLE_SELECT("single_answer", "单选题"),
    MULTI_SELECT("multi_answer", "多选题"),
    JUDGE("true_false", "判断题"),
    FILL_BLANK("fill_in_the_blank", "填空题"),
    ESSAY_QUESTION("question_answer", "问答题");

    private String value;
    private String text;

}
