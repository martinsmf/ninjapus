package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import models.MovieModel;
import org.openqa.selenium.Keys;

import java.io.File;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class MoviePage {

    public MoviePage add() {
        $(".movie-add").click();
        return this;
    }

    public MoviePage shearch(String value){
        $("input[placeholder^=Pesquisar]").setValue(value);
        $("#search-movie").click();
        return this;
    }

    public MoviePage create(MovieModel movie) {
        $("input[name=title]").setValue(movie.title);
        this.selectStatus(movie.status);
        $("input[name=year]").setValue(movie.year.toString());
        $("input[name=release_date]").setValue(movie.releaseDate).sendKeys(Keys.ENTER);
        this.inputCast(movie.cast);
        $("textarea[name=overview]").setValue(movie.plot);
        this.upload(movie.cover);
        $("#create-movie").click();
        return this;
    }

    public ElementsCollection items() {
        return $$("table tbody tr");
    }

    private void upload(File cover) {
        String jsScriptRemove = "document.getElementById('upcover').classList.remove('el-upload__input')";
        executeJavaScript(jsScriptRemove);
        $("#upcover").uploadFile(cover);
        String jsScriptAdd = "document.getElementById(\"upcover\").classList.add(\"el-upload__input\");";
        executeJavaScript(jsScriptAdd);
    }

    private void inputCast(List<String> cast) {
        SelenideElement element = $(".cast");

        for (String actor : cast) {
            element.sendKeys(actor);
            element.sendKeys(Keys.TAB);
        }
    }

    private void selectStatus(String status) {
        $("input[placeholder='Status']").click();
        $$("ul li span").findBy(text(status)).click();
    }
}
