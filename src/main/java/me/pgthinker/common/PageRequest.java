package me.pgthinker.common;

import lombok.Data;
import me.pgthinker.constant.CommonConstant;

/**
 * 分页请求
 * @Author: Zhang De ning
 */
@Data
public class PageRequest {
    public final static int DEFAULT_CURRENT = 1;
    public final static int DEFAULT_PAGESIZE = 10;

    /**
     * 当前页号
     */
    private int current = DEFAULT_CURRENT;

    /**
     * 页面大小
     */
    private int pageSize = DEFAULT_PAGESIZE;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
