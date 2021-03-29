package tests;

import common.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;


public class LoginTests extends BaseTest {


    @DataProvider(name = "login-alerts")
    public Object[][] loginProvide() {
        return new Object[][]{
                {"matheus@qaplus.com", "abc123", "Usuário e/ou senha inválidos"},
                {"404@gmail.com", "abc123", "Usuário e/ou senha inválidos"},
                {"", "abc123", "Opps. cadê o email?"},
                {"matheus@qaplus.com", "", "Opps. Cadê a senha?"}
        };
    }

    @Test
    public void shouldSeeLoggedUser() {

        login
                .open()
                .with("matheus@qaplus.com", "pwd123");

        side.loggedUser().shouldHave(text("Matheus"));

        /*String test = $(".user .info span").text();
        Assert.assertEquals(test,"João");*/
    }

    @Test(dataProvider = "login-alerts")
    public void shouldSeeLoginAlerts(String email, String pass, String expectAlert) {
        login
                .open()
                .with(email, pass)
                .alert().shouldHave(text(expectAlert));


    }

    @AfterMethod
    public void cleanup() {
        login.clearSession();
    }

}
