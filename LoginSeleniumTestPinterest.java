import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginSeleniumTestPinterest {

    private WebDriver driver;
    private static final String FILE_INPUT = "src/InputAndOutput/input.txt";
    private static final String FILE_OUTPUT = "src/InputAndOutput/output.txt";
    private BufferedReader reader;
    private BufferedWriter writer;

    @BeforeTest
    public void setUp() throws InterruptedException, IOException {
        // Tạo đối tượng ChromeOptions để cấu hình trình duyệt Chrome
        ChromeOptions options = new ChromeOptions();
        // Cấu hình ngôn ngữ cho trình duyệt Chrome
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("intl.accept_languages", "vi"); // "vi" là mã ngôn ngữ tiếng Việt
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        // Mở trang web
        driver.get("https://www.pinterest.com/");
        // Thực hiện click vào button đăng nhập
        Thread.sleep(5000);
        driver.findElement(By.xpath("/html[@class='vi fp-enabled']/body[@class='fp-viewing-top']/div[@id='__PWS_ROOT__']/div/div[1]/div[@class='zI7 iyn Hsu']/div[@id='mweb-unauth-container']/div[@class='zI7 iyn Hsu ']/div[@class='zI7 iyn Hsu']/div[@class='QLY _he zI7 iyn Hsu']/div[@class='Jea KS5 b8T fZz hs0 qDf urM zI7 iyn Hsu']/div[@class='Eqh KS5 hs0 un8 C9i TB_']/div[@class='wc1 zI7 iyn Hsu']/button[@class='RCK Hsu USg adn CCY NTm KhY iyn oRi lnZ wsz YbY']/div[@class='RCK Hsu USg adn CCY NTm KhY iyn S9z Vxj aZc Zr3 hA- Il7 Jrn hNT BG7 hDj _O1 KS5 mQ8 Tbt L4E']"))
                .click();
        // Mở file output để ghi kết quả
        writer = new BufferedWriter(new FileWriter(FILE_OUTPUT));
    }

    @Test(priority = 1)
    public void loginFailWithEmptyEmail() throws InterruptedException {
        System.out.println("Test case loginFailWithEmptyEmail is running.");
        // Thực hiện hành động đăng nhập
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.red.SignupButton.active")).click();

        // Kiểm phần tử thông báo lỗi email có xuất hiện không
        Thread.sleep(1000);
        boolean isAlertEmailError = isElementPresent(driver, By.cssSelector("div.tBJ.dyH.iFc.dR0.c8S.zDA.IZT.swG"));
        String emailErrorMessage = driver.findElement(By.id("email-error")).getText();

        // Xác nhận
        Assert.assertTrue(isAlertEmailError);
        Assert.assertEquals(emailErrorMessage, "Bạn đã bỏ lỡ điều gì đó! Đừng quên thêm địa chỉ email của bạn.");
    }

    @Test(priority = 2)
    public void loginFailWithIncorrectUser() throws InterruptedException, IOException {
        System.out.println("Test case loginFailWithIncorrectUser is running.");
        String caseInput = "// login fail with incorrect user";
        // Đọc dữ liệu từ file input
        readerInput(caseInput);

        // Kiểm phần tử thông báo lỗi đăng nhập sai mật có xuất hiện không
        Thread.sleep(1000);
        boolean isAlertPasswordError = isElementPresent(driver, By.cssSelector("div.tBJ.dyH.iFc.dR0.c8S.zDA.IZT.swG"));
        String passwordErrorMessage = driver.findElement(By.id("password-error")).getText();

        // Xác nhận
        Thread.sleep(1000);
        Assert.assertTrue(isAlertPasswordError);
        Assert.assertEquals(passwordErrorMessage, "Mật khẩu bạn nhập không chính xác.");
    }

    @Test(priority = 3)
    public void loginFailWithIncorrectEmailFormat() throws IOException, InterruptedException {
        System.out.println("Test case loginFailWithIncorrectEmailFormat is running.");
        String caseInput = "// login fail with incorrect email format";
        // Đọc dữ liệu từ file input
        readerInput(caseInput);

        // Kiểm phần tử thông báo lỗi định dạng email có xuất hiện không
        Thread.sleep(2000);
        boolean isAlertEmailError = isElementPresent(driver, By.cssSelector("div.tBJ.dyH.iFc.dR0.c8S.zDA.IZT.swG"));
        String emailErrorMessage = driver.findElement(By.id("email-error")).getText();

        // Xác nhận
        Assert.assertTrue(isAlertEmailError);
        Assert.assertEquals(emailErrorMessage, "Hmm, đây trông không giống một địa chỉ email.");
    }

    @Test(priority = 4)
    public void loginFailWithIncorrectEmailFormatTestCaseFail() throws IOException, InterruptedException {
        System.out.println("Test case loginFailWithIncorrectEmailFormatTestCaseFail is running.");
        String caseInput = "// login fail with incorrect email format";
        // Đọc dữ liệu từ file input
        readerInput(caseInput);

        // Kiểm phần tử thông báo lỗi định dạng email có xuất hiện không
        Thread.sleep(2000);
        boolean isAlertEmailError = isElementPresent(driver, By.cssSelector("div.tBJ.dyH.iFc.dR0.c8S.zDA.IZT.swG"));
        String emailErrorMessage = driver.findElement(By.id("email-error")).getText();

        // Xác nhận
        Assert.assertTrue(isAlertEmailError);
        Assert.assertEquals(emailErrorMessage, "Đây trông không giống một địa chỉ email.");
    }

    @Test(priority = 5)
    public void loginFailWithEmailDontExist() throws IOException, InterruptedException {
        System.out.println("Test case loginFailWithEmailDontExist is running.");
        String caseInput = "// email don't exist";
        // Đọc dữ liệu từ file input
        readerInput(caseInput);

        // Kiểm phần tử thông báo lỗi email không tồn tại có xuất hiện không
        Thread.sleep(2000);
        boolean isAlertEmailError = isElementPresent(driver, By.cssSelector("div.tBJ.dyH.iFc.dR0.c8S.zDA.IZT.swG"));
        String emailErrorMessage = driver.findElement(By.id("email-error")).getText();

        // Xác nhận
        Assert.assertTrue(isAlertEmailError);
        Assert.assertEquals(emailErrorMessage, "Email bạn nhập vào không thuộc về bất kỳ tài khoản nào.");
    }

    @Test(priority = 6)
    public void loginSuccess() throws InterruptedException, IOException {
        System.out.println("Test case loginSuccess is running.");
        String caseInput = "// login success";
        // Đọc dữ liệu từ file input
        readerInput(caseInput);

        // Kiểm tra phần tử profile (header-profile) có xuất hiện không
        Thread.sleep(5000);
        boolean isAlertEmailError = isElementPresent(driver, By.cssSelector("[data-test-id='header-profile']"));

        // Xác nhận
        Assert.assertTrue(isAlertEmailError);
    }

    @Test(priority = 7)
    public void logout() throws InterruptedException, IOException {
        Thread.sleep(5000);
        System.out.println("Test case logout is running.");
        boolean checkLogin = isElementPresent(driver, By.cssSelector("[data-test-id='header-profile']"));
        if (checkLogin) { // Nếu đăng nhập rồi thì thực hiện đăng xuất
            // Thực hiện đăng xuất
            Thread.sleep(1000);
            driver.findElement(By.cssSelector("button[aria-label='Tài khoản và các tùy chọn khác']")).click();
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("[data-test-id='header-menu-options-logout']")).click();

            // Kiểm tra phần tử button đăng nhập có xuất hiện không
            Thread.sleep(5000);
            boolean isAlertEmailError = isElementPresent(driver, By.xpath("/html[@class='vi fp-enabled']/body[@class='fp-viewing-top']/div[@id='__PWS_ROOT__']/div/div[1]/div[@class='zI7 iyn Hsu']/div[@id='mweb-unauth-container']/div[@class='zI7 iyn Hsu ']/div[@class='zI7 iyn Hsu']/div[@class='QLY _he zI7 iyn Hsu']/div[@class='Jea KS5 b8T fZz hs0 qDf urM zI7 iyn Hsu']/div[@class='Eqh KS5 hs0 un8 C9i TB_']/div[@class='wc1 zI7 iyn Hsu']/button[@class='RCK Hsu USg adn CCY NTm KhY iyn oRi lnZ wsz YbY']/div[@class='RCK Hsu USg adn CCY NTm KhY iyn S9z Vxj aZc Zr3 hA- Il7 Jrn hNT BG7 hDj _O1 KS5 mQ8 Tbt L4E']"));

            // Xác nhận
            Assert.assertTrue(isAlertEmailError);
        } else { // Nếu chưa đăng nhập thì thực hiện đăng nhập rồi mới đăng xuất
            String caseInput = "// login success";
            // Đọc dữ liệu từ file input
            readerInput(caseInput);
            // Thực hiện đăng xuất
            Thread.sleep(1000);
            driver.findElement(By.cssSelector("button[aria-label='Tài khoản và các tùy chọn khác']")).click();
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("[data-test-id='header-menu-options-logout']")).click();

            // Kiểm tra phần tử button đăng nhập có xuất hiện không
            Thread.sleep(5000);
            boolean isAlertEmailError = isElementPresent(driver, By.xpath("/html[@class='vi fp-enabled']/body[@class='fp-viewing-top']/div[@id='__PWS_ROOT__']/div/div[1]/div[@class='zI7 iyn Hsu']/div[@id='mweb-unauth-container']/div[@class='zI7 iyn Hsu ']/div[@class='zI7 iyn Hsu']/div[@class='QLY _he zI7 iyn Hsu']/div[@class='Jea KS5 b8T fZz hs0 qDf urM zI7 iyn Hsu']/div[@class='Eqh KS5 hs0 un8 C9i TB_']/div[@class='wc1 zI7 iyn Hsu']/button[@class='RCK Hsu USg adn CCY NTm KhY iyn oRi lnZ wsz YbY']/div[@class='RCK Hsu USg adn CCY NTm KhY iyn S9z Vxj aZc Zr3 hA- Il7 Jrn hNT BG7 hDj _O1 KS5 mQ8 Tbt L4E']"));

            // Xác nhận
            Assert.assertTrue(isAlertEmailError);
        }
    }

    @AfterMethod
    public void afterTestMethod(ITestResult result) throws IOException {
        String nameMethod = result.getMethod().getMethodName();
        if (result.getStatus() == ITestResult.SUCCESS && nameMethod != null) {
            writeResult("Test passed: " + nameMethod);
            System.out.println("Test case " + nameMethod + " ends - Test passed!");
            System.out.println("=========================================================================");
        } else if (result.getStatus() == ITestResult.FAILURE && nameMethod != null) {
            writeResult("Test failed: " + nameMethod);
            System.out.println("Test case " + nameMethod + " ends - Test failed!");
            System.out.println("=========================================================================");
        } else if (result.getStatus() == ITestResult.SKIP && nameMethod != null) {
            writeResult("Test skipped: " + nameMethod);
            System.out.println("Test case " + nameMethod + " ends - Test skipped!");
            System.out.println("=========================================================================");
        }
    }

    private void writeResult(String result) throws IOException {
        // Ghi kết quả vào file
        writer.write(result);
        writer.newLine();
    }

    @AfterSuite
    public void writeDown() throws IOException {
        // Đóng file sau khi hoàn thành tất cả các test
        writer.close();
    }

    public void readerInput(String caseInput) throws IOException, InterruptedException {
        // Đọc dữ liệu từ file input
        reader = new BufferedReader(new FileReader(FILE_INPUT));
        String line;
        Thread.sleep(1000);
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("// login fail with incorrect user") && line.equals(caseInput)) { // login fail with incorrect user
                String nextLine = reader.readLine();
                String[] userInfo = nextLine.split(" ");
                Thread.sleep(1000);
                driver.findElement(By.id("email")).sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
                Thread.sleep(1000);
                driver.findElement(By.id("email")).sendKeys(userInfo[0]);
                Thread.sleep(1000);
                driver.findElement(By.id("password")).sendKeys(userInfo[1]);
                Thread.sleep(1000);
                driver.findElement(By.cssSelector("button.red.SignupButton.active")).click();
                break;
            } else if (line.startsWith("// login fail with incorrect email format") && line.equals(caseInput)) { // login fail with incorrect email format
                String nextLine = reader.readLine();
                String[] userInfo = nextLine.split(" ");
                Thread.sleep(1000);
                driver.findElement(By.id("email")).sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
                Thread.sleep(1000);
                driver.findElement(By.id("email")).sendKeys(userInfo[0]);
                Thread.sleep(1000);
                driver.findElement(By.cssSelector("button.red.SignupButton.active")).click();
                break;
            } else if (line.startsWith("// email don't exist") && line.equals(caseInput)) { // email don't exist
                String nextLine = reader.readLine();
                String[] userInfo = nextLine.split(" ");
                Thread.sleep(1000);
                driver.findElement(By.id("email")).sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
                Thread.sleep(1000);
                driver.findElement(By.id("email")).sendKeys(userInfo[0]);
                Thread.sleep(1000);
                driver.findElement(By.id("password")).sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
                Thread.sleep(1000);
                driver.findElement(By.cssSelector("button.red.SignupButton.active")).click();
                break;
            } else if (line.startsWith("// login success") && line.equals(caseInput)) { // login success
                String nextLine = reader.readLine();
                String[] userInfo = nextLine.split(" ");
                Thread.sleep(1000);
                driver.findElement(By.id("email")).sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
                Thread.sleep(1000);
                driver.findElement(By.id("email")).sendKeys(userInfo[0]);
                Thread.sleep(1000);
                driver.findElement(By.id("password")).sendKeys(userInfo[1]);
                Thread.sleep(1000);
                driver.findElement(By.cssSelector("button.red.SignupButton.active")).click();
                break;
            }
        }
    }

    private static boolean isElementPresent(WebDriver driver, By by) {
        // Kiểm tra sự xuất hiện của phần tử
        return driver.findElements(by).size() > 0;
    }

    @AfterTest
    public void tearDown() {
        // Đóng trình duyệt sau khi kiểm thử hoàn tất
        driver.quit();
    }
}
