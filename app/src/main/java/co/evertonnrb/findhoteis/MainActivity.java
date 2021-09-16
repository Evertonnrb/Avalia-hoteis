package co.evertonnrb.findhoteis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import co.evertonnrb.findhoteis.fragaments.HotelDetalheFragment;
import co.evertonnrb.findhoteis.interfaces.AoCliclarNoHotel;
import co.evertonnrb.findhoteis.model.Hotel;

public class MainActivity extends AppCompatActivity implements AoCliclarNoHotel{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void clicouNoHotel(Hotel hotel) {
        Intent intent = new Intent(this, HotelDetalheActivity.class);
        intent.putExtra(HotelDetalheFragment.EXTRA_HOTEL,hotel);
        startActivity(intent);
    }
}