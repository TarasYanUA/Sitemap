import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Selenide.*;

/*
Настройка модуля: "Общие -- Исключить категории -- не исключать"
Проверяем, что:
    * Категория "iPods" присутствует в карте сайта
    * Категория "Android" присутствует в карте сайта
*/

public class GeneralSettings_ExcludeCategories_DoNotExclude extends TestRunner{
    @Test
    public void checkGeneralSettings_ExcludeCategories_DoNotExclude() {
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.navigateToEditingCategoryPage();
        csCartSettings.selectCategory_Ipods.click();
        csCartSettings.goToStorefront_CategoryPage(1);
        String currentUrl_CategoryIpods = WebDriverRunner.getWebDriver().getCurrentUrl(); //Получили ссылку категории "iPods"
        String[] arrayIpods = currentUrl_CategoryIpods.split("\\?");
        String urlForCategoryIpods = arrayIpods[0];
        System.out.println("iPods URL is: " + urlForCategoryIpods);
        csCartSettings.shiftBrowserTab(0);
        csCartSettings.navigateToEditingCategoryPage();
        csCartSettings.selectCategory_Android.click();
        csCartSettings.goToStorefront_CategoryPage(2);
        String currentUrl_CategoryAndroid = WebDriverRunner.getWebDriver().getCurrentUrl(); //Получили ссылку категории "Android"
        csCartSettings.shiftBrowserTab(0);
        String[] arrayAndroid = currentUrl_CategoryAndroid.split("\\?");
        String urlForCategoryAndroid = arrayAndroid[0];
        System.out.println("Android URL is: " + urlForCategoryAndroid);

        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.setting_ExcludeCategories.selectOptionByValue("none");
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
        sitemapSettings.setting_EnableXMLSitemap.click();   }
        if(!sitemapSettings.setting_CategoriesSettings_IncludeToSitemap.isSelected()){
            sitemapSettings.setting_CategoriesSettings_IncludeToSitemap.click();    }
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        csCartSettings.shiftBrowserTab(3);
        String urlForCategories = sitemapSettings.splitLinkMethod(2);
        Selenide.executeJavaScript("window.open('"+urlForCategories+"');");
        csCartSettings.shiftBrowserTab(4);
        //Проверяем, что ссылка на категорию "iPods" присутствует
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text(urlForCategoryIpods)),
                "There is no link for category 'iPods'!");
        //Проверяем, что ссылка на категорию "Android" присутствует
        softAssert.assertTrue($(".pretty-print").has(Condition.text(urlForCategoryAndroid)),
                "There is no link for category 'Android'!");
        screenshot("GeneralSettings_ExcludeCategories_DoNotExclude");
        softAssert.assertAll();
        System.out.println("GeneralSettings_ExcludeCategories_DoNotExclude has passed successfully!");
    }
}