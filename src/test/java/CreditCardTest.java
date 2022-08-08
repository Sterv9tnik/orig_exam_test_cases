import entity.User;
import helper.DateFormatter;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.CreditCardMtsCashback;
import steps.CreditCardMtsCashbackSteps;

import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.refresh;

public class CreditCardTest {

    CreditCardMtsCashback creditCardMtsCashback = new CreditCardMtsCashback();

    CreditCardMtsCashbackSteps creditCardMtsCashbackSteps = new CreditCardMtsCashbackSteps();

/*    @BeforeAll
    public void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true));
        WebDriverManager.chromedriver().driverVersion("103").setup();
        System.setProperty("chromeoptions.args", "--no-sandbox");
        Configuration.browser = "chrome";
        Configuration.headless = true;
    }*/

    @BeforeAll
    public void openPage(){

        creditCardMtsCashback.openPage("https://www.mtsbank.ru/");
        creditCardMtsCashbackSteps.hoverToCards(creditCardMtsCashback);
        creditCardMtsCashbackSteps.clickOnCreditCards(creditCardMtsCashback);
        creditCardMtsCashbackSteps.clickOnCreditCardMtsCashbackButton(creditCardMtsCashback);
    }

    @Test
    @Description("Ввести корректные данные пользователя и оформить карту")
    public void succesGetCreditCard(){

        User user = User.builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkSuccesGetCard(creditCardMtsCashback);

        refresh();
    }

    @Test
    @Description("Не вводить никаких данных пользователя и оформить карту")
    public void failureGetCreditCard(){

        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkAttentions(creditCardMtsCashback);

        refresh();
    }

    @Test
    @Description("Ввести ФИО латиницей и оформить карту")
    public void failureGetCreditCardInvalidFio(){

        User user = User.builder()
                .setFio("Anton Antonov Antonovich")
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();


        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.checkFioAttention(creditCardMtsCashback);

        refresh();
    }

    @Test
    @Description("Ввести в качестве даты рождения сегодняшнюю дату и оформить карту")
    public void failureGetCreditCardInvalidDateBirth(){

        User user = User.builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate(DateFormatter.formatDate(LocalDate.now()))
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();


        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.checkInvalidBirthDateAttention(creditCardMtsCashback);

        refresh();
    }

    @Test
    @Description("Ввести некорректный почтовый адрес и оформить карту")
    public void failureGetCreditCardInvalidDateEmail(){

        User user = User.builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .setEmail("alo")
                .build();


        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkInvalidEmailAttention(creditCardMtsCashback);

        refresh();
    }
}
