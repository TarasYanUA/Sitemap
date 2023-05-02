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

public class XmlSitemap_PageSettings extends TestRunner{
    @Test
    public void checkXmlSitemap_PageSettings() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
        sitemapSettings.setting_EnableXMLSitemap.click();   }
        if(!sitemapSettings.setting_ProductsSettings_IncludeToSitemap.isSelected()){
            sitemapSettings.setting_ProductsSettings_IncludeToSitemap.click();    }
        sitemapSettings.setting_CategoriesSettings_ChangeFrequency.selectOptionByValue("monthly");
        sitemapSettings.setting_CategoriesSettings_Priority.selectOptionByValue("0.9");
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        sitemapSettings.xmlLink.click();
        shiftBrowserTab(1);
        //Проверяем, что ссылка на категории присутствует в xml карте-сайта
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text("categories")),
                "There is no a link for categories in the xml-sitemap!");
        String urlForCategories = sitemapSettings.splitLinkMethod(2);
        Selenide.executeJavaScript("window.open('"+urlForCategories+"');");
        shiftBrowserTab(2);
        //Проверяем, что Частота изменений "Еженедельно"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<changefreq>monthly</changefreq>")),
                "There is no Change frequency 'Monthly'!");
        //Проверяем, что Приоритет "0.9"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<priority>0.9</priority>")),
                "There is no Priority '0.9'!");
        screenshot("XmlSitemap_PageSettings");
        softAssert.assertAll();
        System.out.println("XmlSitemap_PageSettings has passed successfully!");
    }
}