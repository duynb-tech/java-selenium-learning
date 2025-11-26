package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        // --- CÁC BƯỚC AUTO ---
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sai_roi");
        driver.findElement(By.id("login-button")).click();

        Thread.sleep(2000); // Đợi trang load xong

        // --- BƯỚC KIỂM TRA (VERIFICATION) ---

        // Cách kiểm tra: Lấy URL hiện tại xem có chứa từ "inventory" không
        String currentUrl = driver.getCurrentUrl();
        String expectedUrlPart = "inventory.html";

        System.out.println("Đang kiểm tra kết quả...");
        System.out.println("URL hiện tại là: " + currentUrl);

        if (currentUrl.contains(expectedUrlPart)) {
            System.out.println("✅ TEST PASSED: Đăng nhập thành công, đã vào trang Inventory.");
        } else {
            System.err.println("❌ TEST FAILED: Vẫn chưa vào được trang Inventory.");
        }

        // Đóng trình duyệt
        driver.quit();
    }
}