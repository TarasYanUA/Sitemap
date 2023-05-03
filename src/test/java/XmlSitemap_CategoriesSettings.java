import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Selenide.*;

/*
Настройка модуля: "XML карта-сайта -- Настройки для категорий":
    Включить в карту сайта -- да
    Частота изменений -- Еженедельно
    Приоритет -- 0.3
*/

public class XmlSitemap_CategoriesSettings extends TestRunner{
    @Test
    public void checkXmlSitemap_CategoriesSettings() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
        sitemapSettings.setting_EnableXMLSitemap.click();   }
        if(!sitemapSettings.setting_CategoriesSettings_IncludeToSitemap.isSelected()){
            sitemapSettings.setting_CategoriesSettings_IncludeToSitemap.click();    }
        sitemapSettings.setting_CategoriesSettings_ChangeFrequency.selectOptionByValue("weekly");
        sitemapSettings.setting_CategoriesSettings_Priority.selectOptionByValue("0.3");
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(1);
        //Проверяем, что ссылка на категории присутствует в xml карте-сайта
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text("categories")),
                "There is no a link for categories in the xml-sitemap!");
        String urlForCategories = sitemapSettings.splitLinkMethod(2);
        Selenide.executeJavaScript("window.open('"+urlForCategories+"');");
        shiftBrowserTab(2);
        //Проверяем, что Частота изменений "Еженедельно"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<changefreq>weekly</changefreq>")),
                "There is no Change frequency 'Weekly'!");
        //Проверяем, что Приоритет "0.3"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<priority>0.3</priority>")),
                "There is no Priority '0.3'!");
        screenshot("XmlSitemap_CategoriesSettings");
        softAssert.assertAll();
        System.out.println("XmlSitemap_CategoriesSettings has passed successfully!");
    }
}