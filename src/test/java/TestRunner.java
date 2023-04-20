import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.*;

/*
Модуль "Расширенная карта сайта" 2.5.0.
Скриншоты смотреть в папке: reports -> tests
*/
public class TestRunner {
    public static final String BASIC_URL = "https://trs.test.abt.team/4162mven/admin.php";

    @BeforeClass
    public void openBrowser() {
        Configuration.browser = "chrome";
        Configuration.holdBrowserOpen = false; //не закрываем браузер пока ведём разработку
        Configuration.screenshots = true; //делаем скриншоты при падении
        Configuration.browserSize = "1920x1050"; //Увеличиваем размер экрана
        open(BASIC_URL);
        $(".btn.btn-primary").click();
        $("#bp_off_bottom_panel").click();
    }

    @AfterClass
    public void closeBrowser() {Selenide.closeWebDriver();}

    public void makePause(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}