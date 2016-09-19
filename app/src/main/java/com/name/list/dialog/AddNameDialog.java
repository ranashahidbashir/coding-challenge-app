package com.name.list.dialog;


import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.name.list.R;
import com.name.list.util.Name;

/**
 * Created by Rana Shahid Bashir on 9/18/2016.
 */
public class AddNameDialog extends DialogFragment {


    Button btn_add;
    EditText et_first_name, et_last_name;
    public onSubmitListener mListener;


    public interface onSubmitListener {
        void setOnSubmitListener(Name name);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.add_name_dialog);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        btn_add = (Button) dialog.findViewById(R.id.btn_add);
        et_first_name = (EditText) dialog.findViewById(R.id.et_first_name);
        et_last_name = (EditText) dialog.findViewById(R.id.et_last_name);
        // et_last_name.setText(text);
        btn_add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.setOnSubmitListener(new Name(et_first_name.getText().toString(), et_last_name.getText().toString()));
                dismiss();
            }
        });
        return dialog;
    }
}

