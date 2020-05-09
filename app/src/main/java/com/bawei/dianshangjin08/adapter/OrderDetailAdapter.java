package com.bawei.dianshangjin08.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dianshangjin08.R;
import com.bawei.dianshangjin08.bean.Order;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单列表适配器
 */
public class OrderDetailAdapter extends XRecyclerView.Adapter<OrderDetailAdapter.MyViewHouler> {
    //定义
    private List<Order> list = new ArrayList<>();
    public List<Order> getList() {
        return list;
    }
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private OrderDetailListAdapter orderDetailListAdapter;
    //方法实现
    @NonNull
    @Override
    public OrderDetailAdapter.MyViewHouler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false);
        return new MyViewHouler(view);
    }
    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.MyViewHouler holder, int position) {
        //获取数据
        Order order = list.get(position);
        //时间转换
        String orderTime = format.format(new Date(order.getOrderTime()));
        //设置文本
        holder.orderId.setText(order.getOrderId());
        holder.orderTime.setText(orderTime);
        holder.payAmount.setText("￥" + order.getPayAmount());
        //判断状态
        switch (order.getOrderStatus()){
            case 1:{holder.orderStatus.setText("待付款");}break;
            case 2:{holder.orderStatus.setText("待收货");}break;
            case 3:{holder.orderStatus.setText("待评价");}break;
            case 9:{holder.orderStatus.setText("已完成");}break;
        }
        //设置适配器
        holder.detailList.setLayoutManager(new LinearLayoutManager(holder.detailList.getContext(),LinearLayoutManager.VERTICAL,false));
        orderDetailListAdapter = new OrderDetailListAdapter();
        holder.detailList.setAdapter(orderDetailListAdapter);
        orderDetailListAdapter.getList().addAll(order.getDetailList());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    //寄存器
    public class MyViewHouler extends RecyclerView.ViewHolder {
        protected TextView orderId,orderTime,payAmount,orderStatus;
        protected RecyclerView detailList;
        public MyViewHouler(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id);
            orderTime = itemView.findViewById(R.id.order_time);
            detailList = itemView.findViewById(R.id.detail_list);
            payAmount = itemView.findViewById(R.id.pay_amount);
            orderStatus = itemView.findViewById(R.id.order_status);
        }
    }
}
