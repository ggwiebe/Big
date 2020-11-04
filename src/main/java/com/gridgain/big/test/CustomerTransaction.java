package com.gridgain.big.test;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;
import org.apache.ignite.transactions.TransactionOptimisticException;

import static org.apache.ignite.transactions.TransactionConcurrency.PESSIMISTIC;
import static org.apache.ignite.transactions.TransactionIsolation.REPEATABLE_READ;

import com.gridgain.big.model.Customer;
import com.gridgain.big.model.Contact;

import java.sql.Timestamp;

public class CustomerTransaction {

    /**
     * Transactional Customer example.
     *
     * @param args Command line arguments, none required.
     * @throws IgniteException If example execution failed.
     */
    public static void main(String[] args) throws IgniteException {

        Timestamp startDT = new Timestamp(System.currentTimeMillis());
        try (Ignite ignite = Ignition.start("Big-client-transactional.xml")) {
            System.out.println();
            System.out.println(">>> CustomerCache transaction example started.");

            // Auto-close cache at the end of the example.
            try (
                    IgniteCache<Integer, Customer> cusCache = ignite.getOrCreateCache("CustomerTransCache");
                    IgniteCache<Integer, Contact> conCache = ignite.getOrCreateCache("ContactTransCache")
                ) {


                try (Transaction tx = ignite.transactions().txStart(TransactionConcurrency.OPTIMISTIC, TransactionIsolation.SERIALIZABLE)) {
                    // Modify cache entires as part of this transacation.
                    cusCache.put(1, new Customer("a1b2c3d4e5f6", startDT, "Sales", "GridGain Sales", "Great", "PASSWORD"));
                    cusCache.put(2, new Customer("f1e2d3c4b5a6", startDT, "Services", "GridGain Professional Services", "Good", "1234567!"));
    
                    conCache.put(1, new Contact(startDT, "Judy Ameli", "123 Some St.", "Wistler", "BC", "W5X 2Q1", "604.555.1212", "V1"));
                    conCache.put(2, new Contact(startDT, "Colin Capriati", "36604 Riviera Drive", "San Diego", "CA", "90210", "619.555.9021", "V1"));
                              
                    // commit transaction.  
                    tx.commit();
           
               }
               catch (TransactionOptimisticException e) {
                       // Transaction has failed. Retry.
               }


                // Load
                cusCache.put(1, new Customer("a1b2c3d4e5f6", startDT, "Sales", "GridGain Sales", "Great", "PASSWORD"));
                cusCache.put(2, new Customer("f1e2d3c4b5a6", startDT, "Services", "GridGain Professional Services", "Good", "1234567!"));

                conCache.put(1, new Contact(startDT, "Judy Ameli", "123 Some St.", "Wistler", "BC", "W5X 2Q1", "604.555.1212", "V1"));
                conCache.put(2, new Contact(startDT, "Colin Capriati", "36604 Riviera Drive", "San Diego", "CA", "90210", "619.555.9021", "V1"));

                print("Customers in [Trans] Table: ", cusCache.query(new SqlFieldsQuery("SELECT * FROM CustomerTrans")).getAll());
                print("Contacts in [Trans] Table: ", cusCache.query(new SqlFieldsQuery("SELECT * FROM ContactTrans")).getAll());

                // simulate time going by...
                Long rnd = (long) (Math.random() * 1000);
                try {
                    Thread.sleep(rnd);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }

                // Make transactional changes.
                changeContact(conCache, cusCache, 1, "v" + rnd.toString());

                print(
                    "Customers in [Trans] Table [AFTER]: ",
                    cusCache.query(new SqlFieldsQuery("SELECT * FROM CustomerTrans")).getAll()
                );
                print(
                    "Contacts in [Trans] Table: [AFTER]: ",
                    cusCache.query(new SqlFieldsQuery("SELECT * FROM ContactTrans")).getAll()
                );

                System.out.println(">>> Cache transaction example finished.");
            }
            catch(Exception e){
                System.out.println(e);
            }
            finally {
                // Anything?
            }
        }
    }

    /**
     * Change Contact Info Transactionally
     *
     * @param acctId Account ID.
     * @param amount Amount to deposit.
     * @throws IgniteException If failed.
     */
    private static void changeContact(IgniteCache<Integer, Contact> conCache, IgniteCache<Integer, Customer> cusCache, int seqNum, String verComment) throws IgniteException {
        try (Transaction tx = Ignition.ignite().transactions().txStart(PESSIMISTIC, REPEATABLE_READ)) {
            Timestamp updateDT =  new Timestamp(System.currentTimeMillis());
    
            Contact con = conCache.get(seqNum);
            assert con != null;

            // Take existing comment and add a version update.
            String currComment = con.getComment();
            con.setComment(currComment + " :: " + verComment);
            con.setUpdateDT(updateDT);

            // Update the update datetime in the customer cache to reflect the change
            Customer cus = cusCache.get(seqNum);
            assert con != null;

            // Update the customer UpdateDT with this UpdateDT
            cus.setUpdateDT(updateDT);

            // Commit the transaction to both tables
            System.out.println(">>> Committing Contact & Customer updates.............................");
            tx.commit();

            System.out.println();
            System.out.println(">>> Contact updated to version: " + con.getComment() );
            System.out.println(">>> Customer also updated to reflect the change date: " + updateDT);
        }

    }

    /**
     * Prints message and query results.
     *
     * @param msg Message to print before all objects are printed.
     * @param col Query results.
     */
    private static void print(String msg, Iterable<?> col) {
        print(msg);
        print(col);
    }

    /**
     * Prints message.
     *
     * @param msg Message to print before all objects are printed.
     */
    private static void print(String msg) {
        System.out.println();
        System.out.println(">>> " + msg);
    }

    /**
     * Prints query results.
     *
     * @param col Query results.
     */
    private static void print(Iterable<?> col) {
        for (Object next : col)
            System.out.println(">>>     " + next);
    }

}
