package adminPanel;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CustomersPage {
    public CustomersPage(){super();}

    public SelenideElement gearwheelOfSimtechVendor = $("tr[data-ca-id=\"10\"] a[class='btn dropdown-toggle']");
    public SelenideElement actAsUser = $("a[href*='act_as_user&user_id=10']");
    private SelenideElement vendorProductMenu = $("a[href='#products']");
    private SelenideElement vendorProductPage = $("a[href*='dispatch=products.manage'][class*='nav__menu-subitem']");
    private SelenideElement sidebar_ProductsThatCanBeSold = $x("//a[text()='Товары, которые можно продавать']");
    private SelenideElement productButtonSell = $("a[href*='product_id'][class*='btn']");
    public SelenideElement iconThumbUp = $(".cs-icon.icon-thumbs-up");


    public void sellProductAsVendor(){
        vendorProductMenu.click();
        vendorProductPage.click();
        sidebar_ProductsThatCanBeSold.click();
        if(productButtonSell.exists()){productButtonSell.click();   }
    }
}