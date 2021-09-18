package co.evertonnrb.findhoteis;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import co.evertonnrb.findhoteis.db.HotelRepository;
import co.evertonnrb.findhoteis.fragaments.HotelDetalheFragment;
import co.evertonnrb.findhoteis.fragaments.HotelDialogAddFragment;
import co.evertonnrb.findhoteis.interfaces.AoEditarHotel;
import co.evertonnrb.findhoteis.interfaces.AoSalvarHotel;
import co.evertonnrb.findhoteis.model.Hotel;

public class HotelDetalheActivity extends AppCompatActivity implements
        AoEditarHotel, AoSalvarHotel {

    public static final String EXTRA_HOTEL = "hotel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detalhe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent it = getIntent();
        Hotel hotel = (Hotel) it.getSerializableExtra(EXTRA_HOTEL);
        exibirHotel(hotel);

        /*if (savedInstanceState == null){

            Intent intent = getIntent();

            Hotel hotel = (Hotel) intent.getSerializableExtra(EXTRA_HOTEL);

            HotelDetalheFragment hotelDetalheFragment = HotelDetalheFragment.newIntance(hotel);

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.activityHotelDetalhe,hotelDetalheFragment,HotelDetalheFragment.TAG_DETALHE);

            fragmentTransaction.commit();
        }*/
    }

    private void exibirHotel(Hotel hotel) {
        HotelDetalheFragment fragment = HotelDetalheFragment.newIntance(hotel);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.activityHotelDetalhe,fragment,HotelDetalheFragment.TAG_DETALHE);
        transaction.commit();
    }

    @Override
    public void aoEditarHotel(Hotel hotel) {
        HotelDialogAddFragment edtFragment = HotelDialogAddFragment.newIntance(hotel);
        edtFragment.abrirDialogo(getSupportFragmentManager());
    }

    @Override
    public void aoSalvarHotel(Hotel hotel) {
        HotelRepository repository = new HotelRepository(this);
        repository.merge(hotel);
        exibirHotel(hotel);
        setResult(RESULT_OK);
    }
}