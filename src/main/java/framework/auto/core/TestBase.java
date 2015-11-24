package framework.auto.core;

// Implementation of the core methods for the framework
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import framework.auto.utils.ErrorUtil;
import framework.auto.utils.JSUtil;
import framework.auto.utils.LogUtil;
import framework.auto.utils.TestUtil;
import framework.auto.utils.Xls_Reader;
import org.apache.log4j.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.google.common.base.Function;

public class TestBase {
	public static Properties	CONFIG	                    = null;
	public static Properties	OR	                        = null;
	//public static Xls_Reader	suiteXls	                = null;
	public static Xls_Reader	mylAuto_Classes_xls	        = null;
	public static Xls_Reader	mylAuto_AccountCreation_xls	= null;
	public static Xls_Reader	mylAuto_Independant_xls	    = null;
	public static Xls_Reader	mylAuto_testAndSurveys	    = null;
	public static Xls_Reader    mylAuto_discounts			= null;
	public static Xls_Reader    mylAuto_certifications		= null;
	public static Xls_Reader	mylAuto_credits				= null;
	public static Xls_Reader	mylAuto_Recertification_xls	= null;
	public static Xls_Reader	mylAuto_Reports      	    = null;
	public static Xls_Reader	mylAuto_Production      	= null;
	public static Xls_Reader    mylAuto_Common_xls          = null;
	public static boolean	 isInitialized	                = false;
	public static boolean	 isBrowserOpened	            = false;
	public static WebDriver	 driver	                        = null;
	private String	         existing_window	            = null;
	private String	         new_window	                    = null;
	private static String	 xlsFilePath	                = File.separator + "src" + File.separator + "mylearn" + File.separator + "vmware" + File.separator + "com" + File.separator + "xls"
	                                                                + File.separator;
	// value for the wait loop
	// private final int WAIT_TO_CHECK = 500;
	private static Random	 rand	                        = new Random();
	private static String	 LowerCaseAlpha	                = "abcdefghijklmnopqrstuvwxyz";
	private static String	 UpperCaseAlpha	                = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static String 	AllNumaricsNo					= "123456789";
	private static String	 baseDirectory	                = System.getProperty("user.dir");
	private static String	window_id1	= null;
	private static String	window_id2	= null;
	private String logOutSuccessMsg = "Login";
	public static org.apache.log4j.Logger APP_LOGS = Logger.getLogger(TestBase.class);
	//public static Log APP_LOGS =LogUtil.getLog(TestBase.class);
	public static FirefoxProfile fxProfile;
	@BeforeSuite(alwaysRun = true)
	public void clearScreenShotsFolder() {
		BasicConfigurator.configure();
		clearScreenShotFolder();
		APP_LOGS.setLevel(Level.INFO);
		//APP_LOGS.setLevel(Level.TRACE);
		//PropertyConfigurator.configure("log4j.properties");
		DOMConfigurator.configure("log4j1.xml");
		APP_LOGS.info("Previous screen shots has been deleted.");
		//org.apache.log4j.Logger log = Logger.getLogger(TestBase.class);
		//APP_LOGS.setLevel(Level.WARN);
		
		
	}

	// check if the suite executables has to be skipped
	@BeforeSuite(alwaysRun = true)
	public void checkSuiteSkip() throws IOException {
		initialize();
		openBrowser();// launch browser
		APP_LOGS.info("Checking Run mode of Suite");
		/*if (!TestUtil.isSuiteRunnable(suiteXls, "mylAuto_AccountCreation")) {
			APP_LOGS.info("Skipped Suite Login as the runmode is set to NO");
			throw new SkipException("Runmode of Classes Suite is set to no, so skipping the execution of Login Suite test cases");
		}*/
	}
	/**
	 * initializing the Tests
	 * 
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		if (!isInitialized) {
			// logs
//			APP_LOGS = Logger.getLogger("devpinoyLogger");
			// config
			// reading from config.properties file
			APP_LOGS.info("Loading Property files...");
			CONFIG = new Properties();
			FileInputStream ip = new FileInputStream(baseDirectory + File.separator + File.separator + "src" + File.separator + "mylearn" + File.separator + "vmware" + File.separator + "com"
			        + File.separator + "config" + File.separator + "config.properties");
			CONFIG.load(ip);
			// reading from OR.properties file
			OR = new Properties();
			ip = new FileInputStream(baseDirectory + File.separator + "src" + File.separator + "mylearn" + File.separator + "vmware" + File.separator + "com" + File.separator + "config"
			        + File.separator + "OR.properties");
			OR.load(ip);
			APP_LOGS.info("Loaded Property files successfully");
			APP_LOGS.info("Loading XLS files...");
			// reading from xls file
			/*TestUtil getExce=new TestUtil();
			getExce.*/
			//suiteXls = new Xls_Reader(baseDirectory + xlsFilePath + "Suite.xlsx");
		    
			
		    mylAuto_certifications = new Xls_Reader(baseDirectory + xlsFilePath + "Certifications.xlsx");
			mylAuto_Reports = new Xls_Reader(baseDirectory + xlsFilePath + "mylAuto_Reports.xlsx");
			mylAuto_Production = new Xls_Reader(baseDirectory + xlsFilePath + "mylAuto_Production.xlsx");
			mylAuto_Independant_xls = new Xls_Reader(baseDirectory + xlsFilePath + "mylAuto_Independant.xlsx");
			mylAuto_AccountCreation_xls = new Xls_Reader(baseDirectory + xlsFilePath + "mylAuto_AccountCreation.xlsx");
			mylAuto_Recertification_xls = new Xls_Reader(baseDirectory + xlsFilePath + "mylAuto_Recertifications.xlsx");
			mylAuto_discounts = new Xls_Reader(baseDirectory + xlsFilePath + "Discounts.xlsx");
			mylAuto_credits = new Xls_Reader(baseDirectory + xlsFilePath + "ConsultingAndTrainingCredits.xlsx");
			mylAuto_testAndSurveys = new Xls_Reader(baseDirectory + xlsFilePath + "Tests_Surveys.xlsx");
			mylAuto_Classes_xls = new Xls_Reader(baseDirectory + xlsFilePath + "mylAuto_Classes.xlsx");
			mylAuto_Common_xls = new Xls_Reader(baseDirectory + xlsFilePath + "mylAuto_Common_xls.xlsx");
			
			APP_LOGS.info("baseDirectory:"+baseDirectory+"xlsFilePath:"+xlsFilePath);
			APP_LOGS.info("Loaded XLS files successfully");
		}
		isInitialized = true;
	}

	/**
	 * launching browser
	 */
	public void openBrowser() {
		
		if (!isBrowserOpened) {
			if (CONFIG.getProperty("browserType").equalsIgnoreCase("FIREFOX"))
				//try {
					//driver=new FirefoxDriver(FileDownload1());
					driver = new FirefoxDriver();  
				/*} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			else if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver", baseDirectory + "/IEDriverServer.exe");
				/*DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability("enablePersistentHover", false);
				capabilities.setCapability("ignoreProtectedModeSettings", true);
				capabilities.setCapability("requireWindowFocus", true);
				capabilities.setCapability("javascriptEnabled","true");
				capabilities.setCapability("nativeEvents","false");*/
					driver = new InternetExplorerDriver();
			} else if (CONFIG.getProperty("browserType").equalsIgnoreCase("CHROME")) {
				System.setProperty("webdriver.chrome.driver", baseDirectory + "/chromedriver.exe");
				driver = new ChromeDriver();
				isBrowserOpened = true;
			}
			String waitTime = CONFIG.getProperty("default_TimeOut");
			driver.manage().timeouts().implicitlyWait(Long.parseLong(waitTime), TimeUnit.SECONDS);
		}
	}

	/**
	 * Close browser
	 */
	@AfterSuite(alwaysRun=true)
	public void closeBrowser() {
		driver.quit();
	}
	
	/**
	 * Close PopUp-Message
	 */
	
	public void popUpClose() {
		sleep(3000);;
		try{
		driver.switchTo().alert().accept();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Compare title Assertion
	 * 
	 * @param expectedVal
	 * @return
	 */
	public boolean compareTitle(String expectedVal) {
		try {
			AssertJUnit.assertEquals(driver.getTitle(), expectedVal);
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			APP_LOGS.info("Titles do not match");
			return false;
		}
		return true;
	}

	/**
	 * delete all the cookies and launch base URL
	 * 
	 * @param 	
	 */
	public void openLandingPage(String baseURL) {
		// APP_LOGS.info(baseURL);
		driver.manage().deleteAllCookies();
		driver.get(baseURL);
		APP_LOGS.info("Launch the URL");
		// APP_LOGS.info("Driver initialization");
		driver.manage().window().maximize();
		sleep(4000);
	}

	/**
	 * Compare numbers Assertion
	 * 
	 * @param actualValue
	 * @param expectedVal
	 * @return
	 */
	public boolean compareNumbers(int actualValue, int expectedVal) {
		try {
			AssertJUnit.assertEquals(actualValue, expectedVal);
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			APP_LOGS.info("Values do not match");
			return false;
		}
		return true;
	}

	/**
	 * Check Element presence
	 * 
	 * @param xpathKey
	 * @return
	 */
	public boolean checkElementPresence(String xpathKey) {
		int count = driver.findElements(By.xpath(OR.getProperty(xpathKey))).size();
		try {
			Assert.assertTrue(count > 0, "No element present");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			APP_LOGS.info("No Element Present");
			return false;
		}
		return true;
	}

	/**
	 * accessing object based on Xpath
	 * 
	 * @param xpathKey
	 * @return
	 */
	public WebElement getObject(String xpathKey) {
		try {
			return driver.findElement(By.xpath(OR.getProperty(xpathKey)));
		} catch (Throwable t) {
			APP_LOGS.info("Element not found");
		}
		return null;
	}

	/**
	 * Find an any type of locator(element) with the specified name
	 * 
	 * @param element
	 * @param maxSeconds
	 * @return true if WebDriver is able to find an element with the given name
	 */
	public WebElement waitOnElement(By element, int maxSeconds) {
		// APP_LOGS.info("Element:"+element+",Max seconds:"+maxSeconds);
		WebElement identifier = null;
		/*
		 * for (int i = 0; i < maxSeconds; i++) {
		 * APP_LOGS.info("I value:"+i);
		 */
		final By element1 = element;
		try {
			if(isIE()){
//				sleep(20000);
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					       .withTimeout(120, TimeUnit.SECONDS)
					       .pollingEvery(10, TimeUnit.SECONDS)
					       .ignoring(NoSuchElementException.class);
						APP_LOGS.info("Fluent wait polling for every 2 seconds.");
					   WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
					     public WebElement apply(WebDriver driver) {
//					    	 APP_LOGS.info("XPath:"+OR.getProperty(myXpath));
					       return driver.findElement(element1);
					     }
					   });
			}
			identifier = driver.findElement(element);
			// if element is found return
			// if (identifier != null)
			// break;
		} catch (Exception e) {
			identifier = null;
		}
		// }
//		APP_LOGS.info("Identifier:" + identifier);
		return identifier;
	}

	/**
	 * Waits until webdriver can find an element with the specified name
	 * attribute.
	 * 
	 * @param name
	 * @param maxMilliseconds
	 * @return true if WebDriver is able to find an element with the given name
	 */
	public WebElement waitOnName(String name, int maxSeconds) {
		return waitOnElement(By.name(OR.getProperty(name)), maxSeconds);
	}

	public WebElement waitOnName(String name) {
		return waitOnElement(By.name(OR.getProperty(name)), Integer.parseInt(CONFIG.getProperty("default_TimeOut")));
	}

	/**
	 * Waits until webdriver can find the specified by id
	 * 
	 * @param id
	 * @param maxMilliseconds
	 * @return true if WebDriver is able to find an element with the given id
	 */
	public WebElement waitOnId(String id, int maxSeconds) {
		return waitOnElement(By.id(OR.getProperty(id)), maxSeconds);
	}

	public WebElement waitOnId(String id) {
		return waitOnElement(By.id(OR.getProperty(id)), Integer.parseInt(CONFIG.getProperty("default_TimeOut")));
	}

	/**
	 * Waits until webdriver can find the element specified by xpath.
	 * 
	 * @param path
	 * @param maxMilliseconds
	 * @return true if WebDriver is able to find an element with the given id
	 */
	public WebElement waitOnXPath(String path, int maxSeconds) {
		return waitOnElement(By.xpath(OR.getProperty(path)), maxSeconds);
	}

	public WebElement waitOnXPath(String path) {
		// APP_LOGS.info("Xpath:"+OR.getProperty(path));
		return waitOnElement(By.xpath(OR.getProperty(path)), Integer.parseInt(CONFIG.getProperty("default_TimeOut")));
	}

	/**
	 * Waits until webdriver can find the element specified by CSS.
	 * 
	 * @param path
	 * @param maxMilliseconds
	 * @return true if WebDriver is able to find an element with the given id
	 */
	public WebElement waitOnCSS(String css, int maxSeconds) {
		return waitOnElement(By.cssSelector(OR.getProperty(css)), maxSeconds);
	}

	public WebElement waitOnCSS(String css) {
		return waitOnElement(By.cssSelector(OR.getProperty(css)), Integer.parseInt(CONFIG.getProperty("default_TimeOut")));
	}

	/**
	 * Waits until webdriver can find the element specified by linkText.
	 * 
	 * @param path
	 * @param maxMilliseconds
	 * @return true if WebDriver is able to find an element with the given
	 *         linkText
	 */
	public WebElement waitOnText(String linkText, int maxSeconds) {
		return waitOnElement(By.linkText(OR.getProperty(linkText)), maxSeconds);
	}

	public WebElement waitOnText(String linkText) {
		return waitOnElement(By.linkText(OR.getProperty(linkText)), Integer.parseInt(CONFIG.getProperty("default_TimeOut")));
	}

	/**
	 * @param xPath
	 *            Give count of matching(array) objects
	 * 
	 */
	public int getCount(String xPath) {
		int count = 0;
		count = driver.findElements(By.xpath(OR.getProperty(xPath))).size();
		return count;
	}

	/**
	 * Webdriver can find the edit box with specified by locator and passing
	 * text into it
	 * 
	 * @param locator
	 * @param strData
	 * @param message
	 */
	public void clearValue(String locator, String message) {
		APP_LOGS.info("Clear the value of Object:" + locator);
		WebElement identifier = waitOnXPath(locator);
		Assert.assertNotNull(identifier, "\n" + message + " not found.");
		identifier.clear();
	}

	/**
	 * Webdriver can find the edit box with specified by locator and passing
	 * text into it
	 * 
	 * @param locator
	 * @param strData
	 * @param message
	 */
	public void input_TextField(String locator, String strData, String message) {
		if(isIE()){
			sleep(4000);
		}
		//APP_LOGS.info(" Validating - " + message + " : " + strData);
		WebElement identifier = waitOnXPath(locator);
		identifier.clear();
		Assert.assertNotNull(identifier, "\n" + message + " not found.");
		identifier.sendKeys(strData);
		
	}

	/**
	 * Webdriver can find the object with specified by locator and click
	 * 
	 * @param locator
	 * @param message
	 */
	public void objectClickByXpath(String xPath, int maxSeconds, String msg) {
		if(isIE()){
			sleep(8000);
		}
		WebElement identifier = null;
		identifier = driver.findElement(By.xpath(xPath));
		Assert.assertNotNull(identifier, "\n" + msg + " not found.");
		identifier.click();
	}

	/**
	 * Webdriver can find the object with specified by locator and click
	 * 
	 * @param locator
	 * @param message
	 */
	public void objectClick(String locator, String message) {
		// APP_LOGS.info("Xpath:"+locator);
		if(isIE()){
			sleep(5000);
		}
		//WebDriverWait weWait=new WebDriverWait(driver, 60);
		//weWait.until(ExpectedConditions.elementToBeClickable(By.id("myDynamicElement")));
		//return waitOnElement(By.xpath(OR.getProperty(path)), Integer.parseInt(CONFIG.getProperty("default_TimeOut")));
		WebElement button = waitOnXPath(locator);
		Assert.assertNotNull(button, message);
		button.click();
	}
	
	/**
	 * Webdriver can find the object with specified by locator and click using java script
	 * 
	 * @param locator
	 * @param message
	 */
	public void useJSObjectClick(String locator, String message) {
		// APP_LOGS.info("Xpath:"+locator);
		WebElement button = waitOnXPath(locator);
		Assert.assertNotNull(button, message);
		JSUtil.JSClickByElement(driver, button);
	}

	// capturing screenshots
	public static void captureScreenshot(String filename) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(baseDirectory + File.separator + "screenshots" + File.separator + filename + ".png"));
	}

	/**
	 * Clears Screen shot folder before execution of a test suite
	 */
	public void clearScreenShotFolder() {
		// Deletes all files and subdirectories under dir.
		// Returns true if all deletions were successful.
		// If a deletion fails, the method stops attempting to delete and
		// returns false.
		// String workingDir = baseDirectory;
		String screenShotFolder = baseDirectory + File.separator + "screenshots";
		APP_LOGS.info("Folder to be cleared is " + screenShotFolder);
		File dir = new File(screenShotFolder);
		if (dir.exists()) {
			try {
				delete(dir);
			} catch (IOException iex) {
				APP_LOGS.info("Error seen on deleting a directory " + iex.getMessage());
				iex.printStackTrace();
			}
		} else
			APP_LOGS.info("Screen Shot director not present in test-output, no action needed");
	}

	/*****
	 * sleep: Replace the try...catch block and Thread.sleep with this method
	 * for a cleaner code
	 * 
	 * @param timeout
	 */
	public void sleep(long timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param list
	 *            the element for selection list
	 * @param selection
	 *            the text for the element to be selected from the selection
	 *            list
	 * @return true if the selection is found
	 */
	public boolean selectDropDownList(WebElement list, String selection) {
		List<WebElement> componentList = list.findElements(By.tagName("option"));
		for (WebElement component : componentList) {
//			APP_LOGS.info(selection+",Current Item:"+component.getText());
			if (component.getText().trim().equalsIgnoreCase(selection.trim())) {
//				APP_LOGS.info(selection+",Item selected:"+component.getText());
				component.click();
				sleep(2000);
				
				return true;
				
			}
		}
		return false;
	}

	/**
	 * check the current test is running on IE or non-IE browsers
	 * 
	 * @return return true if it's a IE browser test, else it will return false
	 */
	public static boolean isIE() {
		return CONFIG.getProperty("browserType").toUpperCase().contains("IE");
	}

	/**
	 * check the current test is running on IE8 or non-IE browsers
	 * 
	 * @return return true if it's a IE browser test, else it will return false
	 */
	public static boolean isIE8() {
		return CONFIG.getProperty("browserType").toUpperCase().contains("IE8");
	}

	/**
	 * check the current test is running on IE9 or non-IE browsers
	 * 
	 * @return return true if it's a IE browser test, else it will return false
	 */
	public static boolean isIE9() {
		return CONFIG.getProperty("browserType").toUpperCase().contains("IE9");
	}

	/**
	 * check the current test is running on IE or non-IE browsers
	 * 
	 * @return return true if it's a IE browser test, else it will return false
	 */
	public static boolean isFF() {
		return CONFIG.getProperty("browserType").toUpperCase().contains("FIREFOX");
	}

	/**
	 * check the current test is running on IE or non-IE browsers
	 * 
	 * @return return true if it's a IE browser test, else it will return false
	 */
	public static boolean isChrome() {
		return CONFIG.getProperty("browserType").toUpperCase().contains("CHROME");
	}

	/*******
	 * useJSClickById: Method to use Javascript click for IE and normal selenium
	 * click for Firefox/Chrome for an element with Id
	 * 
	 * @param menuItem_id
	 * @param menuItem
	 */
	public void useJSClickById(String menuItem_id, WebElement menuItem) {
		if (isIE()) {
			JSUtil.JSClickById(driver, OR.getProperty(menuItem_id));
			sleep(1000);
		} else {
			menuItem.click();
		}
	}

	/***
	 * useJSClickByElement: Method to use Javascript click for IE and normal
	 * selenium click for Firefox/Chrome for an element with xpath
	 * 
	 * @param menuItem
	 */
	public void useJSClickByElement(WebElement menuItem) {
		if (isIE()) {
			JSUtil.JSClickByElement(driver, menuItem);
			sleep(1000);
		} else {
			menuItem.click();
		}
	}

	/**
	 * Verifies the landing page with given URL and locator
	 * 
	 * @param locator
	 * @param URL
	 * @param message
	 */
	public void verifyLandingUrl(String locator, String URL, String message) {
		waitOnXPath(OR.getProperty(locator), Integer.parseInt(CONFIG.getProperty("default_TimeOut")));
		Assert.assertTrue(driver.getCurrentUrl().contains(URL), "Target Page not found: " + message);
	}

	/**
	 * Deletes a directory. If directory has files, it will delete all files and
	 * then delete the drectory for empty directories it deletes directly
	 * 
	 * @param file
	 * @throws IOException
	 */
	private static void delete(File file) throws IOException {
//		APP_LOGS.info("File Existis " + file.exists() + " file Directory " + file.isDirectory());
		if (file.exists() && file.isDirectory()) {
			// directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
				APP_LOGS.info("Directory is deleted : " + file.getAbsolutePath());
			} else {
				// list all the directory contents
				String files[] = file.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);
					// recursive delete
					fileDelete.delete();
					// delete(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					APP_LOGS.info("Directory is deleted : " + file.getAbsolutePath());
				}
			}
		}
	}

	/**
	 * Hover on Primary Link give Secondary links list
	 * 
	 * @param driver
	 * @param primaryXPath
	 * @param secondaryXpath
	 * @return secondaryLinks
	 */
	protected List<String> hoverAndGetSecondaryLinks(WebDriver driver, String primaryXPath, String secondaryXpath) {
		Actions builder = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String javaScriptForHover = javaScriptProvider();
		WebElement menu = waitOnXPath(primaryXPath);
		Assert.assertNotNull(menu, "Element not found");
		if (isIE9()) {
			js.executeScript(javaScriptForHover, menu);
		} else {
			builder.clickAndHold(menu).build().perform();
		}
		WebElement menuItem = waitOnXPath(secondaryXpath);
		Assert.assertNotNull(menuItem, "MenuItem element not found");
		List<WebElement> secondaryMenuItems = menuItem.findElements(By.xpath(secondaryXpath));
		List<String> secondaryLinks = new ArrayList<String>();
		for (WebElement secondNav : secondaryMenuItems) {
			secondaryLinks.add(secondNav.getText());
		}
		return secondaryLinks;
	}

	/**
	 * Using Java Script mouse over on an element
	 * 
	 * @return
	 */
	private String javaScriptProvider() {
		return "if( document.createEvent) {" + "var evObj = document.createEvent('MouseEvents');" + "evObj.initEvent( 'mouseover', true, false );" + "arguments[0].dispatchEvent(evObj);"
		        + "} else if( document.createEventObject ) {" + "arguments[0].fireEvent('onmouseover');}";
	}

	/**
	 * Return the url with given xpath of an anchor tag
	 * 
	 * @param driver
	 * @param xPathExpression
	 * @return
	 */
	public String getHrefForLink(WebDriver driver, String xPathExpression) {
		String href = null;
		// Check if the xpath Express has anchor tag /a
		if (xPathExpression != null && xPathExpression.contains("/a")) {
			href = driver.findElement(By.xpath(xPathExpression)).getAttribute("href");
		}
		return href;
	}

	/**
	 * Return the text of given xpath
	 * 
	 * @param driver
	 * @param xPath
	 * @return
	 */
	public String getTextForAnElement(WebDriver driver, String xPath) {
		// APP_LOGS.info("Xpath:"+xPath);
		if(isIE()){
			sleep(5000);
		}
		WebElement pageElement = waitOnXPath(xPath);
		sleep(1000);
		Assert.assertNotNull(pageElement, "Object not to be null");
		String elementText = null;
		if (pageElement != null) {
			elementText = pageElement.getText();
		}
		if (elementText != null && !elementText.isEmpty()) {
			// Replace any new line characters
			elementText = elementText.replaceAll("\n", " ");
		}
		return elementText;
	}

	/**
	 * Return the urls as a list with given sublinksXpath
	 * 
	 * @param driver
	 * @param subNavLinkXPath
	 * @return
	 */
	public List<String> getHrefFromElementsWithSameXpath(WebDriver driver, String subNavLinkXPath) {
		WebElement links = waitOnXPath(subNavLinkXPath);
		List<WebElement> subNavLinks = links.findElements(By.xpath(subNavLinkXPath));
		List<String> subNavLinksHref = new ArrayList<String>();
		String linkUrl = null;
		for (WebElement subNavLink : subNavLinks) {
			linkUrl = subNavLink.getAttribute("href");
			subNavLinksHref.add(linkUrl);
		}
		return subNavLinksHref;
	}

	/**
	 * Return the text as a list of given sublinkXpath
	 * 
	 * @param driver
	 * @param subNavLinkXPath
	 * @return
	 */
	public List<String> getTextFromElementWithSameXpath(WebDriver driver, String subNavLinkXPath) {
//		WebElement linkTexts = waitOnXPath(subNavLinkXPath);
		List<WebElement> subNavLinks = driver.findElements(By.xpath(subNavLinkXPath));
		List<String> subNavLinksText = new ArrayList<String>();
		String elementTxt = null;
		for (WebElement subNavLink : subNavLinks) {
			// Replace any new line characters with spaces
			elementTxt = subNavLink.getText();
			if (elementTxt != null && !elementTxt.isEmpty()) {
				elementTxt = elementTxt.replace("\n", " ");
				subNavLinksText.add(elementTxt);
			}
		}
		return subNavLinksText;
	}

	/**
	 * verify target page
	 * 
	 * @param driver
	 * @param clickedElementXpath
	 * @param targetPageIdentifierXpath
	 * @return
	 */
	public String clickAndWaitElement(WebDriver driver, String clickedElementXpath, String targetPageIdentifierXpath) {
		// Wait for the Element to load
		WebElement clickElement = null;
		// String pageUrl = null;
		int count = 0;
		while (count < 20) {
			try {
				// Wait for element to laod and click it
				clickElement = waitOnXPath(clickedElementXpath);
				clickElement.click();
				APP_LOGS.info("Clicked element " + clickElement.getText());
			} catch (StaleElementReferenceException e) {
				APP_LOGS.info("Trying to recover from a stale element :" + e.getMessage());
				count = count + 1;
			}
			count = count + 20;
		}
		// Clicking on the element should take to the new page
		// Wait for the unique identifier to load
		// Added to reuse this method when target identifier is not specified
		if (targetPageIdentifierXpath != null) {
			waitOnXPath(targetPageIdentifierXpath);
		}
		return driver.getCurrentUrl();
	}

	/*****
	 * verifyUrlWindowHandles: Methods to handle multiple windows and verify url
	 */
	public void verifyUrlWindowHandles(String id, String url, String msg) {
		Set<String> ids = driver.getWindowHandles();
		existing_window = (String) ids.toArray()[0];
		new_window = (String) ids.toArray()[1];
		driver.switchTo().window(new_window);
		if (isIE())
			sleep(2000);
		verifyLandingUrl(id, url, msg);
		driver.close();
		driver.switchTo().window(existing_window);
	}

	/*****
	 * switchToFrame: switch to the specified frame
	 * 
	 * @param xPath
	 */
	public void switchToFrame(String xPath) {
		sleep(2000);
		driver.switchTo().defaultContent();
		WebElement frame = driver.findElement(By.xpath(OR.getProperty(xPath)));
		Assert.assertNotNull(frame, "Frame not found");
		driver.switchTo().frame(frame);
		sleep(3000);
	}

	/*****
	 * selectDropDown: select specified option from a list box
	 * 
	 * @param xPath
	 * @param option
	 * @param msg
	 */
	public void selectDropDown(String xpath, String option, String msg) {
		if(isIE()){
			sleep(8000);
		}
		WebElement selectBox = waitOnXPath(xpath);
		Assert.assertNotNull(selectBox, "List box: " + msg + " Not found.");
		selectDropDownList(selectBox, option);
		
	}

	/*****
	 * loginMethod: verifies login functionality
	 * 
	 * @param Username
	 * @param Password
	 */
	public void loginMethod(String Username, String Password) {
		//popUpClose();
		//sleep(15000);
		
		System.out.println("01"+Username +Password);
		input_TextField("usernameKey", Username, "Login pexpiredAddage:Username field ");
		APP_LOGS.info("Enter the UserName as "+Username);
		input_TextField("passwordKey", Password, "Login Page: Password field");
		APP_LOGS.info("Enter the Password as "+Password);
		objectClick("loginbtnKey", "Login page:Login button not found.");
		APP_LOGS.info("Successfully Click the Login Button");
	}

	/*****
	 * loginMethod: verifies login functionality,if not login then regenerate pswd
	 * 
	 * @param Username
	 * @param Password
	 */
	public void loginMethodWithTrouble(String url,String Username, String Password,String NextElement) {
		//Boolean trouble=false;
		String paymentAccLink ="No Element";
		APP_LOGS.info("Username:"+Username+",Password"+Password);
		input_TextField("usernameKey", Username, "Login page:Username field ");
		input_TextField("passwordKey", Password, "Login Page: Password field");
		objectClick("loginbtnKey", "Login page:Login button not found.");
		try{
		 paymentAccLink = driver.findElement(By.linkText(OR.getProperty(NextElement))).getText();
		}
		 catch(Exception e){
		sleep(3000);
		//if((getTextForAnElement(driver, "troubleSigningInxPath").equals("Trouble Signing In"))){
			if(paymentAccLink.equals("No Element")){
			objectClick("troubleSigningInxPath", "Trouble Signing: Trouble Signing In");
			input_TextField("troubleSigningUsernameFieldxPath", Username, "Trouble UserName:Username field ");
			objectClick("troubleSigningContiuneButonxPath", "Trouble Signing: Trouble Continue");
			Assert.assertTrue(getTextForAnElement(driver, "troubleSingingConfirmationMsgxPath").contains("An e-mail with a link to reset your password has been sent to you at"), "Reset Link is not sent your mail id");
			openEmailId();
			objectClick("troubleSingingResetYourPswedLinkxPath", "Trouble Signing: Reset Your Password Link");
			WebElement userName = driver.findElement(By.xpath(OR.getProperty("troubleSingingUserNameTextxPath")));
			Assert.assertNotNull(userName, "userName Text not found");
			input_TextField("troubleSingingPasswordFieldxPath", Password+"1!", "Trouble Page: Password field");
			input_TextField("troubleSingingConfPasswordFieldxPath", Password+"1!", "Trouble Page: ConfirmPassword field");
			APP_LOGS.info("Username Trouble:"+Username+",Password Trouble"+Password+"1!");
			objectClick("troubleSingingConfPswdContiueButnxPath", "Trouble Signing: Trouble Continue buton at Resestn Pswd");
			driver.navigate().to(CONFIG.getProperty(url));
			sleep(4000);
			loginMethod(Username, Password+"1!");
			//trouble=true;
			}
		}
		
		//trouble=false;
	}
	/*****
	 * navaigationToRosters: navigates to rosters page
	 */
	public void navaigationToRosters() {
		APP_LOGS.info("Navigation to new class link");
		switchToFrame("leftmenu_xpath_Navigate2Admin");
		// APP_LOGS.info("I m in left frame");
		objectClick("manage_xpath_Navigate2Admin", "Admin Page: Manage classes link");
		objectClick("classLink_xpath_RostersAdd", "Admin Page: Class link");
		objectClick("manage_xpath_Navigate2Admin", "Admin Page: Manage classes link");
		driver.switchTo().defaultContent();
	}

	/*****
	 * searchClassForRosters: Search class for rosters
	 */
	public void searchClassForRosters(String classId) {
		switchToFrame("mainContent_xpath_Navigate2Admin");
		input_TextField("classId_xpath_RostersAdd", classId, "Roster Convert Page: Class Id field not found.");
		sleep(2000);
		objectClick("findButton_xpath_RostersAdd", "Roster Convert Page: Find button not found.");
		sleep(3000);
		objectClick("rosterIconLink_xpath_RostersAdd", "Roster Convert Page: Roster icon link not found.");
		sleep(2000);
	}

	/*****
	 * clickOnManganeLink: Click on manage link
	 */
	public void clickOnManageLink() {
		sleep(3000);
		driver.switchTo().defaultContent();
		switchToFrame("leftmenu_xpath_Navigate2Admin");
		objectClick("manage_xpath_Navigate2Admin", "Admin Page: Manage classes link");
		driver.switchTo().defaultContent();
	}

	/*****
	 * generateAlphaString: genarate Alphabatic string with specified no of
	 * length
	 * 
	 * @param length
	 */
	public static String generateAlphaString(int length) {
		return generateString(LowerCaseAlpha + UpperCaseAlpha, length);
	}

	public static String generateString(String characters, int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rand.nextInt(characters.length()));
		}
		return new String(text);
	}
	
	public static String generateNumarics(int length) {
		return generateString(AllNumaricsNo, length);
	}
	
	public static String generateLowerCaseAlphabits(int length) {
		return generateString(LowerCaseAlpha, length);
	}

	public void openEmailId() {
		driver.manage().deleteAllCookies();
		openLandingPage(CONFIG.getProperty("emailURL"));// Load  email URL
		sleep(10000);
		driver.navigate().refresh();
		sleep(8000);
		driver.navigate().refresh();
		sleep(2000);
		int count = getCount("emailConformationMessageLink_xpath_RostersInvite");
		objectClickByXpath("(" + OR.getProperty("emailConformationMessageLink_xpath_RostersInvite") + ")[" + count + "]", 1000, "Create Account Page: Email conformation message ");// Email
		sleep(2000);
	}
	
	public String getActivationCode(){
		
		String invitationMsg = getTextForAnElement(driver, "activationCode_xPath");// Get email success message
		APP_LOGS.info("Create Account Page: Email conversation message: " + invitationMsg);
		String activationCode = invitationMsg.split("Activation ID:")[1];
		return activationCode.trim();
	}
	
	public String getActivationCodeFromEmail(){
		
		String invitationMsg = getTextForAnElement(driver, "userVerificationCodexPath");// Get email success message
		APP_LOGS.info("Create Account Page: Email conversation message: " + invitationMsg);
		String activationCode = invitationMsg.split("Activation ID:")[1];
		return activationCode.trim();
	}
	
	
	public String activateRegisteredUser(String openURL, String activeCode,String strCaptcha){
		//launch activation URL
		driver.get(openURL);
		sleep(5000);
		// Enter Activation code
		input_TextField("id_xpath_AccountCreation", activeCode + "", "Account creation Page: Activation Code");
		// Enter Captcha
		input_TextField("cCode_xpath_AccountCreation", strCaptcha, "Account creation Page: Capcha");
		// Click on Continue button
		objectClick("continueBtn_xpath_ClassRoomClass", "Account Activation Page: Continue button ");
		
		sleep(2000);
		String actActivationSucessMsg = getTextForAnElement(driver, "accountActivationSucessMsg");
		return actActivationSucessMsg;
	}
	/**
	 * getAttributeValueOfElement: this will return a value of given attribute of a locator
	 * @param locator
	 * @param attribute
	 * @param message
	 * @return attribute value
	 */
	public String getAttributeValueOfElement(String locator, String attribute, String message){
		APP_LOGS.info(" Validating - " + message + " : " + attribute);
		WebElement identifier = waitOnXPath(locator);
		Assert.assertNotNull(identifier, "\n" + message + " not found.");
		return identifier.getAttribute(attribute);
	}
	/**
	 * navigateToTestsSurveys: This method is help us navigate to Tests and Surveys Page
	 */
	public void navigateToTestsSurveys(){
		switchToFrame("leftmenu_xpath_Navigate2Admin");
		objectClick("manage_xpath_Navigate2Admin", "Admin Page: Manage classes link");
		objectClick("manageTestlinkxPath", "Tests, Surveys and Polls link");
		objectClick("manage_xpath_Navigate2Admin", "Admin Page: Manage classes link");
		
		switchToFrame("mainContent_xpath_Navigate2Admin");// Navigate to content frame
	}
	
	/**
	 * navigateToDiscount: This method is help us navigate to discount Page
	 */
	public void navigateToDiscount(){
		switchToFrame("leftmenu_xpath_Navigate2Admin");
		objectClick("financeLinkxPath", "Admin Page: Finance link");
		objectClick("financeLinkxPath", "Admin Page: Finance link");
		
		switchToFrame("mainContent_xpath_Navigate2Admin");// Navigate to content frame
		objectClick("manageDiscountLinkxPath", "Manage discounts link");
	}
	
	/**
	 * navigateToCertificates: This will help to navigate to certificates page
	 */
	public void navigateToCertificates(){
		
		switchToFrame("leftmenu_xpath_Navigate2Admin");
		objectClick("configureLink", "Admin Page: Configure link");
		objectClick("configureLink", "Admin Page: Configure link");
		
		switchToFrame("mainContent_xpath_Navigate2Admin");// Navigate to content frame
		WebElement certLinkText = driver.findElement(By.linkText("certificationsAdminLinkText"));
		Assert.assertNotNull(certLinkText, "Certifications link text not found");
		certLinkText.click();
	}
	
	/**
	 * switchNewWindow: Switch to new opened window
	 */
	public void switchNewWindow(){
		sleep(2000);
		if(!isChrome()){
			sleep(80000);
		}
		// Navigating to new window
		Set<String> windows = driver.getWindowHandles();
		
		window_id1 = (String) windows.toArray()[0];
		window_id2 = (String) windows.toArray()[1];

		APP_LOGS.info("window_id1:"+window_id1);
		APP_LOGS.info("window_id2:"+window_id2);
		sleep(2000);
		driver.switchTo().window(window_id2);
		driver.manage().window().maximize();
	}
	
	public void navigateToReCertification(){
		sleep(2000);
		driver.switchTo().defaultContent();
		// Navigating to Left menu
		switchToFrame("leftmenu_xpath_Navigate2Admin");
		
		objectClick("configureLink", "Configure link not found");
		sleep(2000);
		objectClick("configureLink", "Configure link not found");
		
		driver.switchTo().defaultContent();
		// Switch to content frame
		switchToFrame("mainContent_xpath_Navigate2Admin");
		
	}
	
	public void enduserLogout(){

		APP_LOGS.info(" Validating logoutEndUserxPath");
//		WebElement logout = waitOnXPath("logoutEndUserxPath");
		WebElement logout = waitOnText("logoutLink_publicportal");
		Assert.assertNotNull(logout, "Register class page: Logout link not found");
		if(isIE()){
			APP_LOGS.info(" Validating before");
			//Scroll down
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0,500)");
			/*Actions action =new Actions(driver);
			action.moveToElement(logout).build().perform();
			action.click(logout);*/
			JSUtil.JSClickByElement(driver, logout);
			APP_LOGS.info(" Validating after");
//			new Actions(driver).doubleClick(logout);
		}else{
		logout.click();
		}
		WebElement logoutContinue = waitOnXPath("logoutContinueBtnxPath");
		Assert.assertNotNull(logoutContinue, "Register class page: Logout Continue button not found");
		
		logoutContinue.click();
		/*driver.manage().deleteAllCookies();
		driver.navigate().refresh();*/
		APP_LOGS.info(" Validating refresh");
	}
	
	public void adminLogout(){
		switchToFrame("leftmenu_xpath_Navigate2Admin");
//		objectClick("logout_xpath_AdminPage", "Logout page: Logout button not found.");
		WebElement button = waitOnXPath("logout_xpath_AdminPage");
		Assert.assertNotNull(button, "Logout page: Logout button not found.");
		Assert.assertTrue(button != null, "Logout page: Logout button not found.");
		// Click on logout button
		if(isIE()){
			JSUtil.JSClickByElement(driver, button);
		}else{
			button.click();
		}
		driver.switchTo().defaultContent();
		Assert.assertEquals(logOutSuccessMsg, getTextForAnElement(driver, "adminLogOutSuccessMsgxPath"));
		APP_LOGS.info("Click the LogOut Button");
	}
	
	/**
	 * adminNavigateToCertificatesPage
	 * 
	 * Login as Admin and Search for a paln ID
	 * @param adminUserId
	 * @param password
	 * @param planId
	 */
	public void adminNavigateToCertificatesPage(String adminUserId,String password,String planId){
		//Navigate to Admin login page
		openLandingPage(CONFIG.getProperty("testSiteName"));
		
		//popUp Handle
		
		
		//Admin login
		loginMethod(adminUserId, password);
		
		//Navigate to certification
		navigateToCertification();
		//Click on certification tab link
		objectClick("certCertificationTabLinkxPath", "Certification tab link not found");
	
		//Enter plan Id
		input_TextField("certPalnIdxPath", planId, "Paln id field not found");
		
		//Click on Search button
		objectClick("certSearchBtnxPath", "Search button not found");
		
		//Click on training plan properties link
		objectClick("certEditTrainingPlanPropertiesxPath", "training plan propertiesPage link not found" );
		sleep(2000);
	}
	
	/**
	 * navigateToCertification
	 * Navigating to certification page
	 */
	public void navigateToCertification(){
	// "Admin home Page: Administration Portal link not found.");
		WebElement linkText = waitOnText("administativePortal_Link_Navigate2Admin");
		Assert.assertNotNull(linkText, "Login Page: Administration Portal link not found.");
		linkText.click();// Click on Administration Portal link text
		// Navigating to Left menu
		switchToFrame("leftmenu_xpath_Navigate2Admin");
		
		objectClick("manage_xpath_Navigate2Admin", "Manage link not found");
		sleep(2000);
		objectClick("certTraningPlansLinkxPath", "Traning plan link not found");
		
		objectClick("manage_xpath_Navigate2Admin", "Manage link not found");
		
		driver.switchTo().defaultContent();
		// Switch to content frame
		switchToFrame("mainContent_xpath_Navigate2Admin");
	}
	
	/*****
	 * deleteFilesFromDownloadFolder: delete all the files in donwload folder
	 *
	 */
	public void deleteFilesFromDownloadFolder() throws IOException{
		String dirname = CONFIG.getProperty("downloadFolder");
		File folder = new File(dirname);
	    File[] listOfFiles = folder.listFiles();
	    for(int i=0;i<listOfFiles.length;i++){
//	    	System.out.println("FileName:"+i+listOfFiles[i].getName());
	    	listOfFiles[i].delete();
	    }
	}
	/*****
	 * Return the String value after split as per your given exprexssion
	 * @return 
	 *
	 */
	public String[] splitWithRegArry(String val,String reg) {
		String values[]= val.split(reg);
		return values;
	}
	
	/*****
	 * readEmailData: read the email data return as a string
	 *@return email data
	 */
	public String readDataFromEmailFile() throws IOException{
		String dirname = CONFIG.getProperty("downloadFolder");
		File folder = new File(dirname);
	    File[] listOfFiles = folder.listFiles();
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dirname+"/"+listOfFiles[0].getName())));
			while ((line = br.readLine()) != null) {
//				//System.out.println("Line data: "+line);
				System.out.println("Line data: "+line);
				sb.append(line);
		    }
		    br.close();
//		    //System.out.println("file"+sb.toString());
		    System.out.println("file"+sb.toString());
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		return sb.toString();
	}
	
	public void readEmailData(String uname,String pwd) throws Exception{
		sleep(2000);
		loginMethod(uname, pwd);
		
		sleep(2000);
		WebElement linkText = waitOnText("administativePortal_Link_Navigate2Admin");
		Assert.assertNotNull(linkText, "Login Page: Administration Portal link not found.");
		linkText.click();//Click on Administration Portal linktext
		
		sleep(2000);
		driver.switchTo().defaultContent();
		switchToFrame("mainContent_xpath_Navigate2Admin");
		sleep(2000);
//		WebElement emailLink = waitOnText("readEmailLink");
		WebElement emailLink = waitOnXPath("readEmail_xPath");
		Assert.assertNotNull(emailLink, "Admin Page: Read Email link not found.");
		emailLink.click();//Click on Read Email linktext
		
		sleep(2000);
		driver.switchTo().defaultContent();
		switchToFrame("mainContent_xpath_Navigate2Admin");
		sleep(50000);
		objectClick("mailRefreshBtnxPath", "Refresh image link not found");
		sleep(10000);
		objectClick("mailRefreshBtnxPath", "Refresh image link not found");
		sleep(2000);
		objectClick("emailSubjectLink", "Download email link not found");
		sleep(8000);
		
		if(isFF()){
			System.out.println("We are under Robot class");
			Robot  robot = new Robot();
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_S);
			System.out.println("We are under Robot class!");
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_S);
			System.out.println("We are under Robot class2");
			sleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			System.out.println("We are under Robot class3");
		}
		if(isIE()){
			System.out.println("We are under Robot class");
			Robot  robot = new Robot();
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_S);
			System.out.println("We are under Robot class!");
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_S);
		}
		sleep(5000);
		driver.switchTo().defaultContent();
		switchToFrame("leftmenu_xpath_Navigate2Admin");
		objectClick("logout_xpath_AdminPage", "Admin Logout link not found");
		
	}
	
	public String  accountCreation(String country,String phone,String address1,String address2,String city,String state,String zip,String password){
		openLandingPage(CONFIG.getProperty("testSiteName"));// Load specified URL
		String firstName = generateLowerCaseAlphabits(6); // Generating random String of 5 characters
		String lastName = generateLowerCaseAlphabits(5); // Generating random String of 5 characters
		String domainName = generateLowerCaseAlphabits(6); // Generating random String of 6 characters
		// Enter first name
		input_TextField("fname_xpath_AccountCreation", firstName + "", "Account creation Page: First name");
		// Enter last name
		input_TextField("lname_xpath_AccountCreation", lastName + "", "Account creation Page: Last name");
		// Enter email-id
		input_TextField("email_xpath_AccountCreation", firstName+lastName+"@"+domainName + ".com", "Account creation Page: Email");
		// Enter Company name
		input_TextField("companyName_xpath_AccountCreation", domainName, "Account creation Page: Company name");
		sleep(2000);
		// Select Country
		selectDropDown("country_xpath_AccountCreation", country, "Account creation Page: Country ");
		// Click on Continue button
		objectClick("continueBtn_xpath_ClassRoomClass", "Account creation Page: Continue button ");
		sleep(2000);
		// Enter Phone
		input_TextField("phNum_xpath_AccountCreation", phone + "", "Account creation Page: Phone no");
		// Enter Company Street
		input_TextField("street_xpath_AccountCreation", address1 + "", "Account creation Page: Street");
		// Enter Company Street
		input_TextField("street_xpath_AccountCreation2", address2 + "", "Account creation Page: Street1");
		// Enter Company city
		input_TextField("city_xpath_AccountCreation", city + "", "Account creation Page: City");
		// Enter Company state
		input_TextField("state_xpath_AccountCreation", state + "", "Account creation Page: State");
		// Enter Zip
		input_TextField("zip_xpath_AccountCreation", zip + "", "Account creation Page: ZIP");
		
		// Enter Password
		input_TextField("password_xpath_AccountCreation", password + "", "Account creation Page: Password");
		// Enter Confirm Password
		input_TextField("confirmPwd_xpath_AccountCreation", password + "", "Account creation Page: Confirm password");
		
		return lastName+","+domainName;
	}
	
	public void activateContactAccount(String username,String password, String currentURL,String captcha,String accountActSucessMsg) throws Exception{
		driver.get(CONFIG.getProperty("testSiteName"));
		sleep(2000);
		deleteFilesFromDownloadFolder();
		readEmailData(username, password);
		String eMailData = readDataFromEmailFile();
		sleep(5000);
		String verificationcCode=eMailData.split("<p>Activation ID:")[1].trim().substring(0, 13);
		APP_LOGS.debug("Account Activation code: " + verificationcCode);
		// openLandingPage(currentURL);
		driver.get(currentURL);
		sleep(5000);

		// Enter Activation code
		input_TextField("id_xpath_AccountCreation", verificationcCode + "",
				"Account creation Page: Activation Code");

		// Enter Captcha
		input_TextField("cCode_xpath_AccountCreation", captcha,
				"Account creation Page: Capcha");

		// Click on Continue button
		objectClick("continueBtn_xpath_ClassRoomClass",
				"Account Activation Page: Continue button ");

		String actActivationSucessMsg = getTextForAnElement(driver,
				"loginHeaderTextxPath");

		APP_LOGS.debug("Account Activation Page :: activation Success message "
				+ actActivationSucessMsg
				+ "::: accountActSucessMsg "
				+ accountActSucessMsg);
		Assert.assertTrue(actActivationSucessMsg
				.equalsIgnoreCase(accountActSucessMsg),
				"Login Page :: Login Header message not found :: ");
	}
	public static FirefoxProfile FileDownload1() throws InterruptedException {
		// TODO Auto-generated constructor stub
		fxProfile = new FirefoxProfile();
		fxProfile.setPreference("browser.download.folderList",2);
		fxProfile.setPreference("browser.download.manager.showWhenStarting",false);
		fxProfile.setPreference("browser.download.dir","E:\\Download");
		//http://hul.harvard.edu/ois/systems/wax/wax-public-help/mimetypes.htm (MIMI types)
		//fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/x-msexcel;application/excel;application/x-excel; application/vnd.ms-excel;application/msexcel;application/x-ms-excel;application/x-dos_ms_excel;application/xls;application/x-xls;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;text/x-vcard;application/pdf;application/zip;application/x-7z-compressed;application/x-shockwave-flash;application/x-director;application/vnd.aristanetworks.swi;video/x-msvideo;application/octet-stream;text/css;video/x-flv;text/html;text/vnd.sun.j2me.app-descriptor;application/msword;text/vnd.fly;video/mp4;application/vnd.oasis.opendocument.database;application/vnd.oasis.opendocument.formula;application/pgp-signature;application/x-rar-compressed;video/x-sgi-movie;application/vnd.yamaha.smaf-audio;text/plain;application/xml;application/vnd.ms-powerpoint;application/java-archive;application/java-vm;application/json;image/jpeg;video/jpeg;application/x-ms-application;application/x-msdownload;application/java-archive;application/octet-stream;text/csv;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,application/msexcel,application/x-ms-excel,application/x-dos_ms_excel,application/xls,application/x-xls,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		fxProfile.setPreference("browser.download.manager.showWhenStarting",false);
		fxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
		fxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
		fxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		fxProfile.setPreference("browser.download.manager.closeWhenDone", false);
		fxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
		fxProfile.setPreference("browser.download.manager.useWindow", false);
		fxProfile.setPreference("browser.download.manager.showWhenStarting", false);
		fxProfile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);

		//disable Firefox's built-in PDF viewer
		//fxProfile.setPreference("pdfjs.disabled",true);

		//disable Adobe Acrobat PDF preview plugin
		//fxProfile.setPreference("plugin.scan.plid.all",false);
		//fxProfile.setPreference("plugin.scan.Acrobat","99.0");

		//WebDriver driver = new FirefoxDriver(fxProfile);
		//driver.navigate().to("http://www.ghpltpa.net/wp-content/themes/ghpl/pdf/janatamedicliam.zip");
		/*driver1.navigate().to("http://static.mozilla.com/moco/en-US/pdf/mozilla_privacypolicy.pdf");

		Thread.sleep(3000);*/
		//driver.quit();
		//driver.navigate().to("http://www.kirkland.com/vcard.cfm?itemid=10485&editstatus=0");
		//Thread.sleep(6000);
		return fxProfile;
	}
	/**
	 * Don't delete all the cookies and launch another URL
	 * 
	 * @param Another URL in Existing Browser
	 */
	public void openUrlInExitWebPage(String baseURL) {
		// APP_LOGS.info(baseURL);
		//driver.manage().deleteAllCookies();
		driver.get(baseURL);
		// APP_LOGS.info("Driver initialization");
		driver.manage().window().maximize();
	}
	/**
	 * Verify the Element
	 * @return: Boolean if Exists Given Element
	 * @param Locator Value
	 */
	public Boolean verifyElement_Boolean(String elementXpath){
	Boolean iselementpresent = driver.findElements(By.xpath(OR.getProperty(elementXpath))).size()!= 0;
	if (iselementpresent == true)
	{
		APP_LOGS.info("Element is located in UI");
	}
	return iselementpresent;
	}
	/**
	 * Verify the Element
	 * @return: Boolean if Exists Given Element
	 * @param Locator Value
	 */
	public void Login_ExpiredIPAddress(){
		openLandingPage(CONFIG.getProperty("testSiteName"));
	if(verifyElement_Boolean("expiredIPAddres")){
		String expiredAdd=driver.getCurrentUrl();
		String compUrl=expiredAdd.concat("/ip.cfm");
		openLandingPage(compUrl);
		input_TextField("inputPassword","itlmsqa", "Login Expired page:Username field ");
		objectClick("AddIpAdress_xPth", "Expired Login page:Login button not found.");
		sleep(1000);
		objectClick("AddIpAdress_xPth", "New IP Address page:Add button not found.");
		sleep(1000);
		objectClick("loginToMyLearn", "New IP Address page:loginToMyLearn button not found.");
	}
	}
	/**
	 * Verify the Element
	 * @return: Boolean if Exists Given Element
	 * @param Locator Value
	 */
	public Boolean verifyElementDireXpth_Boolean(String elementXpath){
	Boolean iselementpresent = driver.findElements(By.xpath(elementXpath)).size()!= 0;
	if (iselementpresent == true)
	{
		APP_LOGS.info("Element is located in UI");
	}
	return iselementpresent;
	}
	/**
	 * Click on Element using containstext
	 * @param Locator Value
	 */
	public void Click_Element_ContainsText(String directLocator,String message ){
		WebElement button=waitOnElement(By.xpath(directLocator), Integer.parseInt(CONFIG.getProperty("default_TimeOut")));
		Assert.assertNotNull(button, message);
		button.click();
	}
	
}