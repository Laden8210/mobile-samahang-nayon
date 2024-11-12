package nasaph8210.samahangnayon.fragment.bottomsheet;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import nasaph8210.samahangnayon.R;

public class FilterFragmentGuest extends BottomSheetDialogFragment {

  private TextInputLayout adultEditText, childEditText;
    private OnFilterDismissListener listener;
    private Button btnApply;

    public interface OnFilterDismissListener {
        void onFilterDismiss( String adult, String child);
    }

    public void setOnFilterDismissListener(OnFilterDismissListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bs_filter_guest, container, false);
        btnApply = view.findViewById(R.id.btnConfirmGuest);
        adultEditText = view.findViewById(R.id.textInputLayout);
        childEditText = view.findViewById(R.id.childrens);

        childEditText.getEditText().setText("0");
        adultEditText.getEditText().setText("1");

        btnApply.setOnClickListener(v -> {
            dismiss();
        });

        return view;
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null) {


            String adult = adultEditText.getEditText().getText().toString();
            String child = childEditText.getEditText().getText().toString();

            if (adult.isEmpty()) {
                adult = "1";
            }
            if (child.isEmpty()) {
                child = "0";
            }


            listener.onFilterDismiss(

                    adultEditText != null ? adultEditText.getEditText().getText().toString() : "1",
                    childEditText != null ? childEditText.getEditText().getText().toString() : "0"
            );
        }
    }
}


