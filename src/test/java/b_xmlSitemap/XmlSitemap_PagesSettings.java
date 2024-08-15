package b_xmlSitemap;

import testRunner.TestRunner;
import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
    public void checkXmlSitemap_PagesSettings() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        if (!sitemapSettings.setting_EnableXMLSitemap.isSelected()) {
            sitemapSettings.setting_EnableXMLSitemap.click();
        }
        if (!sitemapSettings.setting_PagesSettings_IncludeToSitemap.isSelected()) {
            sitemapSettings.setting_PagesSettings_IncludeToSitemap.click();
        }
        if (!sitemapSettings.setting_PagesSettings_IncludeBlogPages.isSelected()) {
            sitemapSettings.setting_PagesSettings_IncludeBlogPages.click();
        }
        sitemapSettings.setting_PagesSettings_ChangeFrequency.selectOptionByValue("monthly");
        sitemapSettings.setting_PagesSettings_Priority.selectOptionByValue("0.9");
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        sitemapSettings.xmlLink.click();
        shiftBrowserTab(1);

        //Проверяем, что ссылка на страницы присутствует в xml карте-сайта
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'pages')]").exists(),
                "There is no a link for pages in the xml-sitemap!");

        //Проверяем, что ссылка на блог присутствует в xml карте-сайта
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'blog')]").exists(),
                "There is no a link for blog in the xml-sitemap!");
        String urlForPages = sitemapSettings.splitLinkMethod(5);
        String urlForBlog = sitemapSettings.splitLinkMethod(6);
        Selenide.executeJavaScript("window.open('" + urlForPages + "');");
        shiftBrowserTab(2);

        //Проверяем, что в карте-сайта СТРАНИЦ Частота изменений "Ежемесячно"
        softAssert.assertTrue($("changefreq").has(Condition.text("monthly")),
                "There is no Change frequency 'Monthly' in the sitemap of pages!");

        //Проверяем, что в карте-сайта страниц Приоритет "0.9"
        softAssert.assertTrue($("priority").has(Condition.text("0.9")),
                "There is no Priority '0.9' in the sitemap of pages!");
        screenshot("xmlSitemap.XmlSitemap_PagesSettings - Pages");
        Selenide.executeJavaScript("window.open('" + urlForBlog + "');");
        shiftBrowserTab(3);

        //Проверяем, что в карте-сайта БЛОГА Частота изменений "Ежемесячно"
        softAssert.assertTrue($("changefreq").has(Condition.text("monthly")),
                "There is no Change frequency 'Monthly' in the sitemap of blog!");

        //Проверяем, что в карте-сайта блога Приоритет "0.9"
        softAssert.assertTrue($("priority").has(Condition.text("0.9")),
                "There is no Priority '0.9' in the sitemap of blog!");
        screenshot("xmlSitemap.XmlSitemap_PagesSettings - Blog");
        softAssert.assertAll();
        System.out.println("xmlSitemap.XmlSitemap_PagesSettings has passed successfully!");
    }
}