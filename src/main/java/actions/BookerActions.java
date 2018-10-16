package actions;

import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
//import org.openqa.selenium.firefox.FirefoxBinary;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxProfile;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import java.util.ArrayList;

import actions.BaseTest;
import utils.TimeUtils;
import utils.Util;

import org.apache.commons.io.FileUtils;

public class BookerActions extends BaseTest 
{
	//------------------------------------------- Method 0 - kiem tra viec login vao tk booker voi các truong hop test khac nhau, data lay tu file excel, su dung thu vien fillo - cach nay gon nhe hon
	@Parameters()
	 @Test(enabled = true)
	public void CheckBookerLoginUsingQuery() throws Exception
	{
		 System.out.println("Thuc hien viec kiem tra cac truong hop login vao trang");
		   System.out.println(" "
		   		+ "      * SS1: Enter valid userid & password \r\n" + 
		   		"	     *      Expected: Login successful home page shown \r\n" + 
		   		"	     * SS2: Enter invalid userid & valid password \r\n" + 
		   		"	     * SS3: Enter valid userid & invalid password \r\n" + 
		   		"	     * SS4: Enter invalid userid & invalid password \r\n" + 
		   		"	     *      Expected: A pop-up �User or Password is not valid� is shown "); 
		 String uName, uPass;

		  String actualTitle, actualBoxtitle;
		  // output mong muốn

		  String EXPECT_TITLE = "Tripi.vn - Đặt vé máy bay và khách sạn thuận tiện với giá tốt nhất";
		  String EXPECT_ERROR1 = "Mật khẩu không hợp lệ";
		  String EXPECT_ERROR2 = "Tài khoản chưa được đăng ký tại hệ thống";

		  String tc = "Test case so";
		  
		  //Xu ly ket noi file excel, thuc hien truy van
		  Fillo fillo = new Fillo();
		  Connection conn = fillo.getConnection("testData.xls");
		  String query = "Select * from Data";
		  Recordset record = conn.executeQuery(query);		  
		  List<String> name = new ArrayList<>();
		  List<String> pass = new ArrayList<>();
		  // thêm name và pass vào mảng
		  while (record.next()) 
		  {

		   name.add(record.getField("username"));
		   pass.add(record.getField("password"));

		  }		  
		  

		  // mỗi vị trí i, lấy name và pass ở 2 cột tương ứng		  
		  driver.findElement(By.cssSelector(".icons-v2.i-user-w-t")).click();		 				
		  TimeUtils.sleep(2);	
			
		  for (int i = 0; i < name.size(); i++) 
		  {

		   uName = name.get(i);
		   uPass = pass.get(i);
		   
		   //Chọn trường username
			driver.findElement(By.cssSelector("#username")).clear();
			driver.findElement(By.cssSelector("#username")).sendKeys(uName);
			 			
			//Chon trường password
			driver.findElement(By.cssSelector("#password")).clear();						 			
			driver.findElement(By.cssSelector("#password")).sendKeys(uPass);
			 			
			//Ấn nút Đăng nhập
			driver.findElement(By.cssSelector("#submit-btn")).click();	
			TimeUtils.sleep(5);	 
			
		   try 
		   {
			   WebElement loginForm = driver.findElement(By.cssSelector(".tlp-login-form"));
		       actualBoxtitle = loginForm.findElement(By.cssSelector(".ng-binding")).getText();

		       // So sánh lỗi thực tế với lỗi mong đợi

		       if (actualBoxtitle.contains(EXPECT_ERROR1)||actualBoxtitle.contains(EXPECT_ERROR2)) 
		       {		    	
		    	   System.out.println(tc + "[" + i + "]: Passed");
		       } 
		       else 
		       {
		    	   System.out.println(tc + "[" + i + "]: Failed");
		       }
		   } 
		   
		   catch (Exception Ex) 
		   {
			   actualTitle = driver.getTitle();
			   // So sánh title thực tế với title mong đợi
			   if (actualTitle.contains(EXPECT_TITLE)) 
			   {
				   System.out.println(tc + "[" + i + "]: Login thanh cong");
			   } 
			   else 
			   {
				   System.out.println(tc + "[" + i + "]: Login loi");   
			   }
			   File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Code to save screenshot at desired location
				FileUtils.copyFile(scrFile, new File("C:\\Users\\Asrock.DESKTOP-1D8CK3P\\eclipse-workspace\\BookerActions\\screenshot.png"));
				
			   driver.findElement(By.cssSelector(".dropdown.user-menu")).click();
			   TimeUtils.sleep(1);	   
			   driver.findElement(By.cssSelector(".huf-uf-item.logout.ng-isolate-scope")).click();
			   TimeUtils.sleep(1);	   
			   driver.findElement(By.cssSelector(".btn.width-65.btn-danger")).click();
			   TimeUtils.sleep(5);	  
			   driver.findElement(By.cssSelector(".icons-v2.i-user-w-t")).click();		 				
			   TimeUtils.sleep(2);	
		   }
		  }
		  // đóng kết nối
		  record.close();
		  conn.close();

		 }
	

	
	
	
	//------------------------------------------- Method 0
	    /**
	     * SS1: Enter valid userid & password 
	     *      Expected: Login successful home page shown 
	     * SS2: Enter invalid userid & valid password 
	     * SS3: Enter valid userid & invalid password 
	     * SS4: Enter invalid userid & invalid password 
	     *      Expected: A pop-up �User or Password is not valid� is shown
	     * 
	     * @throws Exception
	     */
	 //  @Parameters()
	  // @Test(enabled = true)
	    public void CheckBookerLoginReadDatFromXlsFile() throws Exception 
	    {
		   System.out.println("Thuc hien viec kiem tra cac truong hop login vao trang");
		   System.out.println(" "
		   		+ "      * SS1: Enter valid userid & password \r\n" + 
		   		"	     *      Expected: Login successful home page shown \r\n" + 
		   		"	     * SS2: Enter invalid userid & valid password \r\n" + 
		   		"	     * SS3: Enter valid userid & invalid password \r\n" + 
		   		"	     * SS4: Enter invalid userid & invalid password \r\n" + 
		   		"	     *      Expected: A pop-up �User or Password is not valid� is shown ");
		// Read test data from excel file
	    // Method 	getDataFromExcel is defined in class Util
	    String[][] testData = Util.getDataFromExcel(Util.FILE_PATH,	Util.SHEET_NAME, Util.TABLE_NAME);
		String username, password;
		String actualTitle;
		String actualBoxtitle;
		
		//Testing for all parameters stored in the Excel File
		
		 // Click vao button Login		
		driver.findElement(By.cssSelector(".icons-v2.i-user-w-t")).click();		 				
		TimeUtils.sleep(2);	
		
		for (int i = 0; i < testData.length; i++)
		{
		    username = testData[i][0]; // get username
		    password = testData[i][1]; // get password		   
		
		//Chọn trường username
		driver.findElement(By.cssSelector("#username")).clear();
		driver.findElement(By.cssSelector("#username")).sendKeys(username);
		 			
		//Chon trường password
		driver.findElement(By.cssSelector("#password")).clear();			
		 			//passwordField.click();	
		driver.findElement(By.cssSelector("#password")).sendKeys(password);
		 			
		//Ấn nút Đăng nhập
		driver.findElement(By.cssSelector("#submit-btn")).click();	
		TimeUtils.sleep(5);	    
		  
	       
	        /* Determine Pass Fail Status of the Script
	         * If login credentials are correct,  Alert(Pop up) is NOT present. An Exception is thrown and code in catch block is executed	
	         * If login credentials are invalid, Alert is present. Code in try block is executed 	    
	         */
		    try
		    { 
		        //neu login khong thanh cong -> get ve message bao loi
		       	WebElement loginForm = driver.findElement(By.cssSelector(".tlp-login-form"));
		       	actualBoxtitle = loginForm.findElement(By.cssSelector(".ng-binding")).getText();

				if (actualBoxtitle.contains("Mật khẩu không hợp lệ") || actualBoxtitle.contains("Tài khoản chưa được đăng ký tại hệ thống")) 
				{ // Compare Error Text with Expected Error Value
				    System.out.println("Test case SS[" + i + "]: Passed"); 
				} 
				else 
				{
				    System.out.println("Test case SS[" + i + "]: Failed");
				}
			}    
		    catch (Exception ex)
		    { 
		    	//neu login thanh cong -> get ve title cua trang
		    	ex.printStackTrace();
		    	actualTitle = driver.getTitle();
				// On Successful login compare Actual Page Title with Expected Title
				if (actualTitle.contains(Util.EXPECT_TITLE)) 
				{
				    System.out.println("Test case SS[" + i + "]: Passed");
				} else 
				{
				    System.out.println("Test case SS[" + i + "]: Failed");
				}
				driver.findElement(By.cssSelector(".dropdown.user-menu")).click();
				TimeUtils.sleep(1);	   
				driver.findElement(By.cssSelector(".huf-uf-item.logout.ng-isolate-scope")).click();
				TimeUtils.sleep(1);	   
				driver.findElement(By.cssSelector(".btn.width-65.btn-danger")).click();
				TimeUtils.sleep(5);	  
				driver.findElement(By.cssSelector(".icons-v2.i-user-w-t")).click();		 				
				TimeUtils.sleep(2);	
	        } 
		    
		    }       
			
		    }
	
	/*
	
		//------------------------------------------- Method 1
	    //@Parameters({"username","password"})
	    //@Test(enabled = true)
		public void BookerLogin (String username, String password)
		{
			System.out.println("Thuc hien viec login vao tai khoan booker "+username);
			// Click vao button Login		
			WebElement loginIcon = driver.findElement(By.cssSelector(".icons-v2.i-user-w-t"));			
			loginIcon.click();	
			TimeUtils.sleep(2);	
			
			//Chọn trường username
			//WebElement usernameField = driver.findElement(By.cssSelector("#username"));			
			//usernameField.click();	
			//usernameField.sendKeys(username);
			driver.findElement(By.cssSelector("#username")).clear();
			driver.findElement(By.cssSelector("#username")).sendKeys(username);
			
			//Chon trường password
			WebElement passwordField = driver.findElement(By.cssSelector("#password"));			
			passwordField.click();	
			passwordField.sendKeys(password);
			
			//Ấn nút Đăng nhập
			driver.findElement(By.cssSelector("#submit-btn")).click();	
			
			TimeUtils.sleep(7);	
			System.out.println("Login thành công");
		}
		
		
		
		//@Parameters({"from_airport","to_airport","from_date_add","to_date_add","adult_num","child_num","infant_num"})
		//@Test(enabled = true)
		public void SearchFlight (String username, String password, String from_airport, String to_airport, int from_date_add, int to_date_add, int adult_num, int child_num, int infant_num) 
		{
		             
			        BookerLogin (username, password);
			        System.out.println("-----Thuc hien viec tim kiem ve may bay tu "+from_airport+" den "+to_airport+".Tong so hanh khach: "+(adult_num+child_num+infant_num));
			       // Click vao button khu hoi		
					WebElement returnButton = driver.findElement(By.cssSelector("span.fw-item:nth-child(2)"));			
					returnButton.click();		
							
					// Nhap departure station	
					WebElement departureStation = driver.findElement(By.cssSelector("#flight-from-airport-value"));		
					departureStation.sendKeys(from_airport);		
					TimeUtils.sleep(2);		
					departureStation.sendKeys(Keys.RETURN);		
							
				//NGAY DI/NGAY VE
					// Nhap arrival station		
					WebElement arrivalStation = driver.findElement(By.cssSelector("#flight-to-airport-value"));		
					arrivalStation.sendKeys(to_airport);		
					TimeUtils.sleep(2);		
					arrivalStation.sendKeys(Keys.RETURN);		
							
				    Calendar now = Calendar.getInstance();			    
				    int monthOfNow = now.get(Calendar.MONTH)+1;
				    
				    System.out.println("Ngay hien tai  "+now.get(Calendar.DATE) + "-"+ (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR));
				    now.add(Calendar.DATE, from_date_add);
				    int from_date = now.get(Calendar.DATE);
				    int from_month = now.get(Calendar.MONTH)+1;
				
				    System.out.println("Ngay khoi hanh muon tim kiem "+now.get(Calendar.DATE) + "-"+ (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR));
				    
				  
					// Nhap departure date		
					WebElement checkinDate = driver.findElement(By.cssSelector("#flight-checkin-date"));		
					checkinDate.click();		
					WebElement tableCheckinDate = checkinDate.findElement(By.xpath(".."));
					
					
					//Lay thang cua calendar hien thi
					WebElement monthOfCalendarDiv = tableCheckinDate.findElement(By.cssSelector(".btn-sm.uib-title"));
					String monthOfCalendar = monthOfCalendarDiv.getText();
					String[] monthOfCalendarArray = monthOfCalendar.split(" ");
					System.out.println("Month : "+monthOfCalendar);
					int monthOfCalendarInt = Integer.parseInt(monthOfCalendarArray[1]);
					System.out.println("Thang cua calendar dang hien thi - thang : "+monthOfCalendarInt);
					
					
					
					//if(from_month==monthOfNow)
					if(from_month==monthOfCalendarInt)	
					{	
						System.out.println("Thang cua ngay khoi hanh trung voi thang hien tai calendar dang hien thi");
				//		WebElement tableCheckinDate = checkinDate.findElement(By.xpath(".."));		
						List<WebElement> days = tableCheckinDate.findElements(By.tagName("td"));		
						for (WebElement day : days) 
						{
							int datef = Integer.parseInt(day.getText());		
							if (from_date== datef) 
							{
								day.click();
								break;
							}
						}		
						System.out.println("Ngay di: "+from_date+"/"+(now.get(Calendar.MONTH)+1));		
					}
					//else if(from_month > monthOfNow)
					else if(from_month > monthOfCalendarInt)
					{
						System.out.println("Thang cua ngay khoi hanh lon hon thang hien tai calendar dang hien thi");						
						//int clicktime = from_month - monthOfNow;
						int clicktime = from_month - monthOfCalendarInt;
								
					//	System.out.println("clicktime: "+clicktime);					
						
						WebElement upMonth = tableCheckinDate.findElement(By.cssSelector(".pull-right.uib-right"));
						for(int i=0; i<clicktime;i++)
						{
							upMonth.click();						
						}
						TimeUtils.sleep(2);	
						
						List<WebElement> days = tableCheckinDate.findElements(By.tagName("td"));		
						for (WebElement day : days) 
						{
							int datef = Integer.parseInt(day.getText());		
							if (from_date== datef) 
							{
								day.click();
								break;
							}
						}		
						System.out.println("Ngay di: "+from_date+"/"+(now.get(Calendar.MONTH)+1));		
						
					}
					
					
					// Nhap ngay ve		
					 Calendar nowup = Calendar.getInstance();
					 System.out.println("Ngay hien tai  "+nowup.get(Calendar.DATE) + "-"+ (nowup.get(Calendar.MONTH) + 1) + "-" + nowup.get(Calendar.YEAR));
				
				//	 int monthOfNowib = nowup.get(Calendar.MONTH)+1;
				
					 
					 nowup.add(Calendar.DATE, to_date_add);
					 System.out.println("Ngay khoi hanh chieu di muon tim kiem "+nowup.get(Calendar.DATE) + "-"+ (nowup.get(Calendar.MONTH) + 1) + "-" + nowup.get(Calendar.YEAR));
					 int to_date = nowup.get(Calendar.DATE);
					 int to_month = nowup.get(Calendar.MONTH)+1;		
					 
					
					WebElement checkoutDate = driver.findElement(By.cssSelector("#flight-checkout-date"));		
					checkoutDate.click();	
					WebElement tableCheckoutDate = checkoutDate.findElement(By.xpath(".."));	
					
					
					//Lay thang cua calendar hien thi chieu ve
					WebElement monthOfCalendarDivib = tableCheckoutDate.findElement(By.cssSelector(".btn-sm.uib-title"));
					String monthOfCalendarib = monthOfCalendarDivib.getText();
					String[] monthOfCalendarArrayib = monthOfCalendarib.split(" ");					
					int monthOfCalendarIntib = Integer.parseInt(monthOfCalendarArrayib[1]);
					System.out.println("Thang cua calendar dang hien thi: "+monthOfCalendarIntib);
						
					
					//if(to_month==from_month)
					if(to_month==monthOfCalendarIntib)
					{	
						System.out.println("to_month == monthOfCalendarInt");
						System.out.println("Thang cua ngay khoi hanh chieu ve trung voi thang hien tai calendar dang hien thi");
						List<WebElement> checkoutDays = tableCheckoutDate.findElements(By.tagName("td"));		
						for (WebElement day : checkoutDays) 
						{					
							int date = Integer.parseInt(day.getText());				
							if (to_date == date) 
							{				
								day.click();
								break;
							}
						}
						System.out.println("Ngay ve: "+to_date+"/"+(nowup.get(Calendar.MONTH)+1)+"-----------");
					}
					//else if(to_month > from_month)
					else if(to_month > monthOfCalendarIntib)
					{
						System.out.println("Thang cua ngay khoi hanh chieu ve lon hon thang hien tai calendar dang hien thi");
						int clicktime = to_month - monthOfCalendarIntib;
						System.out.println("chieu ve ");
						System.out.println("to_month: "+to_month);
						System.out.println("from_month: "+from_month);
						System.out.println("clicktime: "+clicktime);
						
						//WebElement tableCheckoutDate = checkoutDate.findElement(By.xpath(".."));		
						WebElement upMonth = tableCheckoutDate.findElement(By.cssSelector(".pull-right.uib-right"));
						for(int i=0; i<clicktime;i++)
						{
							upMonth.click();						
						}
						TimeUtils.sleep(2);	
						List<WebElement> checkoutDays = tableCheckoutDate.findElements(By.tagName("td"));		
						for (WebElement day : checkoutDays) 
						{					
							int date = Integer.parseInt(day.getText());				
							if (to_date == date) 
							{				
								day.click();
								break;
							}
						}
						System.out.println("Ngay ve: "+to_date+"/"+(nowup.get(Calendar.MONTH)+1)+"-----------");
					}
					/// Change passengers		
					WebElement passenger = driver.findElement(By.cssSelector(".ui-selectmenu-text"));		
					passenger.click();		
					
					WebElement adultNum = driver.findElement(By.cssSelector(".centered > li:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span:nth-child(5) > button:nth-child(1)"));
					for (int i=1; i < adult_num; i++)
					{
						adultNum.click();	
					}
					TimeUtils.sleep(2);	
					WebElement childNum = driver.findElement(By.cssSelector(".flights-touchspin-children > span:nth-child(5) > button:nth-child(1)"));
					for (int i=0; i < child_num; i++)
					{
						childNum.click();	
					}
					TimeUtils.sleep(2);	
					WebElement infantNum = driver.findElement(By.cssSelector(".flights-touchspin-infants > span:nth-child(5) > button:nth-child(1)"));				
					for (int i=0; i < infant_num; i++)
					{
						infantNum.click();	
					}
					TimeUtils.sleep(2);	
							
					// Click tim kiem								
					WebElement searchButton = driver.findElement(By.cssSelector(".flight-search-button"));		
					searchButton.click();		
					TimeUtils.sleep(30);
					 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				  // Code to save screenshot at desired location
				   FileUtils.copyFile(scrFile, new File("C:\\Users\\Asrock.DESKTOP-1D8CK3P\\eclipse-workspace\\BookerActions\\screenshot_searchresult.png"));
					System.out.println("Da thuc hien tim kiem xong");
		}

		
		//Đặt vé máy bay thành công (thanh toán thành công còn xuất vé có thể thành công/thất bại)
		//1. login
		//2. search vé
		//3. book và thanh toán (credit hoặc atm nội địa)
		
		
		//------------------------------------------- Method 3--- Thuc hien dat va thanh toan ve qua cong thanh toan ATM - ngan hang NCB
	//	@Parameters({"username","password", "from_airport_book","to_airport_book","from_date_book_add", "to_date_book_add","adult_num_book","child_num_book","infant_num_book"})
	//	@Test(priority = 3, enabled = true)
		public void BookFlight 	(String username, String password, String from_airport_book, String to_airport_book, int from_date_book_add, int to_date_book_add, int adult_num_book, int child_num_book, int infant_num_book)
	    {
			System.out.println("3. Thuc hien dat va thanh toan ve qua cong thanh toan ATM - ngan hang NCB");
			SearchFlight(username, password, from_airport_book, to_airport_book, from_date_book_add, to_date_book_add, adult_num_book, child_num_book, infant_num_book);
	    	//Chon ve chieu di		
	    	       
	    			WebElement outBoundTicketsDiv = driver.findElement(By.cssSelector("#outBoundTickets"));		
	    			List<WebElement> outboundTickets = outBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));		
	    			System.out.println("Chon ve chieu di:");		
	    			for (WebElement ticket : outboundTickets) {		
	    				WebElement logo = ticket.findElement(By.cssSelector(".alogo"));	
	    				String agency = logo.getAttribute("alt");	
	    					
	    				if (agency.contains("VietJet")) {				
	    					WebElement selectBtnob = ticket.findElement(By.cssSelector(".flight-select-single-ticket"));
	    					selectBtnob.click();
	    					TimeUtils.sleep(2);
	    					System.out.println(agency);
	    					break;
	    				}	
	    				}	
	    			//Chon ve chieu ve		
	    		   //WebElement inBoundTicketstab = driver.findElement(By.cssSelector("div.menu-item:nth-child(3)"));		
	    		  //inBoundTicketstab.click();					
	    			TimeUtils.sleep(5);						
	    			WebElement inBoundTicketsDiv = driver.findElement(By.cssSelector("#inboundTickets.tickets"));		
	    			List<WebElement> inboundTickets = inBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));		
	    					
	    			System.out.println("Chon ve chieu ve:");		
	    			for (WebElement ticketib : inboundTickets) {		
	    					
	    				WebElement logoib = ticketib.findElement(By.cssSelector(".alogo"));	
	    				String agencyib = logoib.getAttribute("alt");	
	    					
	    				if (agencyib.contains("VietJet")) {	
	    					WebElement selectBtnib = ticketib.findElement(By.cssSelector(".flight-select-return-ticket"));
	    					selectBtnib.click();
	    					TimeUtils.sleep(2);
	    					System.out.println(agencyib);
	    					break;
	    					
	    				}	
	    			}		
	    			//Xac nhan chon ve		
	    			WebElement selectedFlightDiv = driver.findElement(By.cssSelector("#selected-flight"));		
	    			WebElement confirmBookTicketbtn = selectedFlightDiv.findElement(By.cssSelector(".flight-search-booking-ticket"));		
	    			confirmBookTicketbtn.click();		
	    			TimeUtils.sleep(5);		
	    					
	    			//Nhap tt hanh khach thu nhat			
	    			WebElement firstGuestDiv = driver.findElement(By.cssSelector("#adult-0"));	
	    			//Nhap lastname	
	    			WebElement lastNametext = firstGuestDiv.findElement(By.cssSelector(".form-control.last-name"));
	    			lastNametext.clear();
	    			lastNametext.sendKeys("Nguyen");	
	    			//Nhap firstname				
	    			WebElement firstNametext = firstGuestDiv.findElement(By.cssSelector(".form-control.first-name"));
	    			firstNametext.clear();
	    			firstNametext.sendKeys("Van An")	;
	    			//Chon gioi tinh	
	    			WebElement genderOfFirstGuestDiv = firstGuestDiv.findElement(By.cssSelector(".form-control.gender"));	
	    			Select dropdown= new Select(genderOfFirstGuestDiv);	
	    			dropdown.selectByVisibleText("Nam");	
	    			TimeUtils.sleep(3);				
	    					
	    			//Nhap tt hanh khach thu hai	
	    			
	    					WebElement secondGuestDiv = driver.findElement(By.cssSelector("#adult-1"));	
	    					//Nhap lastname	
	    					WebElement lastNametext2 = secondGuestDiv.findElement(By.cssSelector(".form-control.last-name"));	
	    					lastNametext2.clear();
	    					lastNametext2.sendKeys("Nguyen");	
	    					//Nhap firstname						
	    					WebElement firstNametext2 = secondGuestDiv.findElement(By.cssSelector(".form-control.first-name"));	
	    					firstNametext2.clear();
	    					firstNametext2.sendKeys("Hong Ha")	;
	    					//Chon gioi tinh	
	    					WebElement genderOfFirstGuestDiv2 = secondGuestDiv.findElement(By.cssSelector(".form-control.gender"));	
	    					Select dropdown2= new Select(genderOfFirstGuestDiv2);	
	    					dropdown2.selectByVisibleText("Nữ");
	    					TimeUtils.sleep(5);			
	    			
	    					//Chon mua them hanh ly
	    					//The chon hanh ly
	    					//WebElement baggagesDiv = driver.findElement(By.cssSelector(".row"));
	    					//chon hanh ly hanh khach thu nhat - chon goi 15kg
	    					//WebElement baggagesNum = baggagesDiv.findElement(By.cssSelector(".form-control.baggage"));
	    					//Select dropdown3= new Select(baggagesNum);	
	    					//dropdown3.selectByVisibleText("Gói (Bag) 15 kg - 160.000đ");	
	    					// dropdown3.selectByIndex(1);
	    					//TimeUtils.sleep(5);		
	    					
	    					//Nhap thong tin khach hang
	    					//tim the danh sach khach hang
	    					WebElement listCustomerDiv = driver.findElement(By.cssSelector(".btn.btn-customer-booker-cb.select-customer"));		
	    					listCustomerDiv.click();
	    					//Chon một khách hàng từ danh sách
	    					WebElement listGuest = driver.findElement(By.cssSelector(".list-guest"));
	    					List<WebElement> listGuestArray = listGuest.findElements(By.cssSelector(".flex-2"));
	    					WebElement firstGuest = listGuestArray.get(0);
	    					firstGuest.click();
	    				//	System.out.println(firstGuest.getText());
	    					
	    					
	    					//Bỏ checkbox Mua bảo hiểm			    					

	    					WebElement checkbox = driver.findElement(By.cssSelector(".noselect > div:nth-child(4)"));	    					
	    			        JavascriptExecutor js = (JavascriptExecutor)driver;		
	    			        js.executeScript("arguments[0].click();",checkbox);
	    					
	    					//Chon phuong thuc thanh toan
	    					//Tim the pttt
	    					TimeUtils.sleep(2);	
	    					WebElement paymentMethodDiv = driver.findElement(By.cssSelector(".row"));	    					
	    					WebElement paymentATM = paymentMethodDiv.findElement(By.cssSelector("#payment-method-3"));
	    					paymentATM.click();
	    					// click nut thanh toan
	    					WebElement paymentButton = driver.findElement(By.cssSelector(".flight-initiate-checkout"));
	    					paymentButton.click();				
	    					TimeUtils.sleep(5);		
	    					
	    					//an nut Xac nhan dong y thanh toan
	    					WebElement confirmBtnDiv = driver.findElement(By.cssSelector(".modal-footer"));
	    					WebElement confirmBtn = confirmBtnDiv.findElement(By.cssSelector(".modal-footer > button:nth-child(2)"));
	    					confirmBtn.click();
	    					TimeUtils.sleep(20);		
	    					
	    				    //Chon thanh toan ATM
	    					WebElement atmMethod = driver.findElement(By.cssSelector(".btn-list-option.collapsed.paytype-localbank"));
	    					atmMethod.click();    					
	    				  	
	    					//Chon ngan hang NCB
	    					WebElement ncbBank = driver.findElement(By.cssSelector("#NCB"));
	    					ncbBank.click();   					
	    					
	    					TimeUtils.sleep(3);
	    				
	    					WebElement cardNumber = driver.findElement(By.cssSelector("#card_number_mask"));
	    					cardNumber.click();
	    					cardNumber.sendKeys("9704198526191432198");
	    					
	    					WebElement cardDate = driver.findElement(By.cssSelector("#cardDate"));
	    					cardDate.click();
	    					
	    					WebElement month = driver.findElement(By.cssSelector("#a-month-7"));
	    					month.click();
	    					
	    					WebElement year = driver.findElement(By.cssSelector("#a-year-2015"));
	    					year.click();
	    					
	    					WebElement cardHolder = driver.findElement(By.cssSelector("#cardHolder"));
	    					cardHolder.click();
	    					cardHolder.sendKeys("NGUYEN VAN A");
	    						    					
	    					WebElement submitButton = driver.findElement(By.cssSelector("#btnSubmit"));
	    					submitButton.click();
	    					TimeUtils.sleep(5);
	    					
	    					WebElement otpValue = driver.findElement(By.cssSelector("#otpvalue"));
	    					otpValue.click();
	    					otpValue.sendKeys("123456");
	    					
	    					WebElement submitPayment = driver.findElement(By.cssSelector("#btnConfirm"));
	    					submitPayment.click();
	    					TimeUtils.sleep(20);
	    					
	    					
	    					List<WebElement> successElement = driver.findElements(By.cssSelector(".ng-binding"));	    					
	    					Assert.assertEquals(successElement.get(1).getText(),"Giao dịch thành công.");
	    					 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				           // Code to save screenshot at desired location
							FileUtils.copyFile(scrFile, new File("C:\\Users\\Asrock.DESKTOP-1D8CK3P\\eclipse-workspace\\BookerActions\\paymentsuccess.png"));
	    					
	    					
	    }		

		//------------------------------------------- Method 4--- Thuc hien mua them hanh ly cho mot don hang - mac dinh tai khoan da co don hang thanh cong
		//@Parameters({"username","password"})
		//@Test(priority = 4, enabled = true)		
		public void AddBaggage (String username, String password)
		{
			System.out.println("3. Thuc hien mua them hanh ly cho mot don hang - mac dinh tai khoan da co don hang thanh cong");
			BookerLogin (username, password);
			
			WebElement menu = driver.findElement(By.cssSelector(".dropdown.user-menu"));
			menu.click();
			TimeUtils.sleep(1);
			
			WebElement sale = driver.findElement(By.cssSelector(".huf-uf-item.ng-scope"));
			sale.click();
			TimeUtils.sleep(3);
			
			List<WebElement> orderManagement = driver.findElements(By.cssSelector(".fa.fa-angle-right"));
			orderManagement.get(2).click();
			TimeUtils.sleep(2); 
			
		
			WebElement successOder = driver.findElement(By.cssSelector(".bm-bi-i-status.success"));
			successOder.click();
			TimeUtils.sleep(2);
			
			
			WebElement addBaggage = driver.findElement(By.cssSelector(".item-action"));
			addBaggage.click();
			TimeUtils.sleep(2);
			
			List<WebElement> baggageListDivArray = driver.findElements(By.cssSelector(".ng-scope"));			
			List<WebElement> baggageList = baggageListDivArray.get(4).findElements(By.cssSelector(".baggage-name"));
			List<WebElement> disableList = baggageListDivArray.get(4).findElements(By.cssSelector(".disabled"));			
			int size = disableList.size();			
			System.out.println(size);
			if(size == 0) 
			{
				baggageList.get(size+1).click();
				System.out.println(baggageList.get(size+1).getText());
			}
			else
			{
			    baggageList.get(size).click();
			    System.out.println(baggageList.get(size).getText());
			}			
			TimeUtils.sleep(2);
			
			WebElement btnConfirm = driver.findElement(By.cssSelector(".btn.btn-default.btn-confirm.ng-scope"));
			btnConfirm.click();
			TimeUtils.sleep(2);
			
			List<WebElement> creditRadioArray = driver.findElements(By.cssSelector(".ng-binding"));
			WebElement creditRadio = creditRadioArray.get(3);
			creditRadio.click();
			TimeUtils.sleep(2);
			
			WebElement btnSubmit = driver.findElement(By.cssSelector(".btn.btn-default.btn-confirm"));
			btnSubmit.click();
			TimeUtils.sleep(2);
			
			WebElement btnConfirm2 = driver.findElement(By.cssSelector(".btn.btn-success"));
			btnConfirm2.click();
			TimeUtils.sleep(10);
			
			List<WebElement> successElement = driver.findElements(By.cssSelector(".ng-binding"));	    					
			Assert.assertEquals(successElement.get(1).getText(),"Giao dịch thành công");
					
			System.out.println("Thuc hien mua hanh ly thanh cong");		
			 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Code to save screenshot at desired location
				FileUtils.copyFile(scrFile, new File("C:\\Users\\Asrock.DESKTOP-1D8CK3P\\eclipse-workspace\\BookerActions\\themhanhlythanhcong.png"));
			
		}

		//------------------------------------------- Method 5--- Thuc hien doi hanh trinh cho mot don hang - mac dinh tai khoan da co don hang thanh cong
	//	@Parameters({"username","password"})
	//	@Test(priority = 5, enabled = true)	
		public void ChangeItinerary (String username, String password)
		{
			System.out.println("4. Thuc hien doi hanh trinh cho mot don hang - mac dinh tai khoan da co don hang thanh cong");
			BookerLogin (username, password);
			
			WebElement menu = driver.findElement(By.cssSelector(".dropdown.user-menu"));
			menu.click();
			TimeUtils.sleep(1);
			
			WebElement sale = driver.findElement(By.cssSelector(".huf-uf-item.ng-scope"));
			sale.click();
			TimeUtils.sleep(3);
			
			List<WebElement> orderManagement = driver.findElements(By.cssSelector(".fa.fa-angle-right"));
			orderManagement.get(2).click();
			TimeUtils.sleep(2); 
			
		
			WebElement successOder = driver.findElement(By.cssSelector(".bm-bi-i-status.success"));
			successOder.click();
			TimeUtils.sleep(2);
			
			
			List<WebElement> actionList = driver.findElements(By.cssSelector(".item-action"));
			WebElement changeItinerary = actionList.get(1);			
			changeItinerary.click();
			TimeUtils.sleep(2);
			
			//luu ngay cua ve da chon
        	String ticketDate = driver.findElement(By.cssSelector(".col-sm-12.no-padding.fil-date-departure.ng-binding")).getText();
        	//tach bo dau - phan cach giua ngay, thang, nam, ta duoc mang gom cac phan tu
        	String[] dateArray = ticketDate.split("-");
        	//bo qua dau cach o dau, ta lay ki tu thu 1 va 2 -> tra ve la dd
        	String dateChain = dateArray[0].substring(1,2);
        	      	
        	
			WebElement editIcon = driver.findElement(By.cssSelector(".fi-right.ng-scope"));			
			WebElement editImage = editIcon.findElement(By.tagName("img"));
			editImage.click();
            TimeUtils.sleep(3);           
            
            try
            {
            	//xu ly doi hanh trinh qua trip credit
            	WebElement listTicketDiv = driver.findElement(By.cssSelector(".list-ticket"));            	            	
            	List<WebElement> listTicket = listTicketDiv.findElements(By.cssSelector(".item-ticket.ng-scope"));
            	//chon ngay cach ngay da chon 1 ngay
            	            	
            	int date = Integer.parseInt(dateChain);
            	date = date+1;
            	WebElement datePicker = driver.findElement(By.cssSelector("#profit-from-date"));
            	datePicker.click();
            	TimeUtils.sleep(2);          	
            	WebElement tableCheckoutDate = driver.findElement(By.cssSelector(".uib-daypicker")); 
            	List<WebElement> checkoutDays = tableCheckoutDate.findElements(By.tagName("td"));		
				for (WebElement day : checkoutDays) 
				{					
					int dateTemp = Integer.parseInt(day.getText());				
					if (dateTemp == date) 
					{				
						day.click();
						break;
					}
				}
				TimeUtils.sleep(10); 
				//chon mot ve
				WebElement selectNew = driver.findElement(By.cssSelector(".item-ticket.ng-scope"));
				selectNew.click();
				TimeUtils.sleep(2);
				List<WebElement> paymentMethods = driver.findElements(By.cssSelector(".col-sm-12.ii-pm-table"));
				paymentMethods.get(2).click();
				TimeUtils.sleep(2);
				WebElement continueBtn = driver.findElement(By.cssSelector(".btn.btn-default.btn-confirm"));
				continueBtn.click();
				TimeUtils.sleep(3);
				WebElement confirmBtn = driver.findElement(By.cssSelector(".btn.btn-success"));
				confirmBtn.click();
				TimeUtils.sleep(10);
				List<WebElement> successElement = driver.findElements(By.cssSelector(".ng-binding"));	    					
				Assert.assertEquals(successElement.get(1).getText(),"Giao dịch thành công");
				 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Code to save screenshot at desired location
				FileUtils.copyFile(scrFile, new File("C:\\Users\\Asrock.DESKTOP-1D8CK3P\\eclipse-workspace\\BookerActions\\doihanhtrinhthanhcong.png"));		
				System.out.println("Thuc hien doi hanh trinh thanh cong");				
						
            }	
            catch(Exception e)
            {
            	WebElement failedNoti = driver.findElement(By.cssSelector(".message.ng-binding"));
                if (failedNoti.isDisplayed()==true)
                {
                	//hien thi thong bao tra ve va thoat testcase
                	System.out.println(failedNoti.getText());
                	return;
                }
            }            
		}

		//------------------------------------------- Method 6--- Thuc hien Gui lai email xac nhan - mac dinh tai khoan da co don hang thanh cong
	//	@Parameters({"username","password"})
	//	@Test(priority = 6, enabled = true)	
		public void ResendConfirmEmail (String username, String password)
		{
			System.out.println(". Thuc hien Gui lai email xac nhan - mac dinh tai khoan da co don hang thanh cong");
			BookerLogin (username, password);
			
			WebElement menu = driver.findElement(By.cssSelector(".dropdown.user-menu"));
			menu.click();
			TimeUtils.sleep(1);
			
			WebElement sale = driver.findElement(By.cssSelector(".huf-uf-item.ng-scope"));
			sale.click();
			TimeUtils.sleep(3);
			
			List<WebElement> orderManagement = driver.findElements(By.cssSelector(".fa.fa-angle-right"));
			orderManagement.get(2).click();
			TimeUtils.sleep(2); 
			
		
			WebElement successOder = driver.findElement(By.cssSelector(".bm-bi-i-status.success"));
			successOder.click();
			TimeUtils.sleep(2);
			
			
			List<WebElement> actionList = driver.findElements(By.cssSelector(".item-action"));
			WebElement changeItinerary = actionList.get(3);			
			changeItinerary.click();
			TimeUtils.sleep(2);	
			
			WebElement textEmail = driver.findElement(By.cssSelector(".ng-pristine.ng-untouched.ng-valid"));
			textEmail.sendKeys("traxanh122016@gmail.com");
			WebElement sendBtn = driver.findElement(By.cssSelector(".btn.btn-default.btn-confirm"));
			sendBtn.click();
			TimeUtils.sleep(2);	
			
			WebElement okBtn = driver.findElement(By.cssSelector(".btn.btn-default.btn-confirm"));
			okBtn.click();
			TimeUtils.sleep(2);	
			
		}
		
		//------------------------------------------- Method 7 -- Thuc hien Hoan huy - mac dinh tai khoan da co don hang thanh cong
		//	@Parameters({"username","password"})
		//	@Test(priority = 7, enabled = true)	
		public void RefundRequest (String username, String password)
			{
				System.out.println(". Thuc hien Hoan huy - mac dinh tai khoan da co don hang thanh cong");
				BookerLogin (username, password);
				
				WebElement menu = driver.findElement(By.cssSelector(".dropdown.user-menu"));
				menu.click();
				TimeUtils.sleep(1);
				
				WebElement sale = driver.findElement(By.cssSelector(".huf-uf-item.ng-scope"));
				sale.click();
				TimeUtils.sleep(3);
				
				List<WebElement> orderManagement = driver.findElements(By.cssSelector(".fa.fa-angle-right"));
				orderManagement.get(2).click();
				TimeUtils.sleep(2); 
				
			
				WebElement successOder = driver.findElement(By.cssSelector(".bm-bi-i-status.success"));
				successOder.click();
				TimeUtils.sleep(2);
				
				
				List<WebElement> actionList = driver.findElements(By.cssSelector(".item-action"));
				WebElement changeItinerary = actionList.get(4);			
				changeItinerary.click();
				TimeUtils.sleep(2);	
				
				
				WebElement acceptBtn = driver.findElement(By.cssSelector(".btn.btn-default.btn-confirm"));
				acceptBtn.click();
				TimeUtils.sleep(2);	
				
				WebElement okBtn = driver.findElement(By.cssSelector(".btn.btn-default.btn-confirm"));
				okBtn.click();
				TimeUtils.sleep(2);	
				
			}
			//------------------------------------------- Method 8 -- Yeu cau xuat hoa don VAT - mac dinh tai khoan da co don hang thanh cong
				@Parameters({"username","password"})
				@Test(priority = 8, enabled = true)	
		public void VATRequest (String username, String password)
				{
					System.out.println(". Thuc hien Yeu cau xuat hoa don VAT - mac dinh tai khoan da co don hang thanh cong");
					BookerLogin (username, password);
					
					WebElement menu = driver.findElement(By.cssSelector(".dropdown.user-menu"));
					menu.click();
					TimeUtils.sleep(1);
					
					WebElement sale = driver.findElement(By.cssSelector(".huf-uf-item.ng-scope"));
					sale.click();
					TimeUtils.sleep(3);
					
					List<WebElement> orderManagement = driver.findElements(By.cssSelector(".fa.fa-angle-right"));
					orderManagement.get(2).click();
					TimeUtils.sleep(2); 
					
				
					WebElement successOder = driver.findElement(By.cssSelector(".bm-bi-i-status.success"));
					successOder.click();
					TimeUtils.sleep(2);
					
					
					List<WebElement> actionList = driver.findElements(By.cssSelector(".item-action"));
					WebElement changeItinerary = actionList.get(5);			
					changeItinerary.click();
					TimeUtils.sleep(2);	
					
					try
					{
					List<WebElement> Fields = driver.findElements(By.cssSelector(".form-control.ng-pristine.ng-untouched.ng-valid.ng-valid-maxlength"));
					
					WebElement unitnameField = Fields.get(0);
					unitnameField.click();
					unitnameField.sendKeys("Công ty Cổ phần Phát triển Công nghệ Thương mại và Du lịch");
					TimeUtils.sleep(2);														
					
					WebElement taxCodeField = Fields.get(1);
					taxCodeField.click();
					taxCodeField.sendKeys("0101248141");
					TimeUtils.sleep(2);	
					
					WebElement addressField = Fields.get(2);
					addressField.click();
					addressField.sendKeys("81A Trần Quốc Toản, Hoàn Kiếm, Hà Nội ");
					TimeUtils.sleep(2);	
					
					WebElement receivedNameField = Fields.get(3);
					receivedNameField.click();
					receivedNameField.sendKeys("Công ty Cổ phần Phát triển Công nghệ Thương mại và Du lịch");
					TimeUtils.sleep(2);	
					
					WebElement receivedAddressField = Fields.get(4);
					receivedAddressField.click();
					receivedAddressField.sendKeys("81A Trần Quốc Toản, Hoàn Kiếm, Hà Nội");
					TimeUtils.sleep(2);	
					
					WebElement emailField = Fields.get(5);
					emailField.click();
					emailField.sendKeys("hotro@tripi.vn");
					TimeUtils.sleep(2);	
					
					WebElement telephoneNumberField = Fields.get(6);
					telephoneNumberField.click();
					telephoneNumberField.sendKeys("0245655252");
					TimeUtils.sleep(2);	
					
					WebElement noteField = Fields.get(7);
					noteField.click();
					noteField.sendKeys("Nhờ giao hóa đơn vào giờ hành chính");
					TimeUtils.sleep(2);					
					
					WebElement updateBtn = driver.findElement(By.cssSelector(".btn.default-confirm-button"));
					updateBtn.click();
					TimeUtils.sleep(2);	
					
					WebElement sendBtn = driver.findElement(By.cssSelector(".btn.default-confirm-button"));
					sendBtn.click();
					TimeUtils.sleep(3);	
					
				//	List<WebElement> successElement = driver.findElements(By.cssSelector(".ng-binding"));	    					
				//	Assert.assertEquals(successElement.get(1).getText(),"Yêu cầu xuất hóa đơn VAT đã được gửi thành công");
					
					}
					catch(Exception e)
					{
						WebElement notice = driver.findElement(By.cssSelector(".modal-body.ng-binding"));
						System.out.println(notice.getText());
						WebElement closeBtn = driver.findElement(By.cssSelector(".btn.default-cancel-button"));
						closeBtn.click();
						TimeUtils.sleep(2);	
					}
					
				}
*/
			}


