package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	@Test(groups={"Sanity","Master"})
	public void verify_Login() {
		
		logger.info("***** Starting of TC002_LoginTest *****");
		try {
		//HomePage
		HomePage hp=new HomePage(getDriver());
		hp.clickMyAccount();
		hp.clickLogin();
		
		//Login Page
		LoginPage lp = new LoginPage(BaseClass.getDriver());
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//MyAccount Page 
		MyAccountPage macc=new MyAccountPage(getDriver());
		boolean targetPage=macc.isMyAccountPageExists();
		
		Assert.assertEquals(targetPage,true,"Login failed");
		//Assert.assertTrue(targetPage);
		}catch(Exception e) {
			Assert.fail();
		}
		logger.info("***** Finished TC002_LoginTest *****");
		
	}

}
