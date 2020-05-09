package com.bawei.dianshangjin08.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dianshangjin08.R;
import com.bawei.dianshangjin08.bean.OrderGoodsDetail;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单列表内嵌套子布局列表适配器
 */
public class OrderDetailListAdapter extends RecyclerView.Adapter<OrderDetailListAdapter.MyViewHouler> {
    //定义
    private List<OrderGoodsDetail> list = new ArrayList<>();
    public List<OrderGoodsDetail> getList() {
        return list;
    }
    //方法实现
    @NonNull
    @Override
    public OrderDetailListAdapter.MyViewHouler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_llist_item, parent, false);
        return new MyViewHouler(view);
    }
    @Override
    public void onBindViewHolder(@NonNull OrderDetailListAdapter.MyViewHouler holder, int position) {
        //获取数据
        OrderGoodsDetail orderGoodsDetail = list.get(position);
        //设置图片
        String[] commodityPicArray = orderGoodsDetail.getCommodityPic().split(",");
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.zhan_pict)
                .error(R.drawable.zhan_pict)
                .fallback(R.drawable.zhan_pict)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)));
        Glide.with(holder.commodityPic.getContext())
                .applyDefaultRequestOptions(options)
                .load(commodityPicArray[0])
                .into(holder.commodityPic);
        //设置文本
        holder.commodityName.setText(orderGoodsDetail.getCommodityName());
        holder.commodityPrice.setText("￥" + orderGoodsDetail.getCommodityPrice());
        holder.commodityCount.setText("数量：" + orderGoodsDetail.getCommodityCount());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    //寄存器
    public class MyViewHouler extends RecyclerView.ViewHolder {
        protected ImageView commodityPic;
        protected TextView commodityName,commodityPrice,commodityCount;
        public MyViewHouler(@NonNull View itemView) {
            super(itemView);
            commodityPic = itemView.findViewById(R.id.commodity_pic);
            commodityName = itemView.findViewById(R.id.commodity_name);
            commodityPrice = itemView.findViewById(R.id.commodity_price);
            commodityCount = itemView.findViewById(R.id.commodity_count);
        }
    }
}
