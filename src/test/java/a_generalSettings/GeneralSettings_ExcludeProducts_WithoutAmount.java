package a_generalSettings;

import testRunner.TestRunner;
import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

/*
Двум товарам из категории "Палатки" настраиваем:
    * Товар "Elite" - с ценой и в наличии
    * Товар "WeatherMaster" - с ценой и без наличия
Настройка модуля: "Общие -- Исключить товары -- без наличия"
Проверяем, что:
    * Товар "Elite" присутствует в карте сайта
    * Товар "WeatherMaster" отсутствует в карте сайта
*/

public class GeneralSettings_ExcludeProducts_WithoutAmount extends TestRunner {

    @Test
    public void checkGeneralSettings_ExcludeProducts_WithoutAmount() throws Exception {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Настраиваем 2 товара из категории "Палатки"
        csCartSettings.navigateToSection_Categories();
        csCartSettings.selectCategory_Tents.click();
        if ($(".alert").exists()) {
            $(".close.cm-notification-close").click();
        }   //Выключаем сообщение о предупредлении, если оно появилось
        csCartSettings.gearwheelOnEditingPage.click();
        csCartSettings.button_ViewProducts.click();
        csCartSettings.setFirstProduct("300", "8");
        csCartSettings.setSecondProduct("350", "0");
        csCartSettings.button_SaveListOfProducts.click();
        csCartSettings.chooseAnyProduct.click();
        csCartSettings.gearwheelOnEditingPage.click();
        csCartSettings.button_Preview.click();
        shiftBrowserTab(1);
        String currentUrl_ProductElite = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] arrayProductElite = currentUrl_ProductElite.split("\\?");
        String urlForProductElite = arrayProductElite[0];     //Получили ссылку товара "Elite"
        System.out.println("URL for a product Elite: " + urlForProductElite);
        shiftBrowserTab(0);
        csCartSettings.button_ArrowLeft.click();
        $("tr[data-ca-id='236'] .products-list__image").click();
        csCartSettings.gearwheelOnEditingPage.click();
        csCartSettings.button_Preview.click();
        shiftBrowserTab(2);
        String currentUrl_ProductWeatherMaster = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] arrayProductWeatherMaster = currentUrl_ProductWeatherMaster.split("\\?");
        String urlForProductWeatherMaster = arrayProductWeatherMaster[0];     //Получили ссылку товара "WeatherMaster"
        System.out.println("URL for a product WeatherMaster: " + urlForProductWeatherMaster);

        //Настраиваем настройки модуля
        shiftBrowserTab(0);
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.setting_ExcludeProducts.selectOptionByValue("without_amount");
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        if (!sitemapSettings.setting_EnableXMLSitemap.isSelected()) {
            sitemapSettings.setting_EnableXMLSitemap.click();
        }
        if (!sitemapSettings.setting_ProductsSettings_IncludeToSitemap.isSelected()) {
            sitemapSettings.setting_ProductsSettings_IncludeToSitemap.click();
        }
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(3);
        String urlForProducts = sitemapSettings.findLinkByPartialName("products1");
        Selenide.executeJavaScript("window.open('" + urlForProducts + "');");
        shiftBrowserTab(4);

        //Проверяем, что ссылка на товар "Elite" присутствует
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($("[href='" + urlForProductElite + "']").exists(),
                "There is no link for product 'Elite' in the 'products1' sitemap!");

        //Проверяем, что ссылка на товар "WeatherMaster" отсутствует
        softAssert.assertFalse($("[href='" + urlForProductWeatherMaster + "']").exists(),
                "There is a link for product 'WeatherMaster' but shouldn't in the 'products1' sitemap!");
        screenshot("gGeneralSettings_ExcludeProducts_WithoutAmount");
        softAssert.assertAll();
        System.out.println("GeneralSettings_ExcludeProducts_WithoutAmount has passed successfully!");
    }
}