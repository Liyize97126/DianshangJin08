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
import com.bawei.dianshangjin08.view.AddReduceView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 二级列表适配器
 */
public class ShopCarAdapter extends BaseExpandableListAdapter implements AddReduceView.AddReduceListener {
    //定义
    private List<Category> list = new ArrayList<>();
    public List<Category> getList() {
        return list;
    }
    //定义回调
    private OnPriceChangeListener onPriceChangeListener;
    public void setOnPriceChangeListener(OnPriceChangeListener onPriceChangeListener) {
        this.onPriceChangeListener = onPriceChangeListener;
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
            myGroupViewHouler.groupCheck = convertView.findViewById(R.id.group_check);
            convertView.setTag(myGroupViewHouler);
        } else {
            myGroupViewHouler = (MyGroupViewHouler) convertView.getTag();
        }
        //设置数据
        Category category = list.get(groupPosition);
        myGroupViewHouler.groupCheck.setChecked(category.isChecked());//设置商铺选中
        //传值
        myGroupViewHouler.groupCheck.setTag(category);
        //点击事件
        myGroupViewHouler.groupCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category category = (Category) v.getTag();
                CheckBox checkBox = (CheckBox) v;
                //控件选中，最终的选中值必须赋值给对象进行保存
                category.setChecked(checkBox.isChecked());
                for (int i = 0; i < category.getShoppingCartList().size(); i++) {
                    //取出商品
                    ShoppingCartList shoppingCartList = category.getShoppingCartList().get(i);
                    //商品的选中状态需要看商铺是否选中
                    shoppingCartList.setChecked(category.isChecked());
                }
                //计算总价，价格回调
                totalMoney();
                //刷新适配器
                notifyDataSetChanged();
            }
        });
        myGroupViewHouler.groupCheck.setText(category.getCategoryName());
        return convertView;
    }
    //子视图加载
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //定义
        MyChildViewHouler myChildViewHouler;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item, parent, false);
            myChildViewHouler = new MyChildViewHouler();
            myChildViewHouler.childCheck = convertView.findViewById(R.id.child_check);
            myChildViewHouler.childImage = convertView.findViewById(R.id.child_image);
            myChildViewHouler.childName = convertView.findViewById(R.id.child_name);
            myChildViewHouler.childPrice = convertView.findViewById(R.id.child_price);
            myChildViewHouler.childAddReduce = convertView.findViewById(R.id.child_add_reduce);
            convertView.setTag(myChildViewHouler);
        } else {
            myChildViewHouler = (MyChildViewHouler) convertView.getTag();
        }
        //设置数据
        ShoppingCartList shoppingCartList = list.get(groupPosition).getShoppingCartList().get(childPosition);
        myChildViewHouler.childCheck.setChecked(shoppingCartList.isChecked());//设置商品选中
        //寄存商品对象
        myChildViewHouler.childCheck.setTag(shoppingCartList);
        //联动：点击监听
        myChildViewHouler.childCheck.setOnClickListener(new View.OnClickListener() {//匿名内部类
            @Override
            public void onClick(View v) {
                //得到当前集合对象
                ShoppingCartList cartList = (ShoppingCartList) v.getTag();
                //得到当前控件
                CheckBox checkBox = (CheckBox) v;
                //设置控件选中状态
                cartList.setChecked(checkBox.isChecked());
                //和商铺选项联动（商品如果都选中，商铺必须选中，商品有一个未选中，商铺则不能被选中。）
                for (int i = 0; i < list.size(); i++) {
                    //取出商铺
                    Category category = list.get(i);
                    //取出商铺下所有商品
                    List<ShoppingCartList> cartLists = list.get(i).getShoppingCartList();
                    //定义一个标志值
                    boolean categoryCheck = true;//可用if判断的逻辑来处理，通过使用&&，如果有一个未选中则最终结果就是false
                    for (int j = 0; j < cartLists.size(); j++) {
                        //获取商品对象
                        ShoppingCartList sCartList = cartLists.get(j);
                        categoryCheck = categoryCheck && sCartList.isChecked();//有一个sCartList商品的状态未选中，最终结果就是false
                    }
                    //修改商铺选中状态
                    category.setChecked(categoryCheck);
                }
                //计算总价，价格回调
                totalMoney();
                //刷新适配器
                notifyDataSetChanged();
            }
        });
        myChildViewHouler.childName.setText(shoppingCartList.getCommodityName());
        myChildViewHouler.childPrice.setText("￥" + shoppingCartList.getPrice());
        //设置加减器（设置数量，设置商品对象，设置监听器）
        myChildViewHouler.childAddReduce.setGoodsCount(shoppingCartList.getCount());
        myChildViewHouler.childAddReduce.setTag(shoppingCartList);
        myChildViewHouler.childAddReduce.setAddReduceListener(this);
        //设置图片
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.zhan_pict)
                .error(R.drawable.zhan_pict)
                .fallback(R.drawable.zhan_pict)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)));
        Glide.with(myChildViewHouler.childImage.getContext())
                .applyDefaultRequestOptions(options)
                .load(shoppingCartList.getPic())
                .into(myChildViewHouler.childImage);
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    //加减器获取数量，获取商品对象，设置数量
    @Override
    public void addReduce(AddReduceView view) {
        int goodsCount = view.getGoodsCount();
        ShoppingCartList shoppingCartList = (ShoppingCartList) view.getTag();
        shoppingCartList.setCount(goodsCount);
        //计算总价，价格回调
        totalMoney();
    }
    //全选方法
    public void checkAll(boolean isAll){
        for (int i = 0; i < list.size(); i++) {
            //设置商铺全选
            list.get(i).setChecked(isAll);
            List<ShoppingCartList> shoppingCartList = list.get(i).getShoppingCartList();
            for (int j = 0; j < shoppingCartList.size(); j++) {
                //设置商品全选
                shoppingCartList.get(j).setChecked(isAll);
            }
        }
        //计算总价，价格回调
        totalMoney();
        //刷新适配器
        notifyDataSetChanged();
    }
    //计算价格，而且最终决定是否全选和反选
    public void totalMoney() {
        //定义
        boolean isAll = true;
        double sumPrice = 0;
        for (int i = 0; i < list.size(); i++) {
            //取出商铺对象
            Category category = list.get(i);
            //商铺如果全选，那就是全选
            isAll = category.isChecked() && isAll;
            //取出商铺下所有商品
            List<ShoppingCartList> shoppingCartList = category.getShoppingCartList();
            for (int j = 0; j < shoppingCartList.size(); j++) {
                //获得商品对象
                ShoppingCartList cartList = shoppingCartList.get(j);
                if(cartList.isChecked()){
                    sumPrice += (cartList.getPrice() * cartList.getCount());//价格*数量
                }
            }
        }
        //回调页面
        if(onPriceChangeListener != null){
            onPriceChangeListener.priceChange(sumPrice,isAll);
        }
    }
    //寄存器
    class MyGroupViewHouler{
        CheckBox groupCheck;
    }
    class MyChildViewHouler{
        CheckBox childCheck;
        ImageView childImage;
        TextView childName,childPrice;
        AddReduceView childAddReduce;
    }
    //声明反馈接口
    public interface OnPriceChangeListener{
        void priceChange(double totalMoney,boolean isAll);
    }
}
