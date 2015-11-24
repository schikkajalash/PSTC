package framework.auto.utils;

//Util methods for use during test executions
public class TestUtil {
	//public String getExcelName;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
//find if the Test Suite is runnable based on the run mode
		public static boolean isSuiteRunnable(Xls_Reader xls, String suiteName){
			System.out.println("Xls name:"+xls+", Suite name:"+suiteName);
			boolean isExecutable =false;
			for(int  i = 2; i<=xls.getRowCount("Test Suite"); i++){
				String tsid = xls.getCellData("Test Suite", "TSID", i);
				String runmode = xls.getCellData("Test Suite", "Runmode", i);
				System.out.println(tsid + " Runmode is " + runmode);
				
				if(tsid.equalsIgnoreCase(suiteName)){
					if(runmode.equalsIgnoreCase("Y")){
						isExecutable = true;
					}
						else{
							isExecutable = false;
							}
					}
				}
		xls = null;
		return isExecutable;
		}
	
		
		
//Find if the specified test is runnable based on runmode
		public static boolean isTestRunnable(Xls_Reader xls, String testCaseName){
			boolean isExecutable = false;
			
			System.out.println("Sheet name:"+xls+", Test case name:"+testCaseName);
			for(int i =2; i<=xls.getRowCount("Test Cases"); i++){
				String tcid = xls.getCellData("Test Cases", "TCID", i);
				String runmode = xls.getCellData("Test Cases", "Runmode", i);
				
				//System.out.println(tcid + " Runmode " + runmode);
				/*System.out.println("TIDA:"+tcid);
				System.out.println("TIDE:"+testCaseName);
				System.out.println("runmodeA:"+runmode);
				System.out.println("runmodeE:"+"Y");*/
				
				if(tcid.equalsIgnoreCase(testCaseName)){
					if(runmode.equalsIgnoreCase("Y")){
						isExecutable = true;						
					}else{
						isExecutable = false;
						}
					}
				}
				
			xls =null;//release memory
			return isExecutable;		
		}

	
	
		//Return the test data from a two dimensional array
		public static Object[][] getData(Xls_Reader xls, String testCaseName){
			
			System.out.println("Reader sheet name:"+xls+", testcasename:"+testCaseName);
			//if the test data sheet is not present for a test case
			if(! xls.isSheetExist(testCaseName)){
				xls = null;
				return new Object[1][0];
			}
			
			int rows = xls.getRowCount(testCaseName);
			int cols = xls.getColumnCount(testCaseName);
			
			//Retrieving data from excel
			Object[][] data = new Object[rows-1][cols-3];
			for(int rowNum =2; rowNum<=rows; rowNum++ ){
				for(int colNum =0; colNum<cols-3; colNum++){
					//System.out.print(xls.getCellData(testCaseName, colNum, rowNum)+ " -- ");
					data[rowNum-2][colNum] = xls.getCellData(testCaseName, colNum, rowNum);
				}
				//System.out.println();
			}
				return data;
		}
		
// checks Runmode for dataSet
				public static String[] getDataSetRunmodes(Xls_Reader xlsFile,String sheetName){
					String[] runmodes=null;
					if(!xlsFile.isSheetExist(sheetName)){
						xlsFile=null;
						sheetName=null;
						runmodes = new String[1];
						runmodes[0]="Y";
						xlsFile=null;
						sheetName=null;
						return runmodes;
					}
					runmodes = new String[xlsFile.getRowCount(sheetName)-1];
					for(int i=2;i<=runmodes.length+1;i++){
						runmodes[i-2]=xlsFile.getCellData(sheetName, "Runmode", i);
						System.out.println("Runmodes of sheet:"+xlsFile.getCellData(sheetName, "Runmode", i));
					}
					xlsFile=null;
					sheetName=null;
					return runmodes;
					
				}
		
//updating results for a particular data set
		public static void reportDataSetResult(Xls_Reader xls, String testCaseName, int rowNum, String result){
			xls.setCellData(testCaseName, "Results", rowNum, result);
		}
		
	//updating results for a particular data set
		public static void reportDataSetResultClassLink(Xls_Reader xls, String testCaseName, int rowNum, String result){
			xls.setCellData(testCaseName, "ClassregLnk", rowNum, result);
		}
		//updating results for a particular data set
		public static void reportDataSetResultClassId(Xls_Reader xls, String testCaseName, int rowNum, String result){
			xls.setCellData(testCaseName, "ClassId", rowNum, result);
		}
		// updating New Test link into a particular data set
		public static void enterNewTestData(Xls_Reader xls, String testCaseName, int rowNum, String result) {
			xls.setCellData(testCaseName, "TestRegLink", rowNum, result);
		}

		// updating New new survey link into a particular data set
		public static void enterNewSurveyData(Xls_Reader xls, String testCaseName, int rowNum, String result) {
			xls.setCellData(testCaseName, "SurveyRegLink", rowNum, result);
		}

		// updating new survey name into a particular data set
		public static void newSurveyName(Xls_Reader xls, String testCaseName, int rowNum, String result) {
			xls.setCellData(testCaseName, "SurveyName", rowNum, result);
		}
		
		// updating class Id for creating discount
		public static void enterClassIdForDiscountsData(Xls_Reader xls, String testCaseName, int rowNum, String result) {
			xls.setCellData(testCaseName, "ClassId", rowNum, result);
		}
		
		// updating Coordinator email id
		public static void reportDataSetResultCoordinatorEmailId(Xls_Reader xls, String testCaseName, int rowNum, String result) {
			xls.setCellData(testCaseName, "Username", rowNum, result);
		}
		// updating Coordinator email id
		public static void reportDataSetResultCertificateDesc(Xls_Reader xls, String testCaseName, int rowNum, String result) {
			xls.setCellData(testCaseName, "CertficateDesc", rowNum, result);
		}
		

//return the Row Number for a Test
		public static int getRowNum(Xls_Reader xls, String id){
			for(int i =2; i<=xls.getRowCount("Test Cases"); i++){
				String tcid = xls.getCellData("Test Cases", "TCID", i);
				if(tcid.equals(id)){
					xls=null;
					return i;
				}
			}
			return -1;
		}
}