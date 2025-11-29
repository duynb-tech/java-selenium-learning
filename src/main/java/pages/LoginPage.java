package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions; // Import điều kiện chờ
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    By usernameInput =  By.id("user-name");
    By passwordInput = By.id("password");
    By loginButton = By.id("login-button");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void enterUsername (String username){
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword (String password){
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin(){
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password){
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        driver.findElement(usernameInput).sendKeys(username);

        driver.findElement(passwordInput).sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        driver.findElement(loginButton).click();
    }
}
