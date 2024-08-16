package c_htmlSitemap;

import testRunner.TestRunner;
import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.screenshot;

public class HTMLSitemap extends TestRunner {
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
        String url = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] split = url.split("admin");
        String storefrontUrl = split[0]; //получили ссылку
        String urlOfHTMLSitemap = storefrontUrl + "sitemap-ru/";
        System.out.println("urlOfHTMLSitemap is: " + urlOfHTMLSitemap);
        Selenide.executeJavaScript("window.open('" + urlOfHTMLSitemap + "');");
        shiftBrowserTab(1);
        screenshot("htmlSitemap.HTMLSitemap on storefront (RU)");

        String urlOfHTMLSitemapRTL = storefrontUrl + "sitemap-ar/";
        System.out.println("urlOfHTMLSitemapRTL is: " + urlOfHTMLSitemapRTL);
        Selenide.executeJavaScript("window.open('" + urlOfHTMLSitemapRTL + "');");
        shiftBrowserTab(2);
        screenshot("HTMLSitemap on storefront (RTL)");
        System.out.println("HTMLSitemap has passed successfully!");
    }
}