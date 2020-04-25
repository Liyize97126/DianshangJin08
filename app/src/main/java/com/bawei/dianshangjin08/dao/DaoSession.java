package com.bawei.dianshangjin08.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.bawei.dianshangjin08.bean.LoginInfo;

import com.bawei.dianshangjin08.dao.LoginInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig loginInfoDaoConfig;

    private final LoginInfoDao loginInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        loginInfoDaoConfig = daoConfigMap.get(LoginInfoDao.class).clone();
        loginInfoDaoConfig.initIdentityScope(type);

        loginInfoDao = new LoginInfoDao(loginInfoDaoConfig, this);

        registerDao(LoginInfo.class, loginInfoDao);
    }
    
    public void clear() {
        loginInfoDaoConfig.clearIdentityScope();
    }

    public LoginInfoDao getLoginInfoDao() {
        return loginInfoDao;
    }

}
