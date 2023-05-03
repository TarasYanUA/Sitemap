import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

/*
Настройка модуля: "XML карта-сайта -- Настройки для пользовательских ссылок":
    Частота изменений -- Ежегодно
    Приоритет -- 1
*/

public class XmlSitemap_CustomerLinksSettings extends TestRunner{
    @Test
    public void checkXmlSitemap_CustomerLinksSettings() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.click();
        if(!sitemapSettings.setting_EnableXMLSitemap.isSelected()){
        sitemapSettings.setting_EnableXMLSitemap.click();   }
        sitemapSettings.setting_CustomerLinksSettings_ChangeFrequency.selectOptionByValue("yearly");
        sitemapSettings.setting_CustomerLinksSettings_Priority.selectOptionByValue("1");
        csCartSettings.button_Save.click();

        //Добавляем пользовательскую ссылку
        csCartSettings.navigateToUserLinksSection();
        String customerLink = "https://cs-cart.alexbranding.com/";
        if($(".cm-pagination-container .no-items").exists()) {
            sitemapSettings.button_Add.click();
            $(".ui-dialog-title").shouldBe(Condition.appear);
            sitemapSettings.field_Link.click();
            sitemapSettings.field_Link.clear();
            sitemapSettings.field_Link.sendKeys(customerLink);
            sitemapSettings.button_CreateUserLink.click();
        }

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(1);
        String urlForCustomerLinks = sitemapSettings.splitLinkMethod(4);
        Selenide.executeJavaScript("window.open('"+urlForCustomerLinks+"');");
        shiftBrowserTab(2);
        //Проверяем, что Частота изменений "Ежегодно"
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<changefreq>yearly</changefreq>")),
                "There is no Change frequency 'Yearly'!");
        //Проверяем, что Приоритет "1"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("<priority>1</priority>")),
                "There is no Priority '1'!");
        //Проверяем, что пользовательская ссылка присутствует
        softAssert.assertTrue($(".pretty-print").has(Condition.text(customerLink)),
                "There is no customer link in the xml sitemap!");
        screenshot("XmlSitemap_CustomerLinksSettings");
        softAssert.assertAll();
        System.out.println("XmlSitemap_CustomerLinksSettings has passed successfully!");
    }
}