package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.storageImpl.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.storageImpl.StorageAccountDAOImpl;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.storageImpl.StorageTransactionDAOImpl;

public class StorageExpenseManager extends ExpenseManager {
    private DatabaseHelper dbHelper;

    public StorageExpenseManager(DatabaseHelper dbHelper){
        this.dbHelper = dbHelper;
        setup();
    }

    @Override
    public void setup() {

        StorageAccountDAOImpl accountDAO = new StorageAccountDAOImpl(dbHelper);
        setAccountsDAO(accountDAO);

        StorageTransactionDAOImpl transactionDAO = new StorageTransactionDAOImpl(dbHelper);
        setTransactionsDAO(transactionDAO);

        Account dummy1 = new Account("2019121901A", "Sampath", "Saman", 10000);
        Account dummy2 = new Account("2019121902A", "BoC", "John", 2500);
        getAccountsDAO().addAccount(dummy1);
        getAccountsDAO().addAccount(dummy2);

    }
}
