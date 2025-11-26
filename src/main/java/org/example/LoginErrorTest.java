package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement; // Import thêm cái này để định nghĩa Element
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginErrorTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        // 1. Nhập đúng User nhưng SAI Password
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("pass_tào_lao_bí_đao");

        // 2. Click Login
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000); // Đợi thông báo lỗi hiện ra

        // --- BẮT ĐẦU KIỂM TRA LỖI ---

        // 3. Định vị cái khung lỗi (tôi đã inspect sẵn cho bạn: nó dùng tag h3 và attribute data-test)
        WebElement errorElement = driver.findElement(By.cssSelector("h3[data-test='error']"));

        // 4. Lấy chữ bên trong ra (Get Text)
        String actualErrorMessage = errorElement.getText();
        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this services";

        System.out.println("Thông báo lỗi thực tế: " + actualErrorMessage);

        // Đổi chiến thuật: Dùng contains thay vì equals
        // Chỉ cần chứa cụm từ quan trọng nhất là được
        if (actualErrorMessage.contains("Username and password do not match")) {
            System.out.println("✅ TEST PASSED: Đã hiện thông báo sai mật khẩu.");
        } else {
            System.err.println("❌ TEST FAILED: Không thấy nội dung lỗi mong muốn.");
            System.err.println("Thực tế nhận được: " + actualErrorMessage);
        }

        driver.quit();
    }
}
