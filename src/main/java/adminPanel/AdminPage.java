package adminPanel;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AdminPage {
    public AdminPage(){super();}
    public SelenideElement menuProducts = $x("//li[@class='dropdown nav__header-main-menu-item ']//a[@href='#products']");
    public SelenideElement sectionCategories = $("a[href$='categories.manage']");
    public SelenideElement menuOfSitemap = $("tr#addon_ab__advanced_sitemap button.btn.dropdown-toggle");
    public SelenideElement sectionSitemapSettings = $("div.nowrap a[href*='addon=ab__advanced_sitemap']");

    //Страница категории
    public SelenideElement button_Save = $(".btn.cm-submit");
    public SelenideElement selectCategory_Ipods = $(".longtap-selection a[href*='category_id=178']");
    public SelenideElement selectCategory_Android = $(".longtap-selection a[href$='category_id=182']");
    public SelenideElement gearwheelOnCategoryPage = $(".dropdown-icon--tools");
    public SelenideElement button_ViewProducts = $(".dropleft a[href*='products.manage']");
    private SelenideElement field_PriceForCategoryIpod_ProdOne = $("input[name='products_data[242][price]']");
    private SelenideElement field_AmountForCategoryIpod_ProdOne = $("input[name='products_data[242][amount]']");
    private SelenideElement field_PriceForCategoryIpod_ProdTwo = $("input[name='products_data[243][price]']");
    private SelenideElement field_AmountForCategoryIpod_ProdTwo = $("input[name='products_data[243][amount]']");


    public void navigateToEditingCategoryPage(){
        menuProducts.hover();
        sectionCategories.click();
    }
    public SitemapSettings navigateToSitemapSettings(){
        menuOfSitemap.click();
        sectionSitemapSettings.click();
        return new SitemapSettings();
    }
    public void clickAndType_PriceForCategoryIpod_ProdOne(String value){
        field_PriceForCategoryIpod_ProdOne.click();
        field_PriceForCategoryIpod_ProdOne.clear();
        field_PriceForCategoryIpod_ProdOne.sendKeys(value);
    }
    public void clickAndType_AmountForCategoryIpod_ProdOne(String value){
        field_AmountForCategoryIpod_ProdOne.click();
        field_AmountForCategoryIpod_ProdOne.clear();
        field_AmountForCategoryIpod_ProdOne.sendKeys(value);
    }
    public void clickAndType_PriceForCategoryIpod_ProdTwo(String value){
        field_PriceForCategoryIpod_ProdTwo.click();
        field_PriceForCategoryIpod_ProdTwo.clear();
        field_PriceForCategoryIpod_ProdTwo.sendKeys(value);
    }
    public void clickAndType_AmountForCategoryIpod_ProdTwo(String value){
        field_AmountForCategoryIpod_ProdTwo.click();
        field_AmountForCategoryIpod_ProdTwo.clear();
        field_AmountForCategoryIpod_ProdTwo.sendKeys(value);
    }
}
