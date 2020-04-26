package com.bawei.dianshangjin08.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bawei.dianshangjin08.R;

/**
 * 加减器控件
 */
public class AddReduceView extends FrameLayout {
    //定义
    private Button reduceBtn,addBtn;
    private TextView countText;
    private int count;
    private AddReduceListener addReduceListener;
    public void setAddReduceListener(AddReduceListener addReduceListener) {
        this.addReduceListener = addReduceListener;
    }
    //实现构造方法
    public AddReduceView(@NonNull Context context) {
        super(context);
        initView();
    }
    public AddReduceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    public AddReduceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    //视图方法
    private void initView(){
        //获取
        View view = View.inflate(getContext(), R.layout.view_reduce_item, this);
        reduceBtn = view.findViewById(R.id.reduce_btn);
        countText = view.findViewById(R.id.count_text);
        addBtn = view.findViewById(R.id.add_btn);
        //点击事件
        reduceBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count > 1) {
                    //执行数量减操作
                    count--;
                    countText.setText(String.valueOf(count));
                } else {
                    Toast.makeText(getContext(),"商品数量不得小于1",Toast.LENGTH_LONG).show();
                }
                //回调
                if(addReduceListener != null){
                    addReduceListener.addReduce(AddReduceView.this);
                }
            }
        });
        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行数量加操作
                count++;
                countText.setText(String.valueOf(count));
                //回调
                if(addReduceListener != null){
                    addReduceListener.addReduce(AddReduceView.this);
                }
            }
        });
    }
    //设置商品数量
    public void setGoodsCount(int count){
        this.count = count;
        countText.setText(String.valueOf(count));
    }
    //得到商品数量
    public int getGoodsCount(){
        return count;
    }
    //加减监听
    public interface AddReduceListener{
        void addReduce(AddReduceView view);
    }
}
