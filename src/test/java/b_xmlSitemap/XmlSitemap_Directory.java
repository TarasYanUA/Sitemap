package b_xmlSitemap;

import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testRunner.TestRunner;

import static com.codeborne.selenide.Selenide.*;

/*
Настройка модуля: "XML карта-сайта -- Каталог для XML-карты сайта"
*/

public class XmlSitemap_Directory extends TestRunner {

    @Test
    public void checkXmlSitemap_Directory() {
        CsCartSettings csCartSettings = new CsCartSettings();
        String textForSetting_XmlSitemapDirectory = "/my_part~for~url-is.not.simple";
        //Настраиваем настройки модуля
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemap.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        sitemapSettings.clickAndTypeSetting_XmlSitemapDirectory(textForSetting_XmlSitemapDirectory);
        csCartSettings.button_Save.click();

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();

        //Проверяем, что присутствует xml карта-сайта с указанным каталогом
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($("a[href$='" + textForSetting_XmlSitemapDirectory + "/sitemap.xml']").exists(),
                "There is no XML xitemap with my Directory");
        screenshot("XmlSitemap_Directory - Link of the sitemap with directory");

        //Проверяем, что в каталоге xml карты-сайта присутствуют ссылки на товары или категории
        $("a[href$='" + textForSetting_XmlSitemapDirectory + "/sitemap.xml']").click();
        shiftBrowserTab(1);
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'products')]").exists() ||
                $x("//*[local-name()='span' and contains(text(), 'categories')]").exists(),
                "There is no links for products and categories in the XML sitemap with my Directory!");
        screenshot("XmlSitemap_Directory - List of links in XML xitemap with my Directory");
        softAssert.assertAll();
        System.out.println("XmlSitemap_Directory has passed successfully!");
    }
}