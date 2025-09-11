package e_integrations;

import adminPanel.UtilsAdm;
import testRunner.TestRunner;
import adminPanel.AB_seo_filters;
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
Модуль "AB: SEO-страницы для фильтров":
    * Устанавливаем модуль
    * Включаем настройку
    * Добавляем Правило
    * Генерируем SEO-страницы для фильтров
*/

public class Integration__AB_seo_filters extends TestRunner {

    @Test
    public void checkIntegration_AB_seo_filters() throws Exception {
        BasicPage basicPage = new BasicPage();

        //Работа с модулем "AB: SEO страницы для фильтров"
        basicPage.installAddonAtAddonsManager(basicPage.menuOfAB__seo_filters, "ab__seo_filters", "form[name=ab_install_form_54338]");
        AB_seo_filters ab_seo_filters = basicPage.navigateTo_GeneralSettingsOf_ab_seo_filters();
        ab_seo_filters.setting_AddSeoPagesToSitemap.selectOptionByValue("all");
        basicPage.button_Save.click();
        ab_seo_filters.navigateToGenerationRulesForFilters();
        if (!$x("//a[text()='Операционная система']").exists() && !$x("//a[text()='Бренд']").exists()) {
            ab_seo_filters.button_AddRule.click();
            ab_seo_filters.field_Features.click();
            sleep(2000);
            ab_seo_filters.feature_Brand.scrollIntoCenter().click();
            sleep(1000);
            ab_seo_filters.feature_OperatingSystem.scrollIntoCenter().click();
            sleep(1000);
            ab_seo_filters.checkbox_IncludeSubcategories.click();
            ab_seo_filters.button_AddCategories.click();
            $(".ui-dialog").shouldBe(Condition.visible);
            ab_seo_filters.categoryComputers.click();
            ab_seo_filters.button_SaveCategories.click();
            ab_seo_filters.select_ParentCategories.selectOptionByValue("by_all_filter_categories");
            ab_seo_filters.placeAllPlaceholdersOnRulePage("[category] [variant]");
            ab_seo_filters.button_Create.click();
            ab_seo_filters.gearwhealOnRulePage.shouldBe(Condition.enabled).click();
            ab_seo_filters.button_GenerateRulePage.click();
            UtilsAdm.switchToAndAcceptAlertWindow();
        }
        ab_seo_filters.navigateToSeoPagesList();
        $("a[href$='samsung-android-2.2-froyo/']").click();
        Utils.shiftBrowserTab(1);
        String urlOfSeoPage = WebDriverRunner.getWebDriver().getCurrentUrl();   //получили ссылку на SEO-страницу
        System.out.println("Ссылка на SEO-страницу: " + urlOfSeoPage);
        Utils.shiftBrowserTab(0);

        //Настраиваем XML-карту сайта
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_CategoriesSettings_IncludeToSitemap, true);
        basicPage.button_Save.click();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(2);
        String urlForXMLCategories = sitemapSettings.findLinkByPartialName("categories2");
        Selenide.executeJavaScript("window.open('" + urlForXMLCategories + "');");
        Utils.shiftBrowserTab(3);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на SEO-страницу для фильтров присутствует
        softAssert.assertTrue($x("//*[contains(@href, '" + urlOfSeoPage + "')]").exists(),
                "There is no link to the SEO page for filters in the 'categories2.xml' sitemap!");

        screenshot("Integration__AB_seo_filters");
        softAssert.assertAll();
        System.out.println("Integration__AB_seo_filters has passed successfully!");
    }
}