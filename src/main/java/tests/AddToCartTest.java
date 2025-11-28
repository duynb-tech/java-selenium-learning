package tests;

import pages.InventoryPage;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest{
    @Test
    public void addToCart(){
        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

        InventoryPage inventory = new InventoryPage(driver);
        inventory.addBackpackToCart();

        int badgeCount = inventory.getCartBadgeCount();
        System.out.println("số lượng sản phẩm trong giỏ hàng : "+ badgeCount);

        Assert.assertEquals(badgeCount, 1, "Lỗi: Giỏ hàng chưa cập nhật số lượng!");
    }
}
