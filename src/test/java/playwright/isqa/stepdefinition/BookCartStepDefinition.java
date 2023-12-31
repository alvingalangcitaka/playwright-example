package playwright.isqa.stepdefinition;

import com.microsoft.playwright.*;
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
    private Scenario scenario;
    private Browser browser;
    private BrowserContext context;

    @Given("user already logged in")
    public void userAlreadyLoggedIn() {
        //navigate to application
        page.navigate("https://bookcart.azurewebsites.net/");

        //run the automation
        page.getByText("Login").click();
        page.getByLabel("Username").fill("ortoni");
        page.getByLabel("Password").fill("Pass1234$");
        byte[] screenshotBytes = page.screenshot();
        scenario.attach(screenshotBytes, "image/png", "login page");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                .setName("Login")).last().click();
    }

    @When("user searches for a {string} book")
    public void userSearchesForABook(String bookTitle) throws InterruptedException {
        Thread.sleep(1000);
        page.getByPlaceholder("Search books", new Page.GetByPlaceholderOptions()
                .setExact(false)).type(bookTitle);
        byte[] screenshotBytes = page.screenshot();
        scenario.attach(screenshotBytes, "image/png", "search result");
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
    public void before(Scenario scenario) {
        // put scenario on the field
        this.scenario = scenario;

        // create playwright and browser instances
        playwright = Playwright.create();
        BrowserType.LaunchOptions setHeadless = new BrowserType.LaunchOptions().setHeadless(false);
        browser = playwright.chromium().launch(setHeadless);
        context = browser.newContext(
                new Browser.NewContextOptions().setRecordVideoDir(Paths.get("videos"))
        );
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true));
        page = context.newPage();
    }

    @After
    public void after(Scenario scenario) {
        byte[] screenshotBytes = page.screenshot();
        scenario.attach(screenshotBytes, "image/png", "final screenshot");

        // stop tracing
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("traces/"
                        + System.currentTimeMillis()
                        + "-trace.zip")));

        //close browsers and playwright instances
        context.close();
        playwright.close();
    }
}
