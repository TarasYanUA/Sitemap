package a_generalSettings;

import adminPanel.ProductPage;
import testRunner.TestRunner;
import adminPanel.BasicPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;

/*
Настройка модуля: "Общие -- Исключить вариации товаров из карты сайта"
*/

public class GeneralSettings_ExcludeProductVariations extends TestRunner {

    @Test
    public void checkGeneralSettings_ExcludeProductVariations() throws Exception {
        BasicPage basicPage = new BasicPage();
        ProductPage productPage = new ProductPage();
        
        //Переходим на страницу товара с вариациями (футболка синия)
        productPage.navigateToEditingProductPage("Футболка, Цвет: Синий");
        basicPage.gearwheelOnEditingPage.click();
        basicPage.button_Preview.click();
        Utils.shiftBrowserTab(1);
        String currentUrl_ProductTshirt = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] arrayProductTshirt = currentUrl_ProductTshirt.split("\\?");
        String urlForProductTshirt = arrayProductTshirt[0]; //Получили ссылку товара "Футболка, Цвет: Синий"
        System.out.println("URL for a product Tshirt: " + urlForProductTshirt);
        Utils.shiftBrowserTab(0);

        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_FeatureVariantsSettings_IncludeToSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_ProductsSettings_IncludeToSitemap, true);
        basicPage.button_Save.click();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(2);
        String urlForProducts = sitemapSettings.findLinkByPartialName("products1");
        Selenide.executeJavaScript("window.open('" + urlForProducts + "');");
        Utils.shiftBrowserTab(3);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на товар "Футболка, Цвет: Синий" присутствует
        softAssert.assertTrue($("[href='" + urlForProductTshirt + "']").exists(),
                "There is no link for product 'T-Shirt' in the 'products1' sitemap!");

        //Проверяем, что присутствует только 1 ссылка на товар "Футболка, Цвет: Синий". То есть, вариации этого товара должны отсутствовать
        int actualSizeOfUrls = $$("[href='" + urlForProductTshirt + "']").size();
        softAssert.assertEquals(actualSizeOfUrls, 1,
                "There are more than 1 link of a product 'T-Shirt' in the 'products1' sitemap. It means there are variations but shouldn't!");

        screenshot("GeneralSettings_ExcludeProductVariations");
        softAssert.assertAll();
        System.out.println("GeneralSettings_ExcludeProductVariations has passed successfully!");
    }
}