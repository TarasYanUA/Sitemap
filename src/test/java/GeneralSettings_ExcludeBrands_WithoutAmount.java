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
Двум брендам "GoPro" и "Panasonic" настраиваем по 1 товару:
    * Бренд "GoPro" - с ценой и в наличии
    * Бренд "Panasonic" - с ценой и без наличия
Настройка модуля: "Общие -- Исключить бренды -- с товарами без наличия"
Проверяем, что:
    * Бренд "GoPro" присутствует в карте сайта
    * Бренд "Panasonic" отсутствует в карте сайта
*/

public class GeneralSettings_ExcludeBrands_WithoutAmount extends TestRunner{
    @Test
    public void checkGeneralSettings_ExcludeBrands_WithoutAmount() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Настраиваем товары двух брендов
        String url = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] split = url.split("admin");
        String mainUrl = split[0]; //получили ссылку
        csCartSettings.goAndSetEditingProductPage("GoPro - Hero3", "400", "15");
        csCartSettings.goAndSetEditingProductPage("KX-MB2000", "130", "0");

        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.setting_ExcludeBrands.selectOptionByValue("without_product_amount");
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
        sitemapSettings.setting_EnableXMLSitemap.click();   }
        if(!sitemapSettings.setting_FeatureVariantsSettings_IncludeToSitemap.isSelected()){
            sitemapSettings.setting_FeatureVariantsSettings_IncludeToSitemap.click();    }
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(1);
        String urlForFeatureBrand = sitemapSettings.splitLinkMethod(3);
        Selenide.executeJavaScript("window.open('"+urlForFeatureBrand+"');");
        shiftBrowserTab(2);
        //Проверяем, что ссылка на бренд "GoPro" присутствует
        String urlForGoPro = mainUrl + "gopro-ru/";
        String urlForPanasonic = mainUrl + "panasonic-ru/";
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text(urlForGoPro)),
                "There is no link for brand 'GoPro' in the 'feature_variants1' sitemap!");
        //Проверяем, что ссылка на бренд "Panasonic" отсутствует
        softAssert.assertFalse($(".pretty-print").has(Condition.text(urlForPanasonic)),
                "There is a link for brand 'Panasonic' but shouldn't in the 'feature_variants1' sitemap!");
        screenshot("GeneralSettings_ExcludeBrands_WithoutAmount");
        softAssert.assertAll();
        System.out.println("GeneralSettings_ExcludeBrands_WithoutAmount has passed successfully!");
    }
}