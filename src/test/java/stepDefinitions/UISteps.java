package stepDefinitions;


import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Predicate;

import FileReader.ConfigfileReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import manager.DriverManager;
import page.ToDoPage;

public class UISteps {
	
	public WebDriver driver;
	DriverManager drivermanager;
	ConfigfileReader filereader;
	ToDoPage todopage;
	static  Predicate<WebDriver> pageloadstatus=null;
	JavascriptExecutor js;
	int item_left = 0;
	private static Response response;
	private static String jsonString;
	private static final String BASE_URL = "https://jsonplaceholder.typicode.com/todos/";
	
	
	@Before
	public void setup() {
		drivermanager = new DriverManager();
		filereader = new ConfigfileReader();
		driver = drivermanager.getDriver();
		todopage = new ToDoPage(driver);
	}

	/**
	 * UI Automation steps
	 */

	@Given("user open ToDo application")
	public void user_open_to_do_application() {
		filereader.getBrowser();
		driver.get(filereader.getApplicationUrl());

	}
	
	
	@When("user add a new item {string} to the list")
	public void user_add_a_new_item_to_the_list(String item) throws InterruptedException {
		 //item_left = todopage.item_left();
		 todopage.waitforpageload(driver, 500);
		 Thread.sleep(500);
		 todopage.addTask(item);
	}
	@Then("the item {string} is added")
	public void the_item_is_added(String item) throws InterruptedException {
		todopage = new ToDoPage(driver);
		 Assert.assertTrue(todopage.check_label(item));
	}
	@Then("remaining item count should be updated")
	public void remaining_item_count_should_be_updated() {
		int item_remaining = todopage.item_left();
		 Assert.assertEquals(item_remaining, item_left + 1);
		 System.out.println(item_remaining + " remaining updated, test pass");
	}
	@Then("filter is set to all")
	public void filter_is_set_to_all() {
		Assert.assertEquals("All", todopage.find_filter());
	}
	@Then("clear completed is unavailable.")
	public void clear_completed_is_unavailable() {
		 Assert.assertFalse(todopage.check_clearcompleted());
	}
	
	
	@When("user add multiple items {string} {string} to the list")
	public void user_add_multiple_items_to_the_list(String item1, String item2) throws InterruptedException {
		todopage.waitforpageload(driver, 500);
		Thread.sleep(500);
		 todopage.addTask(item1);
		todopage.addTask(item2);
	}
	@When("{string} is marked as complete")
	public void is_marked_as_complete(String item) {
	    todopage.mark_complete(item);
	}
	@Then("{string} is marked as active")
	public void is_marked_as_active(String item) {
	    todopage.filter_Active();
	    Assert.assertTrue(todopage.check_label(item));
	    System.out.println("active tasks checked, test pass");
	}
	@Then("clear completed is available")
	public void clear_completed_is_available() {
		Assert.assertTrue(todopage.check_clearcompleted());
		System.out.println("clear completed is present, test pass");
	}

	
	@When("filter is set to active")
	public void filter_is_set_to_active() {
	    todopage.filter_Active();
	}
	@Then("item {string} should not display.")
	public void completed_item_should_not_display(String item) {
	    Assert.assertFalse(todopage.check_label(item));
	    System.out.println("item "+ item + " not displayed, test pass");
	}
	
	
	@When("filter is set to completed")
	public void filter_is_set_to_completed() {
	   todopage.filter_Completed();
	}
	@Then("item {string} should  display")
	public void completed_item_should_display(String item) {
		 Assert.assertTrue(todopage.check_label(item));
		 System.out.println("item "+ item + " displayed, test pass");
	}
	
	
	@When("clear complete is clicked")
	public void clear_complete_is_clicked() {
	   todopage.clear_completed();
	}


	@When("few or none of the items are completed")
	public void few_or_none_of_the_items_are_completed() {
	   
		int item_left = todopage.item_left();
		if(item_left == 0) {
			System.out.println("all items are completed, incorrect data setup");
		}
	}
	@When("clear all toggle is clicked")
	public void clear_all_toggle_is_clicked() {
	    todopage.clear_unclear();
	}
	@Then("all items are marked complete")
	public void all_items_are_marked_complete() {
		Assert.assertTrue(todopage.check_completed());
		System.out.println("items completed, test pass");
	}
	@Then("list summary is updated as {int}.")
	public void list_summary_is_updated_as(Integer int1) {
		int item_remaining = todopage.item_left();
		 Assert.assertEquals(item_remaining, 0);
		 System.out.println("item remaining is "+item_remaining + " test pass");
	}

	
	@When("all of the items are completed")
	public void all_of_the_items_are_completed() {
		int item_remaining = todopage.item_left();
	    if(item_remaining != 0) {
	    	System.out.println("incorrect data setup");
	    	Assert.assertTrue(false);
	    }
	}
	@Then("all items are marked active")
	public void all_items_are_marked_active() {
	    todopage.filter_All();
	    int total = todopage.item_count();
	    todopage.filter_Active();
	    int active = todopage.item_count();
	    Assert.assertEquals(total, active);
	    System.out.println("All items marked active, test pass");
	}
	@Then("remaining item count should not be {int}.")
	public void remaining_item_count_should_not_be(Integer int1) {
		int item_remaining = todopage.item_left();
		boolean flag = false;
		 if(item_remaining != 0) {
			 flag = true;
		 }
		 Assert.assertTrue(flag);
		 System.out.println("Count updated, test pass");
	}
	
	
	@When("edit item {string} to {string}")
	public void edit_item_to(String item1, String item2) {
	    todopage.edit_item(item1, item2);
	}
	@Then("new name {string} is updated")
	public void new_name_is_updated(String item) {
	   Assert.assertTrue(todopage.check_label(item));
	}





	/**
	 * API steps
	 */
	
	@Given("user calls ToDo application via API")
	public void user_calls_ToDo_application_via_API() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.get();
		jsonString = response.asString();
	}
	@When("user add item {string} to the list")
	public void user_add_item_itemA_to_the_list(String item1) {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.body("{ \"userId\": \"" + "1" + "\", \"title\":\"" + item1 + "\", \"completed\":\"" + "false" + "\"}").post();
	}
	@Then("the item is added")
	public void the_item_is_added() {
		Assert.assertEquals(201, response.getStatusCode());
		System.out.println("Item added" + response.getStatusCode());
	}
	@When("user delete the newly added item")
	public void user_delete_the_newly_added_item() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.delete("201");
	}
	@Then("item is removed")
	public void item_is_removed() {
		Assert.assertEquals(200, response.getStatusCode());
		System.out.println("Item deleted" + response.getStatusCode() );
	}
	
	
	@When("user search for item with id {string} in the list")
	public void user_search_for__item_in_the_list(String id) {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.get(id);
		jsonString = response.asString();
	}
	@Then("item details are updated for id {string}.")
	public void item_details_are_updated(String id) {
		Assert.assertEquals(200, response.getStatusCode());
		System.out.println(jsonString);
		int new_id = JsonPath.from(jsonString).get("id");
		Assert.assertEquals(new_id, Integer.parseInt(id));
	}

	
	@After 
	 public void quit() throws IOException {
		  drivermanager.closeDriver();

	}

}
