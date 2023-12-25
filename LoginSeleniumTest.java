import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.*;

public class LoginSeleniumTest {

    private WebDriver driver;
    private static final String FILE_INPUT = "src/InputAndOutput/input.txt";
    private static final String FILE_OUTPUT = "src/InputAndOutput/output.txt";
    private BufferedReader reader;
    private BufferedWriter writer;

    @BeforeTest
    public void setUp() throws InterruptedException, IOException {
        driver = new ChromeDriver();
        // Mở trang web
        driver.get("https://hoclaptrinh.vn/");
        // Chuyển đến trang đăng nhập
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[@id='app']/header/div[@class='container']/div[@class='pull-right auth']/a[@class='login hidden-xs hidden-sm']"))
                .click();
        // Mở file để ghi kết quả
        writer = new BufferedWriter(new FileWriter(FILE_OUTPUT));
    }

    @Test(priority = 1)
    public void loginFailWithBlankUser() throws InterruptedException, IOException {
        System.out.println("Test case loginFailWithBlankUser is running.");
        // Thực hiện hành động đăng nhập
        Thread.sleep(1000);
        driver.findElement(By.name("login")).click();

        // Kiểm tra xem phần tử login is required có xuất hiện không
        Thread.sleep(1000);
        boolean isRequiredPresent = isElementPresent(driver, By.className("help-block"));
        String errorMessageEmail = driver.findElement(By.xpath("/html/body[@class='modal-open']/div[@id='app']/div[@id='modalauth']/div[@class='modal-dialog']/div[@class='modal-content']/div[@class='modal-body container-fluid']/div[@class='block-input-left show-mdlogin login-modal']/form[@id='loginForm']/div[@class='form-group'][1]/div[@class='col-md-9']/span[@class='help-block']")).getText();
        String errorMessagePassword = driver.findElement(By.xpath("/html/body[@class='modal-open']/div[@id='app']/div[@id='modalauth']/div[@class='modal-dialog']/div[@class='modal-content']/div[@class='modal-body container-fluid']/div[@class='block-input-left show-mdlogin login-modal']/form[@id='loginForm']/div[@class='form-group'][2]/div[@class='col-md-9']/span[@class='help-block']")).getText();

        // Xác nhận
        Assert.assertTrue(isRequiredPresent);
        Assert.assertEquals(errorMessageEmail, "The email field is required.");
        Assert.assertEquals(errorMessagePassword, "The password field is required.");
    }

    @Test(priority = 2)
    public void loginFailWithIncorrectEmailFormat() throws IOException, InterruptedException {
        System.out.println("Test case loginFailWithIncorrectEmailFormat is running.");
        Thread.sleep(1000);
        driver.findElement(By.name("email")).clear();
        Thread.sleep(1000);
        // Đọc dữ liệu từ file input
        reader = new BufferedReader(new FileReader(FILE_INPUT));
        String line;
        Thread.sleep(1000);
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("// login fail with incorrect email format")) {
                String nextLine = reader.readLine();
                String[] userInfo = nextLine.split(" ");
                driver.findElement(By.name("email")).sendKeys(userInfo[0]);
                Thread.sleep(1000);
                driver.findElement(By.name("login")).click();
            }
        }

        // Kiểm tra xem phần tử login error có xuất hiện không
        Thread.sleep(1000);
        String errorMessage = driver.findElement(By.name("email")).getAttribute("validationMessage");

        // Xác nhận
        String email = driver.findElement(By.name("email")).getAttribute("value");
        Assert.assertEquals(errorMessage, "Please include an '@' in the email address. '" + email + "' is missing an '@'.");
    }

    @Test(priority = 3)
    public void loginFailWithIncorrectEmailFormatTestCaseFail() throws IOException, InterruptedException {
        System.out.println("Test case loginFailWithIncorrectEmailFormatTestCaseFail is running.");
        Thread.sleep(1000);
        driver.findElement(By.name("email")).clear();
        Thread.sleep(1000);
        // Đọc dữ liệu từ file input
        reader = new BufferedReader(new FileReader(FILE_INPUT));
        String line;
        Thread.sleep(1000);
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("// login fail with incorrect email format")) {
                String nextLine = reader.readLine();
                String[] userInfo = nextLine.split(" ");
                driver.findElement(By.name("email")).sendKeys(userInfo[0]);
                Thread.sleep(1000);
                driver.findElement(By.name("login")).click();
            }
        }

        // Kiểm tra xem phần tử login error có xuất hiện không
        Thread.sleep(1000);
        String errorMessage = driver.findElement(By.name("email")).getAttribute("validationMessage");

        // Xác nhận
        Assert.assertEquals(errorMessage, "Please include an '@' in the email address.");
    }

    @Test(priority = 4)
    public void loginFailWithIncorrectUser() throws IOException, InterruptedException {
        System.out.println("Test case loginFailWithIncorrectUser is running.");
        Thread.sleep(1000);
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("password")).clear();
        Thread.sleep(1000);
        // Đọc dữ liệu từ file input
        reader = new BufferedReader(new FileReader(FILE_INPUT));
        String line;
        Thread.sleep(1000);
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("// login fail with incorrect user")) {
                String nextLine = reader.readLine();
                String[] userInfo = nextLine.split(" ");
                driver.findElement(By.name("email")).sendKeys(userInfo[0]);
                Thread.sleep(1000);
                driver.findElement(By.name("password")).sendKeys(userInfo[1]);
                Thread.sleep(1000);
                driver.findElement(By.name("login")).click();
            }
        }

        // Kiểm tra xem phần tử login error có xuất hiện không
        Thread.sleep(1000);
        boolean isCurrentUserPresent = isElementPresent(driver, By.className("help-block"));
        String errorMessage = driver.findElement(By.xpath("/html/body[@class='modal-open']/div[@id='app']/div[@id='modalauth']/div[@class='modal-dialog']/div[@class='modal-content']/div[@class='modal-body container-fluid']/div[@class='block-input-left show-mdlogin login-modal']/form[@id='loginForm']/div[@class='form-group'][1]/div[@class='col-md-9']/span[@class='help-block']")).getText();

        // Xác nhận
        Assert.assertTrue(isCurrentUserPresent);
        Assert.assertEquals(errorMessage, "Thông tin đăng nhập không chính xác!");
    }

    @Test(priority = 5)
    public void loginSuccess() throws InterruptedException, IOException {
        System.out.println("Test case loginSuccess is running.");
        Thread.sleep(1000);
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("password")).clear();
        Thread.sleep(1000);
        // Đọc dữ liệu từ file input
        reader = new BufferedReader(new FileReader(FILE_INPUT));
        String line;
        Thread.sleep(1000);
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("// login success")) {
                String nextLine = reader.readLine();
                String[] userInfo = nextLine.split(" ");
                driver.findElement(By.name("email")).sendKeys(userInfo[0]);
                Thread.sleep(1000);
                driver.findElement(By.name("password")).sendKeys(userInfo[1]);
                Thread.sleep(1000);
                driver.findElement(By.name("login")).click();
            }
        }

        // Kiểm tra xem phần tử write-header có xuất hiện không
        Thread.sleep(3000);
        boolean isCurrentUserPresent = isElementPresent(driver, By.xpath("/html/body/div[@id='app']/header/div[@class='container']/div[@class='pull-right auth']/a[1]/img[@class='img-responsive user-info avatar']"));
        String successMessage = driver.findElement(By.className("write-header")).getText();

        // Xác nhận
        Assert.assertTrue(isCurrentUserPresent);
        Assert.assertEquals(successMessage, "Chào truongminh2k0 Hôm nay bạn muốn hỏi hoặc chia sẻ điều gì?");
    }

    @Test(priority = 6)
    public void logOut() throws InterruptedException, IOException {
        System.out.println("Test case logOut is running.");
        Thread.sleep(1000);
        boolean isCurrentUserPresent = isElementPresent(driver, By.xpath("/html/body/div[@id='app']/header/div[@class='container']/div[@class='pull-right auth']/a[1]/img[@class='img-responsive user-info avatar']"));
        if (isCurrentUserPresent) {
            driver.findElement(By.xpath("/html/body/div[@id='app']/header/div[@class='container']/div[@class='pull-right auth']/a[@class='logout hidden-xs hidden-sm']")).click();
            String welcomeMessage = driver.findElement(By.className("write-header")).getText();

            // Xác nhận
            Assert.assertEquals(welcomeMessage, "Chào bạn Hôm nay bạn muốn hỏi hoặc chia sẻ điều gì?");
        } else {
            reader = new BufferedReader(new FileReader(FILE_INPUT));
            String line;
            Thread.sleep(1000);
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("// login success")) {
                    String nextLine = reader.readLine();
                    String[] userInfo = nextLine.split(" ");
                    driver.findElement(By.name("email")).sendKeys(userInfo[0]);
                    Thread.sleep(1000);
                    driver.findElement(By.name("password")).sendKeys(userInfo[1]);
                    Thread.sleep(1000);
                    driver.findElement(By.name("login")).click();
                }
            }
            Thread.sleep(1000);
            driver.findElement(By.xpath("/html/body/div[@id='app']/header/div[@class='container']/div[@class='pull-right auth']/a[@class='logout hidden-xs hidden-sm']")).click();
            String welcomeMessage = driver.findElement(By.className("write-header")).getText();
            boolean isButtonLoginPresent = isElementPresent(driver, By.xpath("/html/body/div[@id='app']/header/div[@class='container']/div[@class='pull-right auth']/a[@class='login hidden-xs hidden-sm']"));
            Thread.sleep(2000);

            // Xác nhận
            Assert.assertEquals(welcomeMessage, "Chào bạn Hôm nay bạn muốn hỏi hoặc chia sẻ điều gì?");
            Assert.assertTrue(isButtonLoginPresent);
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
