import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Selenide.$;

public class XMLSitemapOfImages extends TestRunner {
    @Test
    public void checkXMLSitemapOfImages(){
        //Включаем XML-карту изображений
        CsCartSettings csCartSettings = new CsCartSettings();
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemapOfImages.click();
        if(!sitemapSettings.setting_EnableXMLImages.isSelected()){
            sitemapSettings.setting_EnableXMLImages.click();
            csCartSettings.button_Save.click();
        }

        //Работаем с выгрузкой
        csCartSettings.navigateToSitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        shiftBrowserTab(1);
        //Проверяем, что ссылка на XML-карту изображений присутствует в xml карте-сайта
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($(".pretty-print").has(Condition.text("images")),
                "There is no a link for XML images in the xml-sitemap!");
        String urlForXMLImages = sitemapSettings.splitLinkMethod(8);
        Selenide.executeJavaScript("window.open('"+urlForXMLImages+"');");
        shiftBrowserTab(2);
        //Проверяем, что изображения присутствуют в карте сайта
        softAssert.assertTrue($(".pretty-print").has(Condition.text("images/detailed/")),
                "There are no images in the xml-sitemap");
        softAssert.assertAll();
        System.out.println("XMLSitemapOfImages has passed successfully!");
    }
}