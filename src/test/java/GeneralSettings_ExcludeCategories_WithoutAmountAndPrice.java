import adminPanel.AdminPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Alert;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.codeborne.selenide.Selenide.*;

/*
Двум категориям "iPods" и "Android" настраиваем по 2 товара:
    * Категория "iPods" - с ценой и в наличии
    * Категория "Android" - без цены и без наличия
Настройка модуля: "Общие -- Исключить категории -- с товарами без цен и наличия"
Итог: Категория "Android" не попадает в карту сайта.
*/

public class GeneralSettings_ExcludeCategories_WithoutAmountAndPrice extends TestRunner{
    @Test
    public void checkGeneralSettings_ExcludeCategories_WithoutAmountAndPrice() {
        AdminPage adminPage = new AdminPage();
        //Настраиваем первую категорию "iPods"
        adminPage.navigateToEditingCategoryPage();
        adminPage.selectCategory_Ipods.click();
        adminPage.goToStorefront_CategoryPage(1);
        String currentUrl_CategoryIpods = WebDriverRunner.getWebDriver().getCurrentUrl(); //Запоминаем ссылку категории "iPods"
        switchTo().window(0);
        adminPage.navigateToProductsOfCategory();
        adminPage.clickAndType_PriceForCategory_ProdOne("249.00");
        adminPage.clickAndType_AmountForCategory_ProdOne("10");
        adminPage.clickAndType_PriceForCategory_ProdTwo("255.00");
        adminPage.clickAndType_AmountForCategory_ProdTwo("15");
        adminPage.button_Save.click();
        //Настраиваем вторую категорию "Android"
        adminPage.navigateToEditingCategoryPage();
        adminPage.selectCategory_Android.click();
        adminPage.goToStorefront_CategoryPage(2);
        String currentUrl_CategoryAndroid = WebDriverRunner.getWebDriver().getCurrentUrl(); //Запоминаем ссылку категории "Android"
        switchTo().window(0);
        adminPage.navigateToProductsOfCategory();
        if($$(".products-list__image").size() > 2){
            do{
                $(".mobile-hide .dropdown-icon--tools").hover().click();
                $("a[href*='products.delete']").click();
                Alert alert = Selenide.webdriver().driver().switchTo().alert();
                alert.accept();
                Selenide.sleep(1500);
            } while ($$(".products-list__image").size() > 2);
        }
        adminPage.clickAndType_PriceForCategory_ProdOne("0");
        adminPage.clickAndType_AmountForCategory_ProdOne("0");
        adminPage.clickAndType_PriceForCategory_ProdTwo("0");
        adminPage.clickAndType_AmountForCategory_ProdTwo("0");
        adminPage.button_Save.click();

        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = adminPage.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.setting_ExcludeCategories.selectOptionByValue("without_product_amount_and_price");
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
        sitemapSettings.setting_EnableXMLSitemap.click();   }
        if(!sitemapSettings.setting_CategoriesSettings_IncludeToSitemap.isSelected()){
            sitemapSettings.setting_CategoriesSettings_IncludeToSitemap.click();    }
        adminPage.button_Save.click();


        String[] arrayIpods = currentUrl_CategoryIpods.split("\\?");
        String urlForCategoryIpods = arrayIpods[0];
        System.out.println("URL is: " + urlForCategoryIpods);

        String[] arrayAndroid = currentUrl_CategoryAndroid.split("\\?");
        String urlForCategoryAndroid = arrayAndroid[0];
        System.out.println("URL is: " + urlForCategoryAndroid);
    }
}