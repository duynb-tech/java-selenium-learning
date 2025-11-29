package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "boDuLieuLogin")
    public void testLoginNhieuTaiKhoan(String username, String password) {
        System.out.println("--- Đang test user: " + username + " ---");

        // Sửa dòng này: Truyền thêm 'wait' vào
        // 'wait' này lấy từ BaseTest (do mình đã extends BaseTest)
        LoginPage loginPage = new LoginPage(driver, wait);

        loginPage.login(username, password);

        InventoryPage inventoryPage = new InventoryPage(driver);

        // Đoạn check giữ nguyên
        boolean isUrlCorrect = driver.getCurrentUrl().contains("inventory");
        Assert.assertTrue(isUrlCorrect, "Lỗi: Login thất bại với user " + username);
    }

    @Test
    public void testLoginFail() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.login("locked_out_user", "secret_sauce");

        WebElement LoginErrorAlert = driver.findElement(By.xpath("//div[contains(@class, 'error-message-container error')]"));
        String errorString = LoginErrorAlert.getText();

        System.out.println("lỗi là : "+errorString);
    }

    @DataProvider(name = "boDuLieuLogin")
    public Object[][] dataProviderMethod() {
        return new Object[][] {
                // { "User", "Pass" }
                { "standard_user", "secret_sauce" },
                { "problem_user", "secret_sauce" },
                { "performance_glitch_user", "secret_sauce" }
        };
    }
}
