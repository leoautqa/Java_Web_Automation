package comum;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class extendReport {

	public static ExtentReports _extentReports;
    public static ExtentTest _feature;
    public static ExtentTest _scenario;

    private static final String baseDir = System.getProperty("user.dir");
    private static final String testResultPath = baseDir + "/TestResults";

    // Informações constantes para o relatório
    private static final String applicationName = "front.serverest";
    private static final String browserName = "Chrome";
    private static final String osName = System.getProperty("os.name");
//    private static final int imageWidth = 1290;
//    private static final int imageHeight = 1080;
//    private static final int imageQuality = 30;
    
    public static void extentReportInit() {
        try {
            ensureTestResultDirectoryExists();

            ExtentSparkReporter htmlReporter = new ExtentSparkReporter(testResultPath + "/AutomationStatusReport.html");
            htmlReporter.config().setReportName("Automation Status Report");
            htmlReporter.config().setDocumentTitle("Automation Status Report");

            _extentReports = new ExtentReports();
            _extentReports.attachReporter(htmlReporter);
            addSystemInfo();
        } catch (Exception ex) {
            System.out.println("Erro ao inicializar o relatório: " + ex.getMessage());
        }
    }

    private static void ensureTestResultDirectoryExists() {
        File directory = new File(testResultPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private static void addSystemInfo() {
        _extentReports.setSystemInfo("Application", applicationName);
        _extentReports.setSystemInfo("Browser", browserName);
        _extentReports.setSystemInfo("OS", osName);
    }

    public static void extentReportTearDown() {
        try {
            if (_extentReports != null) {
                _extentReports.flush();
            }
        } catch (Exception ex) {
            System.out.println("Erro ao finalizar o relatório: " + ex.getMessage());
        }
    }

    public static String takeScreenshot(WebDriver driver) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File dest = new File(testResultPath + "/screenshot.png");
            FileUtils.copyFile(source, dest);
            return dest.getAbsolutePath();
        } catch (IOException ex) {
            System.out.println("Erro ao capturar o screenshot: " + ex.getMessage());
            return "";
        }
    }
	
//	private static ExtentReports extent;
//	private static ExtentTest test;
//
//	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mm-ss");
//
//	@Before
//	public static void setup() {
//		// Configurar o ExtentReports
//		String reportFile = System.getProperty("user.dir") + "\\" + "Relatorio_Testes_"
//				+ LocalDateTime.now().format(formatter) + ".html";
//		ExtentSparkReporter spark = new ExtentSparkReporter(reportFile);
//		//ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extentReport.html");
//		extent = new ExtentReports();
//		extent.attachReporter(spark);
//	}
//
//	public static void startTest(String testName) {
//		test = extent.createTest(testName);
//	}
//
//	public static void logInfo(String message) {
//		test.info(message);
//	}
//
//	public static void logPass(String message) {
//		test.pass(message);
//	}
//
//	public static void logFail(String message) {
//		test.fail(message);
//	}
//
//	@AfterAll
//	public static void tearDown() {
//		// Finalizar o relatório
//		extent.flush();
//	}
}
