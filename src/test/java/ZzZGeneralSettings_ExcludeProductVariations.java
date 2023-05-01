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
Тест-кейс не записан в файл TestNG, потому что ещё не исправлена ошибка https://abteam.planfix.com/task/33046
Настройка модуля: "Общие -- Исключить вариации товаров из карты сайта"
*/

public class ZzZGeneralSettings_ExcludeProductVariations extends TestRunner{
    @Test
    public void checkGeneralSettings_ExcludeProductVariations(){
        //Переходим на страницу товара с вариациями (футболка синия)
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.navigateToEditingProductPage("Футболка, Цвет: Синий");
        csCartSettings.gearwheelOnEditingPage.click();
        csCartSettings.button_Preview.click();
        csCartSettings.shiftBrowserTab(1);
        String currentUrl_ProductTshirt = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] arrayProductTshirt = currentUrl_ProductTshirt.split("\\?");
        String urlForProductTshirt = arrayProductTshirt[0]; //Получили ссылку товара "Футболка, Цвет: Синий"
        System.out.println("URL for a product Tshirt: " + urlForProductTshirt);
        csCartSettings.shiftBrowserTab(0);

        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        if(!sitemapSettings.setting_ExcludeProductVariations.isSelected()){
            sitemapSettings.setting_ExcludeProductVariations.click();
        }
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
            sitemapSettings.setting_EnableXMLSitemap.click();   }
        if(!sitemapSettings.setting_ProductsSettings_IncludeToSitemap.isSelected()){
            sitemapSettings.setting_ProductsSettings_IncludeToSitemap.click();    }
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        csCartSettings.shiftBrowserTab(3);
        String urlForProducts = sitemapSettings.splitLinkMethod(1);
        Selenide.executeJavaScript("window.open('"+urlForProducts+"');");
        csCartSettings.shiftBrowserTab(4);

        SoftAssert softAssert = new SoftAssert();
        //Проверяем, что ссылка на товар "Футболка, Цвет: Синий" присутствует
        softAssert.assertTrue($(".pretty-print").has(Condition.text(urlForProductTshirt)),
                "There is no link for product 'T-Shirt'!");
        screenshot("GeneralSettings_ExcludeProductVariations");
        softAssert.assertAll();
        System.out.println("GeneralSettings_ExcludeProductVariations has passed successfully!");
    }
}