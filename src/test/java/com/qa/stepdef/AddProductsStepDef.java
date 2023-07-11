package com.qa.stepdef;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.xpath.XPath;
import java.time.Duration;

public class AddProductsStepDef {

    public AndroidDriver driver = SharedDriverContext.getDriver();
    public WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @AndroidFindBy(id = "com.cottontradersltd.cottontraders:id/onboarding_page_skip_button")
    public WebElement skipBtn;

    @AndroidFindBy(accessibility = "Log In ")
    public WebElement loginPrompt;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@content-desc=\"SHOP\"]/android.widget.ImageView")
    public WebElement shopNavBtn;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"Men collapsed\"]/android.widget.TextView")
    public WebElement menCollapsedDropdown;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"Clothing collapsed\"]/android.widget.TextView")
    public WebElement clothingCollapsedDropdown;

    @AndroidFindBy(accessibility = "Knitwear")
    public WebElement knitwearClothing;

    @AndroidFindBy(id = "com.cottontradersltd.cottontraders:id/product_list_view")
    public WebElement productGrid;

    @AndroidFindBy(xpath = "//android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.widget.Button[1]")
    public WebElement shirt;

    @AndroidFindBy(accessibility = "Cool Blue")
    public WebElement shirtColorBlue;

    @AndroidFindBy(id = "com.cottontradersltd.cottontraders:id/new_add_to_bag")
    public WebElement addToBag;

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[4]")
    public WebElement xlShirtSize;

    @AndroidFindBy(id = "com.cottontradersltd.cottontraders:id/snackbar_text")
    public WebElement snackBarPopUp;

    @AndroidFindBy(id = "com.cottontradersltd.cottontraders:id/action_bag")
    public WebElement cart;

    @AndroidFindBy(id = "com.cottontradersltd.cottontraders:id/quantity_textView")
    public WebElement totalQty;


    public AddProductsStepDef() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Given("I am on the on boarding page")
    public void i_am_on_the_on_boarding_page() {
        wait.until(ExpectedConditions.visibilityOf(skipBtn));
        skipBtn.click();
        wait.until(ExpectedConditions.visibilityOf(loginPrompt));
        Assert.assertTrue("Login Prompt is displayed as expected", loginPrompt.isDisplayed());
    }

    @Given("I navigate to knit wear shopping category")
    public void i_navigate_to_knit_wear_shopping_category() {
        wait.until(ExpectedConditions.visibilityOf(shopNavBtn));
        shopNavBtn.click();
        wait.until(ExpectedConditions.visibilityOf(menCollapsedDropdown));
        menCollapsedDropdown.click();
        wait.until(ExpectedConditions.visibilityOf(clothingCollapsedDropdown));
        clothingCollapsedDropdown.click();
        wait.until(ExpectedConditions.visibilityOf(knitwearClothing));
        knitwearClothing.click();
        wait.until(ExpectedConditions.visibilityOf(productGrid));
        Assert.assertTrue("Product list view is displayed", productGrid.isDisplayed());
    }

    @Given("I add 7th listed product")
    public void i_add_7th_listed_product() {
        wait.until(ExpectedConditions.visibilityOf(shirt));

        driver.executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) shirt).getId(),
                "direction", "up",
                "percent", 1
        ));

        shirt.click();
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(100000)"));
        wait.until(ExpectedConditions.visibilityOf(shirtColorBlue));
        Assert.assertTrue("Shirt Color is visible", shirtColorBlue.isDisplayed());
        Assert.assertTrue("Add to Bag Button is visible", addToBag.isDisplayed());
    }

    @Given("Select color and size")
    public void select_color_and_size() {
        shirtColorBlue.click();
        addToBag.click();
        wait.until(ExpectedConditions.visibilityOf(xlShirtSize));
        xlShirtSize.click();
        wait.until(ExpectedConditions.visibilityOf(snackBarPopUp));
        Assert.assertEquals("Snack Bar prompt is received", "Added to Bag", snackBarPopUp.getText());
    }
    @Then("Open the bag and confirm")
    public void open_the_bag_and_confirm() {
        wait.until(ExpectedConditions.visibilityOf(cart));
        cart.click();
        wait.until(ExpectedConditions.visibilityOf(totalQty));
        Assert.assertEquals("Expected item is added to cart", "Total (1 item):", totalQty.getText());
    }

}
