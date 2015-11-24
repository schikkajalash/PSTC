package framework.auto.utils;

//Util methods for JSExecutor
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JSUtil {
	/**
	 * JSClickById will take in the id and use Javascript to generate a click to an element not accessible by normal selenium click
	 * @param driver current webdriver instance
	 * @param id the element id
	 */
	public static void JSClickById (WebDriver driver, String id) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("$('#" + id + "')[0].click();");
	}
	
	/**
	 * JSClickById will take in the id and use Javascript to generate a click to an element not accessible by normal selenium click
	 * @param driver current webdriver instance
	 * @param id the element id
	 */
	public static void JSClickByElement (WebDriver driver, WebElement el) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", el);
	}
	
}
