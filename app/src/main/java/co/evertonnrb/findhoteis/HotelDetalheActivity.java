package co.evertonnrb.findhoteis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import co.evertonnrb.findhoteis.fragaments.HotelDetalheFragment;
import co.evertonnrb.findhoteis.model.Hotel;

public class HotelDetalheActivity extends AppCompatActivity {

    public static final String EXTRA_HOTEL = "hotel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detalhe);

        if (savedInstanceState == null){

            Intent intent = getIntent();

            Hotel hotel = (Hotel) intent.getSerializableExtra(EXTRA_HOTEL);

            HotelDetalheFragment hotelDetalheFragment = HotelDetalheFragment.newIntance(hotel);

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.activityHotelDetalhe,hotelDetalheFragment,HotelDetalheFragment.TAG_DETALHE);

            fragmentTransaction.commit();
        }
    }
}