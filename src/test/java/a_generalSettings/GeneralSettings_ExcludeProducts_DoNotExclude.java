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
    * Товар "WeatherMaster" - без наличия и без цены
Настройка модуля: "Общие -- Исключить товары -- не исключать"
Проверяем, что:
    * Товар "Elite" присутствует в карте сайта
    * Товар "WeatherMaster" присутствует в карте сайта
*/

public class GeneralSettings_ExcludeProducts_DoNotExclude extends TestRunner {

    @Test
    public void checkGeneralSettings_ExcludeProducts_DoNotExclude() throws Exception {
        BasicPage basicPage = new BasicPage();

        //Настраиваем 2 товара из категории "Палатки"
        CategoryPage categoryPage = basicPage.navigateToSection_Categories();
        categoryPage.selectCategory_Tents.click();
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
        sitemapSettings.setting_ExcludeProducts.selectOptionByValue("none");
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

        //Проверяем, что ссылка на товар "WeatherMaster" присутствует
        softAssert.assertTrue($("[href='" + urlForProductWeatherMaster + "']").exists(),
                "There is no link for product 'WeatherMaster' in the 'products1' sitemap!");

        screenshot("GeneralSettings_ExcludeProducts_DoNotExclude");
        softAssert.assertAll();
        System.out.println("GeneralSettings_ExcludeProducts_DoNotExclude has passed successfully!");
    }
}