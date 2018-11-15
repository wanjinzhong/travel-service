package com.tip.travel.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.tip.travel.common.domain.SysException;
import com.tip.travel.common.service.ExceptionService;
import com.tip.travel.service.config.SystemConfig;
import com.tip.travel.service.dao.SysExceptionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExceptionServiceImpl implements ExceptionService {
    @Autowired
    private SysExceptionDao exceptionDao;

    @Autowired
    private SystemConfig config;

    @Override
    public void logException(Throwable e) {
        SysException sysException = new SysException();
        sysException.setClassName(e.getClass().getName());
        sysException.setMessage(e.getMessage());
        StringBuilder stackInfo = new StringBuilder();
        for (StackTraceElement element :e.getStackTrace()) {
            stackInfo.append(element.toString());
            stackInfo.append("\n");
        }
        sysException.setStackTrace(stackInfo.toString());
        if (config.getLogExceptionToDB()) {
            exceptionDao.saveException(sysException);
        }
        if (config.getMailException()) {
            // todo send exception by email
        }
    }
}
