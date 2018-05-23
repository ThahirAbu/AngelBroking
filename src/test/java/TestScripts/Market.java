package TestScripts;

import ActionKeyword.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by abuth on 27-03-2018.
 */
public class Market extends Actions {

    String msg;
//    Properties OR1 = ObjectRepository.OR.OR;

    Watchlist wl = new Watchlist();

    void Quote()throws InterruptedException
    {
        dr.findElement(By.id("com.msf.angelmobile:id/symbolname")).click();
        dr.findElement(By.id("com.msf.angelmobile:id/watchlist_moreimg")).click();
        sleep(2000);
        try {
            dr.findElementByClassName("android.widget.ImageButton").click();
        }
        catch (Exception e)
        {
            msg = dr.findElement(By.id("com.msf.angelmobile:id/errorMessage")).getText();
            dr.findElement(By.id("com.msf.angelmobile:id/ok_button")).click();
        }
        sleep(2000);
        swipingHorizontalRtoL();
    }

    void Nifty_Sensex_Chart() throws InterruptedException, IOException {
        try
        {
            sleep(3000);
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("Chart"));
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("Backtolist"));
        }
        catch (Exception e)
        {
            msg = msgRead(ObjectRepository.OR.ObjectRepo().getProperty("NoChartmsg"));
            Assert.assertEquals("No Intrachart Available.", msg);
        }
//        Quote();
    }

    @Test(groups = {"Positive"},priority = 1)
    public void  Nifty50() throws InterruptedException, IOException
    {
        Nifty_Sensex_Chart();
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("Expand"));
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("More"));
        btnclick_class(ObjectRepository.OR.ObjectRepo().getProperty("Homemenu"));
        sleep(2000);
        swipingHorizontalRtoL();
    }

    @Test(groups = {"Positive"},priority = 2)
    public void Sensex30() throws InterruptedException, IOException {
        Nifty_Sensex_Chart();
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("Expand"));
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("More"));
        btnclick_class(ObjectRepository.OR.ObjectRepo().getProperty("Homemenu"));
        sleep(2000);
        swipingHorizontalRtoL();
    }

    @Test(groups = {"Positive"},priority = 3)
    public void Indian_Indices() throws Throwable
    {
        sleep(3000);
        btnclick_class(ObjectRepository.OR.ObjectRepo().getProperty("ExcSpinner"));
        List<WebElement> exchange = dr.findElementsByClassName("android.widget.CheckedTextView");
        exchange.get(1).click();
        sleep(2000);
        swipingVerticalBtoT();
        swipingHorizontalRtoL();
    }

    @Test(groups = {"Positive"},priority = 4)
    public void Global_Indices() throws Throwable
    {
        swipingVerticalBtoT();
        swipingHorizontalRtoL();
    }

    @Test(groups = {"Positive"},priority = 5)
    public void Gainers() throws Throwable
    {
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("SeriesSpinner"));
        List<WebElement> series = dr.findElementsByClassName("android.widget.CheckedTextView");
        series.get(4).click();
        btnclick_class(ObjectRepository.OR.ObjectRepo().getProperty("ExcSpinner"));
        List<WebElement> exchange = dr.findElementsByClassName("android.widget.CheckedTextView");
        exchange.get(0).click();
        sleep(2000);
        Quote();
    }

    @Test(groups = {"Positive"},priority = 6)
    public void Losers() throws Throwable
    {
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("SeriesSpinner"));
        List<WebElement> series = dr.findElementsByClassName("android.widget.CheckedTextView");
        series.get(4).click();
        sleep(2000);
        btnclick_class(ObjectRepository.OR.ObjectRepo().getProperty("ExcSpinner"));
        List<WebElement> exchange = dr.findElementsByClassName("android.widget.CheckedTextView");
        exchange.get(0).click();
        sleep(2000);
        Quote();
    }

    @Test(groups = {"Positive"},priority = 7)
    public void Value()throws Throwable
    {
        btnclick_class(ObjectRepository.OR.ObjectRepo().getProperty("ExcSpinner"));
        List<WebElement> exchange = dr.findElementsByClassName("android.widget.CheckedTextView");
        exchange.get(1).click();
        sleep(2000);
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("SeriesSpinner"));
        List<WebElement> series = dr.findElementsByClassName("android.widget.CheckedTextView");
        series.get(3).click();
        sleep(2000);
        Quote();
    }

    @Test(groups = {"Positive"},priority = 8)
    public void Volume()throws Throwable
    {
        btnclick_class(ObjectRepository.OR.ObjectRepo().getProperty("ExcSpinner"));
        List<WebElement> exchange = dr.findElementsByClassName("android.widget.CheckedTextView");
        exchange.get(1).click();
        sleep(2000);
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("SeriesSpinner"));
        List<WebElement> series = dr.findElementsByClassName("android.widget.CheckedTextView");
        series.get(3).click();
        sleep(2000);
        Quote();
    }

    @Test(groups = {"Positive"},priority = 9)
    public void News() throws Throwable
    {
        btnclick_class(ObjectRepository.OR.ObjectRepo().getProperty("ExcSpinner"));
        List<WebElement> category = dr.findElementsByClassName("android.widget.CheckedTextView");
        category.get(2).click();
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("News"));
        sleep(5000);
        dr.navigate().back();
//        dr.findElementByXPath("//android.widget.ImageButton[@index = '0']").click();
        sleep(2000);
        swipingHorizontalRtoL();
    }

    @Test(groups = {"Positive"},priority = 10)
    public void Portfolio() throws Throwable
    {
        sleep(5000);
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("PFtype"));
        List<WebElement> type = dr.findElementsByClassName("android.widget.CheckedTextView");
        type.get(1).click();
        /*btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("PFuser"));
        dr.findElementByXPath("//*[@text = 'SUBMIT']").click();*/
        sleep(2000);
        swipingHorizontalRtoL();
    }

    @Test (groups = {"Positive"},priority = 11)
    public void Watchlist () throws Throwable
    {
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("WlEdit"));
        wl.Checking_Group();

    }

}
