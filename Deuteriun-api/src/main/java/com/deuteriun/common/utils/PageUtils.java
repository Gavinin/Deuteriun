package com.deuteriun.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deuteriun.common.enums.PageStatus;
import org.springframework.stereotype.Component;

/**
 * @ClassName PageUtils
 * @Description TODO
 * @Author gavin
 * @Date 31/5/2022 18:25
 * @Version 1.0
 **/
@Component
public class PageUtils<T> {
    public  IPage<T> page(Long page, Long limit) {
        long pageValue = PageStatus.DEFAULT_PAGE_NUMBER;
        long limitValue = PageStatus.DEFAULT_PAGE_LIMIT;
        if (limit != null && page != null) {
            pageValue = page;
            limitValue = limit;
        }
        return new Page<>(pageValue, limitValue);

    }
}
