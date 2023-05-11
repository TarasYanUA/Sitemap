import adminPanel.AB_deal_of_the_day;
import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

/*
Модуль "AB: Расширенные промоакции":
    * Устанавливаем модуль
    * Включаем настройку
*/

public class Integration_AB_deal_of_the_day extends TestRunner {
    @Test
    public void checkIntegration_AB_deal_of_the_day (){
        CsCartSettings csCartSettings = new CsCartSettings();
        //Устанавливаем модуль "AB: Расширенные промоакции"
        csCartSettings.installAddonAtAddonsManager(csCartSettings.menuOfAB__deal_of_the_day, "ab__deal_of_the_day", "form[name=ab_install_form_54317]");
        AB_deal_of_the_day ab_deal_of_the_day = csCartSettings.addDemoDataTo_ab_deal_of_the_day();
        ab_deal_of_the_day.navigateToSection_GeneralSettings();
        if(!ab_deal_of_the_day.setting_AddPromotionsToXml.isSelected()){
            ab_deal_of_the_day.setting_AddPromotionsToXml.click();
            csCartSettings.button_Save.click();
        }

        //Настраиваем XML-карту сайта
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
            sitemapSettings.setting_EnableXMLSitemap.click();   }
        sitemapSettings.setting_OtherAddons_ChangeFrequency.selectOptionByValue("always");
        sitemapSettings.setting_OtherAddons_Priority.selectOptionByValue("0.8");
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(1);
        //Проверяем, что ссылка на XML-карту промо-акций присутствует в xml карте-сайта
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text("other_links")),
                "There is no a link for XML of integrated add-ons in the xml-sitemap!");
        String urlForXMLPromotions = sitemapSettings.splitLinkMethod(9);
        Selenide.executeJavaScript("window.open('" + urlForXMLPromotions + "');");
        shiftBrowserTab(2);
        //Проверяем, что в карте-сайта промо-акций Частота изменений "Всегда"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<changefreq>always</changefreq>")),
                "There is no Change frequency 'Always' in the 'other_links1' sitemap!");
        //Проверяем, что в карте-сайта промо-акций Приоритет "0.8"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<priority>0.8</priority>")),
                "There is no Priority '0.8' in the 'other_links1' sitemap!");
        softAssert.assertAll();
        screenshot("Integration_AB_deal_of_the_day");
        System.out.println("Integration_AB_deal_of_the_day has passed successfully!");
    }
}