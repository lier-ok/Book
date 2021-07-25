package com.lier.pojo;


import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author lier
 * @date 2021/4/18 - 16:44
 * @Decription 购物车类
 * @since jdk1.8
 */
public class Cart {
    private Map<Integer, com.lier.pojo.CartItem> items = new LinkedHashMap<Integer, CartItem>();

    //添加操作
    public void addItem(com.lier.pojo.CartItem item){
        com.lier.pojo.CartItem cartItem = items.get(item.getId());
        if(cartItem == null){//购物车中没有该项
            items.put(item.getId(),item);
        }else{
            cartItem.setCount(cartItem.getCount() + 1);
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));
        }
    }
    //删除操作
    public void deleteItem(int id){
         items.remove(id);
    }
    //清空购物车
    public void clear(){
        items.clear();
    }
    //修改购物车指定id数量
    public void updateCount(Integer id,int count){
        com.lier.pojo.CartItem cartItem = items.get(id);
        if(cartItem != null){
            cartItem.setCount(count);
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));
        }
    }

    public Cart() {
    }

    public Integer getTotalCount() {
        Integer totalCount = 0;
        for(Map.Entry<Integer, com.lier.pojo.CartItem> entry : items.entrySet()){
            totalCount += entry.getValue().getCount();
        }
        return totalCount;
    }



    public BigDecimal getPriceTotal() {
        BigDecimal totalPrice = new BigDecimal(0);
        for(Map.Entry<Integer, com.lier.pojo.CartItem>entry:items.entrySet()){
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        }
        return totalPrice;
    }


    public Map<Integer, com.lier.pojo.CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, com.lier.pojo.CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getPriceTotal() +
                ", items=" + items +
                '}';
    }
}