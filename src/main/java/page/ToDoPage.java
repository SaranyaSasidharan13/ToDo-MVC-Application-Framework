package page;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


public class ToDoPage {
	
	@FindBy(xpath = "//input[@class='new-todo']")
	private WebElement newtoDo;
	@FindBy(xpath = "//a[text()='All']")
	private WebElement filterAll;
	@FindBy(xpath = "//a[text()='Active']")
	private WebElement filterActive;
	@FindBy(xpath = "//a[text()='Completed']")
	private WebElement filterCompleted;
	@FindAll(@FindBy(xpath = "//ul[@class='todo-list']/li[1]/div/input"))
	private List<WebElement> markComplete;
	@FindBy(xpath = "//label[@for='toggle-all']")
	private WebElement clearAll;
	@FindBy(xpath = "//span[@class='todo-count']/strong")
	private WebElement count;
	@FindBy(xpath = "//li[1]/div/button[@class='destroy']")
	private WebElement remove;
	@FindAll(@FindBy(xpath = "//ul[@class='todo-list']/li"))
	private List<WebElement> itemCount;
	@FindBy(xpath = "//button[@class='clear-completed']")
	private WebElement clearcomplete;
	@FindAll(@FindBy(xpath = "//ul/li/div/label"))
	private List<WebElement> items;
	@FindBy(xpath = "//li/a[@class='selected']")
	private WebElement currentFilter;
	@FindAll(@FindBy(xpath = "//ul[@class='todo-list']/li"))
	private List<WebElement> checkComplete;
	@FindBy(xpath = "//input[@class='edit']")
	private WebElement editItem;
	
	
	WebDriver driver;
	
	public ToDoPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	public void addTask(String item) {
		newtoDo.sendKeys(item);
		newtoDo.sendKeys(Keys.ENTER);
	}
	
	public void filter_All() {
		filterAll.click();
	}
	
	public void filter_Active() {
		filterActive.click();
	}
	
	public void filter_Completed() {
		filterCompleted.click();
	}
	
	
	public void mark_complete(String item) {
		for(int i = 0; i< items.size(); i++) {
			if(items.get(i).getText().equalsIgnoreCase(item))
			{
				markComplete.get(i).click();
				break;
			}
		}
	}
	
	public int item_left() {
		try {
		return Integer.parseInt(count.getText());
		}
		catch(Exception e) {
			return 0;
		}
	}
	
	public void clear_unclear() {
		clearAll.click();
	}
	
	public void remove_item() {
		Actions action = new Actions(driver);
		action.moveToElement(remove).perform();
		remove.click();
	}
	
	public int item_count() {
		return itemCount.size();
	}
	
	public void clear_completed() {
		clearcomplete.click();
	}
	
	/**
	 * Verify mentioned item is present
	 * @param item
	 * @return
	 */
	public boolean check_label(String item) {
		for(WebElement e: items)
		{
			if(e.getText().equalsIgnoreCase(item)) {
				System.out.println(e.getText() +" added");
				return true;
		}
			
			}
		return false;
	}
	
	public String find_filter() {
		System.out.println("current filter is "+ currentFilter.getText());
		return currentFilter.getText();
	}
	
	
	public boolean check_clearcompleted() {
		try {
		if(clearcomplete.isDisplayed()) {
			return true;
		}
		}
		catch(Exception e)
		{
		System.out.println("clear completed not present");
		}
		return false;
	}
	
	/**
	 * verify if there is any item with active status
	 * @return
	 */
	public boolean check_completed() {
		for(WebElement e: checkComplete) {
			if(e.getAttribute("class").equalsIgnoreCase("completed")) {
				continue;
			}
			else
				return false;
		}
		return true;
	}
	
	public void edit_item(String old, String change) {
		for(WebElement e: items) {
			if(e.getText().equalsIgnoreCase(old)) {
				Actions action = new Actions(driver);
				action.doubleClick(e).perform();
				action.clickAndHold(editItem).sendKeys(Keys.CONTROL, "a");
				action.clickAndHold(editItem).sendKeys(change).perform();
				editItem.sendKeys(Keys.ENTER);
				break;
			}
		}
	}
	
	/**
	 * Wait for the page to load before doing any operation
	 * @param driver
	 * @param timeout
	 */
	public void waitforpageload(WebDriver driver,int timeout)
	{
		ExpectedCondition<Boolean> condition=new ExpectedCondition<Boolean>()
				{
					public Boolean apply(WebDriver driver) {
						// TODO Auto-generated method stub
						return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
					}
			
				};
				Wait wait=new FluentWait(driver).withTimeout(timeout, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS);
				wait.until(condition);
	}
	

}
