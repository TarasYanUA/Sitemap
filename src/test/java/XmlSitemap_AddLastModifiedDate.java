import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

/*
Настройка модуля: "XML карта-сайта -- Добавить дату последнего редактирования"
*/

public class XmlSitemap_AddLastModifiedDate extends TestRunner{
    @Test
    public void checkXmlSitemap_AddLastModifiedDate(){
        //Настраиваем настройки модуля
        CsCartSettings csCartSettings = new CsCartSettings();
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
            sitemapSettings.setting_EnableXMLSitemap.click();   }
        if(!sitemapSettings.setting_AddLastModifiedDate.isSelected()){
            sitemapSettings.setting_AddLastModifiedDate.click();    }
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(1);
        String urlForCategories = sitemapSettings.splitLinkMethod(2);
        String urlForCompanies = sitemapSettings.splitLinkMethod(7);
        //Проверяем, что теги даты последнего редактирования присутствуют в xml карте-сайта категорий
        SoftAssert softAssert = new SoftAssert();
        Selenide.executeJavaScript("window.open('"+urlForCategories+"');");
        shiftBrowserTab(2);
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<lastmod>")),
                "There are no tags <lastmod> at xml sitemap of the categories!");
        screenshot("XmlSitemap_AddLastModifiedDate - Last modified date at categories");
        //Проверяем, что теги даты последнего редактирования присутствуют в xml карте-сайта компаний
        Selenide.executeJavaScript("window.open('"+urlForCompanies+"');");
        shiftBrowserTab(3);
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<lastmod>")),
                "There are no tags <lastmod> at xml sitemap of the companies!");
        screenshot("XmlSitemap_AddLastModifiedDate - Last modified date at companies");
        softAssert.assertAll();
        System.out.println("XmlSitemap_AddLastModifiedDate has passed successfully!");
    }
}