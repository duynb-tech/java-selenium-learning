package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration; // Nhớ import cái này để setup thời gian

public class ShoppingTest {
    public static void main(String[] args) { // Không cần throws InterruptedException nữa

        // 1. SETUP CHROME ẨN DANH
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito"); // Chế độ ẩn danh (Sạch sẽ, không popup)

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // 2. THIẾT LẬP IMPLICIT WAIT (THAY THẾ THREAD.SLEEP)
        // Selenium sẽ luôn dành ra tối đa 10s để tìm element trước khi báo lỗi
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // ----------------------------------------
        // BẮT ĐẦU TEST (Code chạy nhanh vù vù)
        // ----------------------------------------

        driver.get("https://www.saucedemo.com/");

        // Login (Không cần Thread.sleep nữa)
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Click Add to cart
        driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']")).click();

        // Click vào icon Giỏ hàng
        driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();

        // Verification
        if(driver.getPageSource().contains("Sauce Labs Backpack")) {
            System.out.println("✅ TEST PASSED: Đã thêm Balo vào giỏ hàng thành công!");
        } else {
            System.err.println("❌ TEST FAILED: Giỏ hàng rỗng tuếch!");
        }

        driver.quit();
    }
}