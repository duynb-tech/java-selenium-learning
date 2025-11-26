package org.example;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SimpleLoginTest {
    public static void main(String[] args) throws InterruptedException{
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        System.out.println("đã mở google");

        Thread.sleep(5000);

        driver.quit();
        System.out.println("Set up trình duyệt thành công, trình duyệt đã mỡ");
    }
}
