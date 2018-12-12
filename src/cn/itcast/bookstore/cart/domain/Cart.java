package cn.itcast.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车类
 */
public class Cart {
    private Map<String, CartItem> map = new LinkedHashMap<>();

    /**
     * 计算所有合计
     *
     * @return
     */
    public double getTotal() {
        BigDecimal total = new BigDecimal("0");
        for (CartItem cartItem : map.values()) {
            BigDecimal subtotal = new BigDecimal("" + cartItem.getSubtotal());
            total = total.add(subtotal);
        }
        return total.doubleValue();
    }

    /**
     * 添加条目到购物车
     *
     * @param cartItem
     */
    public void add(CartItem cartItem) {
        if (map.containsKey(cartItem.getBook().getBid())) {//判断原来车中是否包含条目
            CartItem _cartItem = map.get(cartItem.getBook().getBid());
            _cartItem.setCount(cartItem.getCount() + _cartItem.getCount());//老条目的数量加上旧条目的数量
            map.put(cartItem.getBook().getBid(), _cartItem);
        } else {
            map.put(cartItem.getBook().getBid(), cartItem);
        }
    }

    /**
     * 清空购物车
     */
    public void clear() {
        map.clear();
    }

    /**
     * 删除指定条目
     *
     * @param bid
     */
    public void delete(String bid) {
        map.remove(bid);
    }


    /**
     * 我的条目
     *
     * @return
     */
    public Collection<CartItem> getCartItems() {
        return map.values();
    }


}
