package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select; // 1. Import class này
import java.time.Duration;

public class SortTest {
    public static void main(String[] args) {

        // Setup Chrome ẩn danh & Wait (Giữ nguyên như bài trước)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // --- TEST STEP ---
        driver.get("https://www.saucedemo.com/");

        // Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // --- XỬ LÝ DROPDOWN ---

        // B1: Tìm thẻ Select (Tôi đã inspect giúp bạn class của nó)
        WebElement sortDropdown = driver.findElement(By.className("product_sort_container"));

        // B2: Khởi tạo đối tượng Select
        Select selectFilter = new Select(sortDropdown);

        // B3: Chọn theo chữ hiển thị trên màn hình
        // Hãy chú ý: Chữ phải chính xác 100% (cả khoảng trắng)
        selectFilter.selectByIndex(2);

        System.out.println("Đã chọn lọc theo giá thấp nhất.");

        // --- MUA MÓN RẺ NHẤT ---
        // Khi sort xong, món đầu tiên sẽ đổi.
        // Chúng ta vẫn dùng XPath tìm "nút Add to cart đầu tiên"
        // (Cách dùng index bài trước phát huy tác dụng ở đây!)

        driver.findElement(By.xpath("(//button[text()='Add to cart'])[1]")).click();

        // Vào giỏ hàng
        driver.findElement(By.className("shopping_cart_link")).click();

        // Verify: Món rẻ nhất tên là "Sauce Labs Onesie"
        if(driver.getPageSource().contains("Sauce Labs Onesie")) {
            System.out.println("✅ TEST PASSED: Đã mua đúng món rẻ nhất ($7.99)!");
        } else {
            System.err.println("❌ TEST FAILED: Mua nhầm hàng rồi!");
        }

        driver.quit();
    }
}
