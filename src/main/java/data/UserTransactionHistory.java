package data;

import com.google.common.annotations.VisibleForTesting;
import model.Transaction;

import java.util.*;

/**
 * A database of the last K transactions performed by a user.
 * The transactions are maintained in a list in order of most recent to least recent.
 */
public class UserTransactionHistory {

    private static final int K = 5; // Max no. of recent transactions maintained.

    private Map<Long, LinkedList<Transaction>> sentTransactions;
    private Map<Long, LinkedList<Transaction>> receivedTransactions;

    private UserTransactionHistory() {
        sentTransactions = new HashMap<>();
        receivedTransactions = new HashMap<>();
    }

    private static class LazyHolder {
        private static final UserTransactionHistory INSTANCE = new UserTransactionHistory();
    }
    public static UserTransactionHistory getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Add a transaction to the database. Entries are added for both the sender and the recipient in the respective transaction lists.
     * @param transaction the {@link Transaction} to be added
     * @return {@code true} indicating {@link Transaction} was successfully added.
     */
    public boolean addTransaction(Transaction transaction) {
        long senderUID = transaction.getSenderUID();
        long recipientUID = transaction.getRecipientUID();

        if (!sentTransactions.containsKey(senderUID)) {
            sentTransactions.put(senderUID, new LinkedList<>());
        }
        addTransactionToFixedSizedList(sentTransactions.get(senderUID), transaction);

        if (!receivedTransactions.containsKey(recipientUID)) {
            receivedTransactions.put(recipientUID, new LinkedList<>());
        }
        addTransactionToFixedSizedList(receivedTransactions.get(recipientUID), transaction);

        return true;
    }

    private void addTransactionToFixedSizedList(LinkedList<Transaction> list, Transaction transaction) {
        list.addFirst(transaction);
        if (list.size() > K) {
            list.removeLast();
        }
    }

    /**
     * Returns a maximum of K of the most recent transactions of the user in which he was the sender.
     * @param userID senderUID
     * @return List of {@link Transaction}s
     */
    public LinkedList<Transaction> getLastKSentTransactions(long userID) {
        return sentTransactions.getOrDefault(userID, new LinkedList<>());
    }

    /**
     * Returns a maximum of K of the most recent transactions of the user in which he was the recipient.
     * @param userID recipientUID
     * @return List of {@link Transaction}s
     */
    public LinkedList<Transaction> getLastKReceivedTransactions(long userID) {
        return receivedTransactions.getOrDefault(userID, new LinkedList<>());
    }

    @VisibleForTesting
    public void clear() {
        sentTransactions = new HashMap<>();
        receivedTransactions = new HashMap<>();
    }

}
