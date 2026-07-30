package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

//Data is valid ->login success ->Test pass 
//Data is valid ->login unsuccess ->Test fail -logout
//Data is invalid ->login success ->Test fail -logout
//Data is invalid ->login unsuccess ->Test pass 

public class TC003_LoginDataDrivenTest extends BaseClass {
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="Datadriven") //getting data provider from different class
	public void verify_login_DDT(String email,String pwd, String exp) {
		
		logger.info("****Starting TC003_LoginDataDrivenTest******* ");
		
		try {
		//HomePage
				HomePage hp=new HomePage(getDriver());
				hp.clickMyAccount();
				hp.clickLogin();
				
				//Login Page
				LoginPage lp=new LoginPage(getDriver());
				lp.setEmail(email);
				lp.setPassword(pwd);
				lp.clickLogin();
				
				//MyAccount Page 
				MyAccountPage macc=new MyAccountPage(getDriver());
				boolean targetPage=macc.isMyAccountPageExists();
				
				//Valid data
				if(exp.equalsIgnoreCase("Valid")) {
					if(targetPage==true) {
						macc.clickLogout();
						Assert.assertTrue(true);
						
					}else {
						Assert.assertTrue(false);
					}
				}
				
				//InValid data
				if(exp.equalsIgnoreCase("Invalid")) {
					if(targetPage==true) {
						macc.clickLogout();
						Assert.assertTrue(false);
						
					}else {
						Assert.assertTrue(true);
					}
				}
		}catch(Exception e) {
				Assert.fail();
	}
		logger.info("****Finished TC003_LoginDataDrivenTest******* ");
}
}

