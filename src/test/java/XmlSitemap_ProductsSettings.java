import adminPanel.CsCartSettings;
import adminPanel.CustomersPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Selenide.*;

/*
Настройка модуля: "XML карта-сайта -- Настройки для товаров":
    Включить в карту сайта -- да
    Частота изменений -- Каждый день
    Приоритет -- 0.7
    Добавить предложения продавцов -- да
Настраиваем товар для продавца с помощью модуля "Общие товары для продавцов"
Проверяем, что у товара в ссылке должен присутствовать код 'vendor_id'
*/

public class XmlSitemap_ProductsSettings extends TestRunner{
    @Test
    public void checkXmlSitemap_ProductsSettings() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
        sitemapSettings.setting_EnableXMLSitemap.click();   }
        if(!sitemapSettings.setting_ProductsSettings_IncludeToSitemap.isSelected()){
            sitemapSettings.setting_ProductsSettings_IncludeToSitemap.click();    }
        sitemapSettings.setting_ProductsSettings_ChangeFrequency.selectOptionByValue("daily");
        sitemapSettings.setting_ProductsSettings_Priority.selectOptionByValue("0.7");
        if(!sitemapSettings.setting_ProductsSettings_AddVendorsOffers.isSelected()){
            sitemapSettings.setting_ProductsSettings_AddVendorsOffers.click();  }
        csCartSettings.button_Save.click();

        //Устанавливаем модуль "Общие товары для продавцов"
        csCartSettings.menuAddons.hover();
        csCartSettings.sectionDownloadedAddons.click();
        if($(".alert").exists()){
            $(".close.cm-notification-close").click();
        }   //Выключаем сообщение о предупредлении, если оно появилось
        csCartSettings.clickAndTypeSearchFieldAtManagementPage("Общие товары для продавцов");
        if($$("td.nowrap.right a[href*='addon=master_products']").size()>0) {
            csCartSettings.buttonInstallAddon.click();
            Selenide.sleep(3000);
        webdriver().driver().getWebDriver().navigate().refresh();   }
        //Работаем со страницей редактирования товара
        csCartSettings.navigateToEditingProductPage("USB-N53");
        if (!$("label[for*='elm_parent_product']").exists()) {
            csCartSettings.productVendor.click();
            csCartSettings.productBelongsToAllVendors.click();
            csCartSettings.button_Save.click();
        }
        CustomersPage customersPage = csCartSettings.navigateToCustomersPage();
        customersPage.gearwheelOfSimtechVendor.hover().click();
        customersPage.actAsUser.click();
        shiftBrowserTab(1);
        customersPage.sellProductAsVendor();
        shiftBrowserTab(0);
        csCartSettings.menuProducts.hover();
        csCartSettings.sectionProducts.click();
        csCartSettings.field_Search.click();
        csCartSettings.field_Search.clear();
        csCartSettings.field_Search.sendKeys("USB-N53");
        csCartSettings.field_Search.sendKeys(Keys.ENTER);
        if(customersPage.iconThumbUp.exists()) {customersPage.iconThumbUp.click();  }
        csCartSettings.chooseAnyProduct.click();
        csCartSettings.gearwheelOnEditingPage.click();
        csCartSettings.button_Preview.click();
        shiftBrowserTab(2);
        String currentUrl_ProductUSB = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] arrayProductUSB = currentUrl_ProductUSB.split("\\?");
        String urlForProductUSB = arrayProductUSB[0] + "?vendor_id";     //Получили ссылку товара "USB-N53" с кодом продавца
        System.out.println("URL for a product USB: " + urlForProductUSB);
        shiftBrowserTab(0);

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(3);
        //Проверяем, что ссылка на товары присутствует в xml карте-сайта
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text("products")),
                "There is no a link for products in the xml-sitemap!");
        String urlForProducts = sitemapSettings.splitLinkMethod(1);
        Selenide.executeJavaScript("window.open('"+urlForProducts+"');");
        shiftBrowserTab(4);
        //Проверяем, что Частота изменений "Каждый день"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<changefreq>daily</changefreq>")),
                "There is no Change frequency 'Daily'!");
        //Проверяем, что Приоритет "0.7"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<priority>0.7</priority>")),
                "There is no Priority '0.7'!");
        //Проверяем, что ссылка на товар "USB-N53" с кодом продавца присутствует
        softAssert.assertTrue($(".pretty-print").has(Condition.text(urlForProductUSB)),
                "There is no link for product 'USB-N53' or a vendor code is missed!");
        screenshot("XmlSitemap_ProductsSettings");
        softAssert.assertAll();
        System.out.println("XmlSitemap_ProductsSettings has passed successfully!");
    }
}