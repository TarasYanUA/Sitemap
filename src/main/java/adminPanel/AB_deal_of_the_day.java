package adminPanel;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class AB_deal_of_the_day {
    public AB_deal_of_the_day(){super();}

    private SelenideElement ab_menu = $(".ab__am-menu .dropdown-toggle");
    private SelenideElement section_GeneralSettings = $(".ab__am-menu a[href$='addon=ab__deal_of_the_day']");
    private SelenideElement tab_setting = $("#settings");
    public SelenideElement setting_AddPromotionsToXml = $("input[id*='addon_option_ab__deal_of_the_day_ab__as_add_to_sitemap_']");

    public void navigateToSection_GeneralSettings(){
        ab_menu.click();
        section_GeneralSettings.click();
        tab_setting.click();
    }
}
