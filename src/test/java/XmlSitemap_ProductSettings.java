import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

/*
Настройка модуля: "XML карта-сайта -- Настройки для товаров":
    Включить в карту сайта -- да
    Частота изменений -- Каждый день
    Приоритет -- 0.7
    Добавить предложения продавцов -- да
Настраиваем товар для продавца с помощью модуля "Общие товары для продавцов"
*/

public class XmlSitemap_ProductSettings extends TestRunner{
    @Test
    public void checkXmlSitemap_ProductSettings() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
        sitemapSettings.setting_EnableXMLSitemap.click();   }
        if(!sitemapSettings.setting_ProductsSettings_IncludeToSitemap.isSelected()){
            sitemapSettings.setting_ProductsSettings_IncludeToSitemap.click();    }
        sitemapSettings.setting_ProductsSettings_ChangeFrequency.selectOptionByValue("daily");
        sitemapSettings.setting_ProductsSettings_Priority.selectOptionByValue("0.7");
        if(!sitemapSettings.setting_ProductsSettings_AddVendorsOffers.isSelected()){
            sitemapSettings.setting_ProductsSettings_AddVendorsOffers.click();  }
        csCartSettings.button_Save.click();

        //Настраиваем товар для продавца с помощью модуля "Общие товары для продавцов"
        //Модуль "Стикеры" - взять товар для продавца




        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        csCartSettings.shiftBrowserTab(3);
        String urlForProducts = sitemapSettings.splitLinkMethod(1);
        Selenide.executeJavaScript("window.open('"+urlForProducts+"');");
        csCartSettings.shiftBrowserTab(4);

        //Проверяем, что ссылка на товар "Elite" присутствует
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text(urlForProductElite)),
                "There is no link for product 'Elite'!");
        //Проверяем, что ссылка на товар "WeatherMaster" отсутствует
        softAssert.assertFalse($(".pretty-print").has(Condition.text(urlForProductWeatherMaster)),
                "There is a link for product 'WeatherMaster' but shouldn't!");
        screenshot("GeneralSettings_ExcludeProducts_WithoutAmount");
        softAssert.assertAll();
        System.out.println("GeneralSettings_ExcludeProducts_WithoutAmount has passed successfully!");
    }
}