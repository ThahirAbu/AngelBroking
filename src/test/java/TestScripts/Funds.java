package TestScripts;

import ActionKeyword.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import java.io.IOException;
import static java.lang.Thread.sleep;

public class Funds extends Actions
{

    @Test
    void withdraw() throws IOException, InterruptedException {
        Login xyz = new Login();
        xyz.Login();
        btnclick_class(ObjectRepository.OR.ObjectRepo().getProperty("Homemenu"));
        sleep(3000);
        dr.findElementByXPath("//*[@text = 'Funds']").click();
        dr.findElement(By.id("com.msf.angelmobile:id/bo_fs_withdrawal_text")).click();
        sleep(10000);
        dr.findElement(By.id("com.msf.angelmobile:id/avail_balance")).click();
        dr.findElement(By.id("com.msf.angelmobile:id/avail_balance")).sendKeys(Keys.BACK_SPACE);
        dr.findElement(By.id("com.msf.angelmobile:id/avail_balance")).sendKeys("100");
        dr.hideKeyboard();
        sleep(20000);
    }
}
