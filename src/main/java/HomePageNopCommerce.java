import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePageNopCommerce extends Utils {
    LoadProp loadProp = new LoadProp();
    SoftAssert softAssert = new SoftAssert();
    @BeforeMethod
    public void openBrowser()
    {
        System.setProperty("webdriver.chrome.driver","src\\Resourses\\BrowserDriver\\chromedriver.exe");

        //open browser
        driver = new ChromeDriver();

        //set implicitly wait for driver object
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //make browser Full Screen
        driver.manage().window().fullscreen();

        //open the website
        driver.get(loadProp.getProperty("url"));
    }
    @AfterMethod
    public void closing()
    {
        //close all windows open by web driver
        driver.quit();
    }

    @Test
    public void CommentInNews(){
        clickOnElement(By.xpath("//a[@href='/new-online-store-is-open'][@class='read-more']"));

        //enter comment
        enterText((By.id("AddNewComment_CommentTitle")),loadProp.getProperty("commentTitle"));

        enterText((By.id("AddNewComment_CommentText")),loadProp.getProperty("Comment"));

        //check comment enter successfully
        clickOnElement(By.xpath("//input[@value=\"New comment\"]"));

        //testing comment added successfully
        String ActualResult= findGetText(By.xpath("//div[@class=\"result\"]"));
        Assert.assertEquals(ActualResult,loadProp.getProperty("ExpectedComment"));

        //testing Comment is in Comment area
        List<WebElement> commentList = driver.findElements(By.className("comment-text"));

        for (WebElement e : commentList) {
            if(e.getAttribute("outerHTML").contains(loadProp.getProperty("Comment"))) {
                //Testing comment is in bottom
                String actual1 = commentList.get(commentList.size() - 1).getText();
                softAssert.assertEquals(actual1,loadProp.getProperty("Comment"));
            }
        }
    }
    @Test
    public void searchButtonFunctionalityInvalidValue() {

        //enter nike on search button
        enterText((By.id("small-searchterms")),loadProp.getProperty("invalidWordEnter"));

        clickOnElement(By.xpath("//input[@type=\"submit\"]"));

        String ActualResult = findGetText(By.className("no-result"));
        Assert.assertEquals(ActualResult, loadProp.getProperty("invalidExpected"));

    }

    @Test
    public void searchButtonFunctionalityValidValue() {
            //all product should have word nike
        driver.findElement(By.id("small-searchterms")).sendKeys(loadProp.getProperty("validWordEnter"));
        clickOnElement(By.xpath("//input[@type=\"submit\"]"));
            List<WebElement> searchProduct = driver.findElements(By.className("product-item"));
            System.out.println("Total Product available on Home Page " + searchProduct.size());


            int count = 0;
            for (WebElement e : searchProduct) {
                if (e.getAttribute("innerHTML").contains(loadProp.getProperty("validWordEnter"))) {
                    count++;
                    softAssert.assertTrue(true);
                }
            }
            System.out.println(count);
            softAssert.assertAll();
    }
    @Test
    public void CompareFunctionality(){

        //click on 1st add to compare button
        clickOnElement(By.xpath("//div[@data-productid=\"4\"]//input[@title=\"Add to compare list\"]"));

        //testing 1st added
        softAssert.assertEquals(findGetText(By.xpath("//p[@class=\"content\"]")),loadProp.getProperty("expectedProductAddedMessage"));

        //close span line
        clickOnElement(By.xpath("//span[@class=\"close\"]"));

        //click on 2nd add to compare button
        clickOnElement(By.xpath("//div[@data-productid=\"18\"]//input[@title=\"Add to compare list\"]"));

        softAssert.assertEquals(findGetText(By.xpath("//p[@class=\"content\"]")),loadProp.getProperty("expectedProductAddedMessage"));
        //close span line
        clickOnElement(By.xpath("//span[@class=\"close\"]"));

        //click on compare Product
        clickOnElement(By.xpath("//div[@class=\"footer-block customer-service\"]//a[@href=\"/compareproducts\"]"));

        //test in compare product
       List<WebElement> compareProduct = driver.findElements(By.xpath("//tr[@class='product-name']//a[@href]"));
        System.out.println("Product available to compare "+compareProduct.size());
        List<String> products = new ArrayList<>();
        for(WebElement e : compareProduct){
            products.add(e.getText());
            System.out.println(products);
        }
        String Expected[] = {"Apple MacBook Pro 13-inch","HTC One M8 Android L 5.0 Lollipop"};
        String Actual[] =products.toArray(new String[products.size()]);
        Assert.assertEquals(Actual,Expected);

        //click on clear button
        clickOnElement(By.className("clear-list"));

        //Test No items
        String expectedMsg ="You have no items to compare.";
        String actualMsg = findGetText(By.className("no-data"));
        softAssert.assertEquals(actualMsg,expectedMsg);

        softAssert.assertAll();
    }
}
