package co.evertonnrb.findhoteis.fragaments;/*
    @author everton.nrb@gmail.com
*/

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import co.evertonnrb.findhoteis.R;

public class SobreDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        DialogInterface.OnClickListener listener = (dialog, which) -> {
            if (which == DialogInterface.BUTTON_NEGATIVE){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Evertonnrb"));
                startActivity(intent);
            }
        };
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.sobre_titulo)
                .setMessage(R.string.sobre_mensagem)
                .setPositiveButton(android.R.string.ok,null)
                .setNegativeButton(R.string.sobre_desenvolvedor,listener)
                .create();
        return dialog;
    }
}
