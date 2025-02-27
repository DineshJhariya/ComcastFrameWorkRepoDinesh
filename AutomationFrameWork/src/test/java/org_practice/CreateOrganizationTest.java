package org_practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CreateOrganizationTest {

public static void main(String[] args) throws IOException {

		

		

		// 1. Read common data from property file

		FileInputStream fis = new FileInputStream("C:\\Users\\hp\\Desktop\\commondata.properties");

		Properties prop = new Properties();

		prop.load(fis);

		String BROWSER = prop.getProperty("browser");

		String URL = prop.getProperty("Url");

		String USERNAME = prop.getProperty("username");

		String PASSWORD = prop.getProperty("password");

		System.out.println(BROWSER);

		

		

		//2.  Generate random number

		

		Random randomInt = new Random();

	    int random = randomInt.nextInt(1000);

		

		//3. Launch desired browser

		

		WebDriver driver = null;



		if (BROWSER.equals("chrome")) {

			driver = new ChromeDriver();

		} else if (BROWSER.equals("firefox")) {

			driver = new FirefoxDriver();

		} else if (BROWSER.equals("edge")) {

			driver = new EdgeDriver();

		} else {

			driver = new ChromeDriver();

		}

   // 4. Read Test data from excel file

		

		File file = new File("/AutomationFrameWork/selfile/testScriptdata.xlsx");

		FileInputStream fis2 = new FileInputStream(file);

		Workbook wb = WorkbookFactory.create(fis2);

		Sheet sheet = wb.getSheet("Sheet1");

		Row row = sheet.getRow(1);

		Cell cell = row.getCell(2);

		String OrgName = cell.toString()+random ;

		wb.close();

		

		

		// Basic  code for login into application

		

		driver.get(URL);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		driver.manage().window().maximize();

		

		driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(USERNAME);

		driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(PASSWORD);

		driver.findElement(By.id("submitButton")).click();

		

		// navigate to the organization

		

		driver.findElement(By.xpath("//a[.='Organizations']")).click();

		driver.findElement(By.xpath("//img[@alt='Create Organization...']")).click();

		driver.findElement(By.name("accountname")).sendKeys(OrgName);

		driver.findElement(By.xpath("(//input[@class='crmbutton small save'])[1]")).click();

		

		

		//Verify the organization name with header message 

		

	     String ActualOrgName = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();

		

		if (ActualOrgName.contains(OrgName)) {

			System.out.println(OrgName + " is created");

		}

		else {

			System.out.println(OrgName + " is not created");

		}

		

		//5. Verify the header name with Organization name input 

		

		 String Organization = driver.findElement(By.id("dtlview_Organization Name")).getText();

			

			if (Organization.equals(OrgName)) {

				System.out.println(OrgName + " is avilable");

			}

			else {

				System.out.println(OrgName + " is not available");

			}

		

		driver.quit();

	}



}