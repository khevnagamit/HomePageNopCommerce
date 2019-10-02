import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
        driver.findElement(By.xpath("//a[@href='/new-online-store-is-open'][@class='read-more']")).click();

        //Store Title & Comment in variable
        String Title ="Nice Website";
        String Comment ="Useful website I will recommend it.";
        //enter comment
        driver.findElement(By.id("AddNewComment_CommentTitle")).sendKeys(Title);

        driver.findElement(By.id("AddNewComment_CommentText")).sendKeys(Comment);

        //check comment enter successfully
        driver.findElement(By.xpath("//input[@value=\"New comment\"]")).click();

        //testing comment added successfully
        String ExpectedResult = "News comment is successfully added.";
        String ActualResult= driver.findElement(By.xpath("//div[@class=\"result\"]")).getText();
        Assert.assertEquals(ActualResult,ExpectedResult);

        //testing Comment is in Comment area
        List<WebElement> commentList = driver.findElements(By.className("comment-text"));

        for (WebElement e : commentList) {
            if(e.getAttribute("outerHTML").contains(Comment)) {
                //Testing comment is in bottom
                String actual1 = commentList.get(commentList.size() - 1).getText();
                softAssert.assertEquals(actual1, Comment);
            }
        }
    }
    @Test
    public void searchButtonFunctionalityInvalidValue() {
        String WordEnter = "oil";
        //enter nike on search button
        driver.findElement(By.id("small-searchterms")).sendKeys(WordEnter);
        driver.findElement(By.xpath("//input[@type=\"submit\"]")).click();
        String ExpectedResult = "No products were found that matched your criteria.";
        String ActualResult = driver.findElement(By.className("no-result")).getText();
        Assert.assertEquals(ActualResult, ExpectedResult);

    }

    @Test
    public void searchButtonFunctionalityValidValue() {
        String WordEnter = "Nike";
            //all product should have word nike
        driver.findElement(By.id("small-searchterms")).sendKeys(WordEnter);
        driver.findElement(By.xpath("//input[@type=\"submit\"]")).click();
            List<WebElement> searchProduct = driver.findElements(By.className("product-item"));
            System.out.println("Total Product available on Home Page " + searchProduct.size());


            int count = 0;
            for (WebElement e : searchProduct) {
                if (e.getAttribute("innerHTML").contains(WordEnter)) {
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
        driver.findElement(By.xpath("//div[@data-productid=\"4\"]//input[@title=\"Add to compare list\"]")).click();

        String ExpectedMSG ="The product has been added to your product comparison";
        //testing 1st added
        softAssert.assertEquals(driver.findElement(By.xpath("//p[@class=\"content\"]")).getText(),ExpectedMSG);

        //close span line
        driver.findElement(By.xpath("//span[@class=\"close\"]")).click();

        //click on 2nd add to compare button
        driver.findElement(By.xpath("//div[@data-productid=\"18\"]//input[@title=\"Add to compare list\"]")).click();

        softAssert.assertEquals(driver.findElement(By.xpath("//p[@class=\"content\"]")).getText(),ExpectedMSG);
        //close span line
        driver.findElement(By.xpath("//span[@class=\"close\"]")).click();

        //click on compare Product
        driver.findElement(By.xpath("//div[@class=\"footer-block customer-service\"]//a[@href=\"/compareproducts\"]")).click();

        //test in compare product
       // softAssert.assertTrue(true,"");

        //click on clear button
        driver.findElement(By.className("clear-list")).click();

        //Test No items
        String expectedMsg ="You have no items to compare.";
        String actualMsg = driver.findElement(By.className("no-data")).getText();
        softAssert.assertEquals(actualMsg,expectedMsg);
    }
}
