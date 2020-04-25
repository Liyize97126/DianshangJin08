package com.bawei.dianshangjin08.contact;

import com.bawei.dianshangjin08.bean.DataBean;
import com.bawei.dianshangjin08.bean.LoginInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 契约类
 */
public interface IContact {
    //视图
    interface IView<T> {
        void onViewSuccess(T result, String message);
        void onViewFail(DataBean dataBean);
    }
    //请求
    interface IRequest {
        //登录请求
        @POST("small/user/v1/login")
        @FormUrlEncoded
        Observable<DataBean<LoginInfo>> login(@Field("phone") String phone, @Field("pwd") String pwd);
    }
}
