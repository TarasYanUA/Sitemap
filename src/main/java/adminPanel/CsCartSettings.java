package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

public class CsCartSettings implements CheckMenuToBeActive {
    public CsCartSettings() {
        super();
    }

    public SelenideElement button_Save = $(".btn.btn-primary.cm-submit");
    public SelenideElement button_SaveListOfProducts = $(".nav__actions-btn-save");
    public SelenideElement gearwheelOnEditingPage = $(".actions__wrapper .dropdown-icon--tools");

    //Меню "Товары --Товары"
    public SelenideElement menu_Products = $("a[href$='dispatch=products.manage'].main-menu-1__link");
    public SelenideElement section_Products = $(By.id("products_products"));
    public SelenideElement field_ProductPrice = $("#elm_price_price");
    public SelenideElement field_ProductAmount = $("#elm_in_stock");
    public SelenideElement button_dropdown = $(".btn-bar-left .dropdown-toggle");
    public SelenideElement chooseCategory_Tents = $("a[href*='products.manage.reset_view&cid=218']");
    public SelenideElement productVendor = $("#sw_product_data_company_id_selector_wrap_");
    public SelenideElement productBelongsToAllVendors = $("a[title='Все продавцы (общий товар)']");
    SelenideElement menuCustomers = $x("//li[contains(@class, 'dropdown nav__header-main-menu-item')]//a[@href='#customers']");
    SelenideElement customersPage = $x("//span[text()='Администраторы продавца']");

    public void navigateToSection_Products() {
        checkMenuToBeActive("dispatch=products.manage", menu_Products);
        section_Products.click();
    }

    public void goAndSetEditingProductPage(String name, String price, String amount) {
        navigateToSection_Products();
        field_productSearch.click();
        field_productSearch.clear();
        field_productSearch.sendKeys(name);
        Selenide.sleep(1500);
        chooseAnyProduct.click();
        field_ProductPrice.click();
        field_ProductPrice.clear();
        field_ProductPrice.sendKeys(price);
        field_ProductAmount.click();
        field_ProductAmount.clear();
        field_ProductAmount.sendKeys(amount);
        button_Save.click();
    }

    public void navigateToEditingProductPage(String name) {
        navigateToSection_Products();
        field_productSearch.click();
        field_productSearch.clear();
        field_productSearch.sendKeys(name);
        Selenide.sleep(1500);
        if (chooseAnyProduct.exists()) {
            chooseAnyProduct.click();
        }
    }

    public void deleteProductOnProductsSection(String name) {
        if ($(".alert.cm-notification-content").exists()) {     //Выключаем сообщение о сохранении, если оно появилось
            $(".close.cm-notification-close").click();
        }
        navigateToSection_Products();
        field_productSearch.click();
        field_productSearch.clear();
        field_productSearch.sendKeys(name);
        Selenide.sleep(2000);
        if(!$x("//p[text()='Здесь пока ничего нет']").exists())
            deleteAllProductsFromCategory();
    }

    //Меню "Товары -- Категории"
    public SelenideElement section_Categories = $(By.id("products_categories"));
    public SelenideElement selectCategory_Ipods = $(".longtap-selection a[href*='category_id=178']");
    public SelenideElement selectCategory_Android = $(".longtap-selection a[href$='category_id=182']");
    public SelenideElement selectCategory_Tents = $(".longtap-selection a[href*='category_id=218']");
    public SelenideElement button_ViewProducts = $(".dropleft a[href*='products.manage']");
    SelenideElement field_PriceForCategory_ProdOne = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[price]') + 1) = '[price]']])[1]");
    SelenideElement field_AmountForCategory_ProdOne = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[amount]') + 1) = '[amount]']])[1]");
    SelenideElement field_PriceForCategory_ProdTwo = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[price]') + 1) = '[price]']])[2]");
    SelenideElement field_AmountForCategory_ProdTwo = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[amount]') + 1) = '[amount]']])[2]");

    public void navigateToSection_Categories() {
        checkMenuToBeActive("dispatch=products.manage", menu_Products);
        section_Categories.click();
    }

    public void goToStorefront_CategoryPage(int tab) {
        Selenide.sleep(2000);
        gearwheelOnEditingPage.click();
        button_Preview.click();
        switchTo().window(tab);
    }

    public void goAndSetFirstProductOfCategory(String price, String amount) {
        Selenide.sleep(2000);
        gearwheelOnEditingPage.click();
        button_ViewProducts.click();
        if ($$(".products-list__image").size() > 2) {
            deleteProductsFromCategory();
        }
        field_PriceForCategory_ProdOne.click();
        field_PriceForCategory_ProdOne.clear();
        field_PriceForCategory_ProdOne.sendKeys(price);
        field_AmountForCategory_ProdOne.click();
        field_AmountForCategory_ProdOne.clear();
        field_AmountForCategory_ProdOne.sendKeys(amount);
    }

    public void goAndSetSecondProductOfCategory(String price, String amount) {
        field_PriceForCategory_ProdTwo.click();
        field_PriceForCategory_ProdTwo.clear();
        field_PriceForCategory_ProdTwo.sendKeys(price);
        field_AmountForCategory_ProdTwo.click();
        field_AmountForCategory_ProdTwo.clear();
        field_AmountForCategory_ProdTwo.sendKeys(amount);
    }

    public void setFirstProduct(String price, String amount) {
        if ($(".alert").exists()) {     //Выключаем сообщение о предупреждении, если оно появилось
            $(".close.cm-notification-close").click();
        }
        field_PriceForCategory_ProdOne.click();
        field_PriceForCategory_ProdOne.clear();
        field_PriceForCategory_ProdOne.sendKeys(price);
        field_AmountForCategory_ProdOne.click();
        field_AmountForCategory_ProdOne.clear();
        field_AmountForCategory_ProdOne.sendKeys(amount);
    }

    public void setSecondProduct(String price, String amount) {
        field_PriceForCategory_ProdTwo.click();
        field_PriceForCategory_ProdTwo.clear();
        field_PriceForCategory_ProdTwo.sendKeys(price);
        field_AmountForCategory_ProdTwo.click();
        field_AmountForCategory_ProdTwo.clear();
        field_AmountForCategory_ProdTwo.sendKeys(amount);
    }

    public void deleteProductsFromCategory() {
        do {
            $(".mobile-hide .dropdown-icon--tools").hover().click();
            $("a[href*='products.delete']").click();
            Alert alert = Selenide.webdriver().driver().switchTo().alert();
            alert.accept();
            Selenide.sleep(1500);
        } while ($$(".products-list__image").size() > 2);
    }

    public void deleteAllProductsFromCategory() {
        do {
            $(".mobile-hide .dropdown-icon--tools").hover().click();
            $("a[href*='products.delete']").click();
            Alert alert = Selenide.webdriver().driver().switchTo().alert();
            alert.accept();
            Selenide.sleep(1500);
        } while (!$$(".products-list__image").isEmpty());
    }


    //Меню "Модули -- Скачанные модули"
    public SelenideElement menu_Addons = $("a[href$='dispatch=addons.manage'].main-menu-1__link");
    public SelenideElement section_DownloadedAddons = $("#addons_downloaded_add_ons");
    public SelenideElement menuOfSitemap = $("tr#addon_ab__advanced_sitemap button.btn.dropdown-toggle");
    SelenideElement section_SitemapSettings = $("div.nowrap a[href*='addon=ab__advanced_sitemap']");
    SelenideElement section_SitemapGenerating = $("div.nowrap a[href*='ab__advanced_sitemap.manage']");
    SelenideElement section_UserLinks = $("div.nowrap a[href*='ab__as_links.manage']");
    public SelenideElement field_productSearch = $("input[form='search_filters_form']");
    public SelenideElement chooseAnyProduct = $(".products-list__image");
    public SelenideElement button_Preview = $("a[href*='profiles.view_product_as_user']");
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

    private void navigateTo_DownloadedAddonsPage() {
        checkMenuToBeActive("dispatch=addons.manage", menu_Addons);
        section_DownloadedAddons.click();
    }

    public SitemapSettings navigateToSitemapSettings() {
        navigateTo_DownloadedAddonsPage();
        menuOfSitemap.click();
        section_SitemapSettings.click();
        return new SitemapSettings();
    }

    public void navigateToSitemapGenerating() {
        navigateTo_DownloadedAddonsPage();
        menuOfSitemap.click();
        section_SitemapGenerating.click();
    }

    public void navigateToUserLinksSection() {
        navigateTo_DownloadedAddonsPage();
        menuOfSitemap.click();
        section_UserLinks.click();
    }

    public void clickAndTypeSearchFieldAtManagementPage(String value) {
        searchFieldAtManagementPage.click();
        searchFieldAtManagementPage.sendKeys(value);
        searchFieldAtManagementPage.sendKeys(Keys.ENTER);
    }

    public CustomersPage navigateToCustomersPage() {
        menuCustomers.hover();
        customersPage.click();
        return new CustomersPage();
    }

    public void installAddonAtAddonsManager(SelenideElement addonMenu, String addonCode, String installButton) {
        navigateTo_DownloadedAddonsPage();
        if (!$(addonMenu).exists()) {
            menuOfABAddonsManager.click();
            section_ListOfAvailableSets.click();
            addonsManagerField_Search.click();
            addonsManagerField_Search.sendKeys(addonCode);
            addonsManagerField_Search.sendKeys(Keys.ENTER);
            $(installButton).click();
            Alert alert = Selenide.webdriver().driver().switchTo().alert();
            alert.accept();
            Selenide.sleep(11000);
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

    public void navigateToGeneralSettingOf_ab_seo_for_tags() {
        navigateTo_DownloadedAddonsPage();
        menuOfAB__seo_for_tags.click();
        section_TagsGeneralSettings.click();
        $("#settings").click();
    }

    public AB_seo_filters navigateToGeneralSettingsOf_ab_seo_filters() {
        navigateTo_DownloadedAddonsPage();
        menuOfAB__seo_filters.click();
        section_SeoFiltersGeneralSettings.click();
        $("#settings").click();
        return new AB_seo_filters();
    }
}