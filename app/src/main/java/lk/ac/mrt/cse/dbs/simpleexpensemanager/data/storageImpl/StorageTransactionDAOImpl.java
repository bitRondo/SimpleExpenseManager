package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.storageImpl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class StorageTransactionDAOImpl implements TransactionDAO {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public StorageTransactionDAOImpl (DatabaseHelper helper){
        this.helper = helper;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ACCOUNT_NUM, accountNo);
        values.put(DatabaseHelper.AMOUNT, amount);
        values.put(DatabaseHelper.TYPE, expenseType.toString());
        values.put(DatabaseHelper.DATE_OF_TRANSACTION, date.toString());

        db.insert(DatabaseHelper.TRANSACTION_TABLE, null, values);

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        db = helper.getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM " +
                DatabaseHelper.TRANSACTION_TABLE, null);

        if (results.getCount() > 0) {
            Transaction transaction;
            while (results.moveToNext()) {
                Date date = new Date();
                transaction = new Transaction(date,
                        results.getString(0),
                        ExpenseType.EXPENSE, results.getDouble(3));
                transactions.add(transaction);
                System.out.println(results.getString(0));
            }
        }
        return transactions;

    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        db = helper.getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM " +
                DatabaseHelper.TRANSACTION_TABLE + " LIMIT ?", new String[] {String.valueOf(limit)});

        if (results.getCount() > 0) {
            Transaction transaction;
            while (results.moveToNext()) {
                Date date = new Date();
                transaction = new Transaction(date,
                        results.getString(0),
                        ExpenseType.EXPENSE, results.getDouble(3));
                transactions.add(transaction);
                System.out.println(results.getString(0));
            }
        }
        return transactions;
    }
}
