package adminPanel;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Alert;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CsCartSettings {
    public CsCartSettings(){super();}
    public SelenideElement menuProducts = $x("//li[contains(@class, 'dropdown nav__header-main-menu-item')]//a[@href='#products']");
    public SelenideElement sectionCategories = $("a[href$='categories.manage']");
    public SelenideElement menuAddons = $("#elm_menu_addons");
    public SelenideElement sectionDownloadedAddons = $("#elm_menu_addons_downloaded_add_ons");
    public SelenideElement menuOfSitemap = $("tr#addon_ab__advanced_sitemap button.btn.dropdown-toggle");
    public SelenideElement sectionSitemapSettings = $("div.nowrap a[href*='addon=ab__advanced_sitemap']");
    public SelenideElement sectionSitemapGenerating = $("div.nowrap a[href*='ab__advanced_sitemap.manage']");


    //Страница категории
    public SelenideElement button_Save = $(".btn.cm-submit");
    public SelenideElement selectCategory_Ipods = $(".longtap-selection a[href*='category_id=178']");
    public SelenideElement selectCategory_Android = $(".longtap-selection a[href$='category_id=182']");
    public SelenideElement gearwheelOnCategoryPage = $(".dropdown-icon--tools");
    public SelenideElement button_ViewProducts = $(".dropleft a[href*='products.manage']");
    public SelenideElement button_Preview = $("a[href*='profiles.view_product_as_user']");
    private SelenideElement field_PriceForCategory_ProdOne = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[price]') + 1) = '[price]']])[1]");
    private SelenideElement field_AmountForCategory_ProdOne = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[amount]') + 1) = '[amount]']])[1]");
    private SelenideElement field_PriceForCategory_ProdTwo = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[price]') + 1) = '[price]']])[2]");
    private SelenideElement field_AmountForCategory_ProdTwo = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[amount]') + 1) = '[amount]']])[2]");


    public void shiftBrowserTab(int tabNumber){
        getWebDriver().getWindowHandle(); switchTo().window(tabNumber);
    }
    public void navigateToEditingCategoryPage(){
        menuProducts.hover();
        sectionCategories.click();
    }
    public void goToStorefront_CategoryPage(int tab) {
        Selenide.sleep(2000);
        gearwheelOnCategoryPage.click();
        button_Preview.click();
        switchTo().window(tab);
    }
    public void navigateToProductsOfCategory(){
        Selenide.sleep(2000);
        gearwheelOnCategoryPage.click();
        button_ViewProducts.click();
    }
    public void deleteProductsFromCategory(){
        do{
            $(".mobile-hide .dropdown-icon--tools").hover().click();
            $("a[href*='products.delete']").click();
            Alert alert = Selenide.webdriver().driver().switchTo().alert();
            alert.accept();
            Selenide.sleep(1500);
        } while ($$(".products-list__image").size() > 2);
    }
    public void deleteAllProductsFromCategory(){
        do{
            $(".mobile-hide .dropdown-icon--tools").hover().click();
            $("a[href*='products.delete']").click();
            Alert alert = Selenide.webdriver().driver().switchTo().alert();
            alert.accept();
            Selenide.sleep(1500);
        } while ($$(".products-list__image").size() > 0);
    }
    public SitemapSettings navigateToSitemapSettings(){
        menuAddons.hover();
        sectionDownloadedAddons.click();
        menuOfSitemap.click();
        sectionSitemapSettings.click();
        return new SitemapSettings();
    }
    public void navigateToSitemapGenerating(){
        menuAddons.hover();
        sectionDownloadedAddons.click();
        menuOfSitemap.click();
        sectionSitemapGenerating.click();
    }
    public void clickAndType_PriceForCategory_ProdOne(String value){
        field_PriceForCategory_ProdOne.click();
        field_PriceForCategory_ProdOne.clear();
        field_PriceForCategory_ProdOne.sendKeys(value);
    }
    public void clickAndType_AmountForCategory_ProdOne(String value){
        field_AmountForCategory_ProdOne.click();
        field_AmountForCategory_ProdOne.clear();
        field_AmountForCategory_ProdOne.sendKeys(value);
    }
    public void clickAndType_PriceForCategory_ProdTwo(String value){
        field_PriceForCategory_ProdTwo.click();
        field_PriceForCategory_ProdTwo.clear();
        field_PriceForCategory_ProdTwo.sendKeys(value);
    }
    public void clickAndType_AmountForCategory_ProdTwo(String value){
        field_AmountForCategory_ProdTwo.click();
        field_AmountForCategory_ProdTwo.clear();
        field_AmountForCategory_ProdTwo.sendKeys(value);
    }
}