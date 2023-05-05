import adminPanel.AB_seo_filters;
import adminPanel.CsCartSettings;
import org.testng.annotations.Test;

public class Integration_AB_seo_filters extends TestRunner{
    @Test
    public void checkIntegration_AB_seo_filters(){
        CsCartSettings csCartSettings = new CsCartSettings();
        //Устанавливаем модуль "AB: SEO страницы для фильтров"
        csCartSettings.installAddonAtAddonsManager(csCartSettings.menuOfAB__seo_filters, "ab__seo_filters", "form[name=ab_install_form_54338]");
        AB_seo_filters ab_seo_filters = csCartSettings.navigateToGeneralSettingsOf_ab_seo_filters();
    }
}
