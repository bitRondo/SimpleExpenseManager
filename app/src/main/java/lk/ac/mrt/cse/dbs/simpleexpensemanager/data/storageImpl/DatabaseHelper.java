package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.storageImpl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "170669Xnew";

    //Table names
    public static final String ACCOUNT_TABLE = "accountTable",
            TRANSACTION_TABLE = "transactionTable";

    //Common fields
    public static final String ACCOUNT_NUM = "accountNum";

    //Account table specific fileds
    public static final String ACC_HOLDER_NAME = "accHolderName", BANK_NAME = "bankName",
            BALANCE = "balance";

    //Transaction table specific fields
    public static final String DATE_OF_TRANSACTION = "dateOfTransaction",
            TYPE = "expenseType", AMOUNT = "amount";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createAccountTableStatement = "CREATE TABLE " + ACCOUNT_TABLE +
                "( " + ACCOUNT_NUM + " TEXT PRIMARY KEY, " + ACC_HOLDER_NAME + " TEXT, " +
                BANK_NAME + " TEXT, " + BALANCE + " REAL " + ");" ;
        String createTransactionTableStatement = "CREATE TABLE " + TRANSACTION_TABLE +
                "( " + ACCOUNT_NUM + " TEXT, " + TYPE + " TEXT, " +
                DATE_OF_TRANSACTION + " DATE," + AMOUNT + " REAL, " + "FOREIGN KEY (" +
                ACCOUNT_NUM + ")" + " REFERENCES " + ACCOUNT_TABLE + "(" + ACCOUNT_NUM + ")" + ");";

        sqLiteDatabase.execSQL(createAccountTableStatement);
        sqLiteDatabase.execSQL(createTransactionTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRANSACTION_TABLE);
        onCreate(sqLiteDatabase);
    }
}
