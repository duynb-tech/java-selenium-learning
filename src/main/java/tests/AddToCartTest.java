package tests;

import pages.InventoryPage;
import pages.LoginPage;

public class AddToCartTest extends BaseTest{
    public static void main(String[] args) {
        setup();

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

        InventoryPage inventory = new InventoryPage(driver);
        inventory.addBackpackToCart();

        int badgeCount = inventory.getCartBadgeCount();
        System.out.println("số lượng sản phẩm trong giỏ hàng : "+ badgeCount);

        if (badgeCount == 1) {
            System.out.println("✅ Test Passed: Giỏ hàng đã nhảy số 1");
        } else {
            System.out.println("❌ Test Failed: Giỏ hàng không nhảy số");
        }

        tearDown();
    }
}
