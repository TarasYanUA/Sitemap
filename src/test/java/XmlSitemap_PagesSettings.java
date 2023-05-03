import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

/*
Настройка модуля: "XML карта-сайта -- Настройки для страниц":
    Включить в карту сайта -- да
    Включить страницы блога в карту сайта -- да
    Частота изменений -- Ежемесячно
    Приоритет -- 0.9
*/

public class XmlSitemap_PagesSettings extends TestRunner{
    @Test
    public void checkXmlSitemap_PagesSettings() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
        sitemapSettings.setting_EnableXMLSitemap.click();   }
        if(!sitemapSettings.setting_PagesSettings_IncludeToSitemap.isSelected()){
            sitemapSettings.setting_PagesSettings_IncludeToSitemap.click();    }
        if(!sitemapSettings.setting_PagesSettings_IncludeBlogPages.isSelected()){
            sitemapSettings.setting_PagesSettings_IncludeBlogPages.click();    }
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
        softAssert.assertTrue($(".pretty-print").has(Condition.text("pages")),
                "There is no a link for pages in the xml-sitemap!");
        //Проверяем, что ссылка на блог присутствует в xml карте-сайта
        softAssert.assertTrue($(".pretty-print").has(Condition.text("blog")),
                "There is no a link for blog in the xml-sitemap!");
        String urlForPages = sitemapSettings.splitLinkMethod(5);
        String urlForBlog = sitemapSettings.splitLinkMethod(6);
        Selenide.executeJavaScript("window.open('"+urlForPages+"');");
        shiftBrowserTab(2);
        //Проверяем, что в карте-сайта страниц Частота изменений "Ежемесячно"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<changefreq>monthly</changefreq>")),
                "There is no Change frequency 'Monthly' in the sitemap of pages!");
        //Проверяем, что в карте-сайта страниц Приоритет "0.9"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<priority>0.9</priority>")),
                "There is no Priority '0.9' in the sitemap of pages!");
        screenshot("XmlSitemap_PagesSettings - Pages");
        Selenide.executeJavaScript("window.open('" + urlForBlog + "');");
        shiftBrowserTab(3);
        //Проверяем, что в карте-сайта блога Частота изменений "Ежемесячно"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<changefreq>monthly</changefreq>")),
                "There is no Change frequency 'Monthly' in the sitemap of blog!");
        //Проверяем, что в карте-сайта блога Приоритет "0.9"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<priority>0.9</priority>")),
                "There is no Priority '0.9' in the sitemap of blog!");
        screenshot("XmlSitemap_PagesSettings - Blog");
        softAssert.assertAll();
        System.out.println("XmlSitemap_PagesSettings has passed successfully!");
    }
}