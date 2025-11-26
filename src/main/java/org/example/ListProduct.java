package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

public class ListProduct {
    public static void main(String[] args) {

        // setup incongnito chorme mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // driver wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        driver.get("https://www.saucedemo.com/");

        // Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();


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

        driver.quit();
    }
}
