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

import static com.codeborne.selenide.Selenide.*;

/*
Двум категориям "iPods" и "Android" настраиваем по 2 товара:
    * Категория "iPods" - с ценой и в наличии
    * Категория "Android" - без цен и с наличием
Настройка модуля: "Общие -- Исключить категории -- с товарами без цен"
Проверяем, что:
    * Категория "iPods" присутствует в карте сайта
    * Категория "Android" отсутствует в карте сайта
*/

public class GeneralSettings_ExcludeCategories_WithoutPrice extends TestRunner {
    @Test
    public void checkGeneralSettings_ExcludeCategories_WithoutPrice() throws Exception {
        BasicPage basicPage = new BasicPage();

        //Настраиваем первую категорию "iPods"
        CategoryPage categoryPage = basicPage.navigateToSection_Categories();
        categoryPage.openCategoryPage("iPod");
        categoryPage.goToStorefront_CategoryPage(1);
        String currentUrl_CategoryIpods = WebDriverRunner.getWebDriver().getCurrentUrl(); //Получили ссылку категории "iPods"
        String[] arrayIpods = currentUrl_CategoryIpods.split("\\?");
        String urlForCategoryIpods = arrayIpods[0];
        System.out.println("iPods URL is: " + urlForCategoryIpods);
        Utils.shiftBrowserTab(0);
        categoryPage.goAndSetFirstProductOfCategory("249", "10");
        categoryPage.goAndSetSecondProductOfCategory("255", "15");
        categoryPage.button_SaveListOfProducts.click();

        //Настраиваем вторую категорию "Android"
        basicPage.navigateToSection_Categories();
        categoryPage.openCategoryPage("Android");
        categoryPage.goToStorefront_CategoryPage(2);
        String currentUrl_CategoryAndroid = WebDriverRunner.getWebDriver().getCurrentUrl(); //Получили ссылку категории "Android"
        Utils.shiftBrowserTab(0);
        String[] arrayAndroid = currentUrl_CategoryAndroid.split("\\?");
        String urlForCategoryAndroid = arrayAndroid[0];
        System.out.println("Android URL is: " + urlForCategoryAndroid);
        categoryPage.goAndSetFirstProductOfCategory("0", "10");
        categoryPage.goAndSetSecondProductOfCategory("0", "15");
        categoryPage.button_SaveListOfProducts.click();

        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.setting_ExcludeCategories.selectOptionByValue("without_product_price");
        sitemapSettings.tab_XMLSitemap.click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_FeatureVariantsSettings_IncludeToSitemap, true);
        basicPage.button_Save.click();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(3);
        String urlForCategories = sitemapSettings.findLinkByPartialName("categories1");
        Selenide.executeJavaScript("window.open('"+urlForCategories+"');");
        Utils.shiftBrowserTab(4);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на категорию "iPods" присутствует
        softAssert.assertTrue($("[href='" + urlForCategoryIpods + "']").exists(),
                "There is no link for category 'iPods' in the 'categories1' sitemap!");

        //Проверяем, что ссылка на категорию "Android" отсутствует
        softAssert.assertFalse($("[href='" + urlForCategoryAndroid + "']").exists(),
                "There is a link for category 'Android' but shouldn't in the 'categories1' sitemap!");

        screenshot("GeneralSettings_ExcludeCategories_WithoutPrice");
        softAssert.assertAll();
        System.out.println("GeneralSettings_ExcludeCategories_WithoutPrice has passed successfully!");
    }
}