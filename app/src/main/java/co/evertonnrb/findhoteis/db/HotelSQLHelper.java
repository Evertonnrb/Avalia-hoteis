package co.evertonnrb.findhoteis.db;/*
    @author everton.nrb@gmail.com
*/

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HotelSQLHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "dbHotel";
    private static final int VERSAO_BANCO = 1;

    public static final String TABELA_HOTEL = "hotel";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_ENDERECO = "endereco";
    public static final String COLUNA_ESTRELAS = "estrelas";

    public HotelSQLHelper(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE "+TABELA_HOTEL+"("+
                        COLUNA_ID+" INTEGER PRIMARY KEY , "+
                        COLUNA_NOME+" TEXT NOT NULL, "+
                        COLUNA_ENDERECO+" TEXT, "+
                        COLUNA_ESTRELAS+" REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
