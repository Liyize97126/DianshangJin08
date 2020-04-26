package com.bawei.dianshangjin08.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.dianshangjin08.R;
import com.bawei.dianshangjin08.base.BaseActivity;
import com.bawei.dianshangjin08.bean.DataBean;
import com.bawei.dianshangjin08.bean.LoginInfo;
import com.bawei.dianshangjin08.contact.IContact;
import com.bawei.dianshangjin08.dao.DaoMaster;
import com.bawei.dianshangjin08.dao.DaoSession;
import com.bawei.dianshangjin08.dao.LoginInfoDao;
import com.bawei.dianshangjin08.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity implements IContact.IView<LoginInfo> {
    //定义
    @BindView(R.id.edit_phone)
    protected EditText edit_phone;
    @BindView(R.id.edit_pwd)
    protected EditText edit_pwd;
    private LoginPresenter loginPresenter;
    private LoginInfoDao loginInfoDao;
    //方法实现
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        //初始化Presenter
        loginPresenter = new LoginPresenter(this);
        //初始化数据库
        DaoSession daoSession = DaoMaster.newDevSession(this, LoginInfoDao.TABLENAME);
        loginInfoDao = daoSession.getLoginInfoDao();
        //判断是否登录（条件查询）
        LoginInfo loginInfo = loginInfoDao.queryBuilder().where(LoginInfoDao.Properties.Status.eq(1)).unique();//唯一
        if(loginInfo != null){
            //直接跳转
            startGo();
        }
    }
    //发起请求
    @OnClick(R.id.login_do)
    protected void loginDo(){
        //获取文本
        String phone = edit_phone.getText().toString();
        String pwd = edit_pwd.getText().toString();
        //发起请求
        if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"错误提示：请输入合法的用户名和密码！",Toast.LENGTH_LONG).show();
        } else {
            loginPresenter.request(phone,pwd);
        }
    }
    @Override
    protected void initDestroy() {
        if(loginPresenter != null){
            loginPresenter.destroy();
            loginPresenter = null;
        }
    }
    //封装统一跳转方法
    private void startGo(){
        //跳转
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    //请求成功失败方法
    @Override
    public void onViewSuccess(LoginInfo result,String message) {
        //提示
        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
        //保存登录信息
        result.setStatus(1);//表示已登录态
        loginInfoDao.insertOrReplaceInTx(result);
        startGo();
    }
    @Override
    public void onViewFail(DataBean dataBean) {
        //提示
        Toast.makeText(LoginActivity.this,"错误提示：" + dataBean.getMessage(),Toast.LENGTH_LONG).show();
    }
}
