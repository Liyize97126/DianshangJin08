package com.bawei.dianshangjin08.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dianshangjin08.R;
import com.bawei.dianshangjin08.adapter.ShopCarAdapter;
import com.bawei.dianshangjin08.base.BaseActivity;
import com.bawei.dianshangjin08.bean.Category;
import com.bawei.dianshangjin08.bean.DataBean;
import com.bawei.dianshangjin08.bean.LoginInfo;
import com.bawei.dianshangjin08.contact.IContact;
import com.bawei.dianshangjin08.dao.DaoMaster;
import com.bawei.dianshangjin08.dao.LoginInfoDao;
import com.bawei.dianshangjin08.presenter.ShoppingCartListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 购物车页面
 */
public class CarActivity extends BaseActivity implements IContact.IView<List<Category>> {
    //定义
    @BindView(R.id.shop_car_list)
    protected ExpandableListView shopCarList;
    @BindView(R.id.check_all)
    protected CheckBox checkAll;
    @BindView(R.id.total_money)
    protected TextView totalPrice;
    private ShoppingCartListPresenter shoppingCartListPresenter;
    private ShopCarAdapter shopCarAdapter;
    private LoginInfo loginInfo;
    private LoginInfoDao loginInfoDao;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_car;
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        //初始化
        shopCarAdapter = new ShopCarAdapter();
        shopCarList.setAdapter(shopCarAdapter);
        shoppingCartListPresenter = new ShoppingCartListPresenter(this);
        loginInfoDao = DaoMaster.newDevSession(this, LoginInfoDao.TABLENAME).getLoginInfoDao();
        //监听事件
        shopCarAdapter.setOnPriceChangeListener(new ShopCarAdapter.OnPriceChangeListener() {
            @Override
            public void priceChange(double totalMoney, boolean isAll) {
                checkAll.setChecked(isAll);
                totalPrice.setText("总价 :  ￥" + totalMoney);
            }
        });
        //查询登录的用户
        loginInfo = loginInfoDao.queryBuilder().where(LoginInfoDao.Properties.Status.eq(1)).unique();
        //去请求
        shoppingCartListPresenter.request(String.valueOf(loginInfo.getUserId()),loginInfo.getSessionId());
    }
    @Override
    protected void initDestroy() {
        //释放资源
        if(shoppingCartListPresenter != null){
            shoppingCartListPresenter.destroy();
            shoppingCartListPresenter = null;
        }
    }
    //全选点击事件
    @OnClick(R.id.check_all)
    protected void checkAll() {
        shopCarAdapter.checkAll(checkAll.isChecked());
    }
    //成功回调
    @Override
    public void onViewSuccess(List<Category> result, String message) {
        //提示
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        //刷新列表
        shopCarAdapter.getList().addAll(result);
        shopCarAdapter.notifyDataSetChanged();
    }
    //失败回调
    @Override
    public void onViewFail(DataBean dataBean) {
        if(dataBean.getStatus().equals("1001")){
            //提示
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("登录态异常，请重新登录！");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    loginInfo.setStatus(0);
                    loginInfoDao.insertOrReplaceInTx(loginInfo);
                    //跳转
                    Intent intent = new Intent();
                    setResult(200,intent);
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.create();
            builder.show();
        } else {
            //提示
            Toast.makeText(this,dataBean.getStatus() + "  " + dataBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
