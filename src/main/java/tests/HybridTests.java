package tests;

import common.BaseTest;
import constants.APIConfig;
import io.restassured.RestAssured;
import models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.restassured.RestAssured.given;

public class HybridTests extends BaseTest {

    @Test
    public void testApiToUiFlow() {
        // ==========================================
        // GIAI ĐOẠN 1: API (Chuẩn bị dữ liệu)
        // ==========================================
        System.out.println("🔄 Step 1: Gọi API lấy thông tin User...");

        User apiUser = given()
                .spec(requestSpec) // Dùng config chung
                .when()
                .get("/users/1") // Lấy user số 1
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);

        System.out.println("✅ Data from API -> Name: " + apiUser.getName());
        System.out.println("✅ Data from API -> Email: " + apiUser.getEmail());

        // ==========================================
        // GIAI ĐOẠN 2: SELENIUM
        // ==========================================
        System.out.println("🔄 Step 2: Mở trình duyệt để điền form...");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // 🔥 THÊM: Tạo một "người bảo vệ" biết chờ đợi (Tối đa 10 giây)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://demoqa.com/text-box");

            // 🔥 SỬA: Thay vì findElement (tìm ngay), hãy dùng wait.until (chờ nó hiện ra đã)
            WebElement userNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));

            // Các element khác cũng nên chờ cho chắc ăn
            WebElement userEmailInput = driver.findElement(By.id("userEmail")); // userName hiện thì mấy cái này chắc cũng hiện rồi
            WebElement submitBtn = driver.findElement(By.id("submit"));

            // Điền dữ liệu
            userNameInput.sendKeys(apiUser.getName());
            userEmailInput.sendKeys(apiUser.getEmail());

            // Scroll xuống và click
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);
            submitBtn.click();

            // Verify
            WebElement resultName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
            Assert.assertTrue(resultName.getText().contains(apiUser.getName()));

            System.out.println("🎉 Test Hybrid thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test bị lỗi Selenium!");
        } finally {
            // Đóng trình duyệt dù test pass hay fail
            driver.quit();
        }
    }
}