package co.evertonnrb.findhoteis.fragaments;/*
    @author everton.nrb@gmail.com
*/

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import java.util.List;

import co.evertonnrb.findhoteis.model.Hotel;

import static java.util.Arrays.asList;

public class HotelListFragment extends ListFragment {
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

    public List<Hotel> carregarHoteis(){
        return asList(new Hotel("Copacabana Palace","Rio de Janeiro",3.2f),
                new Hotel("Jordao","Campos do Jordão",4.2f),
                new Hotel("Friburgo Palace","Rio de Janeiro",4.9f),
                new Hotel("Fundão da Rodoviária","Mato Grosso do Sul",2.2f));
    }
}
