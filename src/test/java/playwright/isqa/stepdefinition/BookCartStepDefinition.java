package playwright.isqa.stepdefinition;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class BookCartStepDefinition {

    private Page page;
    private Playwright playwright;

    @Given("user already logged in")
    public void userAlreadyLoggedIn() {
        //navigate to application
        page.navigate("https://bookcart.azurewebsites.net/");

        //run the automation
        page.getByText("Login").click();
        page.getByLabel("Username").fill("ortoni");
        page.getByLabel("Password").fill("Pass1234$");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                .setName("Login")).last().click();
    }

    @When("user searches for a {string} book")
    public void userSearchesForABook(String bookTitle) throws InterruptedException {
        Thread.sleep(1000);
        page.getByPlaceholder("Search books", new Page.GetByPlaceholderOptions()
                .setExact(false)).type(bookTitle);
        page.getByRole(AriaRole.OPTION).first().click();
    }

    @Then("user find {string} on the search result")
    public void userFindOnTheSearchResult(String bookTitle) {
        assertTrue(
                page.locator("//app-book-card[.//strong[text()='" + bookTitle + "']]")
                        .isVisible()
        );
    }


    @Before
    public void before() {
        // create playwright and browser instances
        playwright = Playwright.create();
        BrowserType.LaunchOptions setHeadless = new BrowserType.LaunchOptions().setHeadless(false);
        page = playwright.chromium().launch(setHeadless).newPage();
    }

    @After
    public void after(Scenario scenario) {
        byte[] screenshotBytes = page.screenshot();
        scenario.attach(screenshotBytes, "image/png", "final screenshot");
        //close browsers and playwright instances
        playwright.close();
    }
}
