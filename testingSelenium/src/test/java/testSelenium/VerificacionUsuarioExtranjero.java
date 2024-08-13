package testSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.HashMap;
import java.util.Map;
import java.time.Duration;

public class VerificacionUsuarioExtranjero {

    public static void main(String[] args) {
        // Configura WebDriverManager para usar un proxy específico // actualización ya
        // no vale el proxy de verga
        WebDriverManager.chromedriver().proxy("5.172.188.93:5678").setup();

        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        var devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();

        double latitude = 40.712776;
        double longitude = -74.005974;
        double accuracy = 1.0;

        Map<String, Object> params = new HashMap<>();
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("accuracy", accuracy);
        ((ChromeDriver) driver).executeCdpCommand("Emulation.setGeolocationOverride", params);

        System.out.println("\n=============================================");
        System.out.println("Ubicacion simulada activada:");
        System.out.println("Latitud: " + latitude + ", Longitud: " + longitude);
        System.out.println("Precisión: " + accuracy + " metros");
        System.out.println("=============================================\n");

        driver.get("https://tarjetacredito.dev.cuentafuturo.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement campoCid = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='cid']")));
        campoCid.clear();
        campoCid.sendKeys("0956257491");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement fingerPrint = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='fingerPrint']")));
        fingerPrint.sendKeys("B3456F9211");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement checkbox = driver.findElement(By.xpath("//input[@type='checkbox']"));

        WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));

        try {
            checkbox.click();
            Thread.sleep(1000);
            button.click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", checkbox);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            js.executeScript("arguments[0].click();", button);
        }

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}