import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/*
Мультивендор + модуль "Расширенная карта сайта" 2.5.0.
Работает в браузерах Chrome, Edge. В Firefox не работает.
*/

public class TestRunner {
    public static final String BASIC_URL = "https://trs.test.abt.team/4162mvru_video_gallery/admin.php?dispatch=addons.manage";

    @BeforeMethod
    public void openBrowser()  {
        Configuration.browser = "edge";
        Configuration.holdBrowserOpen = false; //не закрываем браузер пока ведём разработку
        Configuration.screenshots = true;  //делаем скриншоты при падении
        Configuration.browserSize = "1920x1050"; //Увеличиваем размер экрана
        open(BASIC_URL);
        $(".btn.btn-primary").click();
        $("#bp_off_bottom_panel").click();
    }
    @AfterMethod
    public void closeBrowser() {Selenide.closeWebDriver();}

    public void shiftBrowserTab(int tabNumber){
        getWebDriver().getWindowHandle(); switchTo().window(tabNumber);
    }
}