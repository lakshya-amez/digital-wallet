package core;

import model.Transaction;

import java.text.SimpleDateFormat;

/**
 * Parses transaction in given format into a {@link Transaction} object.
 */
public class TransactionParser {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Parses a comma-delimited line from a CSV file into a {@link Transaction} POJO. The format is assumed to be: <br/>
     * <b><i> time, id1, id2, amount, message </i></b> <br/>
     * <b>NOTE: </b> Minimal extra checks are made on the input for correctness, the input is assumed to be valid!
     *
     * @param line containing comma delimited fields of a transaction record
     * @return {@link Transaction} object for the given transaction
     */
    public static Transaction parseCSV(String line) {
        String fields[] = line.split(",");

        try {
            return Transaction.builder()
                    .date(dateFormat.parse(fields[0]))
                    .senderUID(Long.parseLong(fields[1].replace(" ", "")))
                    .recipientUID(Long.parseLong(fields[2].replace(" ", "")))
                    .amount(Double.parseDouble(fields[3].replace(" ", "")))
                    .message(fields[4].substring(1)) // ignore leading space
                    .build();
        } catch (Exception e) {
            System.out.println("Unable to parse record: " + line);
        }
        return null;
    }
}