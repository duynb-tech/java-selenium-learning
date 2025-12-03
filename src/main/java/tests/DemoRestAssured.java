package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DemoRestAssured {

    private static final Logger log = LoggerFactory.getLogger(DemoRestAssured.class);

    @Test
    public void testGetSingleUser() {
        // 1. Đổi nhà cung cấp sang JSONPlaceholder
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/users/1") // Lấy thông tin user ID = 1
                .then()
                .log().all() // In ra xem có gì
                .statusCode(200) // Mong đợi 200 OK
                .body("username", equalTo("Bret")) // Kiểm tra username phải là Bret
                .body("address.city", equalTo("Gwenborough")); // Kiểm tra object lồng nhau (address -> city)
    }
    @Test
    public void testPostUser() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // 1. Khai báo chuỗi JSON chuẩn (Có ngoặc nhọn đàng hoàng)
        String bodyJson = "{\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";

        given()
                .header("Content-Type", "application/json")
                .body(bodyJson) // Gửi chuỗi JSON đã sửa
                .when()
                .post("/posts")
                .then()
                .log().all() // Luôn log ra để xem mình gửi đi cái gì và nhận về cái gì
                .statusCode(201) // Đừng quên check status code!
                .body("title", equalTo("foo"));
    }

    @Test
    public void testPostUserWithMap() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Tạo dữ liệu bằng Java Map (Quen thuộc chưa?)
        Map<String, Object> bodyData = new HashMap<>();
        bodyData.put("title", "foo");
        bodyData.put("body", "bar");
        bodyData.put("userId", 1);

        given()
                .header("Content-Type", "application/json")
                .body(bodyData) // RestAssured tự động biến Map này thành JSON nhờ Jackson
                .when()
                .post("/posts")
                .then()
                .log().all()
                .statusCode(201)
                .body("title", equalTo("foo"));
    }

    @Test
    public void testCreateAndExtract() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Map<String, Object> bodyData = new HashMap<>();
        bodyData.put("title", "Bài học Level 21");
        bodyData.put("body", "Học cách extract dữ liệu");
        bodyData.put("userId", 1);

        // 1. Thay vì .then() ngay, ta hứng kết quả vào biến 'response'
        Response res = given()
                .header("Content-Type", "application/json")
                .body(bodyData)
                .when()
                .post("/posts");

        // 2. In ra xem thử (Debug)
        res.prettyPrint();

        // 3. Mổ xẻ dữ liệu (Extraction)
        // Lấy giá trị của trường "title" biến thành String
        String title = res.jsonPath().getString("title");
        // Lấy giá trị của trường "id" biến thành int
        int id = res.jsonPath().getInt("id");

        System.out.println("--------------------");
        System.out.println("Title lấy về là: " + title);
        System.out.println("ID mới được tạo là: " + id);
        System.out.println("--------------------");

        // 4. Verify thủ công (bằng TestNG Assert) nếu thích
        // Lưu ý: jsonplaceholder luôn trả về id = 101 cho bài tạo mới (đây là quy tắc của trang fake này)
        org.testng.Assert.assertEquals(title, "Bài học Level 21");
        org.testng.Assert.assertEquals(id, 101);
    }

    @Test
    public void testUpdatePost(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Map<String, Object> bodyData = new HashMap<>();
        bodyData.put("title", "Updated by Duynb");

        int postId = 1;
        Response res = given()
            .header("Content-Type", "application/json")
                .and()
                .body(bodyData)
                .put("/posts/"+postId);

        res.prettyPrint();

        String title = res.jsonPath().getString("title");
        int id = res.jsonPath().getInt("id");

        System.out.println("--------------------");
        System.out.println("Title lấy về là: " + title);
        System.out.println("ID mới lấy về là: " + id);
        System.out.println("--------------------");

        org.testng.Assert.assertEquals(title, "Updated by Duynb");
        org.testng.Assert.assertEquals(id, postId);
    }

    @Test
    public void testCreatePostWithPOJO() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // 1. Tạo data bằng Java Object (Có gợi ý code, không sợ gõ sai chính tả)
        Post myPost = new Post(1, "Học POJO với Mentor", "Code sạch quá trời!");

        // 2. Gửi đi (Serialization: Java -> JSON)
        Post responsePost = given()
                .header("Content-Type", "application/json")
                .body(myPost) // RestAssured tự động biến object myPost thành JSON
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .extract()
                .as(Post.class); // 3. Hứng về (Deserialization: JSON -> Java)

        // 4. Kiểm tra (Dùng Getter của Java, cực sướng)
        System.out.println("Title server trả về: " + responsePost.getTitle());

        org.testng.Assert.assertEquals(responsePost.getTitle(), "Học POJO với Mentor");
        org.testng.Assert.assertEquals(responsePost.getBody(), "Code sạch quá trời!");
    }

    @Test
    public void testPutPostWithPOJO() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // 1. Chuẩn bị data (Lưu ý: object này id đang là 0)
        Post requestBody = new Post(1, "update with poro", "date changed!!!");

        // 2. Gửi request PUT
        Post responseBody = given()
                .header("Content-Type", "application/json")
                .body(requestBody) // Java Object -> JSON
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200) // PUT thành công thường trả về 200
                .extract()
                .as(Post.class); // 🔥 MAGIC: JSON -> Java Object (Deserialization)

        // 3. Verify
        System.out.println("Title server trả về: " + responseBody.getTitle());

        // So sánh Title (Cái này quan trọng nhất)
        org.testng.Assert.assertEquals(responseBody.getTitle(), "update with poro");

        // So sánh ID (Server trả về 1, nên ta hardcode số 1 để check)
        org.testng.Assert.assertEquals(responseBody.getId(), 1);
    }
}