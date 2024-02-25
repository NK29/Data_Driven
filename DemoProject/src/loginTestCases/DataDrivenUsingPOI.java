package loginTestCases;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class DataDrivenUsingPOI {
	static List<String>userNameList=new ArrayList<String>();
	static List<String>passwordList=new ArrayList<String>();

	public void readExcel() throws IOException {
		
		FileInputStream excel = new FileInputStream("C:\\Users\\Karth\\Documents\\Naveen Kumar S\\LAO\\TestData.xlsx");
		
		Workbook  workbook = new XSSFWorkbook(excel);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row>rowIterator=sheet.iterator();
	
		while(rowIterator.hasNext()) {
			Row rowValue=rowIterator.next();
			Iterator<Cell>columnIterator=rowValue.iterator();
			int i=2;
			while(columnIterator.hasNext()) {
			if(i%2==0) {
				userNameList.add(columnIterator.next().getStringCellValue());			
			}else {
				passwordList.add(columnIterator.next().getStringCellValue());
			}i++;
		}
	}
}
public void Login(String uName,String pWord) {
	System.setProperty("Webdriver.chrome.driver", "C:\\Users\\Karth\\Downloads\\Driver\\ChromeDriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("https://icehrm-open.gamonoid.com/login.php");
		
		WebElement userName=driver.findElement(By.id("username"));
		userName.sendKeys(uName);
		
		WebElement password=driver.findElement(By.name("password"));
		password.sendKeys(pWord);
		
		WebElement loginButton=driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div[4]/button"));
		loginButton.click();
		
		driver.quit();
	}
	public void executeTest() {
		for(int i=0;i<userNameList.size();i++) {
			Login(userNameList.get(i),passwordList.get(i));
		}
	}
	
	public static void main(String[] args) throws IOException{
		DataDrivenUsingPOI usingPOI= new DataDrivenUsingPOI();
		usingPOI.readExcel();
		System.out.println("UserName List"+userNameList);
		System.out.println("Password List"+passwordList);
		usingPOI.executeTest();
		
		
	}
}
