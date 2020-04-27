package com.bawei.dianshangjin08.presenter;

import com.bawei.dianshangjin08.base.BasePresenter;
import com.bawei.dianshangjin08.contact.IContact;

import io.reactivex.Observable;

/**
 * 用户信息请求Presenter
 */
public class UserPresenter extends BasePresenter {
    //方法封装
    public UserPresenter(IContact.IView iView) {
        super(iView);
    }
    @Override
    protected Observable getObservable(Object... args) {
        return getiRequest().getUserById((String) args[0],(String) args[1]);
    }
}
