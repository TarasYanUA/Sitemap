package adminPanel;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class SitemapSettings {
    public SitemapSettings(){super();}

    //вкладка "Общие"
    public SelenideElement tab_Settings = $("#settings");
    public SelenideElement setting_ExcludeCategories = $("select[id*='addon_option_ab__advanced_sitemap_exclude_categories_']");
    public SelenideElement setting_ExcludeBrands = $("select[id*='addon_option_ab__advanced_sitemap_exclude_brands_']");
    public SelenideElement setting_ExcludeProducts = $("select[id*='addon_option_ab__advanced_sitemap_exclude_products_']");
    public SelenideElement setting_ExcludeProductVariations = $("input[id*='addon_option_ab__advanced_sitemap_exclude_products_variations_']");

    //вкладка "XML-карта сайта"
    public SelenideElement tab_XMLSitemap = $("#ab__advanced_sitemap_xml_sitemap");
    public SelenideElement setting_EnableXMLSitemap = $("input[id*='addon_option_ab__advanced_sitemap_enable_xml_sitemap_']");
    public SelenideElement setting_IncludeCompanies = $("input[id*='addon_option_ab__advanced_sitemap_enable_xml_sitemap_']");
    public SelenideElement setting_AddLastModifiedDate = $("input[id*='addon_option_ab__advanced_sitemap_add_lastmod_']");
    public SelenideElement setting_ProductsSettings_IncludeToSitemap = $("input[id*='addon_option_ab__advanced_sitemap_include_products_']");
    public SelenideElement setting_ProductsSettings_ChangeFrequency = $("select[id*='addon_option_ab__advanced_sitemap_products_changefreq_']");
    public SelenideElement setting_ProductsSettings_Priority = $("select[id*='addon_option_ab__advanced_sitemap_products_priority_']");
    public SelenideElement setting_ProductsSettings_AddVendorsOffers = $("input[id*='addon_option_ab__advanced_sitemap_add_vendor_offers_']");
    public SelenideElement setting_CategoriesSettings_IncludeToSitemap = $("input[id*='addon_option_ab__advanced_sitemap_include_categories_']");
    public SelenideElement setting_CategoriesSettings_ChangeFrequency = $("select[id*='addon_option_ab__advanced_sitemap_categories_changefreq_']");
    public SelenideElement setting_CategoriesSettings_Priority = $("select[id*='addon_option_ab__advanced_sitemap_categories_priority_']");
    public SelenideElement setting_PagesSettings_IncludeToSitemap = $("input[id*='addon_option_ab__advanced_sitemap_include_pages_']");
    public SelenideElement setting_PagesSettings_IncludeBlogPages = $("input[id*='addon_option_ab__advanced_sitemap_include_blog_']");
    public SelenideElement setting_PagesSettings_ChangeFrequency = $("select[id*='addon_option_ab__advanced_sitemap_pages_changefreq_']");
    public SelenideElement setting_PagesSettings_Priority = $("select[id*='addon_option_ab__advanced_sitemap_pages_priority_']");
    public SelenideElement setting_FeatureVariantsSettings_IncludeToSitemap = $("input[id*='addon_option_ab__advanced_sitemap_include_extended_']");
    public SelenideElement setting_FeatureVariantsSettings_ChangeFrequency = $("select[id*='addon_option_ab__advanced_sitemap_feature_variants_changefreq_']");
    public SelenideElement setting_FeatureVariantsSettings_Priority = $("select[id*='addon_option_ab__advanced_sitemap_feature_variants_priority_']");
    public SelenideElement setting_CustomerLinksSettings_ChangeFrequency = $("select[id*='addon_option_ab__advanced_sitemap_custom_links_changefreq_']");
    public SelenideElement setting_CustomerLinksSettings_Priority = $("select[id*='addon_option_ab__advanced_sitemap_custom_links_priority_']");

    //вкладка HTML-карта сайта
    public SelenideElement tab_HTMLSitemap = $("#ab__advanced_sitemap_html_sitemap");
    public SelenideElement setting_EnableHTMLSitemap = $("input[id*='addon_option_ab__advanced_sitemap_enable_html_sitemap_']");

    //Секция "Генерация карты сайта"
    private SelenideElement button_GenerateSitemap = $("a[href*='ab__advanced_sitemap.generate_sitemap']");
    public SelenideElement xmlLink = $("a[href$='sitemap.xml']");

    //Секция "Пользовательские ссылки в XML-карте сайта"
    public SelenideElement button_Add = $("#opener_ab__as_add_link span");
    public SelenideElement field_Link = $("#elm_link");
    public SelenideElement button_CreateUserLink = $(".buttons-container-picker .btn.btn-primary");


    public void clickButton_GenerateSitemap(){
        button_GenerateSitemap.click();
        Selenide.sleep(3000);
    }

    public String splitLinkMethod (int partNumber) {
        String allUrls = $(".pretty-print").getText();
        String[] splitOne = allUrls.split("<loc>");
        int i = 0;
        while (i < splitOne.length) {
            i++;
        }
        String preliminaryResult = splitOne[partNumber];
        String[] finalResult = preliminaryResult.split("</loc>");
        int k = 0;
        while (k < finalResult.length) {
            k++;
        }
        System.out.println("Sitemap link is: " + finalResult[0]);
        return finalResult[0];   //Получили ссылку на карту сайта
    }
}