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

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

/*
Двум брендам "GoPro" и "Panasonic" настраиваем по 1 товару:
    * Бренд "GoPro" - без цены и без наличия
    * Бренд "Panasonic" - без товаров
Настройка модуля: "Общие -- Исключить бренды -- не исключать"
Проверяем, что:
    * Бренд "GoPro" присутствует в карте сайта
    * Бренд "Panasonic" присутствует в карте сайта
*/

public class GeneralSettings_ExcludeBrands_DoNotExclude extends TestRunner {

    @Test
    public void checkGeneralSettings_ExcludeBrands_DoNotExclude() throws Exception {
        BasicPage basicPage = new BasicPage();
        ProductPage productPage = new ProductPage();

        String url = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] split = url.split("admin");
        String mainUrl = split[0]; //получили ссылку
        productPage.goAndSetEditingProductPage("GoPro - Hero3", "0", "0");

        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.setting_ExcludeBrands.selectOptionByValue("none");
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_FeatureVariantsSettings_IncludeToSitemap, true);
        basicPage.saveSettings();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(1);
        String urlForFeatureBrand = sitemapSettings.findLinkByPartialName("feature_variants1");
        Selenide.executeJavaScript("window.open('" + urlForFeatureBrand + "');");
        Utils.shiftBrowserTab(2);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на бренд "GoPro" присутствует
        String urlForGoPro = mainUrl + "gopro-ru/";
        String urlForPanasonic = mainUrl + "panasonic-ru/";
        softAssert.assertTrue($("[href='" + urlForGoPro + "']").exists(),
                "There is no link for brand 'GoPro' in the 'feature_variants1' sitemap!");

        //Проверяем, что ссылка на бренд "Panasonic" присутствует
        softAssert.assertTrue($("[href='" + urlForPanasonic + "']").exists(),
                "There is no link for brand 'Panasonic' in the 'feature_variants1' sitemap!");

        screenshot("GeneralSettings_ExcludeBrands_DoNotExclude");
        softAssert.assertAll();
        System.out.println("GeneralSettings_ExcludeBrands_DoNotExclude has passed successfully!");
    }
}