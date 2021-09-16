package co.evertonnrb.findhoteis.fragaments;/*
    @author everton.nrb@gmail.com
*/

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

import co.evertonnrb.findhoteis.interfaces.AoCliclarNoHotel;
import co.evertonnrb.findhoteis.model.Hotel;

import static java.util.Arrays.asList;

public class HotelListFragment extends ListFragment implements AoCliclarNoHotel {
    List<Hotel> mHoteis;
    ArrayAdapter<Hotel> mAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHoteis = carregarHoteis();
        mAdapter = new ArrayAdapter<Hotel>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mHoteis
        );
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(@NonNull ListView l,
                                @NonNull View v,
                                int position,
                                long id) {
        super.onListItemClick(l, v, position, id);

        Activity activity = getActivity();
        if (activity instanceof AoCliclarNoHotel) {
            Hotel hotel = (Hotel) l.getItemAtPosition(position);

            AoCliclarNoHotel listener = (AoCliclarNoHotel) activity;
            listener.clicouNoHotel(hotel);
        }
    }

    public void buscar(String s) {
        if (s == null || s.trim().equals("")) {
            limparBusca();
            return;
        }
        List<Hotel> hoteisEncontrados = new ArrayList<Hotel>(mHoteis);
        for (int i = hoteisEncontrados.size() - 1; i >= 0; i--) {
            Hotel hotel = hoteisEncontrados.get(i);
            if (!hotel.getNome().toUpperCase().contains(s.toUpperCase())) {
                hoteisEncontrados.remove(hotel);
            }
        }
        mAdapter = new ArrayAdapter<Hotel>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                hoteisEncontrados
        );
        setListAdapter(mAdapter);
    }

    public void limparBusca() {
        mAdapter = new ArrayAdapter<Hotel>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mHoteis);
        setListAdapter(mAdapter);
    }

    public void adicionar(Hotel hotel) {
        mHoteis.add(hotel);
        mAdapter.notifyDataSetChanged();
    }

    public List<Hotel> carregarHoteis() {
        List<Hotel> hoteis = new ArrayList<>();
        hoteis.add(new Hotel("Copacabana Palace", "Rio de Janeiro", 3.2f));
        hoteis.add(new Hotel("Friburgo Palace", "Rio de Janeiro", 4.9f));
        hoteis.add(new Hotel("Fundão da Rodoviária", "Mato Grosso do Sul", 2.2f));
        return hoteis;
    }


    @Override
    public void clicouNoHotel(Hotel hotel) {

    }
}
