package d_xmlSitemapOfImages;

import testRunner.TestRunner;
import adminPanel.BasicPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;

public class XmlSitemapOfImages extends TestRunner {
    @Test
    public void checkXMLSitemapOfImages() throws Exception {
        BasicPage basicPage = new BasicPage();

        //Включаем XML-карту изображений
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemapOfImages.click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLImages, true);
        basicPage.saveSettings();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(1);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на XML-карту изображений присутствует в xml карте-сайта
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'images')]").exists(),
                "There is no link for XML images in the xml-sitemap!");

        String urlForXMLImages = sitemapSettings.findLinkByPartialName("images1");
        Selenide.executeJavaScript("window.open('" + urlForXMLImages + "');");
        Utils.shiftBrowserTab(2);

        //Проверяем, что изображения присутствуют в карте сайта
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'images/detailed/')]").exists(),
                "There are no images in the xml-sitemap");

        screenshot("XmlSitemapOfImages");
        softAssert.assertAll();
        System.out.println("XmlSitemapOfImages has passed successfully!");
    }
}