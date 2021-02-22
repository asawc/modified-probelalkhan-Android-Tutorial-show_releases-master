package myapp.model;

import com.google.gson.annotations.SerializedName;

public enum ReleaseStatus {

    @SerializedName("0")
    OCZEKUJĄCY (0),

    @SerializedName("1")
    W_TRAKCIE (1),

    @SerializedName("2")
    ZROBIONY (2);

    private final int value;

    public int getValue() {
        return value;
    }

    private ReleaseStatus(int value) {
        this.value = value;
    }
}
