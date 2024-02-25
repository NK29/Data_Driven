package loginTestCases;

import java.io.FileInputStream;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Login {
	
	String[][]data=null;
	WebDriver driver;
	
	@DataProvider(name="loginData")	
	public String[][] loginDataProvider() throws BiffException, IOException {
		data=geteExcelData();
		return data;
	};
	
	public String[][] geteExcelData() throws BiffException, IOException {
		
		FileInputStream excel= new FileInputStream("C:\\Users\\Karth\\Documents\\Naveen Kumar S\\LAO\\TestData.xls");
		
		Workbook workbook = Workbook.getWorkbook(excel);
		
		Sheet sheet= workbook.getSheet(0);
		
		int rowCount=sheet.getRows();
		int columnCount=sheet.getColumns();
		
		String testData[][]= new String[rowCount-1][columnCount];
		
		for(int i=1;i<rowCount;i++) {
			for(int j=0;j<columnCount;j++) {
				testData[i-1][j]=sheet.getCell(j,i).getContents();	
			}
		}
		return testData;
	}
	
	@BeforeSuite
	public void beforeTest() {
		System.setProperty("Webdriver.chrome.driver", "C:\\Users\\Karth\\Downloads\\Driver\\ChromeDriver.exe");
		driver= new ChromeDriver();
	}
	@AfterSuite
	public void afterTest() {
		driver.quit();
	}
	
	@Test(dataProvider="loginData")
	public void loginWithCorrect(String uName,String pWord) {
		
		driver.get("https://icehrm-open.gamonoid.com/login.php");
		
		WebElement userName=driver.findElement(By.id("username"));
		userName.sendKeys(uName);
		
		WebElement password=driver.findElement(By.name("password"));
		password.sendKeys(pWord);
		
		WebElement loginButton=driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div[4]/button"));
		loginButton.click();

	}
}
