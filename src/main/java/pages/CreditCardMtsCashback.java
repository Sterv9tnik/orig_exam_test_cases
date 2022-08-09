package pages;

import lombok.Getter;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.open;

@Getter
public class CreditCardMtsCashback {

    private final By CARDS =  By.xpath("(//div[@class='styled__WrapperTextLink-sc-ip06ne-5 cKgtWc'])[1]");

    private final By CREDIT_CARDS =  By.xpath("(//a[@class='LinkWrapper-sc-a7l7fm-0 eaxjcO styled__LinkItem-sc-de5z4m-0 cOuqjZ'])[1]");

    private final By CREDIT_CARD_MTS_CASHBACK_BUTTON =  By.xpath("(//div[@class='Inner-sc-1rfqasg-0 jviKiO Inner-sc-48arcs-0 bqTsRY'])[1]");

    private final By BUTTON_NEXT = By.xpath("(//div[@class='Inner-sc-1rfqasg-0 jviKiO Inner-sc-48arcs-0 bqTsRY'])[7]");

    private final By ATTENTION_TEXT = By.xpath("//div[@class='Wrapper-sc-1vydk7-0 OlnRe HelperText-sc-jsokzo-0 hByJHf']");

    private final By FIO = By.xpath("//textarea[@name='clientFio']");

    private final By BIRTH_DATE = By.xpath("//input[@name='birthDate']");

    private final By PHONE_NUMBER = By.xpath("//input[@name='phoneNumber']");

    private final By EMAIL = By.xpath("//input[@name='email']");

    private final By CONFIRM_PHONE_NUMBER = By.xpath("//h4[@class='Wrapper-sc-6nwvzq-0 kRJvZg styled__Subheading-hs9eb9-0 eBFccf']");

    private final By INPUT_ALLOW_PROCESSING_CONDITIONS = By.xpath("(//div[@class='Wrapper-sc-cb89gg-0 bfTvzg'])[1]");

    private final By INPUT_ALLOW_RECEIVE_INFO_FROM_BANK = By.xpath("(//div[@class='Wrapper-sc-cb89gg-0 bfTvzg'])[2]");

    public void openPage(String page){
        open(page);
    }


}
