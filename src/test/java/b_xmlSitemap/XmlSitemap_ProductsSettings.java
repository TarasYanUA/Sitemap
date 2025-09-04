package b_xmlSitemap;

import adminPanel.ProductPage;
import testRunner.TestRunner;
import adminPanel.BasicPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testRunner.Utils;

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

public class XmlSitemap_ProductsSettings extends TestRunner {
    public static final String PRODUCTNAME = "USB-N53";

    @Test
    public void checkXmlSitemap_ProductsSettings() throws Exception {
        BasicPage basicPage = new BasicPage();
        
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_ProductsSettings_IncludeToSitemap, true);
        sitemapSettings.setting_ProductsSettings_ChangeFrequency.selectOptionByValue("daily");
        sitemapSettings.setting_ProductsSettings_Priority.selectOptionByValue("0.7");
        Utils.setCheckboxState(sitemapSettings.setting_ProductsSettings_AddVendorsOffers, true);
        basicPage.button_Save.click();

        //Устанавливаем модуль "Общие товары для продавцов"
        basicPage.navigateTo_DownloadedAddonsPage();
        if ($(".alert").exists())       //Выключаем сообщение о предупреждении, если оно появилось
            $(".close.cm-notification-close").click();
        basicPage.searchFieldAtManagementPage.setValue("Общие товары для продавцов");
        if ($("td.nowrap.right a[href*='addon=master_products']").exists()) {
            basicPage.button_InstallAddon.click();
            Selenide.sleep(3000);
            webdriver().driver().getWebDriver().navigate().refresh();
        }

        //Работаем со страницей редактирования товара
        ProductPage productPage = new ProductPage();
        productPage.navigateToEditingProductPage(PRODUCTNAME);
        if (!$("label[for*='elm_parent_product']").exists()) {
            productPage.productVendor.click();
            productPage.productBelongsToAllVendors.click();
            basicPage.button_Save.click();
        }
        basicPage.navigateTo_VendorAdminsPage();
        Utils.shiftBrowserTab(1);
        basicPage.sellProductAsVendor();
        Utils.shiftBrowserTab(0);
        productPage.setThumbUp(PRODUCTNAME);
        basicPage.chooseAnyProduct.click();
        basicPage.gearwheelOnEditingPage.click();
        basicPage.button_Preview.click();
        Utils.shiftBrowserTab(2);
        String currentUrl_ProductUSB = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] arrayProductUSB = currentUrl_ProductUSB.split("\\?");
        String urlForProductUSB = arrayProductUSB[0] + "?vendor_id";     //Получили ссылку товара с кодом продавца
        System.out.println("URL for a product 'USB': " + urlForProductUSB);
        Utils.shiftBrowserTab(0);

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(3);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на товары присутствует в xml карте-сайта
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'products')]").exists(),
                "There is no a link for products in the xml-sitemap!");
        String urlForProducts = sitemapSettings.findLinkByPartialName("products1");
        Selenide.executeJavaScript("window.open('" + urlForProducts + "');");
        Utils.shiftBrowserTab(4);

        //Проверяем, что Частота изменений "Каждый день"
        softAssert.assertTrue($("changefreq").has(Condition.text("daily")),
                "There is no Change frequency 'Daily'!");

        //Проверяем, что Приоритет "0.7"
        softAssert.assertTrue($("priority").has(Condition.text("0.7")),
                "There is no Priority '0.7'!");

        //Проверяем, что ссылка на товар "USB-N53" с кодом продавца присутствует
        softAssert.assertTrue($x("//*[contains(@href, '" + urlForProductUSB + "')]").exists(),
                "There is no link for product 'USB-N53' or a vendor code is missed!");

        screenshot("XmlSitemap_ProductsSettings");
        softAssert.assertAll();
        System.out.println("XmlSitemap_ProductsSettings has passed successfully!");
    }
}