package ru.netology;


import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.LocalDate;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofSeconds;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.openqa.selenium.Keys.chord;

public class CardDeliveryTest {

    @BeforeEach
    public void setUp() {
//        Configuration.holdBrowserOpen = true;
        Selenide.open("http://localhost:7777");
    }

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(ofPattern("dd.MM.yyyy"));

    }

    @Test
    public void shouldSendForm() {
        String planningDate = generateDate(30);
        $("[data-test-id='city'] input").val("Волгоград");
//        $("[data-test-id='date'] input").val("30.03.2022");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Абдуллаев Ян");
        $("[data-test-id='phone'] input").val("+79005008090");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification] .notification__content").shouldBe(visible, ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));

    }
}
