package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.Alert;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class UtilsAdm {

    public static void waitForSpinnerDisappear() {
        $("div#ajax_loading_box[style=\"display: block;\"]").shouldBe(Condition.disappear, Duration.ofSeconds(10));
        sleep(1500);
    }

    public static void switchToAndAcceptAlertWindow() {
        Alert alert = Selenide.webdriver().driver().switchTo().alert();
        alert.accept();
        Selenide.sleep(2000);
        UtilsAdm.waitForSpinnerDisappear();
    }
}