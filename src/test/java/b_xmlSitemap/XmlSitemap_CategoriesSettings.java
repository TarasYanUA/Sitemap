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
Настройка модуля: "XML карта-сайта -- Настройки для категорий":
    Включить в карту сайта -- да
    Частота изменений -- Еженедельно
    Приоритет -- 0.3
*/

public class XmlSitemap_CategoriesSettings extends TestRunner {

    @Test
    public void checkXmlSitemap_CategoriesSettings() throws Exception {
        BasicPage basicPage = new BasicPage();

        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_CategoriesSettings_IncludeToSitemap, true);
        sitemapSettings.setting_CategoriesSettings_ChangeFrequency.selectOptionByValue("weekly");
        sitemapSettings.setting_CategoriesSettings_Priority.selectOptionByValue("0.3");
        basicPage.button_Save.click();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(1);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на категории присутствует в xml-карте сайта
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'categories')]").exists(),
                "There is no link for categories in the xml sitemap!");
        String urlForCategories = sitemapSettings.findLinkByPartialName("categories1");
        Selenide.executeJavaScript("window.open('" + urlForCategories + "');");
        Utils.shiftBrowserTab(2);

        //Проверяем, что Частота изменений "Еженедельно"
        softAssert.assertTrue($("changefreq").has(Condition.text("weekly")),
                "There is no Change frequency 'Weekly'!");

        //Проверяем, что Приоритет "0.3"
        softAssert.assertTrue($("priority").has(Condition.text("0.3")),
                "There is no Priority '0.3'!");

        screenshot("XmlSitemap_CategoriesSettings");
        softAssert.assertAll();
        System.out.println("XmlSitemap_CategoriesSettings has passed successfully!");
    }
}