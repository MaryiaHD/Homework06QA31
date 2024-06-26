import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class AddNewCustomerTest {
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
    public void testAddCustomer() {
        driver.get("https://shop.pragmatic.bg/admin/");
        WebElement usernameInputField = driver.findElement(By.id("input-username"));
        usernameInputField.sendKeys("admin");
        WebElement passwordInputField = driver.findElement(By.id("input-password"));
        passwordInputField.sendKeys("parola123!");
        WebElement loginButton = driver.findElement(By.cssSelector(".btn"));
        loginButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement customerMenu = wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-customer")));
        customerMenu.click();
        WebElement customersOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='menu-customer']//li[1]/a")));
        customersOption.click();
        WebElement addButton = driver.findElement(By.xpath("//*[@class='fa fa-plus']/ .."));
        addButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-firstname"))).sendKeys("Milen");
        driver.findElement(By.id("input-lastname")).sendKeys("Bozhinov");
        WebElement emailInputField = driver.findElement(By.id("input-email"));
        String prefix = RandomStringUtils.randomAlphanumeric(7);
        String sufix = RandomStringUtils.randomAlphabetic(5);
        String emailAddress = prefix + "@" + sufix + ".com";
        emailInputField.sendKeys(emailAddress);
//        TODO Your Homework starts here


        driver.findElement(By.id("input-telephone")).sendKeys("0888112233");
        driver.findElement(By.id("input-password")).sendKeys("12345");
        driver.findElement(By.id("input-confirm")).sendKeys("12345");

        WebElement NewsletterDropdown = driver.findElement(By.id("input-newsletter"));
        Select NewsletterSelect = new Select(NewsletterDropdown);
        NewsletterSelect.selectByValue("1");

        WebElement statusDropdown = driver.findElement(By.id("input-status"));
        Select statusSelect = new Select(statusDropdown);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("option[value='0']")));
        statusSelect.selectByValue("0");

        WebElement safeDropdown = driver.findElement(By.id("input-safe"));
        Select safeSelect = new Select(safeDropdown);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("option[value='1']")));
        safeSelect.selectByValue("1");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("fa-save")));
        saveButton.click();

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success")));
        assert successMessage.getText().contains("Success: You have modified customers!");

        WebElement filterEmailInput = driver.findElement(By.id("input-email"));
        filterEmailInput.sendKeys(emailAddress);
        WebElement filterButton = driver.findElement(By.id("button-filter"));
        filterButton.click();


        WebElement customerEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//td[contains(text(), '" + emailAddress + "')]")));
        assert customerEmail.getText().equals(emailAddress);



    }
}
