package model;

import lombok.*;

import java.util.Date;

/**
 * A POJO to represent a transaction.
 */
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Transaction {
    private Date date;
    private long senderUID;
    private long recipientUID;
    private double amount;
    private String message;
}
