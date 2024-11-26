package adminPanel;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class AB_landing_categories {
    public AB_landing_categories(){super();}

    SelenideElement ab_menu = $(".ab__am-menu .dropdown-toggle");
    SelenideElement section_GeneralSettings = $(".ab__am-menu a[href$='addon=ab__landing_categories']");
    SelenideElement tab_setting = $("#settings");
    public SelenideElement setting_AddCatalogToXml = $("input[id*='addon_option_ab__landing_categories_ab__as_add_to_sitemap_']");


    public void navigateToSection_GeneralSettings(){
        ab_menu.click();
        section_GeneralSettings.click();
        tab_setting.click();
    }
}