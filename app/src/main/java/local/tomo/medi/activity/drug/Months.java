package local.tomo.medi.activity.drug;

import java.util.Arrays;

import lombok.SneakyThrows;

public enum Months {

    JANUARY(0),
    FEBRUARY(1),
    MARCH(2),
    APRIL(3),
    MAY(4),
    JUNE(5),
    JULY(6),
    AUGUST(7),
    SEPTEMBER(8),
    OCTOBER(9),
    NOVEMBER(10),
    DECEMBER(11);

    private final int value;

    Months(int value) {
        this.value = value;
    }

    @SneakyThrows
    public static Months valueOf(int value) {
        return Arrays.stream(values())
                .filter(months -> months.value == value)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
