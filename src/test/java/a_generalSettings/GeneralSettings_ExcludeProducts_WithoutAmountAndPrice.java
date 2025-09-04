package a_generalSettings;

import adminPanel.CategoryPage;
import testRunner.TestRunner;
import adminPanel.BasicPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

/*
Двум товарам из категории "Палатки" настраиваем:
    * Товар "Elite" - с ценой и в наличии
    * Товар "WeatherMaster" - без цены и без наличия
Настройка модуля: "Общие -- Исключить товары -- без наличия и цены"
Проверяем, что:
    * Товар "Elite" присутствует в карте сайта
    * Товар "WeatherMaster" отсутствует в карте сайта
*/

public class GeneralSettings_ExcludeProducts_WithoutAmountAndPrice extends TestRunner {

    @Test
    public void checkGeneralSettings_ExcludeProducts_WithoutAmountAndPrice() throws Exception {
        BasicPage basicPage = new BasicPage();
        
        //Настраиваем 2 товара из категории "Палатки"
        CategoryPage categoryPage = basicPage.navigateToSection_Categories();
        categoryPage.selectCategory_Tents.click();
        if ($(".alert").exists())
            $(".close.cm-notification-close").click();
        //Выключаем сообщение о предупредлении, если оно появилось
        basicPage.gearwheelOnEditingPage.click();
        basicPage.button_ViewProducts.click();
        categoryPage.setFirstProduct("300", "8");
        categoryPage.setSecondProduct("0", "0");
        basicPage.button_SaveListOfProducts.click();
        basicPage.chooseAnyProduct.click();
        basicPage.gearwheelOnEditingPage.click();
        basicPage.button_Preview.click();
        Utils.shiftBrowserTab(1);
        String currentUrl_ProductElite = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] arrayProductElite = currentUrl_ProductElite.split("\\?");
        String urlForProductElite = arrayProductElite[0];     //Получили ссылку товара "Elite"
        System.out.println("URL for a product Elite: " + urlForProductElite);
        Utils.shiftBrowserTab(0);
        categoryPage.button_ArrowLeft.click();
        $("tr[data-ca-id='236'] .products-list__image").click();
        basicPage.gearwheelOnEditingPage.click();
        basicPage.button_Preview.click();
        Utils.shiftBrowserTab(2);
        String currentUrl_ProductWeatherMaster = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] arrayProductWeatherMaster = currentUrl_ProductWeatherMaster.split("\\?");
        String urlForProductWeatherMaster = arrayProductWeatherMaster[0];     //Получили ссылку товара "WeatherMaster"
        System.out.println("URL for a product WeatherMaster: " + urlForProductWeatherMaster);

        //Настраиваем настройки модуля
        Utils.shiftBrowserTab(0);
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.setting_ExcludeProducts.selectOptionByValue("without_amount_and_price");
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_FeatureVariantsSettings_IncludeToSitemap, true);
        basicPage.button_Save.click();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(3);
        String urlForProducts = sitemapSettings.findLinkByPartialName("products1");
        Selenide.executeJavaScript("window.open('" + urlForProducts + "');");
        Utils.shiftBrowserTab(4);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на товар "Elite" присутствует
        softAssert.assertTrue($("[href='" + urlForProductElite + "']").exists(),
                "There is no link for product 'Elite' in the 'products1' sitemap!");

        //Проверяем, что ссылка на товар "WeatherMaster" отсутствует
        softAssert.assertFalse($("[href='" + urlForProductWeatherMaster + "']").exists(),
                "There is a link for product 'WeatherMaster' but shouldn't in the 'products1' sitemap!");

        screenshot("GeneralSettings_ExcludeProducts_WithoutAmountAndPrice");
        softAssert.assertAll();
        System.out.println("GeneralSettings_ExcludeProducts_WithoutAmountAndPrice has passed successfully!");
    }
}