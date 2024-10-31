package pageObject;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Duration;
import java.util.Random;

import com.github.javafaker.Faker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import locators.administratorAccount_locator;
import comum.CommonSteps;
import comum.hooks;
import comum.variables;

public class administratorAccount_po {
	
	private WebDriver driver;
    private WebDriverWait wait;
    private Faker faker;
    private String name;
    private String email;
    private String password;
    private String prod;
    
    CommonSteps commonSteps = new CommonSteps(driver);

    public administratorAccount_po() {
		this.driver = hooks.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
	}
    
    public void implementation(WebDriver driver) {
        this.driver = driver;
    }

	@Given("I am logged in as administrator")
	public void i_am_logged_in_as_administrator() {
		if (driver.getCurrentUrl().contains("Home") || commonSteps.isUserLoggedIn()) {
		    System.out.println("User already logged in, skipping login.");
		    return;
		}
		
		String adminName = variables.ADMIN_NAME;
		String adminUser = variables.ADMIN_USER;
		String adminPass = variables.ADMIN_PASS;

		driver.findElement(By.xpath(administratorAccount_locator.loginAdm.get("inputEmail"))).sendKeys(adminUser);
		driver.findElement(By.xpath(administratorAccount_locator.loginAdm.get("inputPassword"))).sendKeys(adminPass);
		driver.findElement(By.xpath(administratorAccount_locator.loginAdm.get("butEnter"))).click();
		
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		
		try {
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.loginAdm.get("messageInvalid"))));
	        registerNewAccountAdmin(adminName, adminUser, adminPass);
	    } catch (org.openqa.selenium.TimeoutException e) {
	    }
	}

	private void registerNewAccountAdmin(String name, String email, String password) {

		String adminName = variables.ADMIN_NAME;
		String adminUser = variables.ADMIN_USER;
		String adminPass = variables.ADMIN_PASS;

		driver.findElement(By.xpath(administratorAccount_locator.loginAdm.get("btSign_Up"))).click();
		driver.findElement(By.xpath(administratorAccount_locator.loginAdm.get("userName"))).sendKeys(adminName);
		driver.findElement(By.xpath(administratorAccount_locator.loginAdm.get("userEmail"))).sendKeys(adminUser);
		driver.findElement(By.xpath(administratorAccount_locator.loginAdm.get("userPassword"))).sendKeys(adminPass);
		driver.findElement(By.xpath(administratorAccount_locator.loginAdm.get("checkbox"))).click();
		sendRegistration();
	}

	public void sendRegistration() {
		driver.findElement(By.xpath(administratorAccount_locator.loginAdm.get("btRegister"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.loginAdm.get("ms_Cad_Suc"))));
		page_must_load_correctly();
	}

	@Given("Be at {} tab")
	public void beAtTab(String nameTab) {
		driver.findElement(By.xpath("//a[contains(.,'" + nameTab + "')]")).click();
	}

	@Then("Page must load correctly")
	public void page_must_load_correctly() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.homeAdm.get("hCadUse"))));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.homeAdm.get("hLisUse"))));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.homeAdm.get("hCadProd"))));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.homeAdm.get("hLisProd"))));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.homeAdm.get("hRel"))));
	}
	
	@When("Do the registration")
	public void do_The_Registration() {
		driver.findElement(By.xpath(administratorAccount_locator.cadUsu.get("btCad"))).click();
	}
	
	@Then("Message {} on Register Users")
	public void message_On_Register_Users(String message) {
		try {
			if(message.equals("alert register")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.cadUsu.get("msgNameMan"))));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.cadUsu.get("msgEmailMan"))));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.cadUsu.get("msgPassMan"))));
			}
			else {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.cadUsu.get("msAlrExi"))));
			}
		}
		catch (org.openqa.selenium.TimeoutException e) {
        }
	}
	
	@And("Complete {} registration")
	public void complete_Registration(String account) {
		this.faker = new Faker();
		
		this.name = faker.name().fullName();
        this.email = faker.internet().emailAddress();
        this.password = faker.internet().password();
        
        driver.findElement(By.xpath(administratorAccount_locator.cadUsu.get("inpName"))).sendKeys(name);
        driver.findElement(By.xpath(administratorAccount_locator.cadUsu.get("inpEmail"))).sendKeys(email);
        driver.findElement(By.xpath(administratorAccount_locator.cadUsu.get("inpPass"))).sendKeys(password);
        
        try {
        	if(account.equals("Administrator")) {
        		driver.findElement(By.xpath(administratorAccount_locator.cadUsu.get("cheBoxAdm"))).click();
        	}
        }
        catch (org.openqa.selenium.TimeoutException e) {
        }		
	}
	
	@Then("{} registration must be on the list")
	public void registration_Must_Be_On_The_List(String line) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.lisUsu.get("hUmLisUsu"))));
		
		try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
        WebElement elementToScroll;
        if (line.equals("Regular")) {
            elementToScroll = driver.findElement(By.xpath("//tr[td[contains(., '" + name + "')] and td[contains(., 'false')]]/td"));
        } else {
            elementToScroll = driver.findElement(By.xpath("//tr[td[contains(., '" + name + "')] and td[contains(., 'true')]]/td"));
        }

        commonSteps.scrollElementIntoView(elementToScroll);
	}
	
	@And("Complete registration already exists")
	public void complete_Registration_Already_exists(){
		driver.findElement(By.xpath(administratorAccount_locator.cadUsu.get("inpName"))).sendKeys(name);
        driver.findElement(By.xpath(administratorAccount_locator.cadUsu.get("inpEmail"))).sendKeys(email);
        driver.findElement(By.xpath(administratorAccount_locator.cadUsu.get("inpPass"))).sendKeys(password);
	}
	
//Listar Usuários -------------------------------------------------------------------------------------------------------------------------------------
		
	@When("{} account")
	public void account(String action) {
		try {
        	if(action.equals("Edit")) {
        		driver.findElement(By.xpath("//tr[td[contains(text(), '" + name + "')]]//button[@class='btn btn-info']")).click();
        	}
        	else {
        		driver.findElement(By.xpath("//tr[td[contains(text(), '" + name + "')]]//button[@class='btn btn-danger']")).click();
        	}
        }
        catch (org.openqa.selenium.TimeoutException e) {
        }
	}
	
	@Then("Account must be {}")
	public void account_Must_be(String button) {
		try{
			if(button.equals("edited")) {
				System.out.print("Unfortunately nothing happens when you click the edit button");
			}
			else {
				boolean status = commonSteps.scrollElementIntoViewStatus(By.xpath("//tr[td[contains(., '" + name + "')] and td[contains(., 'false')]]/td"));
		        if (!status) {
		            System.out.println("Account deleted successfully");
		        }
			}
		}
		catch(org.openqa.selenium.TimeoutException e) {
			
		}
	}

//Cadastrar Produtos tab -------------------------------------------------------------------------------------------------------------------------------------
	
	@When("Click button register")
	public void click_Button_Register() {
		driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("btCad"))).click();
	}
	
	@Then("Alert message should appear")
	public void alert_Message_Should_Appear() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.cadProd.get("msNoName"))));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.cadProd.get("msNoPrice"))));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.cadProd.get("msNoDesc"))));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.cadProd.get("msNoQuant"))));
	}
	
	
	@When("Fill product information with {} {}")
	public void fill_Product_Information_With(String option, String category) {
		int price;
		int quantity;
		if(category.equals("price")) {
			driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("nameProd"))).sendKeys("Zero price");
			price = 0;
		}
		else {
			driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("nameProd"))).sendKeys("Negative quantity");
			price = 1;
		}
		
		driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("priceProd"))).sendKeys(String.valueOf(price));
		driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("descProd"))).sendKeys(category);
		
		if(category.equals("quantity")) {
			quantity = -1;
		}
		else {
			quantity = 1;
		}
		
		driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("quantProd"))).sendKeys(String.valueOf(quantity));
		
	}
	
	@Then("Message {} must be {} should be visible")
	public void message_Must_Be_Should_Be_Visible(String alert, String msgpan) {
		if(alert.equals("price")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.cadProd.get("msPrice"))));
		}
		else {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.cadProd.get("msQuant"))));
		}
	}
	
	@When("Fill in product {}")
	public void fill_In_product(String info) {
		long price;
		int quantity;
		
		if(info.equals("information")) {
			this.prod = "Leozirton";
			price = 1000000000;
			quantity = 1;
			driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("choFil"))).sendKeys("C:/Users/lpmuchinski/Desktop/eclipse automation/src/main/java/data/robot.png");
		}
		else if(info.equals("no quantity")) {
				this.prod = "Don't buy this product";
				price = 1;
				quantity = 0;
				driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("choFil"))).sendKeys("C:/Users/lpmuchinski/Desktop/eclipse automation/src/main/java/data/Selenium.png");
		}
		else {
			this.prod = "No picture";
			price = 1;
			quantity = 1;
		}
		driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("nameProd"))).sendKeys(prod);
		driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("priceProd"))).sendKeys(String.valueOf(price));
		driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("descProd"))).sendKeys("Test Leo");
		driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("quantProd"))).sendKeys(String.valueOf(quantity));
	}
	
	@Then("Product must be on the list")
	public void product_Must_Be_On_The_List() {
		String randomNumber;
		String price = null;
		String desc = null;
		String quantity = null;
		
		try {
			WebElement msProdExis = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.lisProd.get("msProdExis"))));
            if (msProdExis != null) {
				Random random = new Random();
				randomNumber = " " + random.nextInt(101);
				this.prod = prod + randomNumber + " refact";
				price = " " + randomNumber;
				desc = " " + randomNumber;
				quantity = " " + randomNumber;
			
				driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("nameProd"))).sendKeys(randomNumber + " refact");
				driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("priceProd"))).sendKeys(String.valueOf(price));
				driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("descProd"))).sendKeys(desc);
				driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("quantProd"))).sendKeys(String.valueOf(quantity));
				driver.findElement(By.xpath(administratorAccount_locator.cadProd.get("btCad"))).click();
			}
		}catch(org.openqa.selenium.TimeoutException e) {
		}
		
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.lisProd.get("titLis"))));
		
		WebElement productElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),\"" + prod + "\")]")));
		commonSteps.scrollElementIntoView(productElement);		
	}
	
	@Then("Message that the product already exists should appear")
	public void message_That_The_Product_Already_exists_Shoul_Appear() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.lisProd.get("msProdExis"))));
	}
	
	@When("Find product {}")
	public void find_the_product(String typeProd) throws InterruptedException {
			        
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.lisProd.get("titLis"))));

        boolean status = commonSteps.scrollElementIntoViewStatus(By.xpath("//tr[td[contains(text(), '" + typeProd + "')]]"));

        if (!status) {
            driver.findElement(By.xpath("//a[contains(text(), 'Cadastrar Produtos')]")).click();

            fill_In_product(typeProd);
            click_Button_Register();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(administratorAccount_locator.lisProd.get("titLis"))));
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            commonSteps.scrollElementIntoView(driver.findElement(By.xpath("//tr[td[contains(text(), '" + typeProd + "')]]")));
            
        } else {
        	commonSteps.scrollElementIntoView(driver.findElement(By.xpath("//tr[td[contains(text(), '" + typeProd + "')]]")));
        }
	}
	
	@And("{} the item {}")
	public void the_Item(String listAction, String typeProdList) {
		if(listAction.equals("Edit")) {
			driver.findElement(By.xpath("//tr[td[contains(text(), '" + typeProdList + "')]]//button[contains(text(), 'Editar')]")).click();
		}
		else {
			driver.findElement(By.xpath("//tr[td[contains(text(), '" + typeProdList + "')]]//button[contains(text(), 'Editar')]")).click();
		}
	}
	
	@Then("Product should be {}")
	public void product_Must_Be(String listMsg) {
		if(listMsg.equals("edited")) {
			System.out.print("Unfortunately nothing happens when you click the edit button");
		}
		else {
			boolean status = commonSteps.scrollElementIntoViewStatus(By.xpath("//tr[td[contains(text(), \"" + prod + "\")]]"));
			
			if(!status) {
				System.out.print("Account deleted successfully");
			}
		}
	}
}


