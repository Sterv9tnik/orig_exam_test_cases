package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import entity.User;
import io.qameta.allure.Step;
import pages.CreditCardMtsCashback;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreditCardMtsCashbackSteps {

    @Step("Навести курсор на поле Карты")
    public void hoverToCards(CreditCardMtsCashback creditCardMtsCashback){
        $(creditCardMtsCashback.getCARDS()).hover();
    }

    @Step("Нажать на поле Кредитные карты")
    public void clickOnCreditCards(CreditCardMtsCashback creditCardMtsCashback){
        $(creditCardMtsCashback.getCREDIT_CARDS()).click();
    }

    @Step("Нажать на кнопку Оформить карту")
    public void clickOnCreditCardMtsCashbackButton(CreditCardMtsCashback creditCardMtsCashback){
        $(creditCardMtsCashback.getCREDIT_CARD_MTS_CASHBACK_BUTTON()).shouldBe(visible, Duration.ofSeconds(60)).click();
    }

    @Step("Нажать на кнопку далее")
    public void clickOnNextButton(CreditCardMtsCashback creditCardMtsCashback){
        $(creditCardMtsCashback.getBUTTON_NEXT()).click();
    }

    @Step("Убрать галочку на ознакомление с обработкой персональных данных")
    public void clickOnInputAllowProcessingConditions(CreditCardMtsCashback creditCardMtsCashback){
        $(creditCardMtsCashback.getINPUT_ALLOW_PROCESSING_CONDITIONS()).click();
    }

    @Step("Убрать галочку на получения рекламных предложений")
    public void clickOnInputAllowReceiveInfoFromBank(CreditCardMtsCashback creditCardMtsCashback){
        $(creditCardMtsCashback.getINPUT_ALLOW_RECEIVE_INFO_FROM_BANK()).click();
    }

    @Step("Отправить данные клиента")
    public void sendClientData(CreditCardMtsCashback creditCardMtsCashback, User user){
        if (user.getFio() != null) {
            $(creditCardMtsCashback.getFIO()).sendKeys(user.getFio());
        }

        if (user.getBirthDate() != null) {
            $(creditCardMtsCashback.getBIRTH_DATE()).sendKeys(user.getBirthDate());
        }

        if (user.getPhoneNumber() != null) {
            $(creditCardMtsCashback.getPHONE_NUMBER()).sendKeys(user.getPhoneNumber());
        }

        if (user.getEmail() != null) {
            $(creditCardMtsCashback.getEMAIL()).sendKeys(user.getEmail());
        }
    }

    @Step("Проверить ожидание смс на телефон")
    public void checkSuccesGetCard(CreditCardMtsCashback creditCardMtsCashback){
        String smsText = $(creditCardMtsCashback.getCONFIRM_PHONE_NUMBER()).shouldBe(Condition.visible).getText();

        assertEquals("Подтвердите номер телефона", smsText);
    }

    @Step("Проверить предупрежедение о незаполненных полях")
    public void checkAttentions(CreditCardMtsCashback creditCardMtsCashback){
        List<SelenideElement> attentionElements = $$(creditCardMtsCashback.getATTENTION_TEXT());

        assertEquals("Введите ФИО полностью", attentionElements.get(0).getText());
        assertEquals("Обязательное поле", attentionElements.get(1).getText());
        assertEquals("Обязательное поле", attentionElements.get(2).getText());
    }

    @Step("Проверить наличие предупреждения о некорректном вводе ФИО")
    public void checkFioLangAttention(CreditCardMtsCashback creditCardMtsCashback){
        SelenideElement attentionFio = $(creditCardMtsCashback.getATTENTION_TEXT());

        assertEquals("Используйте только кириллицу", attentionFio.getText());
    }

    @Step("Проверить наличие предупреждения, что возраст клиента меньше 20 лет")
    public void checkLessThanTwentyBirthDateAttention(CreditCardMtsCashback creditCardMtsCashback){
        SelenideElement attentionBirthDate = $(creditCardMtsCashback.getATTENTION_TEXT());

        assertEquals("Возраст клиента должен быть не менее 20 лет", attentionBirthDate.getText());
    }

    @Step("Проверить наличие предупреждения, что возраст клиента больше 70 лет")
    public void checkMoreThanSeventyBirthDateAttention(CreditCardMtsCashback creditCardMtsCashback) {
        SelenideElement attentionBirthDate = $(creditCardMtsCashback.getATTENTION_TEXT());

        assertEquals("Возраст клиента должен быть до 70 лет", attentionBirthDate.getText());
    }


    @Step("Проверить наличие предупреждения о некорректном вводе почты")
    public void checkInvalidEmailAttention(CreditCardMtsCashback creditCardMtsCashback){
        SelenideElement attentionEmail = $(creditCardMtsCashback.getATTENTION_TEXT());

        assertEquals("Введите верный электронный адрес", attentionEmail.getText());
    }

    @Step("Проверить наличие предупреждения о неполном ФИО")
    public void checkFioFullAttention(CreditCardMtsCashback creditCardMtsCashback){
        SelenideElement attentionFio = $(creditCardMtsCashback.getATTENTION_TEXT());

        assertEquals("Введите ФИО полностью", attentionFio.getText());
    }

    @Step("Проверить наличие предупреждения об отсутствии даты рождения")
    public void checkBirthdateImportnatAttention(CreditCardMtsCashback creditCardMtsCashback){
        SelenideElement attentionBirthDate = $(creditCardMtsCashback.getATTENTION_TEXT());

        assertEquals("Обязательное поле", attentionBirthDate.getText());
    }

    @Step("Проверить наличие предупреждения о некорректном вводе номера телефона")
    public void checkPhoneNumberIncorrectAttention(CreditCardMtsCashback creditCardMtsCashback){
        SelenideElement attentionFio = $(creditCardMtsCashback.getATTENTION_TEXT());

        assertEquals("Введите верный номер телефона", attentionFio.getText());
    }

    @Step("Проверить наличие предупреждения об отсутствии номера телефона")
    public void checkEmptyPhoneNumberImportnatAttention(CreditCardMtsCashback creditCardMtsCashback){
        SelenideElement attentionFio = $(creditCardMtsCashback.getATTENTION_TEXT());

        assertEquals("Обязательное поле", attentionFio.getText());
    }

    @Step("Проверить наличие предупреждения об отсутствии галочки у поля обработки персональных данных")
    public void checkInputAllowProcessingConditions(CreditCardMtsCashback creditCardMtsCashback){
        SelenideElement allowProcessingConditions = $(creditCardMtsCashback.getATTENTION_TEXT());

        assertEquals("Установите этот флажок", allowProcessingConditions.getText());
    }
}
