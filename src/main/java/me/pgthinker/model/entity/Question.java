package me.pgthinker.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

/**
 * 
 * @TableName question
 */
@TableName(value ="`question`",autoResultMap = true)
@Data
public class Question implements Serializable {
    /**
     * 题目ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 题目内容
     */
    @TableField(value = "content")
    private String content;

    @TableField(value = "`options`", typeHandler = JacksonTypeHandler.class)
    private List<String> options;

    /**
     * 答案
     */
    @TableField(value = "answer")
    private String answer;

    /**
     * 答案解释
     */
    @TableField(value = "`explain`")
    private String explain;

    /**
     * 题目难度
     */
    @TableField(value = "difficulty")
    private Integer difficulty;

    /**
     * 题目来源
     */
    @TableField(value = "source")
    private String source;

    /**
     * 考点/知识点
     */
    @TableField(value = "knowledge",typeHandler = JacksonTypeHandler.class)
    private List<String> knowledge;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "isDelete")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}