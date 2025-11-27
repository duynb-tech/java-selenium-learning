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

        double actualPrice = getPrice();

        System.out.println("giá trị thực tế là : " + actualPrice);

        // TEST CASE 1: Kiểm tra giá trị dương
        if (actualPrice > 0) {
            System.out.println("✅ PASS: Giá tiền hợp lệ (Lớn hơn 0)");
        } else {
            System.out.println("❌ FAILED: Lỗi! Giá tiền bằng 0 hoặc số âm");
        }

        // TEST CASE 2: Kiểm tra đúng giá niêm yết (Ví dụ mong đợi là 29.99)
        double expectedPrice = 29.99;
        if (actualPrice == expectedPrice) {
            System.out.println("✅ PASS: Đúng giá niêm yết!");
        } else {
            System.out.println("❌ FAILED: Sai giá! Web đang hiển thị: " + actualPrice);
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

    public static double getPrice(){
        //tìm giá tiền của sản phẩm đầu tiên
        WebElement priceElement = driver.findElement(By.xpath("//div[contains(@class, 'inventory_item_price')]"));

        //lấy text
        String priceText = priceElement.getText();
        System.out.println("giá gốc trên web : "+priceText);

        //xử lý chuỗi
        String cleanPrice = priceText.replace("$","");

        // ép kiểu *sau này nhớ đổi sang try *
        double finalPrice = Double.parseDouble(cleanPrice);

        // trả về
        return  finalPrice;
    }

    public static void tearDown() {
        driver.quit();
    }

}
