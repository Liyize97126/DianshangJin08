package com.bawei.dianshangjin08.bean;

import java.util.List;

/**
 * 购物车一级类目数据
 */
public class Category {
    private String categoryName;
    private List<ShoppingCartList> shoppingCartList;
    private boolean checked;
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public List<ShoppingCartList> getShoppingCartList() {
        return shoppingCartList;
    }
    public void setShoppingCartList(List<ShoppingCartList> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
