import adminPanel.CsCartSettings;
import org.testng.annotations.Test;

public class Integration_AB_deal_of_the_day extends TestRunner {
    @Test
    public void checkIntegration_AB_deal_of_the_day (){
        CsCartSettings csCartSettings = new CsCartSettings();
        //Устанавливаем модуль "AB: Расширенные промоакции"
        csCartSettings.installAddonAtAddonsManager(csCartSettings.menuOfAB__deal_of_the_day, "ab__deal_of_the_day", "form[name=ab_install_form_54317]");
        csCartSettings.addDemoDataTo_ab_deal_of_the_day();
    }
}
