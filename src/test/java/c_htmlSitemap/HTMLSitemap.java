package c_htmlSitemap;

import testRunner.TestRunner;
import adminPanel.BasicPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.screenshot;

public class HTMLSitemap extends TestRunner {
    @Test
    public void checkHTMLSitemap(){
        BasicPage basicPage = new BasicPage();

        //Включаем HTML-карту сайта
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_HTMLSitemap.click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableHTMLSitemap, true);
        basicPage.button_Save.click();

        //Работаем на витрине
        String url = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] split = url.split("admin");
        String storefrontUrl = split[0]; //получили ссылку
        String urlOfHTMLSitemap = storefrontUrl + "sitemap-ru/";
        System.out.println("urlOfHTMLSitemap is: " + urlOfHTMLSitemap);
        Selenide.executeJavaScript("window.open('" + urlOfHTMLSitemap + "');");
        Utils.shiftBrowserTab(1);
        screenshot("htmlSitemap.HTMLSitemap on storefront (RU)");

        String urlOfHTMLSitemapRTL = storefrontUrl + "sitemap-ar/";
        System.out.println("urlOfHTMLSitemapRTL is: " + urlOfHTMLSitemapRTL);
        Selenide.executeJavaScript("window.open('" + urlOfHTMLSitemapRTL + "');");
        Utils.shiftBrowserTab(2);
        screenshot("HTMLSitemap on storefront (RTL)");
        System.out.println("HTMLSitemap has passed successfully!");
    }
}