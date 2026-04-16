package adminPanel;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AB_seo_filters {
    public AB_seo_filters(){super();}

    public SelenideElement setting_AddSeoPagesToSitemap = $("select[id*='addon_option_ab__seo_filters_add_to_sitemap_']");
    SelenideElement ab_menu = $(".ab__am-menu .dropdown-toggle");
    SelenideElement generationRulesForFilters = $(".ab__am-menu a[href$='ab__sf_rules.manage']");
    public SelenideElement button_AddRule = $x("//div[@id='tools_manage_ab__sf_rules_adv_buttons']/div");
    public SelenideElement field_Features = $(".select2-search--inline .select2-search__field");
    public SelenideElement feature_OperatingSystem =
            $x("//li[@aria-label='Электроника']//div[contains(text(), 'Операционная система')]");
    public SelenideElement feature_Brand = $x("//div[contains(text(), 'Бренд [18]')]");
    public SelenideElement button_AddCategories = $x("//a[contains(@id, 'opener_picker_elm_categories')]");
    public SelenideElement categoryElectronics = $("span[id*='on_cat_166']");
    public SelenideElement categoryComputers = $("#input_cat_167");
    public SelenideElement button_SaveCategories = $(".cm-form-dialog-closer");
    public SelenideElement checkbox_IncludeSubcategories = $("#elm_subcats");
    public SelenideElement select_ParentCategories = $("#elm_generated_categories");
    public SelenideElement placeholderOne_PageTagH1 = $("#elm_tag_h1");
    public SelenideElement placeholderTwo_PageTitle = $("#elm_page_title");
    public SelenideElement placeholderThree_PageMetaTagDescription = $("#elm_meta_description");
    public SelenideElement placeholderFour_PageMetaTagKeywords = $("#elm_meta_keywords");
    public SelenideElement placeholderFive_PageBreadcrumb = $("#elm_breadcrumb");
    public SelenideElement placeholderSix_ProductPageBreadcrumb = $("#elm_product_breadcrumb");
    public SelenideElement button_Create = $(".nav__actions-bar .cm-submit");
    public SelenideElement gearwhealOnRulePage = $(".nav__actions-bar .dropdown-icon--tools");
    public SelenideElement button_GenerateRulePage = $("a[href*='ab__sf_rules.generate']");
    SelenideElement section_SeoPagesList = $(".adv-buttons a[href$='ab__sf_names.manage']");


    public void navigateToGenerationRulesForFilters(){
        ab_menu.click();
        generationRulesForFilters.click();
    }

    public void placeAllPlaceholdersOnRulePage(String placeholder){
        placeholderOne_PageTagH1.setValue(placeholder);
        placeholderTwo_PageTitle.setValue(placeholder);
        placeholderThree_PageMetaTagDescription.setValue(placeholder);
        placeholderFour_PageMetaTagKeywords.setValue(placeholder);
        placeholderFive_PageBreadcrumb.setValue(placeholder);
        placeholderSix_ProductPageBreadcrumb.setValue(placeholder);
    }

    public void navigateToSeoPagesList(){
        ab_menu.click();
        section_SeoPagesList.click();
    }
}