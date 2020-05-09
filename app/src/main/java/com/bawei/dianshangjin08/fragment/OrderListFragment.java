package com.bawei.dianshangjin08.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bawei.dianshangjin08.R;
import com.bawei.dianshangjin08.adapter.OrderDetailAdapter;
import com.bawei.dianshangjin08.bean.DataBean;
import com.bawei.dianshangjin08.bean.Order;
import com.bawei.dianshangjin08.contact.IContact;
import com.bawei.dianshangjin08.presenter.OrderListPresenter;
import com.bawei.dianshangjin08.util.RetrofitUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment公共类
 */
public class OrderListFragment extends Fragment implements IContact.IView<List<Order>> {
    //定义
    @BindView(R.id.xrecy)
    protected XRecyclerView xrecy;
    private OrderListPresenter orderListPresenter;
    protected OrderDetailAdapter orderDetailAdapter;
    private Unbinder unbinder;
    private String userId,sessionId;
    private int status;
    private int page = 1;
    public OrderListFragment(String userId, String sessionId, int status) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.status = status;
    }
    //创建视图
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_order_item, null);
        //绑定
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    //执行操作
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化
        xrecy.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        orderDetailAdapter = new OrderDetailAdapter();
        xrecy.setAdapter(orderDetailAdapter);
        orderListPresenter = new OrderListPresenter(this);
        //设置刷新及加载更多监听
        xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //判断网络
                if(RetrofitUtil.getRetrofitUtil().hasNet()){
                    page = 1;
                    //去请求
                    orderListPresenter.request(userId,sessionId,status,page);
                } else {
                    Toast.makeText(getContext(),"当前设备没有网络，请检查网络设置！",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onLoadMore() {
                //判断网络
                if(RetrofitUtil.getRetrofitUtil().hasNet()){
                    page++;
                    //去请求
                    orderListPresenter.request(userId,sessionId,status,page);
                } else {
                    Toast.makeText(getContext(),"当前设备没有网络，请检查网络设置！",Toast.LENGTH_LONG).show();
                }
            }
        });
        //判断网络
        if(RetrofitUtil.getRetrofitUtil().hasNet()){
            //去请求
            orderListPresenter.request(userId,sessionId,status,page);
        } else {
            Toast.makeText(getContext(),"当前设备没有网络，请检查网络设置！",Toast.LENGTH_LONG).show();
        }
    }
    //释放资源
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解绑
        unbinder.unbind();
        if(orderListPresenter != null){
            orderListPresenter.destroy();
            orderListPresenter = null;
        }
        if(orderDetailAdapter != null){
            orderDetailAdapter.getList().clear();
            orderDetailAdapter = null;
        }
    }
    //成功操作
    @Override
    public void onViewSuccess(List<Order> result, String message) {
        //清除数据
        if(page == 1){
            orderDetailAdapter.getList().clear();
        }
        //添加数据
        orderDetailAdapter.getList().addAll(result);
        //刷新适配器
        orderDetailAdapter.notifyDataSetChanged();
        //通知刷新和加载更多完成
        xrecy.refreshComplete();
        xrecy.loadMoreComplete();
    }
    //失败操作
    @Override
    public void onViewFail(DataBean dataBean) {
        Toast.makeText(getContext(),dataBean.getStatus() + "  错误提示：\n" + dataBean.getMessage(),Toast.LENGTH_LONG).show();
    }
}
