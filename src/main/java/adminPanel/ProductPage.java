package adminPanel;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProductPage {
    public ProductPage() {
        super();
    }

    BasicPage basicPage = new BasicPage();
    CategoryPage categoryPage = new CategoryPage();

    public SelenideElement field_ProductPrice = $("#elm_price_price");
    public SelenideElement field_ProductAmount = $("#elm_in_stock");
    public SelenideElement field_productSearch = $("input[form='search_filters_form']");
    public SelenideElement productVendor = $("#sw_product_data_company_id_selector_wrap_");
    public SelenideElement productBelongsToAllVendors = $("a[title='Все продавцы (общий товар)']");
    public SelenideElement button_ThumbUp = $(".cs-icon--type-thumbs-up");


    public void goAndSetEditingProductPage (String name, String price, String amount) {
        basicPage.navigateToSection_Products();
        field_productSearch.setValue(name);
        Selenide.sleep(3000);
        basicPage.chooseAnyProduct.click();
        field_ProductPrice.setValue(price);
        field_ProductAmount.setValue(amount);
        basicPage.button_Save.click();
    }

    public void navigateToEditingProductPage (String name) {
        basicPage.navigateToSection_Products();
        field_productSearch.setValue(name);
        Selenide.sleep(3000);
        if (basicPage.chooseAnyProduct.exists())
            basicPage.chooseAnyProduct.click();
    }

    public void deleteProductOnProductsSection (String name) {
        if ($(".alert.cm-notification-content").exists())     //Выключаем сообщение о сохранении, если оно появилось
            $(".close.cm-notification-close").click();
        basicPage.navigateToSection_Products();
        field_productSearch.setValue(name);
        Selenide.sleep(3000);
        if (!$x("//p[text()='Здесь пока ничего нет']").exists())
            categoryPage.deleteAllProductsFromCategory();
    }

    public void setThumbUp (String name){
        basicPage.navigateToSection_Products();
        field_productSearch.setValue(name);
        Selenide.sleep(3000);
        if (button_ThumbUp.exists()) {
            button_ThumbUp.click();
            Selenide.sleep(2000);
        }
    }
}