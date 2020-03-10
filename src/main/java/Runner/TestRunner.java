package Runner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

//@RunWith(Cucumber.class)
@CucumberOptions(
		features="C:\\SELENIUM\\Workspace\\Cucumber\\src\\main\\java\\features",
		glue={"StepDefination_CucumberBDD"}
		,plugin= {"html:target/cucumber-html-report"}
		,format= {"json:target/cucumber-json-report/cucumber.json"} ,
		tags= {"@SmokeTest"}
		)

//ORed : tags = {"@SmokeTest , @Regression"} - execute all tests tagged as @SmokeTest OR @Regression
//ANDed : tags = {"@SmokeTest" , "@Regression"} - execute all tests tagged as @SmokeTest AND @Regression
//NOT : tags = {"~@SmokeTest"} - except @SmokeTest all other tests will be executed

public class TestRunner extends AbstractTestNGCucumberTests{
 
	private TestNGCucumberRunner testNGCucumberRunner;
	
	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}
	
	@Test(dataProvider = "features")//executes feature files one-by-one
	public void feature(CucumberFeatureWrapper cucumberFeature) {
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}
	
	@DataProvider//retuns all the feature files to @Test for execution of them one-by-one
	public Object[][] features(){
		return testNGCucumberRunner.provideFeatures();
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		testNGCucumberRunner.finish();
	}
	
}
