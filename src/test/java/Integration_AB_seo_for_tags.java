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
Модуль "AB: SEO оптимизация страниц тегов и автоназначение по правилам":
    * Устанавливаем модуль
    * Включаем настройку
*/

public class Integration_AB_seo_for_tags extends TestRunner{
    @Test
    public void checkIntegration_AB_seo_for_tags(){
        CsCartSettings csCartSettings = new CsCartSettings();
        String url = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] split = url.split("admin");
        String mainUrl = split[0]; //получили ссылку

        //Устанавливаем модуль "AB: SEO оптимизация страниц тегов и автоназначение по правилам"
        csCartSettings.installAddonAtAddonsManager(csCartSettings.menuOfAB__seo_for_tags, "ab__seo_for_tags", "form[name=ab_install_form_54344]");
        csCartSettings.navigateToGeneralSettingOf_ab_seo_for_tags();
        if(!$("input[id*='addon_option_ab__seo_for_tags_index_tag_pages_']").isSelected()){
            $("input[id*='addon_option_ab__seo_for_tags_index_tag_pages_']").click();
            csCartSettings.button_Save.click();
        }

        //Настраиваем XML-карту сайта
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
            sitemapSettings.setting_EnableXMLSitemap.click();
            csCartSettings.button_Save.click(); }

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(1);
        String urlForXMLTagSport = sitemapSettings.splitLinkMethod(10);
        String urlForXMLTags = sitemapSettings.splitLinkMethod(5);
        Selenide.executeJavaScript("window.open('" + urlForXMLTagSport + "');");
        shiftBrowserTab(2);
        //Проверяем, что ссылка на страницу тега "Спорт" присутствует
        SoftAssert softAssert = new SoftAssert();
        String urlOfTagSport = mainUrl + "sport/";
        softAssert.assertTrue($(".pretty-print").has(Condition.text(urlOfTagSport)),
                "There is no link to the page of tag 'Sport' in the sitemap!");
        Selenide.executeJavaScript("window.open('" + urlForXMLTags + "');");
        shiftBrowserTab(3);
        //Проверяем, что ссылка на страницу всех тегов присутствует
        String urlForAllTags = mainUrl + "tags";
        softAssert.assertTrue($(".pretty-print").has(Condition.text(urlForAllTags)),
                "There is no link to the page of all tags in the sitemap!");
        softAssert.assertAll();
        screenshot("Integration_AB_seo_for_tags");
        System.out.println("Integration_AB_seo_for_tags has passed successfully!");
    }
}
