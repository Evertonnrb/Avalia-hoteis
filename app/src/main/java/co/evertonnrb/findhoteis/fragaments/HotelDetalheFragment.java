package co.evertonnrb.findhoteis.fragaments;/*
    @author everton.nrb@gmail.com
*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import co.evertonnrb.findhoteis.R;
import co.evertonnrb.findhoteis.interfaces.AoEditarHotel;
import co.evertonnrb.findhoteis.model.Hotel;

public class HotelDetalheFragment extends Fragment {

    public static String TAG_DETALHE = "tagDetalhe";
    public static String EXTRA_HOTEL = "hotel";

    private TextView mTextViewDetalheHotelFragmentNome;
    private TextView mTextViewDetalheHotelFragmentEndereco;
    private RatingBar mRatingBarDetalheHotelFragmentEstrelas;

    private ShareActionProvider mShareActionProvider;

    private Hotel mHotel;

    public static HotelDetalheFragment newIntance(Hotel hotel) {
        Bundle params = new Bundle();
        params.putSerializable(EXTRA_HOTEL, hotel);

        HotelDetalheFragment hotelDetalheFragment = new HotelDetalheFragment();
        hotelDetalheFragment.setArguments(params);
        return hotelDetalheFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHotel = (Hotel) getArguments().getSerializable(EXTRA_HOTEL);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_share,menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        String texto = getString(R.string.compartilhar_experiencia,mHotel.getNome(),mHotel.getEstrelas());
        Intent it = new Intent(Intent.ACTION_SEND);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        it.setType("text/plain");
        it.putExtra(Intent.EXTRA_TEXT,texto);
        mShareActionProvider.setShareIntent(it);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_detalhe_hotel, container, false);

        mTextViewDetalheHotelFragmentNome = layout.findViewById(R.id.textFragmentDetalheHotelNome);
        mTextViewDetalheHotelFragmentEndereco = layout.findViewById(R.id.textFragmentDetalheHotelEndereco);
        mRatingBarDetalheHotelFragmentEstrelas = layout.findViewById(R.id.ratinbarFragmentDetalheHotelEstrelas);

        if (mHotel != null) {
            mTextViewDetalheHotelFragmentNome.setText(mHotel.getNome());
            mTextViewDetalheHotelFragmentEndereco.setText(mHotel.getEndereco());
            mRatingBarDetalheHotelFragmentEstrelas.setRating(mHotel.getEstrelas());
        }
        return layout;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit){
            Activity activity = getActivity();
            if (activity instanceof AoEditarHotel){
                AoEditarHotel aoEditarHotel = (AoEditarHotel) activity;
                aoEditarHotel.aoEditarHotel(mHotel);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public Hotel getmHotel() {
        return mHotel;
    }
}
