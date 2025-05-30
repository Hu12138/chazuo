package site.ahzx.chazuo.domain.enums;

public enum TimeUnitEnum {
    MINUTE("minute"),
    HOUR("hour");

    private final String value;

    TimeUnitEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
