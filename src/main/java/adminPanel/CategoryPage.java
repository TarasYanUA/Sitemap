package adminPanel;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CategoryPage {
    public CategoryPage() {
        super();
    }

    BasicPage basicPage = new BasicPage();

    ElementsCollection collapsedCategoryList = $$("span[id*='off_comp'][class='cm-combination hidden']");
    SelenideElement expandCategoryList = $x("//span[text()='Магазин: CS-Cart']/..//span[contains(@class, 'icon-caret-right')]");
    SelenideElement expand_categoryElectronics = $("#on_cat_166 .icon-caret-right");
    SelenideElement expand_categoryMP3Players = $("#on_cat_177 .icon-caret-right");
    SelenideElement expand_categorySports = $("#on_cat_203 .icon-caret-right");
    SelenideElement expand_categoryCamping = $("#on_cat_215 .icon-caret-right");
    public SelenideElement button_ArrowLeft = $(".cs-icon--type-arrow-left");
    public SelenideElement button_ViewProducts = $(".dropleft a[href*='products.manage']");
    public SelenideElement button_SaveListOfProducts = $(".nav__actions-btn-save");
    SelenideElement field_PriceForCategory_ProdOne = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[price]') + 1) = '[price]']])[1]");
    SelenideElement field_AmountForCategory_ProdOne = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[amount]') + 1) = '[amount]']])[1]");
    SelenideElement field_PriceForCategory_ProdTwo = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[price]') + 1) = '[price]']])[2]");
    SelenideElement field_AmountForCategory_ProdTwo = $x("(//input[starts-with(@name, 'products_data')][@name[substring(.,string-length(.) - string-length('[amount]') + 1) = '[amount]']])[2]");


    public void expandCategoryListIfCollapsed() {
        if (!collapsedCategoryList.isEmpty())
            expandCategoryList.click();
    }

    public void openCategoriesList_MP3Players(String categoryName) {
        expandCategoryListIfCollapsed();
        expand_categoryElectronics.click();
        expand_categoryMP3Players.click();
        $x(String.format("//a[contains(text(), '%s')]", categoryName)).click();
    }

    public void openCategoriesList_Camping(String categoryName) {
        expandCategoryListIfCollapsed();
        expand_categorySports.click();
        expand_categoryCamping.click();
        $x(String.format("//a[contains(text(), '%s')]", categoryName)).click();
    }

    public void goToStorefront_CategoryPage(int tab) {
        Selenide.sleep(3000);
        basicPage.gearwheelOnEditingPage.click();
        basicPage.button_Preview.click();
        switchTo().window(tab);
    }

    public void goAndSetFirstProductOfCategory(String price, String amount) {
        Selenide.sleep(2000);
        basicPage.gearwheelOnEditingPage.click();
        button_ViewProducts.click();
        if ($$(".products-list__image").size() > 2)
            deleteProductsFromCategory();
        field_PriceForCategory_ProdOne.setValue(price);
        field_AmountForCategory_ProdOne.setValue(amount);
    }

    public void goAndSetSecondProductOfCategory(String price, String amount) {
        field_PriceForCategory_ProdTwo.setValue(price);
        field_AmountForCategory_ProdTwo.setValue(amount);
    }

    public void setFirstProduct(String price, String amount) {
        if ($(".alert").exists())     //Выключаем сообщение о предупреждении, если оно появилось
            $(".close.cm-notification-close").click();
        field_PriceForCategory_ProdOne.setValue(price);
        field_AmountForCategory_ProdOne.setValue(amount);
    }

    public void setSecondProduct(String price, String amount) {
        field_PriceForCategory_ProdTwo.setValue(price);
        field_AmountForCategory_ProdTwo.setValue(amount);
    }

    public void deleteProductsFromCategory() {
        do {
            $(".mobile-hide .dropdown-icon--tools").hover().click();
            $("a[href*='products.delete']").click();
            UtilsAdm.switchToAndAcceptAlertWindow();
            sleep(3000);
        } while ($$(".products-list__image").size() > 2);
    }

    public void deleteAllProductsFromCategory() {
        do {
            $(".mobile-hide .dropdown-icon--tools").hover().click();
            $("a[href*='products.delete']").click();
            UtilsAdm.switchToAndAcceptAlertWindow();
        } while (!$$(".products-list__image").isEmpty());
    }
}