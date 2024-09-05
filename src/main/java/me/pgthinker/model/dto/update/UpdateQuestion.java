package me.pgthinker.model.dto.update;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Project: me.pgthinker.model.dto.update
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/9/4 17:49
 * @Description: 更新对象
 */
@Data
public class UpdateQuestion {
    /**
     * 修改题目时要用的密码
     */
    private String password;
    /**
     * 题目ID
     */
    private Long id;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 答案
     */
    private String answer;

    /**
     * 答案解释
     */
    private String explain;
}
