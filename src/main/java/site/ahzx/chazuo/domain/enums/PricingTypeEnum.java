package site.ahzx.chazuo.domain.enums;

public enum PricingTypeEnum {
    BY_ENERGY("by_energy"),
    BY_TIME("by_time"),
    BY_AMOUNT("by_amount");

    private final String value;

    PricingTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
