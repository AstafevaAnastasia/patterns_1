package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.DataGenerator;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {
    private final DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("ru");

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldRescheduleMeeting() {
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + DataGenerator.generateDate(3)));

        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(5));
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id=replan-notification] .notification__content")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $x("//*[text()='Перепланировать']").click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + DataGenerator.generateDate(5)));
    }
}