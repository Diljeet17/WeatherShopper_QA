# WeatherShopper_QA
This is the UI Test Automation project for Weather Shopper website.

Programming Language Used: JAVA

Framework/Design Pattern: Page Object Model (POM). In POM, each web page has its individual class file to store its Locators, Methods.

Project: Maven based in which ‘pom.xml’ file contains all the dependencies like selenium, testing, json-simple & WebDriverManager.

Installation Perquisites: Java 1.8 or above, Any IDE (Eclipse/IntelliJ etc)

Advantage of using POM: As locators & actions are defined once & can be used multiple Programming times for any number of test cases, this way POM helps us reducing code duplication & improving test case maintenance. 

Framework Structure:
a.	‘pages’ package contains all the page files corresponding to application screens
b.	‘utils’ package has JsonUtility.java file to read test data
c.	‘src/test/java’ contains test case
d.	‘resources’ folder contains ‘testData.json’ file
e.	‘test-output’ folder reports, ‘emailable-report.html’ or ‘index.html’ should be checked for results.
f.	‘pom.xml’ contains all the dependencies.
g.	testing.xml file -> this should be used to run test case.

Test Case (this has been automated):
1.	Check Current Temperature on Weather Shopper site
2.	If Current Temperature is below 19 degree celcius then click on ‘Buy Moisturizers’ button, if Current Temperature is above 34 degree celcius then click on ‘Buy Sunscreens’ button.
3.	Based on step 2, If you are on Moisturizers page then, add least expensive item containing ‘aloe’ & then least expensive item containing ‘almond’
Or If you are on Sunscreens page, then add least expensive item of type ‘SPF-50’ & then least expensive item of type ‘SPF-30’.
4.	Click on ‘Cart’ from top right side, Checkout page will be displayed
5.	On Checkout page, verify details of items like Item Name & Price from step 3. Also, verify Total Amount is displayed as actual total of both the items.
6.	Click on ‘Pay with Card’ button, pop up window will be displayed asking Email & Credit Card details.
7.	After entering details from step 6, click on Pay button.
8.	Confirmation page is displayed with success message “Your payment was successful. You should receive a follow-up call from our sales team.”

How to run automated test case: Right click on ‘testing.xml’ file -> Run As -> TestNG Suite.

Test Case Execution Report: report is generated under 'test-output' folder named 'index.html' 

Note: 
1.	As requirement was to automate single test case, hence same test case is mentioned twice in testing.xml file to demonstrate parallel execution, same test case runs on two different browsers Chrome & Firefox parallelly.
2.	Running test case directly from tests-> WeatherShopperTest.java file will not work as test case needs parameter – ‘browser’ which is being passed from testing.xml file, hence we need to run the test from testing.xml file only


