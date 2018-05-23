package ActionKeyword;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by Abuthahir on 9/9/2016.
 */
public class Actions
{
    public static AndroidDriver dr;
    public DesiredCapabilities caps = new DesiredCapabilities();
    File location = new File("src");
    static ITesseract instance;
//    public final java.lang.String datapath = "E:\\Android_APK\\Tess4J-3.2.1-src\\Tess4J\\tessdata";
    DateFormat dateFormat;
    Properties OR = ObjectRepository.OR.OR;
    public static int navigationPosition=0;

    @BeforeTest
    public void SetUp()
    {
        caps.setCapability(MobileCapabilityType.DEVICE_NAME,"Android device");
        try{
            serverConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Declaring APK file location and Appium driver setup*/
    private void serverConnection() throws MalformedURLException
    {
        File AppLocation = new File(location,"AngelBroking_UAT.apk");
        caps.setCapability(MobileCapabilityType.APP, AppLocation.getAbsolutePath());
        dr = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void btnclick_id(String element)
    {
        dr.findElement(By.id((element))).click();
    }

    public void btnclick_class(String element)
    {
        dr.findElementByClassName(element).click();
    }

    public String sendKeys(String element, String value)
    {
        dr.findElement(By.id(element)).sendKeys(value);
        return element;
    }

    public String msgRead (String element) {
        String value;
        value = dr.findElement(By.id(element)).getText();
        return value;
    }

    public void txtClear(String element)
    {
        dr.findElement(By.id(element)).clear();
    }

    public void screenshot(String path) throws IOException
    {
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        String destFile = dateFormat.format(new Date()) + ".png";
        File scrFile = ((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
        String fileName = UUID.randomUUID().toString();
        FileUtils.copyFile(scrFile, new File(path + "Screenshots" + destFile ));
    }

    public void swipingHorizontalRtoL() throws InterruptedException
    {
        Dimension size = dr.manage().window().getSize();
        int startx = (int) (size.width * 0.90);
        int endx = (int) (size.width * 0.10);
        int starty = size.height / 2;
        dr.swipe(startx, starty, endx, starty, 1000);
        sleep(2000);
    }

    public void swipingVerticalBtoT() throws InterruptedException
    {
        sleep(2000);
        Dimension size = dr.manage().window().getSize();
        int starty = (int) (size.height * 0.80);
        int endy = (int) (size.height * 0.20);
        int startx = size.width / 2;
        dr.swipe(startx, starty, startx, endy, 3000);
        sleep(2000);
    }

    public void Symbol_Add() throws InterruptedException {
        dr.findElement(By.id("com.msf.angelmobile:id/floating_search")).click();
        dr.findElement(By.id("com.msf.angelmobile:id/symbol_searchView")).click();
        dr.findElement(By.id("com.msf.angelmobile:id/symbol_searchView")).sendKeys("TCS");
        Thread.sleep(5000);
        dr.findElement(By.id("com.msf.angelmobile:id/symbol_name")).click();
        Thread.sleep(2000);
        dr.findElement(By.id("com.msf.angelmobile:id/symbol_name")).click();
        dr.findElement(By.id("com.msf.angelmobile:id/ok_button")).click();
    }

    public java.lang.String Toast() throws Exception {
        File abcd = new File(location, "\\Tess4J-3.2.1-src\\Tess4J\\tessdata");
        Thread.sleep(1250);
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        String destFile = dateFormat.format(new Date()) + ".png";
        File scrFile = dr.getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(scrFile, new File("./Toast/" + "Screenshots" + destFile));
        instance = new Tesseract();
        instance.setDatapath(new File(String.valueOf(abcd)).getPath());
//        String result = instance.doOCR(dr.getScreenshotAs(OutputType.FILE));
        String result = instance.doOCR(scrFile);
        return result;
    }
    @AfterTest
    public void stop()
    {
        System.out.println("Finished Testing");
        dr.quit();
    }
}
