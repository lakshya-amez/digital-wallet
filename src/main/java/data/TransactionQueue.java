package data;

import java.io.*;

/**
 * A queue for reading transaction records. Ideally this could have been a disk based queue like
 * <a href="https://kafka.apache.org/">Kafka</a> but currently it reads transaction records from a file.
 * After no more records are available(i.e. end of file has been reached), it returns {@code null}.
 * It takes as input a file which is assumed to be in CSV format wherein each transaction record is a comma delimited
 * string of the form: <br/>
 * <b><i> time, id1, id2, amount, message </i></b> <br/>
 * <b>NOTE: </b> Minimal extra checks are made on the input for correctness, the input is assumed to be valid!
 */
public class TransactionQueue implements Closeable {

    private BufferedReader br;

    public TransactionQueue(String transactionFile) {
        try {
            br = new BufferedReader(new FileReader(transactionFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to initialize transaction queue. File not found: " + transactionFile);
        }
    }

    /**
     * Fetches the next transaction line from the file.
     *
     * @return {@link String} consisting of the next transaction in the csv file.
     */
    public String getTransaction() {
        String line;
        try {
            line = br.readLine();
        } catch (IOException e) {
            System.out.println("Error reading transaction record");
            line = null;
        }
        return line;

    }

    @Override
    public void close() throws IOException {
        br.close();
    }
}
