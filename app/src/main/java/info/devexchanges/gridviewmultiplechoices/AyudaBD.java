package info.devexchanges.gridviewmultiplechoices;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by M-RIVERA on 21/03/2018.
 */

public class AyudaBD extends SQLiteOpenHelper{

    public static class DatosFiguras implements BaseColumns {
        public static final String TABLE_NAME = "Figuras";
        public static final String COLUMNA_ID = "idFiguras";
        public static final String COLUMN_NUMERO_FIGURA = "numeroFigura";
        public static final String COLUMN_ESTADO_FIGURA = "estadoFigura";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String Crear_Tabla_Figuras =
                "CREATE TABLE " + DatosFiguras.TABLE_NAME + " (" +
                        DatosFiguras.COLUMNA_ID + " INTEGER PRIMARY KEY," +
                        DatosFiguras.COLUMN_NUMERO_FIGURA + TEXT_TYPE + COMMA_SEP +
                        DatosFiguras.COLUMN_ESTADO_FIGURA + TEXT_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatosFiguras.TABLE_NAME;


    }

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Panini.db";

    public AyudaBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatosFiguras.Crear_Tabla_Figuras);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatosFiguras.SQL_DELETE_ENTRIES);
    }
}
