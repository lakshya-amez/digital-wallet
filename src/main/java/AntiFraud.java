import core.TransactionParser;
import core.TransactionValidations;
//import data.EnrichedUserTransactionGraph;
import data.TransactionQueue;
import data.UserTransactionGraph;
import data.UserTransactionHistory;
import lombok.Getter;
import model.Transaction;

import java.io.*;

/**
 * Main program
 */
public class AntiFraud implements Closeable {

    private UserTransactionGraph userTransactionGraph;
    private UserTransactionHistory userTransactionHistory;
    //private EnrichedUserTransactionGraph enrichedUserTransactionGraph;
    private TransactionValidations transactionValidations;
    private TransactionQueue batchTransactionQueue;
    @Getter
    private TransactionQueue streamingTransactionQueue;
    @Getter
    private PrintWriter[] outputFiles;


    public AntiFraud(String args[]) {
        userTransactionGraph = UserTransactionGraph.getInstance();
        userTransactionHistory = UserTransactionHistory.getInstance();
        //enrichedUserTransactionGraph = EnrichedUserTransactionGraph.getInstance();

        transactionValidations = TransactionValidations.getInstance();

        batchTransactionQueue = new TransactionQueue(args[0]);
        streamingTransactionQueue = new TransactionQueue(args[1]);

        outputFiles = new PrintWriter[5];
        for (int i=0; i<5; i++) {
            try {
                outputFiles[i] = new PrintWriter(new FileWriter(args[i + 2]));
            } catch (IOException e) {
                throw new RuntimeException("Unable to open output files for writing");
            }
        }

        init();
    }

    /**
     * Initialize the framework with the batch file of transactions.
     */
    private void init() {
        String csvString;
        Transaction t;
        System.out.println("Beginning initialization...");
        while ((csvString = batchTransactionQueue.getTransaction()) != null) {
            t = TransactionParser.parseCSV(csvString);
            if (t == null) {
                continue;
            }
            userTransactionGraph.updateGraph(t);
            userTransactionHistory.addTransaction(t);
        }
        System.out.println("Caching user friend circles in enriched graph...");
        //enrichedUserTransactionGraph.enrichUserTransactionGraph();
        System.out.println("Initialization Complete!");
    }

    /**
     * Process streaming data and update framework for new transactions.
     * Writes output for each feature to corresponding output file.
     */
    private void run() {
        String csvString;
        Transaction t;
        int cnt = 0;
        long startTime = System.currentTimeMillis();
        System.out.println("Beginning streaming...");
        while ((csvString = streamingTransactionQueue.getTransaction()) != null) {
            t = TransactionParser.parseCSV(csvString);
            if (t == null) {
                continue;
            }
            outputFiles[0].println(transactionValidations.isVerifiedReceiver(t, 1));
            outputFiles[1].println(transactionValidations.isVerifiedReceiver(t, 2));
            outputFiles[2].println(transactionValidations.isVerifiedReceiver(t, 4));
            outputFiles[3].println(transactionValidations.hasReceivedTooMany(t));
            outputFiles[4].println(transactionValidations.isAmountInconsistent(t));
            userTransactionGraph.updateGraph(t);
            userTransactionHistory.addTransaction(t);
            cnt++;
            if (cnt % 1000 == 0) {
                System.out.println(cnt);
            }
        }
        System.out.println("Streaming Complete! Time taken: " + (System.currentTimeMillis() - startTime) / 1000.0 + " s");
    }

    @Override
    public void close() throws IOException {
        batchTransactionQueue.close();
        streamingTransactionQueue.close();
        for (PrintWriter outputFile: outputFiles) {
            outputFile.close();
        }
    }

    public static void main(String args[]) {

        AntiFraud antiFraud = new AntiFraud(args);

        try {
            antiFraud.run();
        } finally {
            try {
                antiFraud.close();
            } catch (IOException e) {
                System.out.println("Unable to close file handlers");
            }
        }

    }

}
