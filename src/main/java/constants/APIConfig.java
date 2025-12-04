package constants;

public class APIConfig {
    // 1. Base URL: Chỉ sửa 1 chỗ này là cả dự án thay đổi theo
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    // 2. Endpoints: Các đường dẫn con
    public static final String POSTS_ENDPOINT = "/posts";
    public static final String USERS_ENDPOINT = "/users";

    // public static final: Biến hằng số, không ai được phép thay đổi giá trị này lúc chạy
    public static final String BEARER_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.fake_token_demo_123456";
}