package adminPanel;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class AB_images_seo {
    public AB_images_seo(){super();}

    public SelenideElement field_ImageNumber = $("#ab__is_P_N");
    public SelenideElement field_TitlePrefix = $("input[name='ab__is[P][T][prefix]']");
    public SelenideElement field_TitleText = $("#ab__is_P_T_text");
    public SelenideElement field_TitleSuffix = $("input[name='ab__is[P][T][suffix]']");
    private SelenideElement ab_menu = $(".ab__am-menu .dropdown-toggle");
    private SelenideElement section_GeneralSettings = $(".nav__actions-adv-buttons a[href$='addon=ab__images_seo']");
    private SelenideElement tab_settings = $("#settings");
    public SelenideElement setting_AttributeGenerationMethod = $("select[id*='addon_option_ab__images_seo_override_']");

    public void navigateToGeneralSettings(){
        ab_menu.click();
        section_GeneralSettings.click();
        tab_settings.click();
    }
}