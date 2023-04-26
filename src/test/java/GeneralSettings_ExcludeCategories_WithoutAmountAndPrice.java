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
    * Категория "iPods" - с ценой и в наличии
    * Категория "Android" - без цены и без наличия
Настройка модуля: "Общие -- Исключить категории -- с товарами без цен и наличия"
Проверяем, что:
    * Категория "iPods" присутствует в карте сайта
    * Категория "Android" отсутствует в карте сайта.
*/

public class GeneralSettings_ExcludeCategories_WithoutAmountAndPrice extends TestRunner{
    @Test(priority = 1)
    public void setConfigurationsForGeneralSettings_ExcludeCategories_WithoutAmountAndPrice() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Настраиваем первую категорию "iPods"
        csCartSettings.navigateToEditingCategoryPage();
        csCartSettings.selectCategory_Ipods.click();
        csCartSettings.goToStorefront_CategoryPage(1);
        String currentUrl_CategoryIpods = WebDriverRunner.getWebDriver().getCurrentUrl(); //Получили ссылку категории "iPods"
        String[] arrayIpods = currentUrl_CategoryIpods.split("\\?");
        String urlForCategoryIpods = arrayIpods[0];
        System.out.println("iPods URL is: " + urlForCategoryIpods);
        switchTo().window(0);
        csCartSettings.navigateToProductsOfCategory();
        csCartSettings.clickAndType_PriceForCategory_ProdOne("249.00");
        csCartSettings.clickAndType_AmountForCategory_ProdOne("10");
        csCartSettings.clickAndType_PriceForCategory_ProdTwo("255.00");
        csCartSettings.clickAndType_AmountForCategory_ProdTwo("15");
        csCartSettings.button_Save.click();
        //Настраиваем вторую категорию "Android"
        csCartSettings.navigateToEditingCategoryPage();
        csCartSettings.selectCategory_Android.click();
        csCartSettings.goToStorefront_CategoryPage(2);
        String currentUrl_CategoryAndroid = WebDriverRunner.getWebDriver().getCurrentUrl(); //Получили ссылку категории "Android"
        switchTo().window(0);
        String[] arrayAndroid = currentUrl_CategoryAndroid.split("\\?");
        String urlForCategoryAndroid = arrayAndroid[0];
        System.out.println("Android URL is: " + urlForCategoryAndroid);
        csCartSettings.navigateToProductsOfCategory();
        if($$(".products-list__image").size() > 2){
            csCartSettings.deleteProductsFromCategory();
        }
        csCartSettings.clickAndType_PriceForCategory_ProdOne("0");
        csCartSettings.clickAndType_AmountForCategory_ProdOne("0");
        csCartSettings.clickAndType_PriceForCategory_ProdTwo("0");
        csCartSettings.clickAndType_AmountForCategory_ProdTwo("0");
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
        csCartSettings.shiftBrowserTab(1);
        String allUrls = $(".pretty-print").getText();
        String[] splitOne = allUrls.split("<loc>");
        int i=0;
        while (i < splitOne.length) {
            i++;    }
        String preliminaryResult = splitOne[2];
        String[] finalResult = preliminaryResult.split("</loc>");
        int k=0;
        while (k < finalResult.length) {
            k++;    }
        System.out.println("Final result is:" + finalResult[0]);
        String urlForCategories = finalResult[0];   //Получили ссылку на карту сайта категорий
        Selenide.executeJavaScript("window.open('"+urlForCategories+"');");
        csCartSettings.shiftBrowserTab(2);
        //Проверяем, что ссылка на категорию "iPods" присутствует
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text(urlForCategoryIpods)),
                "There is no link for category 'iPods'!");
        //Проверяем, что ссылка на категорию "Android" отсутствует
        softAssert.assertFalse($(".pretty-print").has(Condition.text(urlForCategoryAndroid)),
                "There is a link for category 'Android' but shouldn't!");
        screenshot("GeneralSettings_ExcludeCategories_WithoutAmountAndPrice");
        softAssert.assertAll();
    }
}