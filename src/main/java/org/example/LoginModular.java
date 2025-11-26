package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

public class LoginModular {

    static WebDriver driver;

    public static void main(String[] args) {
        setup();
        login("standard_user", "secret_sauce");

        List<WebElement> productList = driver.findElements(By.xpath("//div[contains(@class, 'inventory_item_name')]"));

        // in số lượng
        System.out.println("tìm thấy số lượng sản phẩm là : "+productList.size());

        //in danh sách sản phẩm
        System.out.println("=== DANH SÁCH SẢN PHẨM ===");

        for(WebElement item: productList){
            String productName = item.getText();
            if (productName.equals("Sauce Labs Backpack")) {
                item.click();
                System.out.println("đã tìm thấy Sauce Labs Backpack");
                break;
            }
        }

        tearDown();
    }

    public static void setup() {
        // 1. SETUP CHROME ẨN DANH
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito"); // Chế độ ẩn danh (Sạch sẽ, không popup)

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // 2. THIẾT LẬP IMPLICIT WAIT (THAY THẾ THREAD.SLEEP)
        // Selenium sẽ luôn dành ra tối đa 10s để tìm element trước khi báo lỗi
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    public static void login(String username, String password) {
        driver.get("https://www.saucedemo.com/");

        // Chỗ này không điền cứng nữa, mà điền biến
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }

    public static void tearDown() {
        driver.quit();
    }

}
