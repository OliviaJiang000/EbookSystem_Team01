package com.example.demo.EntityTest;

import com.example.demo.entity.Wishlist;
import com.example.demo.entity.WishlistItem;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

public class WishlistTest {

    @Test
    public void testWishlistProperties() {
        Wishlist wishlist = new Wishlist();
        LocalDateTime now = LocalDateTime.now();
        List<WishlistItem> itemList = new ArrayList<>();

        wishlist.setWishlistId(1);
        wishlist.setUserId(1001);
        wishlist.setCreatedAt(now);
        wishlist.setItems(itemList);

        assertThat(wishlist.getWishlistId()).isEqualTo(1);
        assertThat(wishlist.getUserId()).isEqualTo(1001);
        assertThat(wishlist.getCreatedAt()).isEqualTo(now);
        assertThat(wishlist.getItems()).isEqualTo(itemList);
    }
}

