package e_integrations;

import testRunner.TestRunner;
import adminPanel.BasicPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;

/*
Модуль "AB: SEO оптимизация страниц тегов и автоназначение по правилам":
    * Устанавливаем модуль
    * Включаем настройку
*/

public class Integration__AB_seo_for_tags extends TestRunner {

    @Test
    public void checkIntegration_AB_seo_for_tags() throws Exception {
        BasicPage basicPage = new BasicPage();
        
        String url = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] split = url.split("admin");
        String mainUrl = split[0]; //получили ссылку

        //Устанавливаем модуль "AB: SEO оптимизация страниц тегов и автоназначение по правилам"
        basicPage.installAddonAtAddonsManager(basicPage.menuOfAB__seo_for_tags, "ab__seo_for_tags",
                "form[name=ab_install_form_54344]");
        basicPage.navigateTo_GeneralSettingOf_ab_seo_for_tags();
        if(!$("input[id*='addon_option_ab__seo_for_tags_index_tag_pages_']").isSelected()){
            $("input[id*='addon_option_ab__seo_for_tags_index_tag_pages_']").click();
            basicPage.button_Save.click();
        }

        //Настраиваем XML-карту сайта
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        basicPage.button_Save.click();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(1);
        String urlForXMLTagSport = sitemapSettings.findLinkByPartialName("other_links1");
        String urlForXMLTags = sitemapSettings.findLinkByPartialName("custom_links2");

        Selenide.executeJavaScript("window.open('" + urlForXMLTagSport + "');");
        Utils.shiftBrowserTab(2);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на страницу тега "Спорт" присутствует
        String urlOfTagSport = mainUrl + "sport/";
        System.out.println("Ссылка на тег 'sport': " + urlOfTagSport);
        softAssert.assertTrue($x("//*[contains(@href, '" + urlOfTagSport + "')]").exists(),
                "There is no link to the page of tag 'Sport' in the 'other_links1.xml' sitemap!");
        Selenide.executeJavaScript("window.open('" + urlForXMLTags + "');");
        Utils.shiftBrowserTab(3);

        //Проверяем, что ссылка на страницу всех тегов присутствует
        String urlForAllTags = mainUrl + "tags";
        System.out.println("Ссылка на страницу всех тегов: " + urlForAllTags);
        softAssert.assertTrue($x("//*[contains(@href, '" + urlForAllTags + "')]").exists(),
                "There is no link to the page of all tags in the 'custom_links2.xml' sitemap!");

        screenshot("Integration__AB_seo_for_tags");
        softAssert.assertAll();
        System.out.println("Integration__AB_seo_for_tags has passed successfully!");
    }
}
