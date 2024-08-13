import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
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

public class XmlSitemap_ProductsSettings extends TestRunner {
    public static final String PRODUCTNAME = "USB-N53";

    @Test
    public void checkXmlSitemap_ProductsSettings() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        if (!sitemapSettings.setting_EnableXMLSitemap.isSelected()) {
            sitemapSettings.setting_EnableXMLSitemap.click();
        }
        if (!sitemapSettings.setting_ProductsSettings_IncludeToSitemap.isSelected()) {
            sitemapSettings.setting_ProductsSettings_IncludeToSitemap.click();
        }
        sitemapSettings.setting_ProductsSettings_ChangeFrequency.selectOptionByValue("daily");
        sitemapSettings.setting_ProductsSettings_Priority.selectOptionByValue("0.7");
        if (!sitemapSettings.setting_ProductsSettings_AddVendorsOffers.isSelected()) {
            sitemapSettings.setting_ProductsSettings_AddVendorsOffers.click();
        }
        csCartSettings.button_Save.click();

        //Устанавливаем модуль "Общие товары для продавцов"
        csCartSettings.navigateTo_DownloadedAddonsPage();
        if ($(".alert").exists()) {   //Выключаем сообщение о предупреждении, если оно появилось
            $(".close.cm-notification-close").click();
        }
        csCartSettings.clickAndTypeSearchFieldAtManagementPage("Общие товары для продавцов");
        if ($("td.nowrap.right a[href*='addon=master_products']").exists()) {
            csCartSettings.button_InstallAddon.click();
            Selenide.sleep(3000);
            webdriver().driver().getWebDriver().navigate().refresh();
        }

        //Работаем со страницей редактирования товара
        csCartSettings.navigateToEditingProductPage(PRODUCTNAME);
        if (!$("label[for*='elm_parent_product']").exists()) {
            csCartSettings.productVendor.click();
            csCartSettings.productBelongsToAllVendors.click();
            csCartSettings.button_Save.click();
        }
        csCartSettings.navigateTo_VendorAdminsPage();
        shiftBrowserTab(1);
        csCartSettings.sellProductAsVendor();
        shiftBrowserTab(0);
        csCartSettings.navigateToSection_Products();
        csCartSettings.field_productSearch.click();
        csCartSettings.field_productSearch.clear();
        csCartSettings.field_productSearch.sendKeys(PRODUCTNAME);
        Selenide.sleep(2000);
        if (csCartSettings.button_ThumbUp.exists()) {
            csCartSettings.button_ThumbUp.click();
            Selenide.sleep(2000);
        }
        csCartSettings.chooseAnyProduct.click();
        csCartSettings.gearwheelOnEditingPage.click();
        csCartSettings.button_Preview.click();
        shiftBrowserTab(2);
        String currentUrl_ProductUSB = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] arrayProductUSB = currentUrl_ProductUSB.split("\\?");
        String urlForProductUSB = arrayProductUSB[0] + "?vendor_id";     //Получили ссылку товара с кодом продавца
        System.out.println("URL for a product USB: " + urlForProductUSB);
        shiftBrowserTab(0);

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(3);

        //Проверяем, что ссылка на товары присутствует в xml карте-сайта
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'products')]").exists(),
                "There is no a link for products in the xml-sitemap!");
        String urlForProducts = sitemapSettings.splitLinkMethod(1);
        Selenide.executeJavaScript("window.open('" + urlForProducts + "');");
        shiftBrowserTab(4);

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