import com.codeborne.selenide.Configuration;
import entity.User;
import helper.BirthDates;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import pages.CreditCardMtsCashback;
import steps.CreditCardMtsCashbackSteps;

import static com.codeborne.selenide.Selenide.refresh;

@Story("Заказ кредитной карты по заполненной анкете")
public class CreditCardTest {

    CreditCardMtsCashback creditCardMtsCashback = new CreditCardMtsCashback();

    CreditCardMtsCashbackSteps creditCardMtsCashbackSteps = new CreditCardMtsCashbackSteps();

    @BeforeAll
    public void openPageAndSetup(){
        WebDriverManager.chromedriver().driverVersion("103").setup();
        System.setProperty("chromeoptions.args", "--no-sandbox");
        Configuration.browser = "chrome";
        Configuration.headless = true;

        creditCardMtsCashback.openPage("https://www.mtsbank.ru/");
        creditCardMtsCashbackSteps.hoverToCards(creditCardMtsCashback);
        creditCardMtsCashbackSteps.clickOnCreditCards(creditCardMtsCashback);
        creditCardMtsCashbackSteps.clickOnCreditCardMtsCashbackButton(creditCardMtsCashback);
    }

    @BeforeEach
    public void refreshPage(){
        refresh();
    }

    @ParameterizedTest(name = "Оформить карту при условии ФИО = {0}. Позитивный сценарий.")
    @Description("Ввести данные пользователя и оформить карту.")
    @ValueSource(strings = {"Антонов Антон Антонович", "Антонов Антон", "Антонов-Бубонов Антон Антонович"})
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

    @Test
    @DisplayName("Оформить карту при отсутсвии данных в поле Почтовый адрес. Позитивный сценарий.")
    @Description("Ввести данные пользователя, оставить поле почты пустым и оформить карту.")
    public void succesGetCreditCardWithoutEmail(){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkSuccesGetCard(creditCardMtsCashback);
    }

    @Test
    @DisplayName("Оформить карту при отсутствии галочки о рекламе. Позитивный сценарий.")
    @Description("Ввести данные пользователя, убрать галочку о рекламе и оформить карту.")
    public void succesGetCreditCardWithoutInputInfoFromBank(){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnInputAllowReceiveInfoFromBank(creditCardMtsCashback);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkSuccesGetCard(creditCardMtsCashback);
    }

    @ParameterizedTest(name = "Оформить карту при условии Дата рождения = {0}. Позитивный сценарий.")
    @Description("Ввести данные пользователя, ввести дату рождения на проверку граничных значений (20, 70 лет).")
    @EnumSource(value = BirthDates.class, names = {"TODAY_MINUS_20_YEARS", "TODAY_MINUS_70_YEARS"})
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
    @DisplayName("Оформить карту при отсутствии галочки обработки персональных данных. Негативный сценарий.")
    @Description("Ввести данные пользователя, убрать галочку с обработки персональных данных и оформить карту.")
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
    @DisplayName("Оформить карту, оставив все поля ввода данных пустыми. Негативный сценарий.")
    @Description("Не вводить никаких данных пользователя и оформить карту.")
    public void failureGetCreditCard(){
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkAttentions(creditCardMtsCashback);
    }

    @Test
    @DisplayName("Оформить карту при отсутствии данных в поле ФИО. Негативный сценарий.")
    @Description("Ввести данные пользователя, оставить пустым поле ФИО и оформить карту.")
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
    @DisplayName("Оформить карту при отсутствии данных в поле Дата рождения. Негативный сценарий.")
    @Description("Ввести данные пользователя, оставить пустым поле Дата рождения и оформить карту.")
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

    @ParameterizedTest(name = "Оформить карту при условии Мобильный телефон = +7{0}. Негативный сценарий.")
    @Description("Ввести данные пользователя, ввести неполный номер в поле Мобильный телефон и оформить карту.")
    @ValueSource(strings = {""})
    public void failureGetCreditCardWithoutPhoneNumber(String phoneNumber){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate("01.01.1990")
                .setPhoneNumber(phoneNumber)
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkPhoneNumberIncorrectAttention(creditCardMtsCashback);
    }

    @Test
    @DisplayName("Оформить карту при отсутствии данных в поле Мобильный телефон. Негативный сценарий.")
    @Description("Ввести данные пользователя, оставить пустым поле Мобильный телефон и оформить карту.")
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

    @ParameterizedTest(name = "Оформить карту при условии ФИО = {0}. Негативный сценарий.")
    @Description("Ввести данные пользователя, ввести ФИО латиницей, спецсимволами, цифрами и оформить карту.")
    @ValueSource(strings = {"Anton Antonow Antonowich", "Антонов Ан!тон", "Антонов Антон Антон11"})
    public void failureGetCreditCardInvalidFio(String fio){
        User user = User.Builder()
                .setFio(fio)
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkFioLangAttention(creditCardMtsCashback);
    }

    @ParameterizedTest(name = "Оформить карту при условии Дата Рождения = {0}. Негативный сценарий.")
    @Description("Ввести данные пользователя, ввести дату рождения, чтобы возраст клиента был менее 20 лет.")
    @EnumSource(value = BirthDates.class, names = {"TODAY_MINUS_20_YEARS_PLUS_1_DAY"})
    public void failureGetCreditCardBirthDateLessThanTwenty(BirthDates birthDates){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate(birthDates.getBirthDate())
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkLessThanTwentyBirthDateAttention(creditCardMtsCashback);
    }

    @ParameterizedTest(name = "Оформить карту при условии Дата Рождения = {0}. Негативный сценарий.")
    @Description("Ввести данные пользователя, ввести дату рождения, чтобы возраст клиента был более 70 лет.")
    @EnumSource(value = BirthDates.class, names = {"TODAY_MINUS_70_YEARS_MINUS_1_DAY"})
    public void failureGetCreditCardBirthDateMoreThanSeventy(BirthDates birthDates){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate(birthDates.getBirthDate())
                .setPhoneNumber("7777777777")
                .setEmail("alo@ya.ru")
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkMoreThanSeventyBirthDateAttention(creditCardMtsCashback);
    }

    @ParameterizedTest(name="Оформить карту при условии Электронная почта = {0}. Негативный сценарий")
    @Description("Ввести данные пользователя, ввести неполный почтовый адрес и оформить карту")
    @ValueSource(strings = {"antonovanton"})
    public void failureGetCreditCardInvalidDateEmail(String email){
        User user = User.Builder()
                .setFio("Антонов Антон Антонович")
                .setBirthDate("01.01.1990")
                .setPhoneNumber("7777777777")
                .setEmail(email)
                .build();

        creditCardMtsCashbackSteps.sendClientData(creditCardMtsCashback, user);
        creditCardMtsCashbackSteps.clickOnNextButton(creditCardMtsCashback);
        creditCardMtsCashbackSteps.checkInvalidEmailAttention(creditCardMtsCashback);
    }
}
