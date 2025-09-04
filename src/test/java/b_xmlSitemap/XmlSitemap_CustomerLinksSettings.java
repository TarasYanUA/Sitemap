package b_xmlSitemap;

import testRunner.TestRunner;
import adminPanel.BasicPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

/*
Настройка модуля: "XML карта-сайта -- Настройки для пользовательских ссылок":
    Частота изменений -- Ежегодно
    Приоритет -- 1
*/

public class XmlSitemap_CustomerLinksSettings extends TestRunner {
    @Test
    public void checkXmlSitemap_CustomerLinksSettings() throws Exception {
        BasicPage basicPage = new BasicPage();

        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        sitemapSettings.setting_CustomerLinksSettings_ChangeFrequency.selectOptionByValue("yearly");
        sitemapSettings.setting_CustomerLinksSettings_Priority.selectOptionByValue("1");
        basicPage.button_Save.click();

        //Добавляем пользовательскую ссылку
        basicPage.navigateTo_UserLinksSection();
        String customerLink = "categories.catalog";
        if ($(".cm-pagination-container .no-items").exists()) {
            sitemapSettings.button_Add.click();
            $(".ui-dialog-title").shouldBe(Condition.appear);
            sitemapSettings.field_Link.click();
            sitemapSettings.field_Link.clear();
            sitemapSettings.field_Link.sendKeys(customerLink);
            sitemapSettings.button_CreateUserLink.click();
        }

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(1);
        String urlForCustomerLinks = sitemapSettings.findLinkByPartialName("custom_links1");

        Selenide.executeJavaScript("window.open('" + urlForCustomerLinks + "');");
        Utils.shiftBrowserTab(2);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что Частота изменений "Ежегодно"
        softAssert.assertTrue($("changefreq").has(Condition.text("yearly")),
                "There is no Change frequency 'Yearly'!");

        //Проверяем, что Приоритет "1"
        softAssert.assertTrue($("priority").has(Condition.text("1")),
                "There is no Priority '1'!");

        //Проверяем, что пользовательская ссылка присутствует
        softAssert.assertTrue($("[href*='catalog']").exists(),
                "There is no customer link in the xml sitemap 'custom_links1'!");

        screenshot("XmlSitemap_CustomerLinksSettings");
        softAssert.assertAll();
        System.out.println("XmlSitemap_CustomerLinksSettings has passed successfully!");
    }
}