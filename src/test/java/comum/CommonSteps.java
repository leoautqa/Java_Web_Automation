package comum;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CommonSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    public CommonSteps(WebDriver driver) {
        this.driver = hooks.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void beAtTab(String nameTab) {
        try {
            driver.findElement(By.xpath("//a[contains(.,'" + nameTab + "')]")).click();
            System.out.println("Navigated to " + nameTab + " tab successfully.");
        } catch (NoSuchElementException ex) {
            System.out.println("Tab '" + nameTab + "' not found: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            System.out.println("An error occurred while navigating to '" + nameTab + "' tab: " + ex.getMessage());
            throw ex;
        }
    }

    public void scrollElementIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public boolean scrollElementIntoViewStatus(By locator) {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            if (element.isDisplayed()) {
                Actions actions = new Actions(driver);
                actions.moveToElement(element).perform();
                return true;
            }
            return false;
        } catch (NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isUserLoggedIn() {
        try {
            return driver.findElement(By.xpath("//a[contains(.,'Home')]")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<WebElement> waitForElementsToBeVisible(By locator) {
        return wait.until(driver -> {
            List<WebElement> elements = driver.findElements(locator);
            return elements.stream().allMatch(WebElement::isDisplayed) ? elements : null;
        });
    }
}

