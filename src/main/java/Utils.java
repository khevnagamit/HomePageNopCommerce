import org.openqa.selenium.By;

public class Utils extends BasePage{
    // click on element
    public void clickOnElement(By by){
        driver.findElement(by).click();
    }

    //get text from given Locator
    public String findGetText(By by){
        String text =driver.findElement(by).getText();
        return text;
    }

    //enter text on input field
    public void enterText(By by,String text) {
        driver.findElement(by).sendKeys(text);
    }

}