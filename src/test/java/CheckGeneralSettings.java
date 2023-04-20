import adminPanel.AdminPage;
import org.testng.annotations.Test;

public class CheckGeneralSettings extends TestRunner{
    @Test
    public void setConfigurationsForCategories() {
    AdminPage adminPage = new AdminPage();
    adminPage.navigateToEditingCategoryPage();
    adminPage.selectCategory_Ipods.click();
    adminPage.gearwheelOnCategoryPage.click();
    adminPage.button_ViewProducts.click();
    adminPage.clickAndType_PriceForCategoryIpod_ProdOne("249.00");
    adminPage.clickAndType_AmountForCategoryIpod_ProdOne("10");
    adminPage.clickAndType_PriceForCategoryIpod_ProdTwo("249.00");
    adminPage.clickAndType_AmountForCategoryIpod_ProdTwo("10");
    adminPage.button_Save.click();
    }
}