package utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import actions.BaseTest;

public class TimeUtils  
{
	public static void sleep(int second) 
	{
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int changeStringtoMoney (List<WebElement> list, int index)
	{
		int Money = 0; 
		String text = (list.get(index)).getText();
		text = text.replaceAll("\\D+","");	
		Money = Integer.parseInt(text);
	//	System.out.println(Money);
		return Money;
	}
	
}
