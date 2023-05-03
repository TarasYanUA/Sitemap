import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

/*
Настройка модуля: "XML карта-сайта -- Настройки для вариантов характеристик":
    Включить в карту сайта -- да
    Частота изменений -- Не использовать
    Приоритет -- 0.1
*/

public class XmlSitemap_FeatureVariantsSettings extends TestRunner{
    @Test
    public void checkXmlSitemap_FeatureVariantsSettings() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
        sitemapSettings.setting_EnableXMLSitemap.click();   }
        if(!sitemapSettings.setting_FeatureVariantsSettings_IncludeToSitemap.isSelected()){
            sitemapSettings.setting_FeatureVariantsSettings_IncludeToSitemap.click();    }
        sitemapSettings.setting_FeatureVariantsSettings_ChangeFrequency.selectOptionByValue("do_not_use");
        sitemapSettings.setting_FeatureVariantsSettings_Priority.selectOptionByValue("0.1");
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(1);
        //Проверяем, что ссылка на варианты характеристик присутствует в xml карте-сайта
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text("feature_variants")),
                "There is no a link for feature variants in the xml-sitemap!");
        String urlForFeatureVariants = sitemapSettings.splitLinkMethod(3);
        Selenide.executeJavaScript("window.open('"+urlForFeatureVariants+"');");
        shiftBrowserTab(2);
        //Проверяем, что Частота изменений отсутствует
        softAssert.assertFalse($(".pretty-print").has(Condition.text("<changefreq>")),
                "There is a Change frequency but shouldn't on the sitemap of feature variants!");
        //Проверяем, что Приоритет "0.1"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<priority>0.1</priority>")),
                "There is no Priority '0.1' on the sitemap of feature variants!");
        screenshot("XmlSitemap_FeatureVariantsSettings");
        softAssert.assertAll();
        System.out.println("XmlSitemap_FeatureVariantsSettings has passed successfully!");
    }
}