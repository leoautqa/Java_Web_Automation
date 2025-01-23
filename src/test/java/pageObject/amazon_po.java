package pageObject;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import locators.amazon_locator;
import comum.CommonSteps;
import comum.hooks;

public class amazon_po {
	
	private WebDriver driver;
    private WebDriverWait wait;
    
    CommonSteps commonSteps = new CommonSteps(driver);

    public amazon_po() {
		this.driver = hooks.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
	}
    
    public void implementation(WebDriver driver) {
        this.driver = driver;
    }
    
    @Given("Be on the amazon page")
    public void be_on_the_amazon_page() {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(amazon_locator.amazon.get("logo"))));
    }
    
    @Given("Look for either '{}', '{}'")
    public void look_for_either(String name, String nickname ) {
    	driver.findElement(By.xpath(amazon_locator.amazon.get("searchBar"))).sendKeys(name);
    	
    	driver.findElement(By.xpath(amazon_locator.amazon.get("butSearch"))).click();
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='a-color-state a-text-bold'][contains(.,'" + name + "')]")));
    }
    
    @And("Filter products found by Books")
    public void filter_products_found_by_books() {
    	
    	driver.findElement(By.xpath(amazon_locator.amazon.get("filter"))).click();
    	
    }
    
    @When("Open a book page")
    public void open_a_book_page() {
    	    	    	
    	driver.findElement(By.xpath(amazon_locator.amazon.get("bookLink"))).click();
    	  	
    }
    
    @And("Add {} item to cart")
    public void add_item_to_cart(Integer item) {
        try {
            
            if (commonSteps.isElementPresent(By.xpath(amazon_locator.amazon.get("quantyBar")))) {
                
                driver.findElement(By.xpath(amazon_locator.amazon.get("quantyBar"))).click();

                
                driver.findElement(By.xpath("//a[@tabindex='-1'][contains(@id,'1')][contains(.,'" + item + "')]")).click();
            } else {
                System.out.println("Barra de quantidade não visível. Continuando com a quantidade padrão.");
            }

            
            driver.findElement(By.xpath(amazon_locator.amazon.get("btAddToCart"))).click();

            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(amazon_locator.amazon.get("msAddedToCart"))));
        } catch (Exception e) {
            
            System.out.println("Erro ao adicionar o item ao carrinho: " + e.getMessage());
            e.printStackTrace(); 
        }
    }

    
    @Then("the cart subtotal must be less than {} dollars")
	public void the_cart_subtotal_must_be_less_than_dollars(Integer dolar) {
    	
    	
        WebElement element = driver.findElement(By.xpath("//div[contains(@class, 'ewc-subtotal')]//h2[@class='a-size-base a-color-price a-text-bold']"));
        
        
        String subtotalText = element.getText().replaceAll("[^\\d.]", "");
        
        
        float subtotalValue = Float.parseFloat(subtotalText);
    	
    	if(subtotalValue < dolar) {
    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'ewc-subtotal')]//h2[contains(text(), '" + subtotalValue + "')]")));
    	}else {
            System.out.println("Subtotal greater than " + dolar);
        }    	    	
	}
    
    @Then("Confirm at least {}% of people liked the book with {} stars")
    public void confirm_at_least_people_liked_the_book_with_stars(Integer percent, String stars) {
    	
    	commonSteps.scrollElementIntoView(driver.findElement(By.xpath(amazon_locator.amazon.get("customerReviews"))));
    	
    	this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    	
    	WebElement element = driver.findElement(By.xpath("//a[contains(@aria-label, 'percent of reviews have " + stars + " stars')]"));
        
        
        String percentageText = element.getText();

        
        String percentageString = percentageText.split("\n")[1].replace("%", "");
        int percentageValue = Integer.parseInt(percentageString);
        
        if (percentageValue > percent) {
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@aria-label, '" + stars + " stars') and contains(@aria-label, '"+ percentageValue + " percent')]")));
        } else {
            System.out.println("Insufficient percentage: " + percentageValue + "%");
        }
    }
}
