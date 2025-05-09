package com.example.demo.EntityTest;

import com.example.demo.entity.WishlistItem;
import com.example.demo.entity.Wishlist;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WishlistItemTest {

    @Test
    public void testWishlistItemProperties() {
        WishlistItem item = new WishlistItem();
        Wishlist wishlist = new Wishlist(); // Simulate dependent objects

        item.setWishlistItemId(1);
        item.setWishlist(wishlist);
        item.setBookId(100);

        assertThat(item.getWishlistItemId()).isEqualTo(1);
        assertThat(item.getWishlist()).isEqualTo(wishlist);
        assertThat(item.getBookId()).isEqualTo(100);
    }
}
