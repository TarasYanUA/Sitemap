import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Selenide.*;

/*
Двум категориям "iPods" и "Android" настраиваем по 2 товара:
    * Категория "iPods" - 1 товар без цены, 1 товар без наличия
    * Категория "Android" - без цены и без наличия
Настройка модуля: "Общие -- Исключить категории -- с товарами без цен и наличия"
Проверяем, что:
    * Категория "iPods" присутствует в карте сайта
    * Категория "Android" отсутствует в карте сайта
*/

public class GeneralSettings_ExcludeCategories_WithoutAmountAndPrice extends TestRunner{
    @Test
    public void checkGeneralSettings_ExcludeCategories_WithoutAmountAndPrice() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Настраиваем первую категорию "iPods"
        csCartSettings.navigateToEditingCategoryPage();
        csCartSettings.selectCategory_Ipods.click();
        csCartSettings.goToStorefront_CategoryPage(1);
        String currentUrl_CategoryIpods = WebDriverRunner.getWebDriver().getCurrentUrl(); //Получили ссылку категории "iPods"
        String[] arrayIpods = currentUrl_CategoryIpods.split("\\?");
        String urlForCategoryIpods = arrayIpods[0];
        System.out.println("iPods URL is: " + urlForCategoryIpods);
        shiftBrowserTab(0);
        csCartSettings.goAndSetFirstProductOfCategory("0", "10");
        csCartSettings.goAndSetSecondProductOfCategory("255", "0");
        csCartSettings.button_Save.click();
        //Настраиваем вторую категорию "Android"
        csCartSettings.navigateToEditingCategoryPage();
        csCartSettings.selectCategory_Android.click();
        csCartSettings.goToStorefront_CategoryPage(2);
        String currentUrl_CategoryAndroid = WebDriverRunner.getWebDriver().getCurrentUrl(); //Получили ссылку категории "Android"
        shiftBrowserTab(0);
        String[] arrayAndroid = currentUrl_CategoryAndroid.split("\\?");
        String urlForCategoryAndroid = arrayAndroid[0];
        System.out.println("Android URL is: " + urlForCategoryAndroid);
        csCartSettings.goAndSetFirstProductOfCategory("0", "0");
        csCartSettings.goAndSetSecondProductOfCategory("0", "0");
        csCartSettings.button_Save.click();

        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.setting_ExcludeCategories.selectOptionByValue("without_product_amount_and_price");
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
        shiftBrowserTab(3);
        String urlForCategories = sitemapSettings.splitLinkMethod(2);
        Selenide.executeJavaScript("window.open('"+urlForCategories+"');");
        shiftBrowserTab(4);
        //Проверяем, что ссылка на категорию "iPods" присутствует
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text(urlForCategoryIpods)),
                "There is no link for category 'iPods' in the 'categories1' sitemap!");
        //Проверяем, что ссылка на категорию "Android" отсутствует
        softAssert.assertFalse($(".pretty-print").has(Condition.text(urlForCategoryAndroid)),
                "There is a link for category 'Android' but shouldn't in the 'categories1' sitemap!");
        screenshot("GeneralSettings_ExcludeCategories_WithoutAmountAndPrice");
        softAssert.assertAll();
        System.out.println("GeneralSettings_ExcludeCategories_WithoutAmountAndPrice has passed successfully!");
    }
}