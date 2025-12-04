package tests;

import common.BaseTest;
import constants.APIConfig;
import models.Post;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PostTests extends BaseTest {

    @Test(dataProvider = "postData") // 👈 Kết nối với kho dữ liệu tên "postData"
    public void testCreatePostWithDataDriven(int userId, String title, String body) {

        // 1. Dùng tham số truyền vào để tạo Object (Thay vì hard-code)
        Post requestBody = new Post(userId, title, body);

        System.out.println("🚀 Đang test với Title: " + title);

        Post responseBody = given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post(APIConfig.POSTS_ENDPOINT)
                .then()
                .statusCode(201)
                .extract()
                .as(Post.class);

        // 2. Verify dữ liệu trả về phải khớp với dữ liệu truyền vào
        Assert.assertEquals(responseBody.getTitle(), title);
        Assert.assertEquals(responseBody.getBody(), body);
        Assert.assertEquals(responseBody.getUserId(), userId);
    }

    @Test
    public void testPutPostWithPOJO() {
        // Chuẩn bị data (Dùng cách set ID chuẩn logic mà chúng ta vừa bàn)
        Post updatePost = new Post(1, "Code clean level 24", "Architecture");
        updatePost.setId(1); // Set ID bài viết muốn sửa là 1

        Post responsePost = given()
                .spec(requestSpec) // 🔥 Kế thừa cấu hình
                .body(updatePost)
                .when()
                // Logic chuẩn: Lấy ID bài viết để nối URL
                .put(APIConfig.POSTS_ENDPOINT + "/" + updatePost.getId())
                .then()
                .statusCode(200)
                .extract()
                .as(Post.class);

        System.out.println("Updated Post Title: " + responsePost.getTitle());
        Assert.assertEquals(responsePost.getTitle(), "Code clean level 24");
    }

    @DataProvider(name = "postData")
    public Object[][] createPostData() {
        return new Object[][] {
                // Hàng 1: User 1, Title bình thường
                { 1, "Title Normal", "Body Normal" },

                // Hàng 2: User 2, Title có ký tự đặc biệt
                { 2, "!@#$%^&*", "Special Chars Body" },

                // Hàng 3: User 3, Tiếng Việt có dấu
                { 3, "Tiêu đề Tiếng Việt", "Nội dung Tiếng Việt" }
        };
    }
}