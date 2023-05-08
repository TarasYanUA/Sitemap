import adminPanel.AB_seo_filters;
import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Alert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Selenide.$;

public class Integration_AB_seo_filters extends TestRunner{
    @Test
    public void checkIntegration_AB_seo_filters(){
        CsCartSettings csCartSettings = new CsCartSettings();
        //Работа с модулем "AB: SEO страницы для фильтров"
        csCartSettings.installAddonAtAddonsManager(csCartSettings.menuOfAB__seo_filters, "ab__seo_filters", "form[name=ab_install_form_54338]");
        AB_seo_filters ab_seo_filters = csCartSettings.navigateToGeneralSettingsOf_ab_seo_filters();
        ab_seo_filters.setting_AddSeoPagesToSitemap.selectOptionByValue("all");
        csCartSettings.button_Save.click();
        ab_seo_filters.navigateToGenerationRulesForFilters();
        ab_seo_filters.button_AddRule.click();
        ab_seo_filters.field_Features.click();
        ab_seo_filters.feature_OperatingSystem.click();
        ab_seo_filters.feature_Brand.click();
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
        Alert alert = Selenide.webdriver().driver().switchTo().alert();
        alert.accept();
        ab_seo_filters.navigateToSeoPagesList();
        $("a[href$='android-2.2-froyo-samsung/']").click();
        shiftBrowserTab(1);
        String urlOfSeoPage = WebDriverRunner.getWebDriver().getCurrentUrl();   //получили ссылку на SEO-страницу
        shiftBrowserTab(0);

        //Настраиваем XML-карту сайта
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
            sitemapSettings.setting_EnableXMLSitemap.click();   }
        if(!sitemapSettings.setting_CategoriesSettings_IncludeToSitemap.isSelected()){
            sitemapSettings.setting_CategoriesSettings_IncludeToSitemap.click();
        }
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(2);
        String urlForXMLCategories = sitemapSettings.splitLinkMethod(3);
        Selenide.executeJavaScript("window.open('" + urlForXMLCategories + "');");
        shiftBrowserTab(3);
        //Проверяем, что ссылка на SEO-страницу для фильтров присутствует
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text(urlOfSeoPage)),
                "There is no link to the SEO page for filters in the sitemap 'categories2.xml'!");
    }
}