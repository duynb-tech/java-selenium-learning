package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();

        // 1. Chế độ ẩn danh (như cũ)
        options.addArguments("--incognito");

        // 2. KÍCH HOẠT CHẾ ĐỘ HEADLESS (MỚI)
        // "--headless=new" là chuẩn mới nhất của Chrome, ổn định hơn bản cũ
        options.addArguments("--headless=new");

        // Lưu ý: Khi chạy headless, nên set cứng kích thước cửa sổ ảo
        // để tránh trường hợp giao diện mobile bị vỡ
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        // driver.manage().window().maximize(); // Dòng này có thể bỏ vì đã set window-size ở trên

        // ... Các đoạn wait phía dưới giữ nguyên ...
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() {
        try { Thread.sleep(2000); } catch (Exception e) {}
        if (driver != null) {
            driver.quit();
        }
    }
}