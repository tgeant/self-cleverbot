import java.io.IOException;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Main {



    public static void main(String[] args) throws IOException, InterruptedException {
        // write your code here


        DesiredCapabilities capability = DesiredCapabilities.chrome();

        capability.setAcceptInsecureCerts(true);
        System.setProperty("webdriver.chrome.driver","C:\\Users\\tgeant\\Downloads\\chromedriver_win32\\chromedriver.exe");

        ChromeOptions co = new ChromeOptions();
        WebDriver driver = new ChromeDriver(co);
        Dimension d = new Dimension(1920,1080);

        //Resize current window to the set dimension
        driver.manage().window().setSize(d);


        driver.navigate().to("https://www.cleverbot.com/");

        WebElement searchbar = driver.findElement(By.className("stimulus"));
        WebElement line1 = driver.findElement(By.id("line1")).findElement(By.className("bot"));

        //Thread.sleep(5000);

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.open('https://www.cleverbot.com/','_blank');");
       // driver.switchTo().activeElement().sendKeys(Keys.chord(Keys.CONTROL,"t"));
        driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());

        WebElement searchbar2 = driver.findElement(By.className("stimulus"));
        WebElement line12= driver.findElement(By.id("line1")).findElement(By.className("bot"));

        driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
        searchbar.sendKeys("Hello."+ Keys.ENTER);

        for(int i=0; i< 100; i++) {
            Thread.sleep(5000);
            String string_line1 = line1.getText();
            System.out.println("bot 1: "+string_line1);

            driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
            searchbar2.sendKeys(string_line1 + Keys.ENTER);

            Thread.sleep(5000);
            String string_line2 = line12.getText();
            System.out.println("bot 2: "+string_line2);

            driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
            searchbar.sendKeys(string_line2 +Keys.ENTER);

        }

       // driver.close();
    }
}
