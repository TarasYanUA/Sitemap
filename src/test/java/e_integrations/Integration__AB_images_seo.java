package e_integrations;

import testRunner.TestRunner;
import adminPanel.AB_images_seo;
import adminPanel.BasicPage;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;

/*
Модуль "AB: Автоматические теги Alt и Title для изображений по шаблонам":
    * Устанавливаем модуль
    * Добавляем демо-данные
    * Включаем настройку
*/

public class Integration__AB_images_seo extends TestRunner {

    @Test
    public void checkIntegration_AB_images_seo() throws Exception {
        BasicPage basicPage = new BasicPage();
        
        //Устанавливаем модуль "AB: Автоматические теги Alt и Title для изображений по шаблонам"
        basicPage.installAddonAtAddonsManager(basicPage.menuOfAB__images_seo, "ab__images_seo", "form[name=ab_install_form_54348]");
        AB_images_seo abImagesSeo = basicPage.navigateTo_ab_images_seo();
        abImagesSeo.field_ImageNumber.setValue(", изображение [n]");
        abImagesSeo.field_TitlePrefix.setValue("Attribute_TitlePrefix");
        abImagesSeo.field_TitleText.setValue("[text][image_num]");
        abImagesSeo.field_TitleSuffix.setValue("Attribute_TitleSuffix");
        basicPage.saveSettings();
        abImagesSeo.navigateToGeneralSettings();
        abImagesSeo.setting_AttributeGenerationMethod.selectOptionByValue("always_generate");
        basicPage.saveSettings();

        //Включаем XML-карту изображений c Alt и Title
        SitemapSettings sitemapSettings = basicPage.navigateTo_SitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemapOfImages.click();
        Utils.setCheckboxState(sitemapSettings.setting_EnableXMLImages, true);
        Utils.setCheckboxState(sitemapSettings.setting_AddTitleAndCaption, true);
        Utils.setCheckboxState(sitemapSettings.setting_AddFeatureValues, true);
        basicPage.saveSettings();

        //Работаем с выгрузкой
        basicPage.navigateTo_SitemapGenerating();
        sitemapSettings.clickButton_GenerateSitemap();
        $("a[href*='sitemap.xml']").click();
        Utils.shiftBrowserTab(1);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что ссылка на XML-карту изображений присутствует в xml-карте сайта
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'images')]").exists(),
                "There is no a link for XML images in the xml-sitemap!");
        String urlForXMLImages = sitemapSettings.findLinkByPartialName("images1");
        Selenide.executeJavaScript("window.open('"+urlForXMLImages+"');");
        Utils.shiftBrowserTab(2);

        //Проверяем, что изображения присутствуют в карте сайта
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'images/detailed/')]").exists(),
                "There are no images in the 'images1' sitemap!");

        //Проверяем, что у изображений присутствует Title и Caption от модуля "AB: Автоматические теги Alt и Title для изображений по шаблонам"
        softAssert.assertTrue($x("//*[local-name()='span' and contains(text(), 'Attribute_TitleSuffix')]").exists(),
                "There is no Attribute_TitleSuffix from the add-on 'ab__images_seo' in the 'images1' sitemap!");

        screenshot("Integration__AB_images_seo");
        softAssert.assertAll();
        System.out.println("Integration__AB_images_seo has passed successfully!");
    }
}