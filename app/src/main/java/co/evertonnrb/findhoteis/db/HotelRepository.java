package co.evertonnrb.findhoteis.db;/*
    @author everton.nrb@gmail.com
*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.evertonnrb.findhoteis.model.Hotel;

public class HotelRepository {
    private HotelSQLHelper helper;

    public HotelRepository(Context context) {
        helper = new HotelSQLHelper(context);
    }

    private long insert(Hotel hotel) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HotelSQLHelper.COLUNA_NOME, hotel.getNome());
        contentValues.put(HotelSQLHelper.COLUNA_ENDERECO, hotel.getEndereco());
        contentValues.put(HotelSQLHelper.COLUNA_ESTRELAS, hotel.getEstrelas());
        long id = database.insert(HotelSQLHelper.TABELA_HOTEL, null, contentValues);
        database.close();
        return id;
    }

    private int update(Hotel hotel) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HotelSQLHelper.COLUNA_ID, hotel.getId());
        contentValues.put(HotelSQLHelper.COLUNA_NOME, hotel.getNome());
        contentValues.put(HotelSQLHelper.COLUNA_ENDERECO, hotel.getEndereco());
        contentValues.put(HotelSQLHelper.COLUNA_ESTRELAS, hotel.getEstrelas());
        int linhasAfetadas = database.update(HotelSQLHelper.TABELA_HOTEL, contentValues,
                HotelSQLHelper.COLUNA_ID + "=?", new String[]{String.valueOf(hotel.getId())});
        database.close();
        return linhasAfetadas;
    }

    public void merge(Hotel hotel) {
        if (hotel.getId() == 0) {
            insert(hotel);
        } else {
            update(hotel);
        }
    }

    public int delete(Hotel hotel) {
        SQLiteDatabase database = helper.getWritableDatabase();
        int linhasAfetadas = database.delete(
                HotelSQLHelper.TABELA_HOTEL,
                HotelSQLHelper.COLUNA_ID,
                new String[]{String.valueOf(hotel.getId())}
        );
        database.close();
        return linhasAfetadas;
    }

    public List<Hotel> findHotels(String filter) {
        SQLiteDatabase database = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + HotelSQLHelper.TABELA_HOTEL;
        String[] args = null;
        if (filter != null) {
            sql += " WHERE "+HotelSQLHelper.COLUNA_NOME +" LIKE ?";
            args = new String[]{filter};
        }
        sql += " ORDER BY "+HotelSQLHelper.COLUNA_NOME;
        Cursor cursor = database.rawQuery(sql,args);
        List<Hotel> hotelList = new ArrayList<>();
        while (cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex(HotelSQLHelper.COLUNA_ID));
            String nome = cursor.getString(cursor.getColumnIndex(HotelSQLHelper.COLUNA_NOME));
            String endereco = cursor.getString(cursor.getColumnIndex(HotelSQLHelper.COLUNA_ENDERECO));
            float estrelas = cursor.getLong(cursor.getColumnIndex(HotelSQLHelper.COLUNA_ESTRELAS));
            Hotel hotel = new Hotel(id,nome,endereco,estrelas);
            hotelList.add(hotel);
        }
        database.close();
        return hotelList;
    }
}
