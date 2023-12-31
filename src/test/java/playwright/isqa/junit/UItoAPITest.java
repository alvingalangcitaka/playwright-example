package playwright.isqa.junit;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.Test;

public class UItoAPITest {

    @Test
    public void runSimpleAPI(){
        // create playwright and browser instances
        Playwright playwright = Playwright.create();
        BrowserType.LaunchOptions setHeadless = new BrowserType.LaunchOptions().setHeadless(false);
        Browser browser = playwright.chromium().launch(setHeadless);
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();

        //navigate to application
        page.navigate("https://www.jakmall.com/login");

        //run the login automation
        page.getByLabel("Email").fill("alvin.citaka@gmail.com");
        page.getByLabel("Password").fill("ok354123");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                .setName("Login")).last().click();

        APIResponse response = browserContext.request().get("https://www.jakmall.com/cart/header",
                RequestOptions.create()
                        .setHeader("x-requested-with", "XMLHttpRequest")
        );

        System.out.println(response.text());

        //close browsers and playwright instances
        playwright.close();

    }
}
