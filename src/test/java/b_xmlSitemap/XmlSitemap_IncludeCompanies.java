package b_xmlSitemap;

import testRunner.TestRunner;
import adminPanel.BasicPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;

/*
Настройка модуля: "XML карта-сайта -- Компании в карте сайта"
*/

public class XmlSitemap_IncludeCompanies extends TestRunner {

    @Test
    public void checkXmlSitemap_IncludeCompanies() throws Exception {
        BasicPage basicPage = new BasicPage();
        
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLSitemap, true);
        Utils.setCheckboxState(sitemapSettings.setting_IncludeCompanies, true);
        basicPage.button_Save.click();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(1);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на компании присутствует в xml карте-сайта
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'companies')]").exists(),
                "There is no a link for companies in the xml-sitemap!");
        String urlForCompanies = sitemapSettings.findLinkByPartialName("companies1");
        Selenide.executeJavaScript("window.open('" + urlForCompanies + "');");
        Utils.shiftBrowserTab(2);

        //Проверяем, что ссылки на страницы компаний присутствуют
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'company_id')]").exists()
                        || $("[href*='company_id']").exists(),
                "There are no links for companies pages!");

        screenshot("XmlSitemap_IncludeCompanies");
        softAssert.assertAll();
        System.out.println("XmlSitemap_IncludeCompanies has passed successfully!");
    }
}