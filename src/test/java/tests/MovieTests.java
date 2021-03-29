package tests;

import common.BaseTest;
import libs.DataBase;
import models.MovieModel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class MovieTests extends BaseTest {


    @BeforeMethod
    public void setup() {

        login
                .open()
                .with("matheus@qaplus.com", "pwd123");

    }
    @BeforeSuite
    public void delorean(){
        DataBase db = new DataBase();
        db.restartMovies();
    }


    @Test
    public void shoudlRegistreANewMovie() {
        MovieModel movieData = new MovieModel(
                "Jumaji - Próxima fase",
                "Pré-venda",
                2020,
                "16/01/2020",
                Arrays.asList("The Rock", "Jeack Black", "Keren Gillan", "Danny DeVito"),
                "Tentado a revisitar o mundo de Jumanji, Spencer decide" +
                        " consertar o bug no jogo do game que permite que seja transportados ao local",
                "jumanj2.jpg"
        );

        movie
                .add()
                .create(movieData)
                .items().findBy(text(movieData.title)).shouldBe(visible);

    }

    @Test
    public void shouldSearchOneMovie() {

        movie
                .shearch("Batman")
                .items().shouldHaveSize(2);

    }

    @AfterMethod
    public void cleanup() {
        login.clearSession();
    }
}
