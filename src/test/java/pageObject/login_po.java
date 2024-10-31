package pageObject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import comum.hooks;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import locators.login_locator;

public class login_po {
	
	private WebDriver driver;
	private WebDriverWait wait;
	public Scenario scenario;
	private Faker faker = new Faker();	
	private String name = faker.name().fullName();
    private String email = faker.internet().emailAddress();
    private String password = faker.internet().password();
    
	administratorAccount_po administratorAccount = new administratorAccount_po();
	
	public login_po() {
        this.driver = hooks.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
		
	@Given("Website loaded")
	public void website_Loaded() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("h1Login"))));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("inputEmail"))));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("inputPassword"))));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("butEnter"))));
	}
	
	@When("Submit login")
	public void submit_Login() {
		driver.findElement(By.xpath(login_locator.login.get("butEnter"))).click();
	}
	
	@Then("Mandatory email and password messages")
	public void mandatory_Email_And_Password_Messages() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("mandatoryEmail"))));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("mandatoryPassword"))));
	}
	
	@When("Report invalid data")
	public void report_Invalid_data() {
		driver.findElement(By.xpath(login_locator.login.get("inputEmail"))).sendKeys("invalid@test.com");
		driver.findElement(By.xpath(login_locator.login.get("inputPassword"))).sendKeys("invalid");
	}
	
	@Then("Invalid email and password message")
	public void invalid_email_And_Password_Message() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("messageInvalid"))));
	}
	
	@And("Registering a new profile")
	public void registering_A_New_profile() {
		driver.findElement(By.xpath(login_locator.login.get("register"))).click();
	}
	
	@And("Send registration")
	public void send_Registration() {
		driver.findElement(By.xpath(login_locator.login.get("btRegistration"))).click();
	}
	
	@Then("Mandatory message")
	public void mandatory_Message() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("mandatoryName"))));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("mandatoryEmail"))));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("mandatoryPassword"))));
	}
	
	@And("Fill in valid data")
	public void fill_In_valid_data() {
		
        driver.findElement(By.xpath(login_locator.login.get("userName"))).sendKeys(name);
        driver.findElement(By.xpath(login_locator.login.get("userEmail"))).sendKeys(email);
        driver.findElement(By.xpath(login_locator.login.get("userPassword"))).sendKeys(password);
	}
	
	@Then("Successful registration message")
	public void successful_Registration_Message() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("successfulMessage"))));
	}
	
	@And("Fill in data that already exists")
	public void fill_In_Data_That_Already_Exists() {
				
		fill_In_valid_data();
		send_Registration();
		
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("successfulMessage"))));
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("logout"))));
			driver.findElement(By.xpath(login_locator.login.get("logout"))).click();
			website_Loaded();
			registering_A_New_profile();
			fill_In_valid_data();
			
		}catch(org.openqa.selenium.TimeoutException e) {
			
		}
	}
	
	@Then("Message this account already exists")
	public void message_This_Account_Already_exists() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login_locator.login.get("MessageAlreadyExists"))));
	}
	
	@And("Filling an administrator profile")
	public void filling_An_Administrator_profile() {
		
		 driver.findElement(By.xpath(login_locator.login.get("userName"))).sendKeys(name);
	     driver.findElement(By.xpath(login_locator.login.get("userEmail"))).sendKeys(email);
	     driver.findElement(By.xpath(login_locator.login.get("userPassword"))).sendKeys(password);
	     
	     driver.findElement(By.xpath(login_locator.login.get("checkbox"))).click();
	     
	}
}
