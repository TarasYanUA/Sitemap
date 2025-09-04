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
Настройка модуля: "XML карта-сайта -- Настройки для страниц":
    Включить в карту сайта -- да
    Включить страницы блога в карту сайта -- да
    Частота изменений -- Ежемесячно
    Приоритет -- 0.9
*/

public class XmlSitemap_PagesSettings extends TestRunner {

    @Test
    public void checkXmlSitemap_PagesSettings() throws Exception {
        BasicPage basicPage = new BasicPage();
        
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_PagesSettings_IncludeToSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_PagesSettings_IncludeBlogPages, true);
        sitemapSettings.setting_PagesSettings_ChangeFrequency.selectOptionByValue("monthly");
        sitemapSettings.setting_PagesSettings_Priority.selectOptionByValue("0.9");
        basicPage.button_Save.click();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        sitemapSettings.xmlLink.click();
        Utils.shiftBrowserTab(1);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на страницы присутствует в xml карте-сайта
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'pages')]").exists(),
                "There is no a link for pages in the xml-sitemap!");

        //Проверяем, что ссылка на блог присутствует в xml карте-сайта
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'blog')]").exists(),
                "There is no a link for blog in the xml-sitemap!");
        String urlForPages = sitemapSettings.findLinkByPartialName("pages1");
        String urlForBlog = sitemapSettings.findLinkByPartialName("blog1");
        Selenide.executeJavaScript("window.open('" + urlForPages + "');");
        Utils.shiftBrowserTab(2);

        //Проверяем, что в карте-сайта СТРАНИЦ Частота изменений "Ежемесячно"
        softAssert.assertTrue($("changefreq").has(Condition.text("monthly")),
                "There is no Change frequency 'Monthly' in the sitemap of pages!");

        //Проверяем, что в карте-сайта страниц Приоритет "0.9"
        softAssert.assertTrue($("priority").has(Condition.text("0.9")),
                "There is no Priority '0.9' in the sitemap of pages!");
        screenshot("xmlSitemap.XmlSitemap_PagesSettings - Pages");
        Selenide.executeJavaScript("window.open('" + urlForBlog + "');");
        Utils.shiftBrowserTab(3);

        //Проверяем, что в карте-сайта БЛОГА Частота изменений "Ежемесячно"
        softAssert.assertTrue($("changefreq").has(Condition.text("monthly")),
                "There is no Change frequency 'Monthly' in the sitemap of blog!");

        //Проверяем, что в карте-сайта блога Приоритет "0.9"
        softAssert.assertTrue($("priority").has(Condition.text("0.9")),
                "There is no Priority '0.9' in the sitemap of blog!");

        screenshot("XmlSitemap_PagesSettings - Blog");
        softAssert.assertAll();
        System.out.println("XmlSitemap_PagesSettings has passed successfully!");
    }
}