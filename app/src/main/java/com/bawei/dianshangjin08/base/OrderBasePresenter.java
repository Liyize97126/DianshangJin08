package com.bawei.dianshangjin08.base;

import com.bawei.dianshangjin08.bean.DataBean;
import com.bawei.dianshangjin08.contact.IContact;
import com.bawei.dianshangjin08.util.RetrofitUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * OrderPresenter基类
 */
public abstract class OrderBasePresenter {
    //定义
    private IContact.IView iView;
    private IContact.IRequest iRequest;
    //封装
    public IContact.IRequest getiRequest() {
        return iRequest;
    }
    //构造
    public OrderBasePresenter(IContact.IView iView) {
        this.iView = iView;
        //初始化iRequest
        iRequest = RetrofitUtil.getRetrofitUtil().create(IContact.IRequest.class);
    }
    //请求方法（...三个点叫做不定参，意思是不确定长度，使用的时候相当于数组）
    public void request(Object...args){
        getObservable(args)
                //把请求订阅到子线程中进行处理
                .subscribeOn(Schedulers.io())
                //把请求到的结果放入主线程中处理
                .observeOn(AndroidSchedulers.mainThread())
                //结果处理
                .subscribe(new Consumer<DataBean>() {
                    @Override
                    public void accept(DataBean dataBean) throws Exception {
                        //判断状态码
                        if(dataBean.getStatus().equals("0000")){
                            iView.onViewSuccess(dataBean.getOrderList(),dataBean.getMessage());
                        } else {
                            iView.onViewFail(dataBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //错误回调
                        throwable.printStackTrace();
                        iView.onViewFail(new DataBean(throwable.getMessage()));
                    }
                });
    }
    //方法封装
    protected abstract Observable getObservable(Object...args);
    //释放资源
    public void destroy(){
        if(iView != null){
            iView = null;
        }
    }
}
