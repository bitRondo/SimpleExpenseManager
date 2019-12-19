package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.storageImpl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

//AccountTable fields: ACCOUNT_NUM, ACC_HOLDER_NAME, BANK_NAME, BALANCE

public class StorageAccountDAOImpl implements AccountDAO {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public StorageAccountDAOImpl(DatabaseHelper helper) {
        this.helper = helper;
    }

    @Override
    public List<String> getAccountNumbersList() {
        ArrayList<String> accNumList = new ArrayList<>();
        db = helper.getReadableDatabase();
        Cursor results = db.rawQuery("SELECT " + DatabaseHelper.ACCOUNT_NUM + " FROM " +
                DatabaseHelper.ACCOUNT_TABLE, null);

        if (results.getCount() > 0) {
            while (results.moveToNext()) {
                accNumList.add(results.getString(0));
                System.out.println(results.getString(0));
            }
        }
        return accNumList;
    }

    @Override
    public List<Account> getAccountsList() {
        ArrayList<Account> accountList = new ArrayList<>();
        db = helper.getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM " +
                DatabaseHelper.ACCOUNT_TABLE, null);

        if (results.getCount() > 0) {
            Account account;
            while (results.moveToNext()) {
                account = new Account(results.getString(0),
                        results.getString(2),
                        results.getString(1), results.getDouble(3));
                accountList.add(account);
                System.out.println(results.getString(0));
            }
        }
        return accountList;
    }

    @Override
    public Account getAccount(String accountNo) {
        db = helper.getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM " +
                DatabaseHelper.ACCOUNT_TABLE + " WHERE " + DatabaseHelper.ACCOUNT_NUM +
                " = ?", new String[] {accountNo});

        if (results.getCount() > 0) {
            Account account = new Account(results.getString(0),
                    results.getString(2),
                    results.getString(1), results.getDouble(3));
            System.out.println(results.getString(0));
            return account;
        }
        return null;
    }

    @Override
    public void addAccount(Account account) {
        System.out.println("adding account");

        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ACCOUNT_NUM, account.getAccountNo());
        values.put(DatabaseHelper.ACC_HOLDER_NAME, account.getAccountHolderName());
        values.put(DatabaseHelper.BANK_NAME, account.getBankName());
        values.put(DatabaseHelper.BALANCE, account.getBalance());

        System.out.println(account.getAccountNo());

        long result = db.insert(DatabaseHelper.ACCOUNT_TABLE, null, values);

        if (result != -1) System.out.println("account added successfully!");

    }

    @Override
    public void removeAccount(String accountNo) {
        db = helper.getWritableDatabase();

        db.delete(DatabaseHelper.ACCOUNT_TABLE, DatabaseHelper.ACCOUNT_NUM + "= ?",
                new String[] {accountNo});

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) {
        db = helper.getWritableDatabase();

        Account account = getAccount(accountNo);
        if (account != null) {
            double newAmount = account.getBalance();
            switch (expenseType){
                case INCOME:
                    newAmount += amount;
                    break;
                case EXPENSE:
                    newAmount -= amount;
                    break;
            }
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.BALANCE, newAmount);

            db.update(DatabaseHelper.ACCOUNT_TABLE, values, DatabaseHelper.ACCOUNT_NUM + " = ?",
                    new String[] {account.getAccountNo()});
        }

    }
}
