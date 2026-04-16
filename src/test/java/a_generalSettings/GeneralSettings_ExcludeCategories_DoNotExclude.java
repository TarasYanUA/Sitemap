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
    * Категория "iPods" - без цены и без наличия
    * Категория "Android" - без товаров
Настройка модуля: "Общие -- Исключить категории -- не исключать"
Проверяем, что:
    * Категория "iPods" присутствует в карте сайта
    * Категория "Android" присутствует в карте сайта
*/

public class GeneralSettings_ExcludeCategories_DoNotExclude extends TestRunner {
    @Test
    public void checkGeneralSettings_ExcludeCategories_DoNotExclude() throws Exception {
        BasicPage basicPage = new BasicPage();

        CategoryPage categoryPage = basicPage.navigateToSection_Categories();
        categoryPage.openCategoriesList_MP3Players("iPod");
        categoryPage.goToStorefront_CategoryPage(1);
        String currentUrl_CategoryIpods = WebDriverRunner.getWebDriver().getCurrentUrl(); //Получили ссылку категории "iPods"
        String[] arrayIpods = currentUrl_CategoryIpods.split("\\?");
        String urlForCategoryIpods = arrayIpods[0];
        System.out.println("iPods URL is: " + urlForCategoryIpods);
        Utils.shiftBrowserTab(0);
        categoryPage.goAndSetFirstProductOfCategory("0", "0");
        categoryPage.button_SaveListOfProducts.click();
        basicPage.navigateToSection_Categories();
        categoryPage.openCategoriesList_MP3Players("Android");
        categoryPage.goToStorefront_CategoryPage(2);
        String currentUrl_CategoryAndroid = WebDriverRunner.getWebDriver().getCurrentUrl(); //Получили ссылку категории "Android"
        Utils.shiftBrowserTab(0);
        String[] arrayAndroid = currentUrl_CategoryAndroid.split("\\?");
        String urlForCategoryAndroid = arrayAndroid[0];
        System.out.println("Android URL is: " + urlForCategoryAndroid);

        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.setting_ExcludeCategories.selectOptionByValue("none");
        sitemapSettings.tab_XMLSitemap.scrollIntoCenter().click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_FeatureVariantsSettings_IncludeToSitemap, true);
        basicPage.saveSettings();

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

        //Проверяем, что ссылка на категорию "Android" присутствует
        softAssert.assertTrue($("[href='" + urlForCategoryAndroid + "']").exists(),
                "There is no link for category 'Android' in the 'categories1' sitemap!");

        screenshot("GeneralSettings_ExcludeCategories_DoNotExclude");
        softAssert.assertAll();
        System.out.println("GeneralSettings_ExcludeCategories_DoNotExclude has passed successfully!");
    }
}