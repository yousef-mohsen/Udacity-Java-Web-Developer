package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().clearDriverCache();
		WebDriverManager.chromedriver().clearResolutionCache();
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password) {
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/

		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password) {
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 * <p>
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() throws InterruptedException {
		// Create a test account
		doMockSignUp("Redirection", "Test", "RT", "123");

		Thread.sleep(3000);
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 * <p>
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 * <p>
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL", "Test", "UT", "123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 * <p>
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 * <p>
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File", "Test", "LFT", "123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}


	//verifies that the home page is not accessible without logging in.
	@Test
	public void unloggedHomepageAccess() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}


	/*
	A Selenium test that signs up a new user,
	 logs that user in, verifies that they can access
	  the home page, then logs out and verifies that
	   the home page is no longer accessible.
	 */
	@Test
	public void loginFlow() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doMockSignUp("a", "a", "a", "a");
		doLogIn("a", "a");
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonLogOut")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonLogOut"));
		buttonSignUp.click();

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

	}

	/*
		a Selenium test that logs in an existing
		user, creates a note and verifies that the note
		details are visible in the note list.
				*/
	@Test
	public void createNote() throws InterruptedException {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doMockSignUp("a", "a", "a", "a");
		doLogIn("a", "a");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

		WebElement navNotes = driver.findElement(By.id("nav-notes-tab"));
		navNotes.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-a-new-note")));

		WebElement addNewNote = driver.findElement(By.id("add-a-new-note"));
		addNewNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement title = driver.findElement(By.id("note-title"));
		title.click();
		title.sendKeys("note title");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement description = driver.findElement(By.id("note-description"));
		description.click();
		description.sendKeys("note content");


		WebElement save = driver.findElement(By.id("Save_Changes"));
		save.click();


		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		 navNotes = driver.findElement(By.id("nav-notes-tab"));
		navNotes.click();

		String titleValue = driver.findElement(By.xpath("//table/tbody/tr[1]/th[1]")).getText();
		String descriptionValue = driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]")).getText();

		Assertions.assertEquals("note title", titleValue);
		Assertions.assertEquals("note content", descriptionValue);

	}
	@Test
	public void editNote()
	{
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doMockSignUp("a", "a", "a", "a");
		doLogIn("a", "a");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

		WebElement navNotes = driver.findElement(By.id("nav-notes-tab"));
		navNotes.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-a-new-note")));

		WebElement addNewNote = driver.findElement(By.id("add-a-new-note"));
		addNewNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement title = driver.findElement(By.id("note-title"));
		title.click();
		title.sendKeys("note title");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement description = driver.findElement(By.id("note-description"));
		description.click();
		description.sendKeys("note content");


		WebElement save = driver.findElement(By.id("Save_Changes"));
		save.click();



		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		navNotes = driver.findElement(By.id("nav-notes-tab"));
		navNotes.click();


		WebElement edit = driver.findElement(By.id("noteEdit"));
		edit.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		title = driver.findElement(By.id("note-title"));
		title.clear();
		title.click();
		title.sendKeys("note title changed");

		description = driver.findElement(By.id("note-description"));
		description.clear();
		description.click();
		description.sendKeys("note content changed");

		 save = driver.findElement(By.id("Save_Changes"));
		save.click();


		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		navNotes = driver.findElement(By.id("nav-notes-tab"));
		navNotes.click();



		String titleValue = driver.findElement(By.xpath("//table/tbody/tr[1]/th[1]")).getText();
		String descriptionValue = driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]")).getText();

		Assertions.assertEquals("note title changed", titleValue);
		Assertions.assertEquals("note content changed", descriptionValue);
	}


	/*
	a Selenium test that logs in an existing
	 user with existing notes, clicks the delete note
	  button on an existing note, and verifies that
	   the note no longer appears in the note list.
	 */

	@Test
	public void noteDelete()
	{
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doMockSignUp("a", "a", "a", "a");
		doLogIn("a", "a");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

		WebElement navNotes = driver.findElement(By.id("nav-notes-tab"));
		navNotes.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-a-new-note")));

		WebElement addNewNote = driver.findElement(By.id("add-a-new-note"));
		addNewNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement title = driver.findElement(By.id("note-title"));
		title.click();
		title.sendKeys("note title");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement description = driver.findElement(By.id("note-description"));
		description.click();
		description.sendKeys("note content");


		WebElement save = driver.findElement(By.id("Save_Changes"));
		save.click();



		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		navNotes = driver.findElement(By.id("nav-notes-tab"));
		navNotes.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDelete")));
		navNotes = driver.findElement(By.id("noteDelete"));
		navNotes.click();


		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		navNotes = driver.findElement(By.id("nav-notes-tab"));
		navNotes.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		WebElement table = driver.findElement(By.id("userTable"));
		List<WebElement> tableRows = table.findElements(By.tagName("tr"));

		Assertions.assertEquals(1, tableRows.size());

	}

	/*
	a Selenium test that logs in an existing
	user, creates a credential and verifies that the
	credential details are visible in the credential list.
	 */
	@Test
	public void createCredential()
	{
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doMockSignUp("a", "a", "a", "a");
		doLogIn("a", "a");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

		WebElement navCredentials = driver.findElement(By.id("nav-credentials-tab"));
		navCredentials.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-a-new-credential")));

		WebElement addNewCredential = driver.findElement(By.id("add-a-new-credential"));
		addNewCredential.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement url = driver.findElement(By.id("credential-url"));
		url.click();
		url.sendKeys("www.example.com");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement username = driver.findElement(By.id("credential-username"));
		username.click();
		username.sendKeys("userA");


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement password = driver.findElement(By.id("credential-password"));
		password.click();
		password.sendKeys("password");



		WebElement save = driver.findElement(By.id("credential_save"));
		save.click();


		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		navCredentials = driver.findElement(By.id("nav-credentials-tab"));
		navCredentials.click();

		String urlValue = driver.findElement(By.xpath("//table/tbody/tr[1]/th[1]")).getText();
		String userValue = driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]")).getText();
		String encryptedPasswordValue = driver.findElement(By.xpath("//table/tbody/tr[1]/td[3]")).getText();


		Assertions.assertEquals(urlValue, "www.example.com");
		Assertions.assertEquals(userValue, "userA");
		Assertions.assertNotEquals(encryptedPasswordValue, "password");
		Assertions.assertNotEquals(encryptedPasswordValue, "");

	}

	/*
	a Selenium test that logs in an existing
	 user with existing credentials, clicks the edit
	 credential button on an existing credential,
	  changes the credential data, saves the changes,
	   and verifies that the changes appear in the credential list.
	 */

	@Test
	public void editCredential ()
	{
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doMockSignUp("a", "a", "a", "a");
		doLogIn("a", "a");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

		WebElement navCredentials = driver.findElement(By.id("nav-credentials-tab"));
		navCredentials.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-a-new-credential")));

		WebElement addNewCredential = driver.findElement(By.id("add-a-new-credential"));
		addNewCredential.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement url = driver.findElement(By.id("credential-url"));
		url.click();
		url.sendKeys("www.example.com");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement username = driver.findElement(By.id("credential-username"));
		username.click();
		username.sendKeys("userA");


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement password = driver.findElement(By.id("credential-password"));
		password.click();
		password.sendKeys("password");



		WebElement save = driver.findElement(By.id("credential_save"));
		save.click();


		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		navCredentials = driver.findElement(By.id("nav-credentials-tab"));
		navCredentials.click();



		WebElement edit = driver.findElement(By.id("credentialEdit"));
		edit.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		url = driver.findElement(By.id("credential-url"));
		url.clear();
		url.click();
		url.sendKeys("new url");

		username = driver.findElement(By.id("credential-username"));
		username.clear();
		username.click();
		username.sendKeys("new username");


		password = driver.findElement(By.id("credential-password"));
		String oldEncryptedPassword = password.getText();
		password.clear();
		password.click();
		password.sendKeys("new password");

		save = driver.findElement(By.id("credential_save"));
		save.click();


		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		navCredentials = driver.findElement(By.id("nav-credentials-tab"));
		navCredentials.click();



		String urlValue = driver.findElement(By.xpath("//table/tbody/tr[1]/th[1]")).getText();
		String userValue = driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]")).getText();
		String newEncryptedPasswordValue = driver.findElement(By.xpath("//table/tbody/tr[1]/td[3]")).getText();


		Assertions.assertEquals(urlValue, "new url");
		Assertions.assertEquals(userValue, "new username");
		Assertions.assertNotEquals(newEncryptedPasswordValue, "oldEncryptedPassword");
		Assertions.assertNotEquals(newEncryptedPasswordValue, "");

	}

	/*
	a Selenium test that logs in an existing
	 user with existing credentials, clicks the delete
	  credential button on an existing credential,
	   and verifies that the credential no longer appears
	    in the credential list.
	 */


	@Test
	public void deleteCredential()
	{
		WebDriverWait webDriverWait = new WebDriverWait(driver, 4);
		doMockSignUp("a", "a", "a", "a");
		doLogIn("a", "a");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

		WebElement navCredentials = driver.findElement(By.id("nav-credentials-tab"));
		navCredentials.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-a-new-credential")));

		WebElement addNewCredential = driver.findElement(By.id("add-a-new-credential"));
		addNewCredential.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement url = driver.findElement(By.id("credential-url"));
		url.click();
		url.sendKeys("www.example.com");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement username = driver.findElement(By.id("credential-username"));
		username.click();
		username.sendKeys("userA");


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement password = driver.findElement(By.id("credential-password"));
		password.click();
		password.sendKeys("password");



		WebElement save = driver.findElement(By.id("credential_save"));
		save.click();


		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		navCredentials = driver.findElement(By.id("nav-credentials-tab"));
		navCredentials.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialDelete")));
		navCredentials = driver.findElement(By.id("credentialDelete"));
		navCredentials.click();


		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		navCredentials = driver.findElement(By.id("nav-credentials-tab"));
		navCredentials.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		WebElement table = driver.findElement(By.id("credentialTable"));
		List<WebElement> tableRows = table.findElements(By.tagName("tr"));

		Assertions.assertEquals(1, tableRows.size());



	}
}