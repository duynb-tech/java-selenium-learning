package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        // 1. Setup đã tự chạy ở BaseTest, không cần gọi nữa

        // 2. Logic Test
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        // 3. CHẤM ĐIỂM (ASSERTION) - Phần mới
        // Thay vì if-else, ta dùng Assert.
        // Cú pháp: Assert.assertTrue(Điều_kiện_muốn_kiểm_tra, "Câu báo lỗi nếu sai");

        // Kiểm tra URL có chứa chữ "inventory" không?
        boolean isUrlCorrect = driver.getCurrentUrl().contains("inventory");
        Assert.assertTrue(isUrlCorrect, "Lỗi: Login xong không vào được trang Inventory!");

        // Kiểm tra số lượng sản phẩm có phải là 6 không?
        int actualProductCount = inventoryPage.getProductCount();
        Assert.assertEquals(actualProductCount, 6, "Lỗi: Số lượng sản phẩm không đúng!");

        System.out.println("✅ Test Login Success đã chạy xong!");
    }

    @Test
    public void testLoginFail() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");

        WebElement LoginErrorAlert = driver.findElement(By.xpath("//div[contains(@class, 'error-message-container error')]"));
        String errorString = LoginErrorAlert.getText();

        System.out.println("lỗi là : "+errorString);
    }

}
