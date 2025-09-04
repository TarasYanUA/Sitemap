package testRunner;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.*;
import static com.codeborne.selenide.Selenide.*;

/*
Мультивендор + модуль "Расширенная карта сайта" 2.6.2.
Работает в браузерах Chrome и Edge (в Firefox не работает).
*/

public class TestRunner {
    public static final String BASIC_URL = "https://trs.test.abt.team/4184ultru/admin.php?dispatch=addons.manage";

    @BeforeMethod
    public void openBrowser()  {
        Configuration.browser = "chrome";
        open(BASIC_URL);
        Configuration.screenshots = true;       //делаем скриншоты при падении
        WebDriverRunner.getWebDriver().manage().window().maximize(); //окно браузера на весь экран
        Configuration.savePageSource = false; //не создавать html файлы при создании скриншотов
        $(".btn.btn-primary").click();
        $("#bp_off_bottom_panel").click();
        if ($(".cm-notification-close").isDisplayed())
            $(".cm-notification-close").click();
        Selenide.sleep(1000);
    }

    @AfterMethod
    public void closeBrowser() {Selenide.closeWebDriver();}
}