package co.evertonnrb.findhoteis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import co.evertonnrb.findhoteis.db.HotelRepository;
import co.evertonnrb.findhoteis.fragaments.HotelDetalheFragment;
import co.evertonnrb.findhoteis.fragaments.HotelDialogAddFragment;
import co.evertonnrb.findhoteis.fragaments.HotelListFragment;
import co.evertonnrb.findhoteis.fragaments.SobreDialogFragment;
import co.evertonnrb.findhoteis.interfaces.AoCliclarNoHotel;
import co.evertonnrb.findhoteis.interfaces.AoEditarHotel;
import co.evertonnrb.findhoteis.interfaces.AoExcluirHotel;
import co.evertonnrb.findhoteis.model.Hotel;

public class HotelActivity extends AppCompatActivity implements
        AoCliclarNoHotel, HotelDialogAddFragment.AoSalvarHotel,
        SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener,
        AoEditarHotel, AoExcluirHotel {

    private FragmentManager mFragmentiManager;
    private HotelListFragment mHotelListFragment;

    private static final int REQUEST_CODE_EDIT = 0;
    private long mIdSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_main);

        mFragmentiManager = getSupportFragmentManager();
        mHotelListFragment = (HotelListFragment) mFragmentiManager.findFragmentById(R.id.fragmentListaHoteis);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && requestCode == RESULT_OK){
            mHotelListFragment.limparBusca();
        }
    }

    /**
     * Reutilização da activity para não perder dados estáticos
     * @return
     */
    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        Intent intent = super.getParentActivityIntent();
        if (intent!=null){
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        return super.getParentActivityIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hotel, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.acao_pesquisar));
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(searchItem,this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.actionInfo:
                SobreDialogFragment dialodFragment = new SobreDialogFragment();
                dialodFragment.show(getSupportFragmentManager(),"sobre");
                break;
           /* case R.id.actionNew:
                HotelDialogAddFragment hotelDialogFragment = HotelDialogAddFragment.newIntance(null);
                hotelDialogFragment.abrirDialogo(getSupportFragmentManager());
                break;*/
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String hotelByName) {
        mHotelListFragment.buscar(hotelByName);
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true; //para abrir a view
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        mHotelListFragment.limparBusca();
        return true;//para voltar ao normal
    }

    @Override
    public void clicouNoHotel(Hotel hotel) {
        hotel.setId(mIdSelecionado);
        Intent intent = new Intent(this, HotelDetalheActivity.class);
        intent.putExtra(HotelDetalheFragment.EXTRA_HOTEL, hotel);
        startActivityForResult(intent,REQUEST_CODE_EDIT);
    }

    @Override
    public void salvouHotel(Hotel hotel) {
        HotelRepository repository = new HotelRepository(this);
        repository.merge(hotel);
        mHotelListFragment.limparBusca();
    }

    public void adicionarHotelNaListaClick(View view) {
        HotelDialogAddFragment hotelDialogAddFragment = HotelDialogAddFragment.newIntance(null);
        hotelDialogAddFragment.abrirDialogo(getSupportFragmentManager());
    }

    @Override
    public void aoEditarHotel(Hotel hotel) {
        HotelDialogAddFragment eedtFragment = HotelDialogAddFragment.newIntance(hotel);
        eedtFragment.abrirDialogo(getSupportFragmentManager());
    }

    @Override
    public void exclusaoCompleta(List<Hotel> hoteis) {
        HotelDetalheFragment detalheFragment = (HotelDetalheFragment) mFragmentiManager.findFragmentByTag(HotelDetalheFragment.TAG_DETALHE);
        if (detalheFragment!=null){
            boolean encontrou = false;
            for (Hotel hotel : hoteis){
                if (hotel.getId() == mIdSelecionado){
                    encontrou = true;
                    break;
                }
            }
            if (encontrou){
                FragmentTransaction transaction = mFragmentiManager.beginTransaction();
                transaction.remove(detalheFragment);
                transaction.commit();
            }
        }
    }
}