package me.pgthinker.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Project: me.pgthinker.model.vo
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/9/4 14:37
 * @Description:
 */
@Data
public class QuestionVO implements Serializable {
    /**
     * 题目ID
     */
    private Long id;

    /**
     * 题型
     */
    private String type;

    /**
     * 题目内容
     */
    private String content;

    private List<String> options;

    /**
     * 答案
     */
    private String answer;

    /**
     * 答案解释
     */
    private String explain;

    /**
     * 题目难度
     */
    private Integer difficulty;

    /**
     * 题目来源
     */
    private String source;

    /**
     * 考点/知识点
     */
    private List<String> knowledge;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
