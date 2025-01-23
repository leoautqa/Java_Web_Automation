package comum;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.core.exception.ExceptionUtils;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.AfterAll;

public class hooks {

    public static WebDriver driver;
    public WebDriverWait wait;
    private static ExtentTest scenarioNode;

    @BeforeAll
    public static void beforeTestRun() {
    	if (driver == null) {
            
            String browser = System.getProperty("browser", "firefox").toLowerCase();

            switch (browser) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", "src/main/java/driver/chromedriver.exe");
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "firefox":
                    System.setProperty("webdriver.gecko.driver", "src/main/java/driver/geckodriver.exe");
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("-private");
                    firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                default:
                    throw new IllegalArgumentException("Navegador não suportado: " + browser);
            }

            driver.manage().window().maximize();
            driver.get(variables.URL);
        }

        extendReport.extentReportInit();
    }
    

    @Before
    public static void beforeScenario(Scenario scenario) {
        String featureName = scenario.getUri().getPath();
        featureName = featureName.substring(featureName.lastIndexOf("/") + 1).replace(".feature", "");
        
        extendReport._feature = extendReport._extentReports.createTest(featureName);
        
        scenarioNode = extendReport._feature.createNode(scenario.getName());
    }

    @After
    public static void afterScenario(Scenario scenario) {
        try {
            String featureName = scenario.getUri().getPath();
            featureName = featureName.substring(featureName.lastIndexOf("/") + 1).replace(".feature", "");

            if (scenario.isFailed()) {
                String errorMessage = "An error occurred in the scenario.";
                String stackTrace = "Stack trace not available.";

                try {
                    Throwable error = new Throwable("Error in Scenario");
                    errorMessage = error.getMessage();
                    stackTrace = ExceptionUtils.printStackTrace(error);
                } catch (Exception e) {
                    System.out.println("Error capturing stack trace: " + e.getMessage());
                }

                scenarioNode.fail("Test failed with error: " + errorMessage)
                            .fail("Stack trace: " + stackTrace);

                String screenshot = takeScreenshot(driver);
                scenarioNode.fail("Screenshot at failure",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
            } else {
                scenarioNode.pass("Test Passed");
            }
        } catch (WebDriverException e) {
            System.out.println("WebDriver error in afterScenario: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error in afterScenario: " + e.getMessage());
        }
    }

    public static String takeScreenshot(WebDriver driver) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            String base64Screenshot = ts.getScreenshotAs(OutputType.BASE64);
            return base64Screenshot;
        } catch (Exception e) {
            System.out.println("Erro ao capturar screenshot: " + e.getMessage());
            return null;
        }
    }

    @AfterStep
    public static void afterStep(Scenario scenario) {
    	String stepName = scenario.getName();    	
        String stepType = determineStepType(stepName);
        Media screenshot = MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(driver)).build();

        switch (stepType) {
		    case "Given":
		        scenarioNode.info(stepName).pass("Success", screenshot);
		        break;
		    case "When":
		        scenarioNode.info(stepName).pass("Success", screenshot);
		        break;
		    case "Then":
		        scenarioNode.info(stepName).pass("Success", screenshot);
		        break;
		    default:
		        scenarioNode.info(stepName).pass("Success", screenshot);
		        break;
		}
    }

    private static String determineStepType(String stepName) {
        if (stepName.startsWith("Given")) {
            return "Given";
        } else if (stepName.startsWith("When")) {
            return "When";
        } else if (stepName.startsWith("Then")) {
            return "Then";
        }
        return "And";
    }

    @AfterAll
    public static void afterTestRun() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        extendReport.extentReportTearDown();
    }

    public static WebDriver getDriver() {
        return driver;
    }
}