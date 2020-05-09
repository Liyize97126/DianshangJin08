package com.bawei.dianshangjin08.presenter;

import com.bawei.dianshangjin08.base.OrderBasePresenter;
import com.bawei.dianshangjin08.contact.IContact;

import io.reactivex.Observable;

/**
 * 订单列表Presenter
 */
public class OrderListPresenter extends OrderBasePresenter {
    //方法实现
    public OrderListPresenter(IContact.IView iView) {
        super(iView);
    }
    @Override
    protected Observable getObservable(Object... args) {
        return getiRequest().findOrderListByStatus((String) args[0],(String) args[1],(int) args[2],(int) args[3],20);
    }
}
