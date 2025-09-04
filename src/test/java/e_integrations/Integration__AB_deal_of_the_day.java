package e_integrations;

import testRunner.TestRunner;
import adminPanel.AB_deal_of_the_day;
import adminPanel.BasicPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;

/*
Модуль "AB: Расширенные промоакции":
    * Устанавливаем модуль
    * Включаем настройку
*/

public class Integration__AB_deal_of_the_day extends TestRunner {

    @Test
    public void checkIntegration_AB_deal_of_the_day () throws Exception {
        BasicPage basicPage = new BasicPage();

        //Устанавливаем модуль "AB: Расширенные промоакции"
        basicPage.installAddonAtAddonsManager(basicPage.menuOfAB__deal_of_the_day, "ab__deal_of_the_day", "form[name=ab_install_form_54317]");
        AB_deal_of_the_day ab_deal_of_the_day = basicPage.addDemoDataTo_ab_deal_of_the_day();
        ab_deal_of_the_day.navigateToSection_GeneralSettings();
        Utils.setCheckboxState(ab_deal_of_the_day.setting_AddPromotionsToXml, true);
        basicPage.button_Save.click();

        //Настраиваем XML-карту сайта
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        sitemapSettings.setting_OtherAddons_ChangeFrequency.selectOptionByValue("always");
        sitemapSettings.setting_OtherAddons_Priority.selectOptionByValue("0.8");
        basicPage.button_Save.click();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(1);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на XML-карту промо-акций присутствует в xml-карте сайта
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'other_links')]").exists(),
                "There is no a link for XML of integrated add-ons in the xml-sitemap!");
        String urlForXMLPromotions = sitemapSettings.findLinkByPartialName("other_links1");
        Selenide.executeJavaScript("window.open('" + urlForXMLPromotions + "');");
        Utils.shiftBrowserTab(2);

        //Проверяем, что в карте-сайта промо-акций Частота изменений "Всегда"
        softAssert.assertTrue($("changefreq").has(Condition.text("always")),
                "There is no Change frequency 'Always' in the 'other_links1' sitemap!");

        //Проверяем, что в карте-сайта промо-акций Приоритет "0.8"
        softAssert.assertTrue($("priority").has(Condition.text("0.8")),
                "There is no Priority '0.8' in the 'other_links1' sitemap!");

        screenshot("Integration__AB_deal_of_the_day");
        softAssert.assertAll();
        System.out.println("Integration__AB_deal_of_the_day has passed successfully!");
    }
}