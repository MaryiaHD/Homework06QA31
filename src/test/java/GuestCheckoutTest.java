import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class GuestCheckoutTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testGuestCheckout() {
        driver.get("http://shop.pragmatic.bg/index.php?route=product/product&product_id=40");
        driver.findElement(By.id("button-cart")).click();
        driver.findElement(By.id("cart-total")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartCheckoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='cart']//*[@class='fa fa-share']/../..")));
        cartCheckoutButton.click();

        WebElement guestRadioButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[value='guest']")));
        guestRadioButton.click();
        driver.findElement(By.id("button-account")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-payment-firstname"))).sendKeys("Milen");
        driver.findElement(By.id("input-payment-lastname")).sendKeys("Bozhinov");

        String prefix = RandomStringUtils.randomAlphanumeric(7);
        String sufix = RandomStringUtils.randomAlphabetic(5);
        String emailAddress = prefix + "@" + sufix + ".com";
        System.out.println("Generated email: " + emailAddress); // Debugging line
        driver.findElement(By.id("input-payment-email")).sendKeys(emailAddress);
        driver.findElement(By.id("input-payment-telephone")).sendKeys("0786543334");
        driver.findElement(By.id("input-payment-address-1")).sendKeys("Ulitza Edikoqsi");
        driver.findElement(By.id("input-payment-city")).sendKeys("Sofia");
        driver.findElement(By.id("input-payment-postcode")).sendKeys("1000");

        WebElement countryDropdown = driver.findElement(By.id("input-payment-country"));
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByValue("33");

        WebElement zoneDropdown = driver.findElement(By.id("input-payment-zone"));
        Select zoneSelect = new Select(zoneDropdown);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("option[value='505']")));
        zoneSelect.selectByValue("505");

        //TODO Your Homework starts here

        driver.findElement(By.id("button-guest")).click();
        wait.until(ExpectedConditions.urlToBe("https://shop.pragmatic.bg/index.php?route=checkout/checkout"));

        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("button-shipping-method")));
        continueButton.click();

        WebElement agreeCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.name("agree")));
        agreeCheckbox.click();

        WebElement nextContinueButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("button-payment-method")));
        nextContinueButton.click();

        WebElement confirmOrderButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("button-confirm")));
        confirmOrderButton.click();

        WebElement confirmMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']/h1[contains(text(), 'Your order has been placed!')]")));
        Assert.assertEquals(confirmMessage.getText(), "Your order has been placed!");
    }

}


