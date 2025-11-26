package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;

public class CartValidation {
    public static void main(String[] args) {
        // Setup
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // 1. Vào luồng mua hàng
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // 2. Sort và Mua
        WebElement sortDropdown = driver.findElement(By.className("product_sort_container"));
        Select selectFilter = new Select(sortDropdown);
        selectFilter.selectByVisibleText("Price (low to high)");

        driver.findElement(By.xpath("(//button[text()='Add to cart'])[1]")).click();
        driver.findElement(By.className("shopping_cart_link")).click();

        // --- 3. KIỂM TRA CHÍNH XÁC (STRICT CHECK) ---

        // Lấy tên sản phẩm thực tế trên web
        String actualName = driver.findElement(By.xpath("//div[@class='inventory_item_name']")).getText();

        // Lấy giá tiền thực tế trên web
        String actualPrice = driver.findElement(By.xpath("//div[@class='inventory_item_price']")).getText();

        // Dữ liệu chuẩn (Expected)
        String expectedName = "Sauce Labs Onesie";
        String expectedPrice = "$7.99";

        System.out.println("Tên trên web: " + actualName);
        System.out.println("Giá trên web: " + actualPrice);

        // So sánh
        if(actualName.equals(expectedName) && actualPrice.equals(expectedPrice)){
            System.out.println("✅ TEST PASSED: Tên và Giá đều đúng!");
        } else {
            System.err.println("❌ TEST FAILED: Thông tin không khớp.");
            if(!actualName.equals(expectedName)) System.err.println("-> Sai tên sản phẩm");
            if(!actualPrice.equals(expectedPrice)) System.err.println("-> Sai giá tiền");
        }

        driver.quit();
    }
}
