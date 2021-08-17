@tag
Feature: End to End test for ToDo Application


  @UITest
 
  Scenario Outline: User is able to add to do items to the list
    Given user open ToDo application  
    When user add a new item "meeting" to the list
    Then the item "meeting" is added
    And remaining item count should be updated
    And filter is set to all
    And clear completed is unavailable.
    
  @UITest
  Scenario Outline: Item completion changes the list
    Given user open ToDo application  
    When user add multiple items "meeting" "lunch" to the list
    And "meeting" is marked as complete
    Then "lunch" is marked as active 
    And remaining item count should be updated
    And clear completed is available
    
	@UITest
  Scenario Outline: Completed items should not be visible in active filter
    Given user open ToDo application  
    When user add a new item "meeting" to the list
    And "meeting" is marked as complete
    And filter is set to active
   	Then item "meeting" should not display.
   	
   	@UITest
  Scenario Outline: Completed items should visible in completed filter
    Given user open ToDo application  
    When user add multiple items "meeting" "lunch" to the list
    And "meeting" is marked as complete
    And filter is set to completed
   	Then item "meeting" should  display
   	And item "lunch" should not display.
   	
   @UITest
  Scenario Outline: Completed items can be cleared
    Given user open ToDo application  
    When user add multiple items "meeting" "lunch" to the list
    And "meeting" is marked as complete
    And clear complete is clicked
   	Then item "lunch" should  display
   	And item "meeting" should not display.
   	
   @UITest
  Scenario Outline: Clear all should mark all items complete
    Given user open ToDo application 
    When user add a new item "meeting" to the list
    And few or none of the items are completed 
    And clear all toggle is clicked
   	Then all items are marked complete
   	And list summary is updated as 0.
   
   @UITest
   Scenario Outline: unclear all should mark all items active
    Given user open ToDo application 
    When user add a new item "meeting" to the list
    And "meeting" is marked as complete
    And all of the items are completed 
    And clear all toggle is clicked
   	Then all items are marked active
   	And remaining item count should not be 0.
   	
   	@UITest
   	 @FirstTest
   	Scenario: Edit a to do item
   	Given user open ToDo application 
    When user add a new item "meeting" to the list
    And edit item "meeting" to "google meet"
    Then new name "google meet" is updated
   	#APITest
   	
  @APITest
  Scenario Outline: User is able to add and delete a to do item.
	    Given user calls ToDo application via API
    When user add item "meeting" to the list
    Then the item is added
    When user delete the newly added item
    Then item is removed
      
    @APITest  
	Scenario: User is able to search an item in to do list
		Given user calls ToDo application via API 
    When user search for item with id "2" in the list
    Then item details are updated for id "2".
    
