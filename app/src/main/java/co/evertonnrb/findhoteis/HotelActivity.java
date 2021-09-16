package co.evertonnrb.findhoteis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import co.evertonnrb.findhoteis.fragaments.HotelDetalheFragment;
import co.evertonnrb.findhoteis.fragaments.HotelListFragment;
import co.evertonnrb.findhoteis.interfaces.AoCliclarNoHotel;
import co.evertonnrb.findhoteis.model.Hotel;

public class HotelActivity extends AppCompatActivity implements
        AoCliclarNoHotel,
        SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener {

    private FragmentManager mFragmentiManager;
    private HotelListFragment mHotelListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentiManager = getSupportFragmentManager();
        mHotelListFragment = (HotelListFragment) mFragmentiManager.findFragmentById(R.id.fragmentListaHoteis);
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
        Intent intent = new Intent(this, HotelDetalheActivity.class);
        intent.putExtra(HotelDetalheFragment.EXTRA_HOTEL, hotel);
        startActivity(intent);
    }
}