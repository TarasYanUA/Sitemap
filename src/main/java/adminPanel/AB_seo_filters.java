package adminPanel;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class AB_seo_filters {
    public AB_seo_filters(){super();}

    public SelenideElement setting_AddSeoPagesToSitemap = $("select[input*='addon_option_ab__seo_filters_add_to_sitemap_']");
}