package b_xmlSitemap;

import testRunner.TestRunner;
import adminPanel.BasicPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

/*
Настройка модуля: "XML карта-сайта -- Добавить дату последнего редактирования"
*/

public class XmlSitemap_AddLastModifiedDate extends TestRunner {

    @Test
    public void checkXmlSitemap_AddLastModifiedDate() throws Exception {
        //Настраиваем настройки модуля
        BasicPage basicPage = new BasicPage();
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_AddLastModifiedDate, true);
        basicPage.saveSettings();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(1);
        String urlForCategories = sitemapSettings.findLinkByPartialName("categories1");
        String urlForCompanies = sitemapSettings.findLinkByPartialName("companies1");

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что теги даты последнего редактирования присутствуют в xml карте-сайта категорий
        Selenide.executeJavaScript("window.open('" + urlForCategories + "');");
        Utils.shiftBrowserTab(2);
        softAssert.assertTrue($("lastmod").exists(),
                "There are no tags <lastmod> at xml sitemap of the categories!");
        screenshot("xmlSitemap.XmlSitemap_AddLastModifiedDate - Last modified date at categories");

        //Проверяем, что теги даты последнего редактирования присутствуют в xml-карте сайта компаний
        Selenide.executeJavaScript("window.open('" + urlForCompanies + "');");
        Utils.shiftBrowserTab(3);
        softAssert.assertTrue($("lastmod").exists(),
                "There are no tags <lastmod> at xml sitemap of the companies!");

        screenshot("XmlSitemap_AddLastModifiedDate - Last modified date at companies");
        softAssert.assertAll();
        System.out.println("XmlSitemap_AddLastModifiedDate has passed successfully!");
    }
}