package e_integrations;

import testRunner.TestRunner;
import adminPanel.AB_landing_categories;
import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Selenide.*;

/*
Модуль "AB: Посадочные категории":
    * Устанавливаем модуль
    * Добавляем демо-данные
    * Включаем настройку
*/

public class Integration__AB_landing_categories extends TestRunner {

    @Test
    public void checkIntegration_AB_landing_categories(){
        CsCartSettings csCartSettings = new CsCartSettings();
        //Устанавливаем модуль "AB: Посадочные категории"
        csCartSettings.installAddonAtAddonsManager(csCartSettings.menuOfAB__landing_categories, "ab__landing_categories", "form[name=ab_install_form_54315]");
        AB_landing_categories ab_landing_categories = csCartSettings.addDemoDataTo_ab_landing_categories();
        ab_landing_categories.navigateToSection_GeneralSettings();
        if(!ab_landing_categories.setting_AddCatalogToXml.isSelected()){
            ab_landing_categories.setting_AddCatalogToXml.click();
            csCartSettings.button_Save.click();
        }

        //Работаем со страницей категории
        csCartSettings.navigateToSection_Categories();
        $x("//table[contains(@class, 'table-tree')] //a[contains(text(), 'AB: Спорт и отдых')]").click();
        $("#elm_category_status_0_a").click();
        csCartSettings.button_Save.click();
        csCartSettings.gearwheelOnEditingPage.click();
        csCartSettings.button_Preview.click();
        shiftBrowserTab(1);
        String url = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] split = url.split("\\?");
        String urlOfLandingCategory = split[0]; //получили ссылку на категорию
        System.out.println("Ссылка на кетегорию: " + urlOfLandingCategory);
        shiftBrowserTab(0);

        //Настраиваем XML-карту сайта
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
            sitemapSettings.setting_EnableXMLSitemap.click();   }
        if(!sitemapSettings.setting_CategoriesSettings_IncludeToSitemap.isSelected()){
            sitemapSettings.setting_CategoriesSettings_IncludeToSitemap.click();    }
        sitemapSettings.setting_ExcludeCategories.selectOptionByValue("none");
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(2);
        String urlForXMLCategories = sitemapSettings.splitLinkMethod(2);
        Selenide.executeJavaScript("window.open('" + urlForXMLCategories + "');");
        shiftBrowserTab(3);

        //Проверяем, что в карте-сайта присутствует ссылка на посадочную категорию
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($x("//*[contains(@href, '" + urlOfLandingCategory + "')]").exists(),
                "There is no link for a landing category in the 'categories1' sitemap!");
        screenshot("Integration__AB_landing_categories");
        softAssert.assertAll();
        System.out.println("Integration__AB_landing_categories has passed successfully!");
    }
}