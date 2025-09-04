package b_xmlSitemap;

import testRunner.TestRunner;
import adminPanel.BasicPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;

/*
Настройка модуля: "XML карта-сайта -- Настройки для вариантов характеристик":
    Включить в карту сайта -- да
    Частота изменений -- Не использовать
    Приоритет -- 0.1
*/

public class XmlSitemap_FeatureVariantsSettings extends TestRunner {

    @Test
    public void checkXmlSitemap_FeatureVariantsSettings() throws Exception {
        BasicPage basicPage = new BasicPage();
        
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_FeatureVariantsSettings_IncludeToSitemap, true);
        sitemapSettings.setting_FeatureVariantsSettings_ChangeFrequency.selectOptionByValue("do_not_use");
        sitemapSettings.setting_FeatureVariantsSettings_Priority.selectOptionByValue("0.1");
        basicPage.button_Save.click();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(1);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на варианты характеристик присутствует в xml-карте сайта
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'feature_variants')]").exists(),
                "There is no a link for feature variants in the xml-sitemap!");
        String urlForFeatureVariants = sitemapSettings.findLinkByPartialName("feature_variants1");
        Selenide.executeJavaScript("window.open('" + urlForFeatureVariants + "');");
        Utils.shiftBrowserTab(2);

        //Проверяем, что Частота изменений отсутствует
        softAssert.assertFalse($("changefreq").exists(),
                "There is a Change frequency but shouldn't on the sitemap 'feature_variants1'!");

        //Проверяем, что Приоритет "0.1"
        softAssert.assertTrue($("priority").has(Condition.text("0.1")),
                "There is no Priority '0.1' on the sitemap of feature variants!");

        screenshot("XmlSitemap_FeatureVariantsSettings");
        softAssert.assertAll();
        System.out.println("XmlSitemap_FeatureVariantsSettings has passed successfully!");
    }
}