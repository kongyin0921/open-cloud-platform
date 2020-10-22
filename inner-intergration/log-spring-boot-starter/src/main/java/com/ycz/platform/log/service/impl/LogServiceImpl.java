package com.ycz.platform.log.service.impl;

import com.open.capacity.common.model.SysLog;
import com.ycz.platform.datasource.annotation.DataSource;
import com.ycz.platform.log.dao.LogDao;
import com.ycz.platform.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;

/**
 * @ClassName LogServiceImpl
 * @Description 切换数据源，存储log-center
 * @Auther kongyin
 * @Date 2020/10/22 14:20
 **/

public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    @DataSource(name = "log")
    @Async
    public void save(SysLog log) {

        if (log.getCreateTime() == null) {
            log.setCreateTime(new Date());
        }
        if (log.getFlag() == null) {
            log.setFlag(Boolean.TRUE);
        }

        logDao.save(log);
    }
}
