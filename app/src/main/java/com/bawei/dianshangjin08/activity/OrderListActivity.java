package com.bawei.dianshangjin08.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bawei.dianshangjin08.R;
import com.bawei.dianshangjin08.base.BaseActivity;
import com.bawei.dianshangjin08.bean.DataBean;
import com.bawei.dianshangjin08.bean.LoginInfo;
import com.bawei.dianshangjin08.bean.UserInfo;
import com.bawei.dianshangjin08.contact.IContact;
import com.bawei.dianshangjin08.dao.DaoMaster;
import com.bawei.dianshangjin08.dao.LoginInfoDao;
import com.bawei.dianshangjin08.fragment.OrderListFragment;
import com.bawei.dianshangjin08.presenter.UserPresenter;
import com.bawei.dianshangjin08.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 订单页面
 */
public class OrderListActivity extends BaseActivity implements IContact.IView<UserInfo> {
    //定义
    private LoginInfo loginInfo;
    private LoginInfoDao loginInfoDao;
    private String userInfo;
    @BindView(R.id.viewp)
    protected ViewPager viewp;
    @BindView(R.id.dingbtn_01)
    protected RelativeLayout dingBtn01;
    @BindView(R.id.dingbtn_02)
    protected RelativeLayout dingBtn02;
    @BindView(R.id.dingbtn_03)
    protected RelativeLayout dingBtn03;
    @BindView(R.id.dingbtn_04)
    protected RelativeLayout dingBtn04;
    @BindView(R.id.dingbtn_05)
    protected RelativeLayout dingBtn05;
    private List<Fragment> list = new ArrayList<>();
    private UserPresenter userPresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }
    @Override
    protected String getPageTitle() {
        return "正在加载...";
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        //初始化
        userPresenter = new UserPresenter(this);
        loginInfoDao = DaoMaster.newDevSession(this, LoginInfoDao.TABLENAME).getLoginInfoDao();
        //获取账号信息
        userInfo = getIntent().getStringExtra("userInfo");
        //查询登录的用户
        loginInfo = loginInfoDao.queryBuilder().where(LoginInfoDao.Properties.Status.eq(1)).unique();
        //判断网络
        if(RetrofitUtil.getRetrofitUtil().hasNet()){
            //去请求
            userPresenter.request(String.valueOf(loginInfo.getUserId()),loginInfo.getSessionId());
        } else {
            Toast.makeText(OrderListActivity.this,"当前设备没有网络，请检查网络设置！",Toast.LENGTH_LONG).show();
            actionBar.setTitle("获取用户信息失败！");
        }
    }
    @Override
    protected void initDestroy() {
        //释放资源
        if(userPresenter != null){
            userPresenter.destroy();
            userPresenter = null;
        }
    }
    //成功方法
    @Override
    public void onViewSuccess(UserInfo result, String message) {
        //添加视图
        list.add(new OrderListFragment(String.valueOf(loginInfo.getUserId()),loginInfo.getSessionId(),0));
        list.add(new OrderListFragment(String.valueOf(loginInfo.getUserId()),loginInfo.getSessionId(),1));
        list.add(new OrderListFragment(String.valueOf(loginInfo.getUserId()),loginInfo.getSessionId(),2));
        list.add(new OrderListFragment(String.valueOf(loginInfo.getUserId()),loginInfo.getSessionId(),3));
        list.add(new OrderListFragment(String.valueOf(loginInfo.getUserId()),loginInfo.getSessionId(),9));
        //设置适配器
        viewp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }
            @Override
            public int getCount() {
                return list.size();
            }
        });
        //设置监听
        dingBtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewp.setCurrentItem(0);
            }
        });
        dingBtn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewp.setCurrentItem(1);
            }
        });
        dingBtn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewp.setCurrentItem(2);
            }
        });
        dingBtn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewp.setCurrentItem(3);
            }
        });
        dingBtn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewp.setCurrentItem(4);
            }
        });
        //设置标题
        actionBar.setTitle("用户：" + userInfo + " 的订单界面");
    }
    //失败方法
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
            actionBar.setTitle("获取用户信息失败！");
        }
    }
}
