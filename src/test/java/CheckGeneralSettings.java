import adminPanel.AdminPage;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.Alert;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;

/*
Двум категориям "iPods" и "Android" настраиваем по 2 товара
*/
public class CheckGeneralSettings extends TestRunner{
    @Test
    public void setConfigurationsForCategories() {
        AdminPage adminPage = new AdminPage();
        //Настраиваем первую категорию "iPods"
        adminPage.navigateToEditingCategoryPage();
        adminPage.selectCategory_Ipods.click();
        adminPage.navigateToProductsOfCategory();
        adminPage.clickAndType_PriceForCategory_ProdOne("249.00");
        adminPage.clickAndType_AmountForCategory_ProdOne("10");
        adminPage.clickAndType_PriceForCategory_ProdTwo("255.00");
        adminPage.clickAndType_AmountForCategory_ProdTwo("15");
        adminPage.button_Save.click();
        //Настраиваем вторую категорию "Android"
        adminPage.navigateToEditingCategoryPage();
        adminPage.selectCategory_Android.click();
        adminPage.navigateToProductsOfCategory();
        if($$(".products-list__image").size() > 2){
            do{
                $(".mobile-hide .dropdown-icon--tools").hover().click();
                $("a[href*='products.delete']").click();
                Alert alert = Selenide.webdriver().driver().switchTo().alert();
                alert.accept();
                Selenide.sleep(1500);
                //adminPage.button_Save.click();
            } while ($$(".products-list__image").size() > 2);
        }
        adminPage.clickAndType_PriceForCategory_ProdOne("249.00");
        adminPage.clickAndType_AmountForCategory_ProdOne("10");
        adminPage.clickAndType_PriceForCategory_ProdTwo("255.00");
        adminPage.clickAndType_AmountForCategory_ProdTwo("15");
        adminPage.button_Save.click();
    }
}