package TestScripts;

import ActionKeyword.Actions;
import ExcelUtils.ExcelRead;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static ObjectRepository.OR.ObjectRepo;
import static java.lang.Thread.sleep;

/**
 * Created by abuth on 12-04-2018.
 */
public class Watchlist extends Actions {
    private File location = new File("src");
    private File FileLocation = new File(location,"AngelBroking_TestData.xlsx");
    String msg;
    int x,y,z;
    @Test (groups = {"Positive","Negative"},priority = 1)
    void Checking_Group() throws IOException {
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("GroupSpinner"));
        dr.findElementByXPath("//*[@text = 'Manage Watchlist']").click();
        List<WebElement> grpname = dr.findElements(By.id("com.msf.angelmobile:id/watch_list_name"));
        List<Integer> toDeleteItem = new ArrayList<Integer>();

        for (int i = 0; i < grpname.size(); i++)
        {
//            System.out.println(grpname.size());
            String grp = grpname.get(i).getText();
            if (grp.contains("abcd")) {
                toDeleteItem.add(i);
            }

            else
            {
                if(i==4)
                {
                    List<WebElement> delete = dr.findElements(By.id("com.msf.angelmobile:id/grid_item_delete"));
                    delete.get(i-1).click();
                    btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
                    btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
                }
            }
        }
        Collections.reverse(toDeleteItem);
        DeleteWatchlistGroup(toDeleteItem);
        System.out.println(toDeleteItem);
    }

    void DeleteWatchlistGroup(List<Integer> position) throws IOException {
        List<WebElement> delete = dr.findElements(By.id("com.msf.angelmobile:id/grid_item_delete"));
        for (int i : position)
        {
            System.out.println(position);
            delete.get(i).click();
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
        }
    }

    @Test (groups = {"Positive"},priority = 2)
    void Creating_Group() throws IOException {

        for(HashMap h: ExcelRead.data(FileLocation,"CreateGroup"))
        {
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("GroupAdd"));
            System.out.println(h.keySet());
            System.out.println(h.values());
            sendKeys(ObjectRepository.OR.ObjectRepo().getProperty("GroupName"),h.get("Groupname").toString());
            dr.hideKeyboard();
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("GroupOk"));
            msg = msgRead(ObjectRepository.OR.ObjectRepo().getProperty("Alertmsg"));
            Assert.assertEquals(h.get("Message").toString(),msg);
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
        }
    }

    @Test(groups ={"Negative"} ,priority = 3)
    void Creating_Groups() throws Exception
    {
        btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("GroupAdd"));
        for(HashMap h: ExcelRead.data(FileLocation, "CreateGroups"))
        {
            System.out.println(h.keySet());
            System.out.println(h.values());
            sendKeys(ObjectRepository.OR.ObjectRepo().getProperty("GroupName"),h.get("Groupname").toString());
            dr.hideKeyboard();
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("GroupOk"));
            String Text = Toast();
            System.out.println(Text);
            msg = h.get("Message").toString();
//            Assert.assertFalse(!Text.contains(msg),msg);

            if (msg.contains("Group"))
            {
                btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
                btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("GroupAdd"));
            }
            else
            {
                System.out.println("Successfully Verified:"+Text.contains(msg));
            }
        }
        Thread.sleep(2000);
    }

    @Test(groups ={"Positive"} ,priority = 4)
    void Editing_Group() throws Exception
    {
        List<WebElement> editgroup = dr.findElements(By.id("com.msf.angelmobile:id/editTxt"));
        x = editgroup.size();
        editgroup.get(x - 1).click();
        for (HashMap h: ExcelRead.data(FileLocation, "EditGroup")) {
            System.out.println(h.keySet());
            System.out.println(h.values());
            sendKeys(ObjectRepository.OR.ObjectRepo().getProperty("GroupName"),h.get("Edit").toString());
            dr.hideKeyboard();
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("GroupSave"));
            String Text = Toast();
            System.out.println(Text);
            msg = h.get("Message").toString();
            System.out.println("Successfully renamed:"+Text.contains(msg));
//            Assert.assertFalse(!Text.contains(msg),msg);
        }
    }

    @Test (groups ={"Negative"}, priority = 5)
    void Editing_Groups() throws Exception
    {
        List<WebElement> editgroup = dr.findElements(By.id("com.msf.angelmobile:id/editTxt"));
        x = editgroup.size();
        editgroup.get(x - 1).click();
        for (HashMap h: ExcelRead.data(FileLocation, "EditGroups")) {
            System.out.println(h.keySet());
            System.out.println(h.values());
            sendKeys(ObjectRepository.OR.ObjectRepo().getProperty("GroupName"),h.get("Edit").toString());
            dr.hideKeyboard();
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("GroupSave"));
            String Text = Toast();
            System.out.println(Text);
            msg = h.get("Message").toString();
//            Assert.assertFalse(Text.contains(msg),msg);
            System.out.println("Successfully Verified:"+Text.contains(msg));
        }
    }

    @Test (groups ={"Positive","Negative"},priority = 6 )
    void Favoriting_Group() throws InterruptedException, IOException {
        List<WebElement> favoritegroup = dr.findElements(By.id("com.msf.angelmobile:id/fav_img"));
        z=1;
        for (HashMap h: ExcelRead.data(FileLocation, "FavoriteGroup"))
        {
            System.out.println(h.keySet());
            System.out.println(h.values());
            if (z==1)
            {
                favoritegroup.get(0).click();
                msg = msgRead(ObjectRepository.OR.ObjectRepo().getProperty("Alertmsg"));
                sleep(2000);
//                Assert.assertEquals(h.get("Message1").toString(),msg);
                System.out.println("Successfully Verified:"+h.get("Message1").toString().contains(msg));
                btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
                z+=1;
            }
            else
            {
                favoritegroup.get(1).click();
                msg = msgRead(ObjectRepository.OR.ObjectRepo().getProperty("Alertmsg"));
                sleep(2000);
//                Assert.assertEquals(h.get("Message1").toString(),msg);
                System.out.println("Successfully Verified:"+h.get("Message1").toString().contains(msg));
                btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
                msg = msgRead(ObjectRepository.OR.ObjectRepo().getProperty("Alertmsg"));
//                Assert.assertEquals(h.get("Message2").toString(), msg);
                System.out.println("Successfully Verified:"+h.get("Message2").toString().contains(msg));
                btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
            }
        }
    }

    @Test (groups ={"Positive","Negative"},priority = 7  )
    void Delete_Group()throws Exception
    {
        y=1;
        List<WebElement> delete = dr.findElements(By.id("com.msf.angelmobile:id/grid_item_delete"));
        x=delete.size();
        for (HashMap h: ExcelRead.data(FileLocation, "DeleteGroup"))
        {
            System.out.println(h.keySet());
            System.out.println(h.values());
            if (y==1)
            {
                delete.get(0).click();
                sleep(2000);
                msg = msgRead(ObjectRepository.OR.ObjectRepo().getProperty("Alertmsg"));
//                Assert.assertEquals(h.get("Message1").toString(),msg);
                System.out.println("Successfully Verified:"+h.get("Message1").toString().contains(msg));
                btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
                msg = msgRead(ObjectRepository.OR.ObjectRepo().getProperty("Alertmsg"));
//                Assert.assertEquals(h.get("Message2").toString(), msg);
                System.out.println("Successfully Verified:"+h.get("Message2").toString().contains(msg));
                btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
                y+=1;
            }
            else
            {
                delete.get(x-1).click();
                sleep(2000);
                msg = msgRead(ObjectRepository.OR.ObjectRepo().getProperty("Alertmsg"));
//                Assert.assertEquals(h.get("Message1").toString(),msg);
                System.out.println("Successfully Verified:"+h.get("Message1").toString().contains(msg));
                btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
                msg = msgRead(ObjectRepository.OR.ObjectRepo().getProperty("Alertmsg"));
//                Assert.assertEquals(h.get("Message2").toString(), msg);
                System.out.println("Successfully Verified:"+h.get("Message2").toString().contains(msg));
                btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
            }
        }
    }

    @Test (groups ={"Positive"},priority = 8 )
    void Add_Symbol() throws InterruptedException, IOException {
        btnclick_class(ObjectRepository.OR.ObjectRepo().getProperty("Homemenu"));

        for (HashMap h : ExcelRead.data(FileLocation, "AddSymbol")) {
            System.out.println(h.keySet());
            System.out.println(h.values());
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("FloatSearch"));
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("SearchField"));
            sendKeys(ObjectRepository.OR.ObjectRepo().getProperty("SearchField"),h.get("Symbol").toString());
            Thread.sleep(2000);
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("ScripSelect"));
            Thread.sleep(2000);
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("ScripSelect"));
            Thread.sleep(2000);
            msg = msgRead(ObjectRepository.OR.ObjectRepo().getProperty("Alertmsg"));
            Assert.assertEquals(h.get("Message2").toString(), msg);
            btnclick_id(ObjectRepository.OR.ObjectRepo().getProperty("AlertOk"));
        }
    }
}
