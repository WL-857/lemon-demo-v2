package com.nhsoft.lemon.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author wanglei
 */
@Data
public class PageUtil {

    private  int offset;

    private  int rows;

    public  PageUtil check(int pageNo, int pageSize) {
        if (pageNo <= 0) {
            pageNo = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        if(pageNo == 1){
            offset = 0;
        }else{
            offset = pageNo * pageNo;
        }
        rows = pageSize;
        PageUtil pageUtil = new PageUtil();
        pageUtil.setOffset(offset);
        pageUtil.setRows(rows);
        return pageUtil;
    }

}
