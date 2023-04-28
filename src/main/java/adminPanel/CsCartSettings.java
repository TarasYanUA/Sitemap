package adminPanel;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CsCartSettings {
    public CsCartSettings(){super();}
    public SelenideElement button_Save = $(".btn.cm-submit");
    public SelenideElement menuProducts = $x("//li[contains(@class, 'dropdown nav__header-main-menu-item')]//a[@href='#products']");
    public SelenideElement sectionCategories = $("a[href$='categories.manage']");
    public SelenideElement sectionProducts = $("a[href$='products.manage']");
    public SelenideElement menuAddons = $("#elm_menu_addons");
    public SelenideElement sectionDownloadedAddons = $("#elm_menu_addons_downloaded_add_ons");
    public SelenideElement menuOfSitemap = $("tr#addon_ab__advanced_sitemap button.btn.dropdown-toggle");
    public SelenideElement sectionSitemapSettings = $("div.nowrap a[href*='addon=ab__advanced_sitemap']");
    public SelenideElement sectionSitemapGenerating = $("div.nowrap a[href*='ab__advanced_sitemap.manage']");
    private SelenideElement field_Search = $("#simple_search input");
    public SelenideElement chooseAnyProduct = $(".products-list__image");
    public SelenideElement gearwheelOnEditingPage = $(".actions__wrapper .dropdown-icon--tools");
    public SelenideElement button_Preview = $("a[href*='profiles.view_product_as_user']");

    //Страница категории
    public SelenideElement selectCategory_Ipods = $(".longtap-selection a[href*='category_id=178']");
    public SelenideElement selectCategory_Android = $(".longtap-selection a[href$='category_id=182']");
    public SelenideElement selectCategory_Tents = $(".longtap-selection a[href*='category_id=218']");
    public SelenideElement button_ViewProducts = $(".dropleft a[href*='products.manage']");
    private SelenideElement field_PriceForCategory_ProdOne = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[price]') + 1) = '[price]']])[1]");
    private SelenideElement field_AmountForCategory_ProdOne = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[amount]') + 1) = '[amount]']])[1]");
    private SelenideElement field_PriceForCategory_ProdTwo = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[price]') + 1) = '[price]']])[2]");
    private SelenideElement field_AmountForCategory_ProdTwo = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[amount]') + 1) = '[amount]']])[2]");

    //Страница товара
    public SelenideElement field_ProductPrice = $("#elm_price_price");
    public SelenideElement field_ProductAmount = $("#elm_in_stock");
    public SelenideElement button_dropdown = $(".btn-bar-left .dropdown-toggle");
    public SelenideElement chooseCategory_Tents = $("a[href*='products.manage.reset_view&cid=218']");

    public void shiftBrowserTab(int tabNumber){
        getWebDriver().getWindowHandle(); switchTo().window(tabNumber);
    }
    public void navigateToEditingCategoryPage(){
        menuProducts.hover();
        sectionCategories.click();
    }
    public void goAndSetEditingProductPage(String name, String price, String amount){
        menuProducts.hover();
        sectionProducts.click();
        field_Search.click();
        field_Search. clear();
        field_Search.sendKeys(name);
        field_Search.sendKeys(Keys.ENTER);
        chooseAnyProduct.click();
        field_ProductPrice.click();
        field_ProductPrice.clear();
        field_ProductPrice.sendKeys(price);
        field_ProductAmount.click();
        field_ProductAmount.clear();
        field_ProductAmount.sendKeys(amount);
        button_Save.click();
    }
    public void deleteProductOnProductsSection(String name){
            menuProducts.hover();
            sectionProducts.click();
            field_Search.click();
            field_Search. clear();
            field_Search.sendKeys(name);
            field_Search.sendKeys(Keys.ENTER);
            deleteAllProductsFromCategory();
    }
    public void goToStorefront_CategoryPage(int tab) {
        Selenide.sleep(2000);
        gearwheelOnEditingPage.click();
        button_Preview.click();
        switchTo().window(tab);
    }
    public void goAndSetFirstProductOfCategory(String price, String amount){
        Selenide.sleep(2000);
        gearwheelOnEditingPage.click();
        button_ViewProducts.click();
        if($$(".products-list__image").size() > 2){
            deleteProductsFromCategory();   }
        field_PriceForCategory_ProdOne.click();
        field_PriceForCategory_ProdOne.clear();
        field_PriceForCategory_ProdOne.sendKeys(price);
        field_AmountForCategory_ProdOne.click();
        field_AmountForCategory_ProdOne.clear();
        field_AmountForCategory_ProdOne.sendKeys(amount);
    }
    public void goAndSetSecondProductOfCategory(String price, String amount){
        Selenide.sleep(2000);
        gearwheelOnEditingPage.click();
        button_ViewProducts.click();
        if($$(".products-list__image").size() > 2){
            deleteProductsFromCategory();   }
        field_PriceForCategory_ProdTwo.click();
        field_PriceForCategory_ProdTwo.clear();
        field_PriceForCategory_ProdTwo.sendKeys(price);
        field_AmountForCategory_ProdTwo.click();
        field_AmountForCategory_ProdTwo.clear();
        field_AmountForCategory_ProdTwo.sendKeys(amount);
    }
    public void setFirstProduct(String price, String amount){
        field_PriceForCategory_ProdOne.click();
        field_PriceForCategory_ProdOne.clear();
        field_PriceForCategory_ProdOne.sendKeys(price);
        field_AmountForCategory_ProdOne.click();
        field_AmountForCategory_ProdOne.clear();
        field_AmountForCategory_ProdOne.sendKeys(amount);
    }
    public void setSecondProduct(String price, String amount){
        field_PriceForCategory_ProdTwo.click();
        field_PriceForCategory_ProdTwo.clear();
        field_PriceForCategory_ProdTwo.sendKeys(price);
        field_AmountForCategory_ProdTwo.click();
        field_AmountForCategory_ProdTwo.clear();
        field_AmountForCategory_ProdTwo.sendKeys(amount);
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
}