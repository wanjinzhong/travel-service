package com.tip.travel.service.impl;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.dubbo.config.annotation.Service;
import com.tip.travel.common.bo.LoginBo;
import com.tip.travel.common.bo.UserBasicInfo;
import com.tip.travel.common.exception.BizException;
import com.tip.travel.common.exception.UnauthenticatedException;
import com.tip.travel.common.service.UserService;
import com.tip.travel.common.utils.Md5SaltTool;
import com.tip.travel.service.UserSecretBo;
import com.tip.travel.service.dao.UserDao;
import com.tip.travel.service.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserBasicInfo login(LoginBo loginBo) {
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

    @Override
    public Boolean cacheUserToken(UserBasicInfo userBasicInfo) {
        String key = buildUserRedisKey(userBasicInfo.getToken());
        return redisUtil.set(key, userBasicInfo.getUserId());
    }

    @Override
    public Long checkLogin(String token) {
        String key = buildUserRedisKey(token);
        return Long.valueOf(redisUtil.get(key).toString());
    }

    private UserBasicInfo loginByPhonePassword(String phoneNumber, String password) {
        UserSecretBo userSecretBo = userDao.querySecurityByPhone(phoneNumber);
        if (userSecretBo == null || !Md5SaltTool.validatePassword(password, userSecretBo.getPassword(), userSecretBo.getSalt())) {
            throw new BizException("手机号或者密码错误");
        }
        UserBasicInfo loginResBo = new UserBasicInfo();
        loginResBo.setUserId(userSecretBo.getUserId());
        loginResBo.setUserName(userSecretBo.getUserName());
        return loginResBo;
    }

    private UserBasicInfo loginByPhoneDynamic(String phoneNumber, String dynamicCode) {
        throw new UnauthenticatedException("暂不支持手机号动态登陆");
    }

    private UserBasicInfo loginByWX(String wxOpenId) {
        throw new UnauthenticatedException("暂不支持微信登陆");
    }

    private String buildUserRedisKey(String token) {
        return "token:" + token;
    }
}
