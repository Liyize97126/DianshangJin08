package com.bawei.dianshangjin08.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.dianshangjin08.R;
import com.bawei.dianshangjin08.bean.Category;
import com.bawei.dianshangjin08.bean.ShoppingCartList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 二级列表适配器
 */
public class ShopCarAdapter extends BaseExpandableListAdapter {
    //定义
    private List<Category> list = new ArrayList<>();
    public List<Category> getList() {
        return list;
    }
    //一级条目总数
    @Override
    public int getGroupCount() {
        return list.size();
    }
    //二级条目总数
    @Override
    public int getChildrenCount(int groupPosition) {
        Category category = list.get(groupPosition);
        List<ShoppingCartList> shoppingCartList = category.getShoppingCartList();
        return shoppingCartList.size();
    }
    //得到一级条目
    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }
    //得到二级条目
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Category category = list.get(groupPosition);
        List<ShoppingCartList> shoppingCartList = category.getShoppingCartList();
        return shoppingCartList.get(childPosition);
    }
    //一级条目ID
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    //二级条目ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    //组视图加载
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //定义
        MyGroupViewHouler myGroupViewHouler;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
            myGroupViewHouler = new MyGroupViewHouler();
            myGroupViewHouler.group_check = convertView.findViewById(R.id.group_check);
            convertView.setTag(myGroupViewHouler);
        } else {
            myGroupViewHouler = (MyGroupViewHouler) convertView.getTag();
        }
        //设置数据
        Category category = list.get(groupPosition);
        myGroupViewHouler.group_check.setChecked(category.isChecked());//设置商铺选中
        myGroupViewHouler.group_check.setText(category.getCategoryName());
        return convertView;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //定义
        MyChildViewHouler myChildViewHouler;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item, parent, false);
            myChildViewHouler = new MyChildViewHouler();
            myChildViewHouler.child_check = convertView.findViewById(R.id.child_check);
            myChildViewHouler.child_image = convertView.findViewById(R.id.child_image);
            myChildViewHouler.child_name = convertView.findViewById(R.id.child_name);
            myChildViewHouler.child_price = convertView.findViewById(R.id.child_price);
            convertView.setTag(myChildViewHouler);
        } else {
            myChildViewHouler = (MyChildViewHouler) convertView.getTag();
        }
        //设置数据
        ShoppingCartList shoppingCartList = list.get(groupPosition).getShoppingCartList().get(childPosition);
        myChildViewHouler.child_check.setChecked(shoppingCartList.isChecked());//设置商品选中
        myChildViewHouler.child_name.setText(shoppingCartList.getCommodityName());
        myChildViewHouler.child_price.setText("￥" + shoppingCartList.getPrice());
        //设置图片
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.zhan_pict)
                .error(R.drawable.zhan_pict)
                .fallback(R.drawable.zhan_pict)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)));
        Glide.with(myChildViewHouler.child_image.getContext())
                .applyDefaultRequestOptions(options)
                .load(shoppingCartList.getPic())
                .into(myChildViewHouler.child_image);
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    //寄存器
    class MyGroupViewHouler{
        CheckBox group_check;
    }
    class MyChildViewHouler{
        CheckBox child_check;
        ImageView child_image;
        TextView child_name,child_price;
    }
}
