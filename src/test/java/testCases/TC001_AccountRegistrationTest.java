package testCases;


import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{
		
	@Test(groups={"Regression","Master"})
	public void verify_account_registration() throws InterruptedException 
	{
		logger.info("******* Starting TC001_AccountRegistrationTest *********");
		
		try {
		HomePage hp= new HomePage(getDriver());
		hp.clickMyAccount();
		logger.info("Clicked on MyAccount link");
		
		hp.clickRegister();
		logger.info("Clicked on register link");
		
		AccountRegistrationPage regPage= new AccountRegistrationPage(getDriver());
		logger.info("Providing customer details....");
		regPage.setFirstName(randomeString().toUpperCase());
		regPage.setLastName(randomeString().toUpperCase());
		regPage.setEmail(randomeString()+"@gmail.com"); //randomly generated the email
		regPage.setTelephone(randomeNumber());
		
		String password=randomeAlphaNumeric();
		
		regPage.setPassword(password);
		Thread.sleep(1000);
		regPage.setConfirmPassword(password);
		
		regPage.setPrivacyPolicy();
		regPage.clickContinue();
		
		logger.info("Validating expected message...");
		String confmsg=regPage.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!")) {
		Assert.assertTrue(true)	;	
	}else {
		logger.error("Test failed...");
		logger.debug("Debug logs..");
		Assert.assertTrue(false);
	}
		//Assert.assertEquals(confmsg,"Your Account Has Been Created!");
		}
	
	catch(Exception e)
	{
		
		Assert.fail();
	}
		logger.info("******* Finished TC001_AccountRegistrationTest *********");
	}

}
