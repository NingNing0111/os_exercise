package me.pgthinker.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.pgthinker.common.ErrorCode;
import me.pgthinker.exception.BusinessException;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Project: com.tydic.qydcfx.utils
 * @Author: Zhang De ning
 * @Date: 2024/8/16 08:48
 * @Description:
 */
public class PageCovertUtils {
    /**
     * 将PageInfo对象泛型中的Po对象转化为Vo对象
     *
     * @param pageInfoPo PageInfo<Po>对象</>
     * @param <V>        V类型
     * @return
     */
    public static <P,V> IPage<V> pageVoCovert(IPage<P> pageInfoPo, Class<V> v) {
        // 创建Page对象，实际上是一个ArrayList类型的集合
        try {
            if (pageInfoPo != null) {

                IPage<V> page = new Page<>(pageInfoPo.getCurrent(), pageInfoPo.getSize());
                page.setTotal(pageInfoPo.getTotal());
                List<P> records = pageInfoPo.getRecords();
                List<V> list = new ArrayList<>();
                for (P record : records) {
                    if(record!=null) {
                        V newV = v.newInstance();
                        // 把原对象数据拷贝到新的对象
                        BeanUtils.copyProperties(record,newV);
                        list.add(newV);
                    }
                }
                page.setRecords(list);
                page.setTotal(pageInfoPo.getTotal());
                return page;
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"查询数据处理出现异常");
        }
        return null;
    }
}
