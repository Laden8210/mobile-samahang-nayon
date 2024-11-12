package nasaph8210.samahangnayon.fragment.bottomsheet;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import nasaph8210.samahangnayon.R;

public class FilterFragment extends BottomSheetDialogFragment {


    private OnFilterDismissListener listener;
    private Button btnApply;


    private DatePicker datePickerRange;
    public interface OnFilterDismissListener {
        void onFilterDismiss(String startDate, String endDate, String adult, String child);
    }

    public void setOnFilterDismissListener(OnFilterDismissListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bs_filter, container, false);
        btnApply = view.findViewById(R.id.btnSelectDate);

        datePickerRange = view.findViewById(R.id.datePicker);
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select Date");
        MaterialDatePicker materialDatePicker = builder.build();
        btnApply.setOnClickListener(v -> {
            materialDatePicker.show(getParentFragmentManager(), "DATE_PICKER");
        });

        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
//        if (listener != null) {
//            listener.onFilterDismiss(
//
//            );
//        }
    }
}


