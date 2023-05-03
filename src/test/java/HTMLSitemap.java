import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.screenshot;

public class HTMLSitemap extends TestRunner{
    @Test
    public void checkHTMLSitemap(){
        //Включаем HTML-карту сайта
        CsCartSettings csCartSettings = new CsCartSettings();
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_HTMLSitemap.click();
        if(!sitemapSettings.setting_EnableHTMLSitemap.isSelected()){
            sitemapSettings.setting_EnableHTMLSitemap.click();
            csCartSettings.button_Save.click();
        }

        //Работаем на витрине
        csCartSettings.storefrontMainButton.click();
        shiftBrowserTab(1);
        String storefrontUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        String urlOfHTMLSitemap = storefrontUrl + "sitemap-ru/";
        Selenide.executeJavaScript("window.open('" + urlOfHTMLSitemap + "');");
        shiftBrowserTab(2);
        screenshot("HTMLSitemap on storefront!");
    }
}
