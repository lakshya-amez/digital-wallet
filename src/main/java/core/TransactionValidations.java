package core;

import data.UserTransactionGraph;
import data.UserTransactionHistory;
import model.Transaction;
import model.TransactionStatus;
import utils.Statistics;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides a set of validations that can be performed for a transaction.
 */
public class TransactionValidations {

    private static final int M = 3; // Threshold for the no. of transactions for a receiver having received too many transactions.
    private static final int T = 10000; // Threshold for the time(in ms) between transactions for a receiver having received too many transactions.

    private UserTransactionGraph userTransactionGraph;
    private UserTransactionHistory userTransactionHistory;

    private TransactionValidations() {
        userTransactionGraph = UserTransactionGraph.getInstance();
        userTransactionHistory = UserTransactionHistory.getInstance();
    }

    private static class LazyHolder {
        private static final TransactionValidations INSTANCE = new TransactionValidations();
    }

    public static TransactionValidations getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Check whether the recipient is a {@code TRUSTED} user or not by checking whether the recipient belongs in the
     * sender's friend circle up to the given max degree.
     * @param transaction {@link Transaction} to be verified
     * @param maxDegree
     * @return {@code TransactionStatus.TRUSTED} if recipient is trusted, {@code TransactionStatus.UNVERIFIED} otherwise.
     */
    public TransactionStatus isVerifiedReceiver(Transaction transaction, int maxDegree) {
        if (userTransactionGraph.isInFriendCircle(transaction, maxDegree) <= maxDegree) {
            return TransactionStatus.TRUSTED;
        }
        return TransactionStatus.UNVERIFIED;
    }

    /**
     * Check whether the recipient has been on the receiving end of more than M (configured; M < K, the max no.
     * of recent transactions maintained for a user) transactions within a configured time period T which maybe a cause
     * of suspicion.
     * @param transaction {@link Transaction} to be verified
     * @return {@code TransactionStatus.TRUSTED} if there is no cause for suspicion, {@code TransactionStatus.UNVERIFIED} otherwise.
     */
    public TransactionStatus hasReceivedTooMany(Transaction transaction) {

        // Fetch transaction history for the recipient in which he was the receiver.
        LinkedList<Transaction> transactions = userTransactionHistory.getLastKReceivedTransactions(transaction.getRecipientUID());

        // If user was involved in less than M transactions, there is no cause for suspicion.
        if (transactions.size() < M) {
            return TransactionStatus.TRUSTED;
        }

        // Get the timestamps for the most recent and Kth recent transactions and check if the difference(dT) is less than T.
        Date oldestTransactionDate = transactions.get(M - 1).getDate();
        Date newestTransactionDate = transactions.getFirst().getDate();

        // If dT < T, then there is cause for suspicion.
        if (newestTransactionDate.getTime() - oldestTransactionDate.getTime() < T) {
            return TransactionStatus.UNVERIFIED;
        }

        return TransactionStatus.TRUSTED;
    }

    /**
     * Check whether the user is sending an amount that is suspiciously different from the ones he's sent recently.
     * @param transaction {@link Transaction} to be verified
     * @return {@code TransactionStatus.TRUSTED} if there is no cause for suspicion, {@code TransactionStatus.UNVERIFIED} otherwise.
     */
    public TransactionStatus isAmountInconsistent(Transaction transaction) {
        // Fetch transaction history for the sender.
        List<Transaction> transactionIds = userTransactionHistory.getLastKSentTransactions(transaction.getSenderUID());

        // If there are no past transactions, then there is no way of finding out if amount is indeed suspicious.
        if (transactionIds.isEmpty()) {
            return TransactionStatus.TRUSTED;
        }

        // Find whether the amount in question is an outlier for the distribution defined by the historic transactions.
        List<Double> transactionAmounts = transactionIds.parallelStream()
                .map(Transaction::getAmount)
                .collect(Collectors.toList());

        //If it is indeed an outlier, then there is cause for suspicion.
        if (Statistics.isOutlier(transactionAmounts, transaction.getAmount())) {
            return TransactionStatus.UNVERIFIED;
        }

        return TransactionStatus.TRUSTED;
    }
}
