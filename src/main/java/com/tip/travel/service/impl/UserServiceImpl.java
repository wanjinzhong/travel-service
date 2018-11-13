package com.tip.travel.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.tip.travel.common.bo.LoginBo;
import com.tip.travel.common.bo.LoginResBo;
import com.tip.travel.common.exception.BizException;
import com.tip.travel.common.exception.UnauthenticatedException;
import com.tip.travel.common.service.UserService;
import com.tip.travel.common.utils.Md5SaltTool;
import com.tip.travel.service.UserSecretBo;
import com.tip.travel.service.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public LoginResBo login(LoginBo loginBo) {
        switch (loginBo.getLoginType()) {
            case WX:
                return loginByWX(loginBo.getWxOpenId());
            case PHONE_DYNAMIC:
                return loginByPhoneDynamic(loginBo.getPhone(), loginBo.getDynamicCode());
            case PHONE_PASSWORD:
                return loginByPhonePassword(loginBo.getPhone(), loginBo.getPassword());
            default:
                throw new UnauthenticatedException("不支持的登陆方式");

        }
    }

    private LoginResBo loginByPhonePassword(String phoneNumber, String password) {
        UserSecretBo userSecretBo = userDao.querySecurityByPhone(phoneNumber);
        if (userSecretBo == null || !Md5SaltTool.validatePassword(password, userSecretBo.getPassword(), userSecretBo.getSalt())) {
            throw new BizException("手机号或者密码错误");
        }
        LoginResBo loginResBo = new LoginResBo();
        loginResBo.setUserId(userSecretBo.getUserId());
        loginResBo.setUserName(userSecretBo.getUserName());
        return loginResBo;
    }

    private LoginResBo loginByPhoneDynamic(String phoneNumber, String dynamicCode) {
        throw new UnauthenticatedException("暂不支持手机号动态登陆");
    }

    private LoginResBo loginByWX(String wxOpenId) {
        throw new UnauthenticatedException("暂不支持微信登陆");
    }
}
