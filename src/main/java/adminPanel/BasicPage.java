package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

public class BasicPage implements CheckMenuToBeActive {
    public BasicPage() {
        super();
    }

    public SelenideElement button_Save = $(".btn.btn-primary.cm-submit");
    public SelenideElement chooseAnyProduct = $(".products-list__image");
    public SelenideElement gearwheelOnEditingPage = $(".actions__wrapper .dropdown-icon--tools");
    public SelenideElement button_Preview = $(".dropdown-menu a[target='_blank']");

    SelenideElement menu_Products = $("a[href$='dispatch=products.manage'].main-menu-1__link");
    SelenideElement menu_Addons = $("a[href$='dispatch=addons.manage'].main-menu-1__link");
    SelenideElement menu_Vendors = $("a[href$='dispatch=companies.manage'].main-menu-1__link");

    SelenideElement section_Products = $(By.id("products_products"));
    SelenideElement section_Categories = $(By.id("products_categories"));
    SelenideElement section_DownloadedAddons = $("#addons_downloaded_add_ons");
    SelenideElement section_Vendors = $(By.id("vendors_vendors"));


    public void navigateToSection_Products() {
        checkMenuToBeActive("dispatch=products.manage", menu_Products);
        section_Products.click();
    }

    public CategoryPage navigateToSection_Categories() {
        checkMenuToBeActive("dispatch=products.manage", menu_Products);
        section_Categories.click();
        return new CategoryPage();
    }


    //Меню "Продавцы -- Продавцы"
    SelenideElement gearwheelOf_CsCartVendor = $("tr[data-ct-company-id='1'] .dropdown-icon--tools");
    SelenideElement section_ViewVendorAdmins = $(".dropleft.open a[href*='dispatch=profiles.manage']");
    SelenideElement gearwheelOfVendor = $(".dropdown-icon--tools");
    SelenideElement section_LogInAsUser = $(".dropleft.open a[href*='dispatch=profiles.act_as_user']");
    SelenideElement button_ProductsThatCanBeSold = $("a[href*='dispatch=products.master_products']");
    SelenideElement button_SellProduct = $("a[href*='dispatch=products.sell_master_product']");

    public void navigateTo_VendorAdminsPage() {
        checkMenuToBeActive("dispatch=companies.manage", menu_Vendors);
        section_Vendors.click();
        gearwheelOf_CsCartVendor.hover().click();
        section_ViewVendorAdmins.click();
        gearwheelOfVendor.hover().click();
        section_LogInAsUser.click();
    }

    public void sellProductAsVendor() {
        menu_Products.click();
        button_ProductsThatCanBeSold.click();
        if (button_SellProduct.exists()) {
            button_SellProduct.click();
            button_Save.click();
        }
    }


    //Меню "Модули -- Скачанные модули"
    SelenideElement menuOfSitemap = $("tr#addon_ab__advanced_sitemap button.btn.dropdown-toggle");
    SelenideElement section_SitemapSettings = $("div.nowrap a[href*='addon=ab__advanced_sitemap']");
    SelenideElement section_SitemapGenerating = $("div.nowrap a[href*='ab__advanced_sitemap.manage']");
    SelenideElement section_UserLinks = $("div.nowrap a[href*='ab__as_links.manage']");
    SelenideElement addonsManagerField_Search = $("#ab__am_search");
    public SelenideElement searchFieldAtManagementPage = $("#elm_addon");
    public SelenideElement button_InstallAddon = $("td.nowrap.right a[href*='addon=master_products']");
    SelenideElement menuOfABAddonsManager = $("tr#addon_ab__addons_manager button.btn.dropdown-toggle");
    SelenideElement section_ListOfAvailableSets = $("div.nowrap a[href*='ab__am.addons']");
    public SelenideElement menuOfAB__images_seo = $("tr#addon_ab__images_seo button.btn.dropdown-toggle");
    SelenideElement section_ManageAttributes = $("div.nowrap a[href$='ab__is.manage_attrs']");
    public SelenideElement menuOfAB__deal_of_the_day = $("tr#addon_ab__deal_of_the_day button.btn.dropdown-toggle");
    SelenideElement section_PromotionDemoData = $("div.nowrap a[href$='ab__dotd.demodata']");
    SelenideElement DemoDataTo_ab_deal_of_the_day = $(".ab__dotd_dbutton a");
    public SelenideElement menuOfAB__landing_categories = $("tr#addon_ab__landing_categories button.btn.dropdown-toggle");
    SelenideElement section_LandingCategoriesDemoData = $("div.nowrap a[href$='ab__lc.demodata']");
    SelenideElement DemoDataTo_ab_landing_categories = $(".cm-process-items");
    public SelenideElement menuOfAB__seo_for_tags = $("tr#addon_ab__seo_for_tags button.btn.dropdown-toggle");
    SelenideElement section_TagsGeneralSettings = $("div.nowrap a[href$='addon=ab__seo_for_tags']");
    public SelenideElement menuOfAB__seo_filters = $("tr#addon_ab__seo_filters button.btn.dropdown-toggle");
    SelenideElement section_SeoFiltersGeneralSettings = $("div.nowrap a[href$='addon=ab__seo_filters']");

    public void navigateTo_DownloadedAddonsPage() {
        checkMenuToBeActive("dispatch=addons.manage", menu_Addons);
        section_DownloadedAddons.click();
    }

    public SitemapSettings navigateTo_SitemapSettings() {
        navigateTo_DownloadedAddonsPage();
        menuOfSitemap.click();
        section_SitemapSettings.click();
        return new SitemapSettings();
    }

    public void navigateTo_SitemapGenerating() {
        navigateTo_DownloadedAddonsPage();
        menuOfSitemap.click();
        section_SitemapGenerating.click();
    }

    public void navigateTo_UserLinksSection() {
        navigateTo_DownloadedAddonsPage();
        menuOfSitemap.click();
        section_UserLinks.click();
    }

    public void installAddonAtAddonsManager(SelenideElement addonMenu, String addonCode, String installButton) {
        navigateTo_DownloadedAddonsPage();
        if (!$(addonMenu).exists()) {
            menuOfABAddonsManager.click();
            section_ListOfAvailableSets.click();
            addonsManagerField_Search.setValue(addonCode);
            addonsManagerField_Search.sendKeys(Keys.ENTER);
            Selenide.sleep(3000);
            $(installButton).click();
            UtilsAdm.switchToAndAcceptAlertWindow();
            $(menu_Addons).shouldBe(Condition.enabled);
        }
    }

    public AB_images_seo navigateTo_ab_images_seo() {
        navigateTo_DownloadedAddonsPage();
        menuOfAB__images_seo.click();
        section_ManageAttributes.click();
        return new AB_images_seo();
    }

    public AB_deal_of_the_day addDemoDataTo_ab_deal_of_the_day() {
        navigateTo_DownloadedAddonsPage();
        menuOfAB__deal_of_the_day.click();
        section_PromotionDemoData.click();
        DemoDataTo_ab_deal_of_the_day.click();
        Selenide.sleep(1000);
        return new AB_deal_of_the_day();
    }

    public AB_landing_categories addDemoDataTo_ab_landing_categories() {
        navigateTo_DownloadedAddonsPage();
        menuOfAB__landing_categories.click();
        section_LandingCategoriesDemoData.click();
        DemoDataTo_ab_landing_categories.click();
        Selenide.sleep(1000);
        return new AB_landing_categories();
    }

    public void navigateTo_GeneralSettingOf_ab_seo_for_tags() {
        navigateTo_DownloadedAddonsPage();
        menuOfAB__seo_for_tags.click();
        section_TagsGeneralSettings.click();
        $("#settings").click();
    }

    public AB_seo_filters navigateTo_GeneralSettingsOf_ab_seo_filters() {
        navigateTo_DownloadedAddonsPage();
        menuOfAB__seo_filters.click();
        section_SeoFiltersGeneralSettings.click();
        $("#settings").click();
        return new AB_seo_filters();
    }
}