import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/*
Мультивендор + модуль "Расширенная карта сайта" 2.6.0.
Работает в браузерах Chrome и Edge (в Firefox не работает).
*/

public class TestRunner {
    public static final String BASIC_URL = "https://trs.test.abt.team/4182mvru/admin.php?dispatch=addons.manage";

    @BeforeMethod
    public void openBrowser()  {
        Configuration.browser = "chrome";
        open(BASIC_URL);
        Configuration.holdBrowserOpen = false;  //не закрываем браузер пока ведём разработку
        Configuration.screenshots = true;       //делаем скриншоты при падении
        WebDriverRunner.getWebDriver().manage().window().maximize(); //окно браузера на весь экран
        $(".btn.btn-primary").click();
        $("#bp_off_bottom_panel").click();
    }
    @AfterMethod
    public void closeBrowser() {Selenide.closeWebDriver();}

    public void shiftBrowserTab(int tabNumber){
        getWebDriver().getWindowHandle(); switchTo().window(tabNumber);
    }

    public void navigateTo_Storefront(int tabNumber) {
        String currentUrl = WebDriverRunner.url();
        String[] url = currentUrl.split("admin.php");
        executeJavaScript("window.open('" + url[tabNumber] + "')");
    }
}