package co.evertonnrb.findhoteis.fragaments;/*
    @author everton.nrb@gmail.com
*/

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.ListFragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import co.evertonnrb.findhoteis.R;
import co.evertonnrb.findhoteis.interfaces.AoCliclarNoHotel;
import co.evertonnrb.findhoteis.model.Hotel;

import static java.util.Arrays.asList;

public class HotelListFragment extends ListFragment implements
        AoCliclarNoHotel,
        ActionMode.Callback,
        AdapterView.OnItemLongClickListener {

    private List<Hotel> mHoteis;
    private ArrayAdapter<Hotel> mAdapter;
    private ListView mListView;
    private ActionMode mActionMode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mHoteis = carregarHoteis();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView = getListView();
        limparBusca();
    }

    @Override
    public void onListItemClick(@NonNull ListView l,
                                @NonNull View v,
                                int position,
                                long id) {
        super.onListItemClick(l, v, position, id);

        if (mActionMode == null) {
            Activity activity = getActivity();
            if (activity instanceof AoCliclarNoHotel) {
                Hotel hotel = (Hotel) l.getItemAtPosition(position);

                AoCliclarNoHotel listener = (AoCliclarNoHotel) activity;
                listener.clicouNoHotel(hotel);
            } else {
                atualizarItensMarcados(mListView, position);
                if (qtdItensMarcados() == 0) {
                    mActionMode.finish();
                }
            }
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
        mListView.setOnItemLongClickListener(null);
        mAdapter = new ArrayAdapter<Hotel>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                hoteisEncontrados
        );
        setListAdapter(mAdapter);
    }

    public void limparBusca() {
        mListView.setOnItemLongClickListener(this);
        mAdapter = new ArrayAdapter<Hotel>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                mHoteis
        );
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

    /*
        Tratando eventos com long click para deletar
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        boolean consumed = (mActionMode == null);
        if (consumed) {
            iniciarExclusao();
            mListView.setItemChecked(position, true);
            atualizarItensMarcados(mListView, position);
        }
        return false;
    }

    private void iniciarExclusao() {
        AppCompatActivity compatActivity = (AppCompatActivity) getActivity();
        mActionMode = compatActivity.startSupportActionMode(this);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }


    private void atualizarItensMarcados(ListView mListView, int position) {
        mListView.setItemChecked(position, mListView.isItemChecked(position));
        atualizarTitulo();
    }

    private void atualizarTitulo() {
        int checkedCount = qtdItensMarcados();
        String selecionados = getResources().getQuantityString(
                R.plurals.numero_selecionados,
                checkedCount, checkedCount);
        mActionMode.setTitle(selecionados);
    }

    private int qtdItensMarcados() {
        SparseBooleanArray checked = mListView.getCheckedItemPositions();
        int checkedCount = 0;
        for (int i = 0; i < checked.size(); i++) {
            if (checked.valueAt(i)) {
                checkedCount++;
            }
        }
        return checkedCount;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.menu_delete_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.acao_delete) {
            remover();
            mode.finish();
            return true;
        }
        return false;
    }

    private void remover() {
        final List<Hotel> hoteisExcluidos = new ArrayList<>();
        SparseBooleanArray checked = mListView.getCheckedItemPositions();
        for (int i = checked.size() - 1; i >= 0; i--) {
            if (checked.valueAt(i)) {
                mHoteis.remove(checked.keyAt(i));
            }
        }
        Snackbar.make(mListView,getString(R.string.mensagem_excluir,hoteisExcluidos.size()), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.desfazer, v -> {
                    for (Hotel h : hoteisExcluidos) {
                        hoteisExcluidos.add(h);
                    }
                    limparBusca();
                }).show();
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode = null;
        mListView.clearChoices();
        mListView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        limparBusca();
    }
}
