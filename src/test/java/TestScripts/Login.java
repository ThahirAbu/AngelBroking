package TestScripts;
import ExcelUtils.ExcelRead;
//import ObjectRepository.OR;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.*;
import java.io.File;

import ActionKeyword.Actions;

import static java.lang.Thread.sleep;

/**
 * Created by Abuthahir on 9/9/2016.
 */
public class Login extends Actions
{
    private File location = new File("src");
    private File FileLocation = new File(location,"AngelBroking_TestData.xlsx");
    Properties OR1 = ObjectRepository.OR.OR;
    public List<HashMap<String, String>> datamap;

    public Login() throws IOException
    {
        OR1 = ObjectRepository.OR.ObjectRepo();
    }

    @Test(groups = {"Positive"},priority = 1)
    public void Login() throws IOException, InterruptedException
    {
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("Open"));
//        btnclick_id(OR1.getProperty("Open"));

        for(HashMap h:ExcelRead.data(FileLocation,"Login"))
        {
            System.out.println(h.keySet());
            System.out.println(h.values());
            sendKeys(ObjectRepository.OR.ObjectRepo().getProperty("UserID"),h.get("Username").toString());
//            sendKeys(OR1.getProperty("UserID"),h.get("Username").toString());
            sendKeys(ObjectRepository.OR.ObjectRepo().getProperty("Pwd"),h.get("Password").toString());
            dr.hideKeyboard();
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("Login"));
//            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("Aadhaarskip"));
        }
    }

    @Test(groups ={"Negative"},priority = 1 )
    public void Logins() throws InterruptedException, IOException {
        Integer abc = 1;
        dr.findElement(By.id("com.msf.angelmobile:id/market_open_acc")).click();
        for(HashMap h:ExcelRead.data(FileLocation,"Logins"))
        {
            System.out.println(h.keySet());
            System.out.println(h.values());
            sendKeys(ObjectRepository.OR.ObjectRepo().getProperty("UserID"),h.get("Username").toString());
            txtClear(ObjectRepository.OR.ObjectRepo().getProperty("Pwd"));
            sendKeys(ObjectRepository.OR.ObjectRepo().getProperty("Pwd"),h.get("Password").toString());
            dr.hideKeyboard();
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("Login"));
            Thread.sleep(2000);
//            takescreenshot("./Screenshots/Login/");
            String msg;
            if (abc == 1)
            {
                try {
                    msg = msgRead(ObjectRepository.OR.ObjectRepo().getProperty("Alertmsgs"));
                }
                catch (Exception e)
                {
                    msg=msgRead(ObjectRepository.OR.ObjectRepo().getProperty("Alertmsg"));
                }
                if (msg.startsWith("Your"))
                {
                    Assert.assertEquals(h.get("Message").toString(),msg);
                    btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
                    btnclick_class(ObjectRepository.OR.ObjectRepo().getProperty("Homemenu"));
                    abc=0;
                }
                else
                {
                    Assert.assertEquals(h.get("Message").toString(),msg);
                }
            }
            else
            {
                System.out.print("User in 2FA Screen");
            }
        }
//        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("Aadhaarskip"));
}

    @Test(groups = {"Positive"},priority = 2)
    public void Mobile() throws IOException, InterruptedException  {
        for(HashMap h:ExcelRead.data(FileLocation,"Mobile"))
        {
            System.out.println(h.keySet());
            System.out.println(h.values());
            txtClear(ObjectRepository.OR.ObjectRepo().getProperty("Pan-or-Mobile"));
            sendKeys(ObjectRepository.OR.ObjectRepo().getProperty("Pan-or-Mobile"),h.get("Mobile").toString());
            dr.hideKeyboard();
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("Login"));
            sleep(2000);
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("Aadhaarskip"));
        }
    }

    @Test(groups ={"Negative"},priority = 2 )
    public void Mobiles() throws IOException, InterruptedException {
        String msg;
        for(HashMap h:ExcelRead.data(FileLocation,"Mobiles"))
        {
            System.out.println(h.keySet());
            System.out.println(h.values());
            txtClear(ObjectRepository.OR.ObjectRepo().getProperty("Pan-or-Mobile"));
            sendKeys(ObjectRepository.OR.ObjectRepo().getProperty("Pan-or-Mobile"),h.get("Mobile").toString());
            dr.hideKeyboard();
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("Login"));

            Thread.sleep(2000);
//            takescreenshot("./Screenshots/Mobile/");
            try {
                msg = msgRead(ObjectRepository.OR.ObjectRepo().getProperty("Alertmsgs"));
                if (msg != "")
                {
                    Assert.assertEquals(h.get("Message").toString(),msg);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.print("User in Market Watchlist Screen");
            }
        }
    }


}
