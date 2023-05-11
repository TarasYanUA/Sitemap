import adminPanel.AB_images_seo;
import adminPanel.CsCartSettings;
import adminPanel.SitemapSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

/*
Модуль "AB: Автоматические теги Alt и Title для изображений по шаблонам":
    * Устанавливаем модуль
    * Добавляем демо-данные
    * Включаем настройку
*/

public class Integration_AB_images_seo extends TestRunner {
    @Test
    public void checkIntegration_AB_images_seo(){
        CsCartSettings csCartSettings = new CsCartSettings();
        //Устанавливаем модуль "AB: Автоматические теги Alt и Title для изображений по шаблонам"
        csCartSettings.installAddonAtAddonsManager(csCartSettings.menuOfAB__images_seo, "ab__images_seo", "form[name=ab_install_form_54348]");
        AB_images_seo abImagesSeo = csCartSettings.navigateTo_ab_images_seo();
        abImagesSeo.field_ImageNumber.click();
        abImagesSeo.field_ImageNumber.clear();
        abImagesSeo.field_ImageNumber.sendKeys(", изображение [n]");
        abImagesSeo.field_TitlePrefix.click();
        abImagesSeo.field_TitlePrefix.clear();
        abImagesSeo.field_TitlePrefix.sendKeys("Attribute_TitlePrefix");
        abImagesSeo.field_TitleText.click();
        abImagesSeo.field_TitleText.clear();
        abImagesSeo.field_TitleText.sendKeys("[text][image_num]");
        abImagesSeo.field_TitleSuffix.click();
        abImagesSeo.field_TitleSuffix.clear();
        abImagesSeo.field_TitleSuffix.sendKeys("Attribute_TitleSuffix");
        csCartSettings.button_Save.click();
        abImagesSeo.navigateToGeneralSettings();
        abImagesSeo.setting_AttributeGenerationMethod.selectOptionByValue("always_generate");
        csCartSettings.button_Save.click();

        //Включаем XML-карту изображений c Alt и Title
        SitemapSettings sitemapSettings = csCartSettings.navigateToSitemapSettings();
        sitemapSettings.tab_Settings.click();
        sitemapSettings.tab_XMLSitemapOfImages.click();
        if(!sitemapSettings.setting_EnableXMLImages.isSelected()){
            sitemapSettings.setting_EnableXMLImages.click();    }
        if(!sitemapSettings.setting_AddTitleAndCaption.isSelected()){
            sitemapSettings.setting_AddTitleAndCaption.click(); }
        if(!sitemapSettings.setting_AddFeatureValues.isSelected()){
            sitemapSettings.setting_AddFeatureValues.click();   }
        csCartSettings.button_Save.click();

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
                "There are no images in the 'images1' sitemap!");
        //Проверяем, что у изображений присутствует Title и Caption от модуля "AB: Автоматические теги Alt и Title для изображений по шаблонам"
        softAssert.assertTrue($(".pretty-print").has(Condition.text("Attribute_TitleSuffix")),
                "There is no Attribute_TitleSuffix from the add-on 'ab__images_seo' in the 'images1' sitemap!");
        softAssert.assertAll();
        screenshot("Integration_AB_images_seo");
        System.out.println("Integration_AB_images_seo has passed successfully!");
    }
}