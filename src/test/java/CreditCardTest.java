import entity.User;
import helper.BirthDates;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import pages.CreditCardMtsCashback;
import steps.CreditCardMtsCashbackSteps;

import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.sleep;

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

    @BeforeEach
    public void refreshPage(){
        refresh();
    }

    @ParameterizedTest
    @Description("Ввести корректные данные пользователя и оформить карту")
    @ValueSource(strings = {"Антонов Антон Антонович", "Антонов Антон", "Антонов Антон-Антон"})
    public void succesGetCreditCard(String fio){
        User user = User.Builder()
                .setFio(fio)
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkSuccesGetCard(creditCardMtsCashback);
    }

    @ParameterizedTest
    @Description("Ввести корректные данные пользователя без почты и оформить карту")
    @ValueSource(strings = {"Антонов Антон Антонович", "Антонов Антон", "Антонов Антон-Антон"})
    public void succesGetCreditCardWithoutEmail(String fio){
        User user = User.Builder()
                .setFio(fio)
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkSuccesGetCard(creditCardMtsCashback);
    }

    @ParameterizedTest
    @Description("Ввести корректные данные пользователя, снять галочку о рекламе и оформить карту")
    @ValueSource(strings = {"Антонов Антон Антонович", "Антонов Антон", "Антонов Антон-Антон"})
    public void succesGetCreditCardWithoutInputInfoFromBank(String fio){
        User user = User.Builder()
                .setFio(fio)
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnInputAllowReceiveInfoFromBank(creditCardMtsCashback);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkSuccesGetCard(creditCardMtsCashback);
    }

    @ParameterizedTest
    @Description("Ввести корректные данные пользователя без почты, снять галочку о рекламе и оформить карту")
    @ValueSource(strings = {"Антонов Антон Антонович", "Антонов Антон", "Антонов Антон-Антон"})
    public void succesGetCreditCardWithoutEmailAndInputInfoFromBank(String fio){
        User user = User.Builder()
                .setFio(fio)
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnInputAllowReceiveInfoFromBank(creditCardMtsCashback);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkSuccesGetCard(creditCardMtsCashback);
    }

    @ParameterizedTest
    @Description("Ввести корректные данные пользователя c датами рождения между 20 и 70 годами включительно")
    @EnumSource(value = BirthDates.class, names = {"TODAY_MINUS_20_YEARS", "TODAY_MINUS_35_YEARS", "TODAY_MINUS_70_YEARS"})
    public void succesGetCreditCardDateBirthBetweenTwentyAndSeventy(BirthDates birthDates){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate(birthDates.getBirthDate())
                .setPhoneNumber("7777777777")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkSuccesGetCard(creditCardMtsCashback);
    }

    @Test
    @Description("Ввести корректные данные пользователя, снять галочку с обработки персональных данных и оформить карту")
    public void failureGetCreditCardWithoutProcessingConditionsInput(){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnInputAllowProcessingConditions(creditCardMtsCashback);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkInputAllowProcessingConditions(creditCardMtsCashback);
    }

    @Test
    @Description("Не вводить никаких данных пользователя и оформить карту")
    public void failureGetCreditCard(){
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkAttentions(creditCardMtsCashback);
    }

    @Test
    @Description("Оставить пустым поле ФИО и оформить карту")
    public void failureGetCreditCardWithoutFio(){
        User user = User.Builder()
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkFioFullAttention(creditCardMtsCashback);
    }

    @Test
    @Description("Оставить пустым поле Дата рождения и оформить карту")
    public void failureGetCreditCardWithoutBirthdate(){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkBirthdateImportnatAttention(creditCardMtsCashback);
    }

    @Test
    @Description("Выбрать поле Мобильный телефон, не заполняя его, и оформить карту")
    public void failureGetCreditCardWithoutPhoneNumber(){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate("01.01.1990")
                .setPhoneNumber("")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkPhoneNumberIncorrectAttention(creditCardMtsCashback);
    }

    @Test
    @Description("Оставить пустым поле Мобильный телефон и оформить карту")
    public void failureGetCreditCardWithEmptyPhoneNumber(){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate("01.01.1990")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkEmptyPhoneNumberImportnatAttention(creditCardMtsCashback);
    }

    @ParameterizedTest
    @Description("Ввести некорректное ФИО ({fio}) и оформить карту")
    @ValueSource(strings = {"Anton Antonow Antonowich", "Антонов Ан!тон", "Антонов Антон Антон11"})
    public void failureGetCreditCardInvalidFio(String fio){
        User user = User.Builder()
                .setFio(fio)
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.checkFioLangAttention(creditCardMtsCashback);
    }

    @ParameterizedTest
    @Description("Ввести в качестве даты рождения {birthDates.getBirthDate()}")
    @EnumSource(value = BirthDates.class, names = {"TODAY", "TODAY_MINUS_20_YEARS_PLUS_1_DAY"})
    public void failureGetCreditCardBirthDateLessThanTwenty(BirthDates birthDates){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate(birthDates.getBirthDate())
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.checkLessThanTwentyBirthDateAttention(creditCardMtsCashback);
    }

    @ParameterizedTest
    @Description("Ввести в качестве даты рождения даты человека старше 70 лет")
    @EnumSource(value = BirthDates.class, names = {"TODAY_MINUS_70_YEARS_MINUS_1_DAY"})
    public void failureGetCreditCardBirthDateMoreThanSeventy(BirthDates birthDates){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate(birthDates.getBirthDate())
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.checkMoreThanSeventyBirthDateAttention(creditCardMtsCashback);
    }

    @Test
    @Description("Ввести некорректный почтовый адрес и оформить карту")
    public void failureGetCreditCardInvalidDateEmail(){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .setEmail("alo")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkInvalidEmailAttention(creditCardMtsCashback);
    }
}
