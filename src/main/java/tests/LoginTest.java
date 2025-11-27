package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.InventoryPage;
import pages.LoginPage;

import java.time.Duration;

public class LoginTest extends BaseTest {

    public static void main(String[] args) {
        setup();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        int totalProducts = inventoryPage.getProductCount();
        System.out.println("Tổng số sản phẩm tìm thấy: " + totalProducts);

        if (totalProducts == 6) {
            System.out.println("✅ Đủ hàng (6 món)");
        } else {
            System.out.println("❌ Thiếu hàng!");
        }

        double price = inventoryPage.getFirstProductPrice();
        System.out.println("Giá món đầu tiên: " + price);

        // Thử thêm vào giỏ
        inventoryPage.addBackpackToCart();
        System.out.println("Đã thêm Balo vào giỏ!");

        tearDown();
    }

}
