package core.enumerator;

/**
 * Created by lgermani on 02/01/2018.
 */
public enum BrowserSessionsEnum {
    SPEC("spec"),
    SCENARIO("scenario");

    private String value;

    BrowserSessionsEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
