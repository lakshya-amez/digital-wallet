package model;

/**
 * Enumerates the various possible transaction statuses.
 * <ul>
 *     <li> {@code TRUSTED}: Transaction is trusted and can go through. </li>
 *     <li> {@code UNVERIFIED}: Transaction is unverified and requires the attention of the user(s) or system admins. </li>
 * </ul>
 */
public enum TransactionStatus {

    TRUSTED("trusted"),
    UNVERIFIED("unverified");

    private String value;

    TransactionStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
