package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name ="LoginData")
	public String[][] getData() throws IOException {

	   // String path = System.getProperty("user.dir")  + "/testData/Opencart_LoginData.xlsx"; //taking data from excel file
        String path =".//testData//Opencart_LoginData.xlsx"; 
        
	    ExcelUtility xlutil = new ExcelUtility(path); //creating an object for excel utility

	    int totalrows = xlutil.getRowCount("Sheet1");
	    int totalcols = xlutil.getCellCount("Sheet1", 1);

	    String logindata[][] = new String[totalrows][totalcols];

	    for (int i = 1; i <= totalrows; i++) {    //1       read the data from xl storing  into 2 dimensional arraty
	        for (int j = 0; j < totalcols; j++) { //0       i is row, j is column

	            logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j);  //1,0

	        }
	    }

	    return logindata;
	}
    //Dataproviders 2
}
