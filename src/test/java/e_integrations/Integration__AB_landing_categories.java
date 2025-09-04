package e_integrations;

import testRunner.TestRunner;
import adminPanel.AB_landing_categories;
import adminPanel.BasicPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;

/*
Модуль "AB: Посадочные категории":
    * Устанавливаем модуль
    * Добавляем демо-данные
    * Включаем настройку
*/

public class Integration__AB_landing_categories extends TestRunner {

    @Test
    public void checkIntegration_AB_landing_categories() throws Exception {
        BasicPage basicPage = new BasicPage();

        //Устанавливаем модуль "AB: Посадочные категории"
        basicPage.installAddonAtAddonsManager(basicPage.menuOfAB__landing_categories, "ab__landing_categories", "form[name=ab_install_form_54315]");
        AB_landing_categories ab_landing_categories = basicPage.addDemoDataTo_ab_landing_categories();
        ab_landing_categories.navigateToSection_GeneralSettings();
        Utils.setCheckboxState(ab_landing_categories.setting_AddCatalogToXml, true);
        basicPage.button_Save.click();

        //Работаем со страницей категории
        basicPage.navigateToSection_Categories();
        $x("//table[contains(@class, 'table-tree')] //a[contains(text(), 'AB: Спорт и отдых')]").click();
        $("#elm_category_status_0_a").click();
        basicPage.button_Save.click();
        basicPage.gearwheelOnEditingPage.click();
        basicPage.button_Preview.click();
        Utils.shiftBrowserTab(1);
        String url = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] split = url.split("\\?");
        String urlOfLandingCategory = split[0]; //получили ссылку на категорию
        System.out.println("Ссылка на категорию: " + urlOfLandingCategory);
        Utils.shiftBrowserTab(0);

        //Настраиваем XML-карту сайта
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_CategoriesSettings_IncludeToSitemap, true);
        sitemapSettings.setting_ExcludeCategories.selectOptionByValue("none");
        basicPage.button_Save.click();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(2);
        String urlForXMLCategories = sitemapSettings.findLinkByPartialName("categories1");
        Selenide.executeJavaScript("window.open('" + urlForXMLCategories + "');");
        Utils.shiftBrowserTab(3);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что в карте-сайта присутствует ссылка на посадочную категорию
        softAssert.assertTrue($x("//*[contains(@href, '" + urlOfLandingCategory + "')]").exists(),
                "There is no link for a landing category in the 'categories1' sitemap!");

        screenshot("Integration__AB_landing_categories");
        softAssert.assertAll();
        System.out.println("Integration__AB_landing_categories has passed successfully!");
    }
}