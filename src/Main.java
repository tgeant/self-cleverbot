import java.io.IOException;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.Locale;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, EngineException, AudioException {

        // ---- Initialiaze the voice

            // set property as Kevin Dictionary
            System.setProperty("freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral
                    ("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            Synthesizer synthesizer =
                    Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Resume Synthesizer
            synthesizer.resume();

            // speaks the given text until queue is empty.
            synthesizer.speakPlainText("test", null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);



         // ---- Initialize selenium (to control the browser)

        //Initialize Chrome
        DesiredCapabilities capability = DesiredCapabilities.chrome();

        capability.setAcceptInsecureCerts(true);
        System.setProperty("webdriver.chrome.driver","chromedriver.exe");

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

        synthesizer.speakPlainText("Hello.", null);
        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);

        for(int i=0; i< 100; i++) {


            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("snipTextIcon")));

            String string_line1 = line1.getText();
            System.out.println("bot 1: "+string_line1);

            synthesizer.speakPlainText(string_line1, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);

            driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
            searchbar2.sendKeys(string_line1 + Keys.ENTER);


            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("snipTextIcon")));


            String string_line2 = line12.getText();
            System.out.println("bot 2: "+string_line2);

            synthesizer.speakPlainText(string_line2, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);

            driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
            searchbar.sendKeys(string_line2 +Keys.ENTER);

        }


        // Deallocate the Synthesizer.
        synthesizer.deallocate();
       // driver.close();
    }
}
