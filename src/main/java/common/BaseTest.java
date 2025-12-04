package common;

import constants.APIConfig;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

// 👇 ĐÓNG ĐINH: Gắn Listener cứng vào đây, không cần quan tâm XML hay POM nữa
@Listeners({AllureTestNg.class})
public class BaseTest {

    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {
        System.out.println("⚡ Đang khởi tạo cấu hình Request chung...");

        RequestSpecBuilder builder = new RequestSpecBuilder();

        // 1. Base URL
        builder.setBaseUri(APIConfig.BASE_URL);

        // 2. Headers chung
        builder.addHeader("Content-Type", "application/json");
        // Quan trọng: Giả lập trình duyệt để không bị chặn
        builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        // 3. Authentication
        builder.addHeader("Authorization", "Bearer " + APIConfig.BEARER_TOKEN);

        // 4. Build ra requestSpec
        requestSpec = builder.build();
    }
}