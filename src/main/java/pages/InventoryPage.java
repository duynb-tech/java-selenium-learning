package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InventoryPage {
    WebDriver driver;

    By productTitle = By.className("inventory_item_name");
    By productPrice = By.className("inventory_item_price");
    By addToCartBtn = By.id("add-to-cart-sauce-labs-backpack");
    By cartBadge = By.className("shopping_cart_badge");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getProductCount(){
        List<WebElement> list =  driver.findElements(productTitle);
        return list.size();
    }

    public double getFirstProductPrice() {
        WebElement priceElement = driver.findElement(productPrice);
        String priceText = priceElement.getText();
        return Double.parseDouble(priceText.replace("$", ""));
    }

    public void addBackpackToCart() {
        driver.findElement(addToCartBtn).click();
    }

    public int getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(cartBadge);

        if (badges.size() > 0) {
            // Lấy text ("1") -> ép sang số (1)
            return Integer.parseInt(badges.get(0).getText());
        } else {
            return 0; // Không thấy badge nghĩa là giỏ rỗng
        }
    }
}
