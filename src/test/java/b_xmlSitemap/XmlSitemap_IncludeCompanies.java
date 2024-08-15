package b_xmlSitemap;

import testRunner.TestRunner;
import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Selenide.*;

/*
Настройка модуля: "XML карта-сайта -- Компании в карте сайта"
*/

public class XmlSitemap_IncludeCompanies extends TestRunner {

    @Test
    public void checkXmlSitemap_IncludeCompanies() {
        //Настраиваем настройки модуля
        CsCartSettings csCartSettings = new CsCartSettings();
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        if (!sitemapSettings.setting_EnableXMLSitemap.isSelected()) {
            sitemapSettings.setting_EnableXMLSitemap.click();
        }
        if (!sitemapSettings.setting_IncludeCompanies.isSelected()) {
            sitemapSettings.setting_IncludeCompanies.click();
        }
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(1);

        //Проверяем, что ссылка на компании присутствует в xml карте-сайта
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'companies')]").exists(),
                "There is no a link for companies in the xml-sitemap!");
        String urlForCompanies = sitemapSettings.splitLinkMethod(7);
        Selenide.executeJavaScript("window.open('" + urlForCompanies + "');");
        shiftBrowserTab(2);

        //Проверяем, что ссылки на страницы компаний присутствуют
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'company_id')]").exists()
                        || $("[href*='company_id']").exists(),
                "There are no links for companies pages!");
        screenshot("xmlSitemap.XmlSitemap_IncludeCompanies");
        softAssert.assertAll();
        System.out.println("xmlSitemap.XmlSitemap_IncludeCompanies has passed successfully!");
    }
}