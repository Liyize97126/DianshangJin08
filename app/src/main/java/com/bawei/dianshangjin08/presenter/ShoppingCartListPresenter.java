package com.bawei.dianshangjin08.presenter;

import com.bawei.dianshangjin08.base.BasePresenter;
import com.bawei.dianshangjin08.contact.IContact;

import io.reactivex.Observable;

/**
 * 查询购物车Presenter
 */
public class ShoppingCartListPresenter extends BasePresenter {
    //方法实现
    public ShoppingCartListPresenter(IContact.IView iView) {
        super(iView);
    }
    @Override
    protected Observable getObservable(Object... args) {
        return getiRequest().findCommodityByKeyword((String)args[0],(String)args[1]);
    }
}
