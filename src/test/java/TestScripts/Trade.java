package TestScripts;

import ActionKeyword.Actions;
import ExcelUtils.ExcelRead;
import com.microsoft.schemas.office.office.STInsetMode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.mustache.Value;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abuth on 26-04-2018.
 */
public class Trade extends Actions {
    private File location = new File("src");
    private File FileLocation = new File(location,"AngelBroking_TestData.xlsx");
    String scrip,ltp;

    public String SymbolSearch(String abc) throws IOException, InterruptedException
    {
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("SearchField"));
        dr.findElement(By.id("com.msf.angelmobile:id/symbol_searchView")).sendKeys(abc);
        Thread.sleep(2000);
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("ScripSelect"));
        Thread.sleep(2000);
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("ScripSelect"));
        Thread.sleep(2000);
        return abc;
    }

    public String Valueconversion(String LtpValue)
    {
        LtpValue = LtpValue.substring(2).replace(",", "");
        System.out.println(LtpValue);
        return LtpValue;
    }

    @Test(groups = {"Positive"},priority = 1)
    void Trade() throws IOException, InterruptedException
    {
        Login xyz = new Login();
        xyz.Login();
        btnclick_class(ObjectRepository.OR.ObjectRepo().getProperty("Homemenu"));
        dr.findElementByXPath("//*[@text = 'Trade']").click();
        dr.findElementByXPath("//*[@text = 'Buy - Sell']").click();
    }

    @Test(groups = {"Positive"},priority = 2)
    void Search_and_PlaceOrder() throws IOException, InterruptedException
    {
        for (HashMap h : ExcelRead.data(FileLocation, "Trade")) {
            System.out.println(h.keySet());
            System.out.println(h.values());
            scrip = h.get("SymbolName").toString();
            SymbolSearch(scrip);
            dr.findElement(By.id("com.msf.angelmobile:id/lot_edit")).click();
            dr.findElement(By.id("com.msf.angelmobile:id/plus")).click();
            dr.hideKeyboard();
            ltp = dr.findElement(By.id("com.msf.angelmobile:id/place_order_ltp")).getText().toString();
            if (ltp == "-")
            {
                dr.findElement(By.id("com.msf.angelmobile:id/price")).sendKeys(h.get("TradedPrice").toString());
            }
            else
            {
                ltp = Valueconversion(ltp);
                dr.findElement(By.id("com.msf.angelmobile:id/price")).sendKeys(ltp);
                dr.hideKeyboard();
            }
            dr.findElement(By.id("com.msf.angelmobile:id/product_type_spin")).click();
            ltp = h.get("ProductType").toString();
            List<WebElement> PType = dr.findElementsByClassName("android.widget.CheckedTextView");
            PType.get(Integer.parseInt(ltp)).click();
            dr.findElement(By.id("com.msf.angelmobile:id/order_type_spin")).click();
            ltp = h.get("OrderType").toString();
            List<WebElement> OType = dr.findElementsByClassName("android.widget.CheckedTextView");
            OType.get(Integer.parseInt(ltp)).click();
        }
    }
}
