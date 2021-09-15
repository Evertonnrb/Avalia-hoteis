package co.evertonnrb.findhoteis.fragaments;/*
    @author everton.nrb@gmail.com
*/

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import co.evertonnrb.findhoteis.R;
import co.evertonnrb.findhoteis.model.Hotel;

public class HotelDetalheFragment extends Fragment {

    public static String TAG_DETALHE = "tagDetalhe";
    public static String EXTRA_HOTEL = "hotel";

    private TextView mTextViewDetalheHotelFragmentNome;
    private TextView mTextViewDetalheHotelFragmentEndereco;
    private RatingBar mRatingBarDetalheHotelFragmentEstrelas;

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
}
