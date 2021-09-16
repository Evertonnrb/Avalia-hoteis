package co.evertonnrb.findhoteis.fragaments;/*
    @author everton.nrb@gmail.com
*/

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import co.evertonnrb.findhoteis.R;
import co.evertonnrb.findhoteis.interfaces.AoCliclarNoHotel;
import co.evertonnrb.findhoteis.model.Hotel;

public class HotelDialogFragment extends DialogFragment implements
        TextView.OnEditorActionListener{

    private static final String DIALOG_TAG = "editDialog";
    private static final String EXTRA_HOTEL = "hotel";

    private EditText mEditTextNome;
    private EditText mEditTextEndereco;
    private RatingBar mRatingBarEstrelas;

    private Hotel mHotel;

    public static HotelDialogFragment newIntance(Hotel hotel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_HOTEL, hotel);

        HotelDialogFragment hotelDialogFragment = new HotelDialogFragment();
        hotelDialogFragment.setArguments(bundle);

        return hotelDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHotel = (Hotel) getArguments().getSerializable(EXTRA_HOTEL);
    }

    /**
     * Abre o teclado virtual para fragment
     *
     * @param inflater           infla o layout para capturar os dados da view
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(
                R.layout.fragment_dialog_hotel,
                container,
                false);

        mEditTextNome = (EditText) layout.findViewById(R.id.edtNomeFragmentDialogHotel);
        mEditTextNome.requestFocus();
        mEditTextEndereco = (EditText) layout.findViewById(R.id.edtEnderecoFragmentDialogHotel);
        mEditTextEndereco.setOnEditorActionListener(this);
        mRatingBarEstrelas = (RatingBar) layout.findViewById(R.id.rtbEstrelasFragmentDialogHotel);

        if (mHotel != null) {
            mEditTextNome.setText(mHotel.getNome());
            mEditTextNome.setText(mHotel.getEndereco());
            mRatingBarEstrelas.setRating(mHotel.getEstrelas());
        }
        //Abre o teclado virtual ao exibir o dialogo
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );
        getDialog().setTitle(R.string.acao_novo);

        return layout;
    }

    public void abrirDialogo(FragmentManager frm) {
        if (frm.findFragmentByTag(DIALOG_TAG) == null) {
            show(frm, DIALOG_TAG);
        }
    }

    /**
     * Trata os botões de ação do fragment
     *
     * @param v        -> acessa os campos de cadastro para instaciar o obj Hotel
     * @param actionId
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            Activity activity = getActivity();
            if (activity instanceof AoSalvarHotel) {
                if (mHotel == null) {
                    mHotel = new Hotel(
                            mEditTextNome.getText().toString(),
                            mEditTextEndereco.getText().toString(),
                            mRatingBarEstrelas.getRating());
                } else {
                    mHotel.setNome(mEditTextNome.getText().toString());
                    mHotel.setEndereco(mEditTextEndereco.getText().toString());
                    mRatingBarEstrelas.setRating(mRatingBarEstrelas.getRating());
                }
                AoSalvarHotel listener = (AoSalvarHotel) activity;
                listener.salvouHotel(mHotel);
                dismiss();
            }
            return true;
        }
        return false;
    }

    public interface AoSalvarHotel{
        void salvouHotel(Hotel hotel);
    }
}

