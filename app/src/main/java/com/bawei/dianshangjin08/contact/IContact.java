package com.bawei.dianshangjin08.contact;

import com.bawei.dianshangjin08.bean.Category;
import com.bawei.dianshangjin08.bean.DataBean;
import com.bawei.dianshangjin08.bean.LoginInfo;
import com.bawei.dianshangjin08.bean.Order;
import com.bawei.dianshangjin08.bean.UserInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
        //获取用户信息
        @GET("small/user/verify/v1/getUserById")
        Observable<DataBean<UserInfo>> getUserById(@Header("userId") String userId, @Header("sessionId") String sessionId);
        //购物车数据
        @GET("small/order/verify/v1/findShoppingCart")
        Observable<DataBean<List<Category>>> findCommodityByKeyword(@Header("userId") String userId, @Header("sessionId") String sessionId);
        //订单数据
        @GET("small/order/verify/v1/findOrderListByStatus")
        Observable<DataBean<List<Order>>> findOrderListByStatus(@Header("userId") String userId, @Header("sessionId") String sessionId,
                                                                @Query("status") int status,@Query("page") int page,@Query("count") int count);
    }
}
