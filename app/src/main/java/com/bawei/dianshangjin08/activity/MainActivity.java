package com.bawei.dianshangjin08.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bawei.dianshangjin08.R;
import com.bawei.dianshangjin08.base.BaseActivity;
import com.bawei.dianshangjin08.bean.DataBean;
import com.bawei.dianshangjin08.bean.LoginInfo;
import com.bawei.dianshangjin08.bean.UserInfo;
import com.bawei.dianshangjin08.contact.IContact;
import com.bawei.dianshangjin08.dao.DaoMaster;
import com.bawei.dianshangjin08.dao.LoginInfoDao;
import com.bawei.dianshangjin08.presenter.UserPresenter;
import com.bawei.dianshangjin08.util.RetrofitUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 控制台界面
 */
public class MainActivity extends BaseActivity implements IContact.IView<UserInfo> {
    //定义
    @BindView(R.id.head_pic)
    protected ImageView headPic;
    @BindView(R.id.nick_name)
    protected TextView nickName;
    private UserPresenter userPresenter;
    private LoginInfo loginInfo;
    private LoginInfoDao loginInfoDao;
    private String phone;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected String getPageTitle() {
        return "登录中...";
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        //初始化
        userPresenter = new UserPresenter(this);
        loginInfoDao = DaoMaster.newDevSession(this, LoginInfoDao.TABLENAME).getLoginInfoDao();
        //查询登录的用户
        loginInfo = loginInfoDao.queryBuilder().where(LoginInfoDao.Properties.Status.eq(1)).unique();
        //判断网络
        if(RetrofitUtil.getRetrofitUtil().hasNet()){
            //去请求
            userPresenter.request(String.valueOf(loginInfo.getUserId()),loginInfo.getSessionId());
        } else {
            Toast.makeText(MainActivity.this,"当前设备没有网络，请检查网络设置！",Toast.LENGTH_LONG).show();
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
    //购物车界面
    @OnClick(R.id.btn_01)
    protected void btn1(){
        //判断网络
        if(RetrofitUtil.getRetrofitUtil().hasNet()){
            //跳转
            Intent intent = new Intent(this, CarActivity.class);
            //传递用户信息
            intent.putExtra("userInfo",phone);
            startActivityForResult(intent,100);
        } else {
            Toast.makeText(MainActivity.this,"当前设备没有网络，请检查网络设置！",Toast.LENGTH_LONG).show();
        }
    }
    //订单页面
    @OnClick(R.id.btn_02)
    protected void btn2(){
        //判断网络
        if(RetrofitUtil.getRetrofitUtil().hasNet()){
            //跳转
            Intent intent = new Intent(this, OrderListActivity.class);
            //传递用户信息
            intent.putExtra("userInfo",phone);
            startActivityForResult(intent,100);
        } else {
            Toast.makeText(MainActivity.this,"当前设备没有网络，请检查网络设置！",Toast.LENGTH_LONG).show();
        }
    }
    //注销系统
    @OnClick(R.id.btn_03)
    protected void btn3(){
        //提示
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定要注销系统吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loginInfo.setStatus(0);
                loginInfoDao.insertOrReplaceInTx(loginInfo);
                //跳转
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }
    //成功方法
    @Override
    public void onViewSuccess(UserInfo result, String message) {
        //加载图片
        RequestOptions apply = new RequestOptions()
                .error(R.drawable.head)
                .fallback(R.drawable.head)
                .placeholder(R.drawable.head)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()));
        Glide.with(headPic.getContext())
                .applyDefaultRequestOptions(apply)
                .load(result.getHeadPic())
                .into(headPic);
        //加载昵称
        nickName.setText(result.getNickName());
        //得到账号信息
        phone = result.getPhone();
        //修改应用标题
        actionBar.setTitle("用户：" + phone + " 的控制台界面");
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
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
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
    //状态回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 200){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
