package common;

import com.codeborne.selenide.Configuration;
import libs.DataBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.MoviePage;
import pages.SideBar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import static com.codeborne.selenide.Screenshots.screenshots;
import static com.codeborne.selenide.Selenide.screenshot;


public class BaseTest {
    protected static LoginPage login;
    protected static MoviePage movie;
    protected static SideBar side;
    protected static DataBase db;

    @BeforeMethod
    public void start() {

        Properties prop = new Properties();
        InputStream inputFile = getClass().getClassLoader().getResourceAsStream("config.properties");
        try {
            prop.load(inputFile);
        }catch (Exception ex){
            System.out.println("Erro ao carregar o arquivo config.properties. Trace =>" + ex.getMessage());
        }


        Configuration.browser = prop.getProperty("browser");
        Configuration.baseUrl = prop.getProperty("url");
        Configuration.timeout = Long.parseLong(prop.getProperty("timeout"));
        login = new LoginPage();
        movie = new MoviePage();
        side = new SideBar();
        db = new DataBase();
    }

    @AfterMethod
    public void finish() {
        // Tira um screenshot pelo selenide
        screenshot("temp_shot");
        File tempShot = screenshots.getLastScreenshot();

        // Transforma em binário para anexar no report do allure
        try {
            BufferedImage image = ImageIO.read(tempShot);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] finalShot = baos.toByteArray();

            io.qameta.allure.Allure.addAttachment("Evidência", new ByteArrayInputStream(finalShot));
        } catch (Exception ex) {
            System.out.println("Deu erro ao anexar Screenshot => " + ex.getMessage());
        }


    }
}
