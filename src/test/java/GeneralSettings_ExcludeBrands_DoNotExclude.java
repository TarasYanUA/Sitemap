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
Настройка модуля: "Общие -- Исключить бренды -- не исключать"
Проверяем, что:
    * Бренд "GoPro" присутствует в карте сайта
    * Бренд "Panasonic" присутствует в карте сайта
*/

public class GeneralSettings_ExcludeBrands_DoNotExclude extends TestRunner{
    @Test
    public void checkGeneralSettings_ExcludeBrands_DoNotExclude() {
        CsCartSettings csCartSettings = new CsCartSettings();
        String url = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] split = url.split("admin");
        String mainUrl = split[0]; //получили ссылку
        csCartSettings.goAndSetEditingProductPage("GoPro - Hero3", "0", "0");
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.setting_ExcludeBrands.selectOptionByValue("none");
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
        csCartSettings.shiftBrowserTab(1);
        String urlForFeatureBrand = sitemapSettings.splitLinkMethod(3);
        Selenide.executeJavaScript("window.open('"+urlForFeatureBrand+"');");
        csCartSettings.shiftBrowserTab(2);
        //Проверяем, что ссылка на бренд "GoPro" присутствует
        String urlForGoPro = mainUrl + "gopro-ru/";
        String urlForPanasonic = mainUrl + "panasonic-ru/";
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text(urlForGoPro)),
                "There is no link for brand 'GoPro'!");
        //Проверяем, что ссылка на бренд "Panasonic" присутствует
        softAssert.assertTrue($(".pretty-print").has(Condition.text(urlForPanasonic)),
                "There is no link for brand 'Panasonic'!");
        screenshot("GeneralSettings_ExcludeBrands_DoNotExclude");
        softAssert.assertAll();
        System.out.println("GeneralSettings_ExcludeBrands_DoNotExclude has passed successfully!");
    }
}