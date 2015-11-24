package tests.sanity;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import framework.auto.core.TestBase;
import framework.auto.utils.LogUtil;
import framework.auto.utils.TestUtil;

public class GoogleSearch extends TestBase {

	private String	       runmodes[]	= null;
	private static int	   count	    = -1;
	// private static boolean pass = false;
	private static boolean	fail	    = false;
	private static boolean	skip	    = false;
	private static boolean	isTestPass	= true;
	private String	       currentURL	= null;
	public static Log	   GOOGLE_LOGS	= LogUtil.getLog(GoogleSearch.class);

	// Runmode of the test case in a suite
	/******
	 * checkTestSkip: initialize config files, checking the run mode and
	 */
	@BeforeTest(alwaysRun = true)
	public void checkTestSkip() throws IOException {
		initialize();
		count++;
		if (!TestUtil.isTestRunnable(PSTC_Google_xls, this.getClass().getSimpleName())) {
			skip = true;
			isTestPass = false;
			GOOGLE_LOGS.info("skipping test case" + this.getClass().getSimpleName() + "as the run mode set to No"); // logs
			throw new SkipException("skipping test case" + this.getClass().getSimpleName() + "as the run mode set to No"); // reports
		}
		// load the run modes of the tests
		runmodes = TestUtil.getDataSetRunmodes(PSTC_Google_xls, this.getClass().getSimpleName());
	}

	/**
	 * createUserAccount: Creates new user account
	 * 
	 * @param SearchString
	 * @param SearchResult
	 */
	@Test(dataProvider = "getTestData")
	public void SearchString(String SearchString, String SearchResult) throws Exception {
		GOOGLE_LOGS.info("Searching for the string");
		// test the runmode of current dataset
		if (!runmodes[count].equalsIgnoreCase("Y")) {
			skip = true;
			throw new SkipException("skipping the current set of data as the runmode is set to No");
		}
		try {
			GOOGLE_LOGS.info("Executing Search String using Google");
			// Account creation
//			openBrowser();
			openLandingPage(CONFIG.getProperty("testSiteName"));
			Thread.sleep(5000);
			// Uncheck education offerings
			input_TextField("GoogleSearchBar",SearchString,"Google Search Page: Enter the string which you want to search ");
			
//			objectClick("GoogleSearchBar", "Google Search Page: Enter the string which you want to search ");
			// Click on Continue button
			objectClick("SearchButton", "Google Search Page: Hit Google Search button ");
			String accountActivationMsg = getTextForAnElement(driver, "ResultString");
//			currentURL = driver.getCurrentUrl();
			Assert.assertTrue(accountActivationMsg.equals(SearchResult), "Google Search Page :: Result showing up :: ");
			// Activate contact account
//			activateContactAccount(adminUsername, adminPassword, currentURL, captcha, successMsg1);
		} catch (AssertionError ae) {
			GOOGLE_LOGS.info("Google Search for a string:" + ae.getMessage());
			fail = true;
			throw ae;
		}
		GOOGLE_LOGS.info("End of Create Account Contact Non Compliance Scripts");
	}

	@AfterMethod
	public void reportDataSetResult() {
		// report the error in the xls file
		if (skip) {
			TestUtil.reportDataSetResult(PSTC_Google_xls, this.getClass().getSimpleName(), count + 2, "skip");
		} else if (fail) {
			isTestPass = false;
			TestUtil.reportDataSetResult(PSTC_Google_xls, this.getClass().getSimpleName(), count + 2, "FAIL");
		} else {
			TestUtil.reportDataSetResult(PSTC_Google_xls, this.getClass().getSimpleName(), count + 2, "PASS");
		}
		skip = false;
		fail = false;
	}

	@AfterTest(alwaysRun = true)
	public void reportTestResults() {
		if (skip) {
			TestUtil.reportDataSetResult(PSTC_Google_xls, "Test Cases", TestUtil.getRowNum(PSTC_Google_xls, this.getClass().getSimpleName()), "SKIP");
		} else {
			if (isTestPass) {
				TestUtil.reportDataSetResult(PSTC_Google_xls, "Test Cases", TestUtil.getRowNum(PSTC_Google_xls, this.getClass().getSimpleName()), "PASS");
			} else {
				TestUtil.reportDataSetResult(PSTC_Google_xls, "Test Cases", TestUtil.getRowNum(PSTC_Google_xls, this.getClass().getSimpleName()), "FAIL");
			}
		}
	}

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(PSTC_Google_xls, this.getClass().getSimpleName());
	}
}
