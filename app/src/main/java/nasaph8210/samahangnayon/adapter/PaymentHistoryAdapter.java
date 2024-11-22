package nasaph8210.samahangnayon.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.model.Payment;
import nasaph8210.samahangnayon.view.PaymentHistoryActivity;
public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.PaymentViewHolder> {

    public static final int UPLOAD_PROOF_REQUEST_CODE =10002 ;
    private List<Payment> paymentList;
    private Context context;
    private OnProofUploadListener proofUploadListener;

    // Constructor with listener
    public PaymentHistoryAdapter(List<Payment> paymentList, OnProofUploadListener proofUploadListener) {
        this.paymentList = paymentList;
        this.proofUploadListener = proofUploadListener;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_history, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment payment = paymentList.get(position);
        holder.tvPaymentAmount.setText("Amount Paid: ₱" + payment.getAmountPaid());
        holder.tvPaymentDate.setText("Date: " + payment.getDateCreated());
        holder.tvPaymentType.setText("Payment Type: " + payment.getPaymentType());
        holder.tvPaymentStatus.setText("Status: " + payment.getStatus());
        holder.tvPaymentReference.setText("Reference #: " + payment.getReferenceNumber());
        holder.tvPaymentPurpose.setText("Purpose: " + payment.getPurpose());

        // Handle Upload Proof button click
        holder.btnUploadProof.setOnClickListener(v -> {
            if (proofUploadListener != null) {
                proofUploadListener.onUploadProofClicked(payment.getPaymentId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        TextView tvPaymentAmount, tvPaymentDate, tvPaymentType, tvPaymentStatus, tvPaymentReference, tvPaymentPurpose;
        Button btnUploadProof;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPaymentAmount = itemView.findViewById(R.id.tvPaymentAmount);
            tvPaymentDate = itemView.findViewById(R.id.tvPaymentDateTime);
            tvPaymentType = itemView.findViewById(R.id.tvPaymentType);
            tvPaymentStatus = itemView.findViewById(R.id.tvPaymentStatus);
            tvPaymentReference = itemView.findViewById(R.id.tvPaymentReference);
            tvPaymentPurpose = itemView.findViewById(R.id.tvPaymentPurpose);
            btnUploadProof = itemView.findViewById(R.id.btnUploadProof);
        }
    }

    // Callback interface
    public interface OnProofUploadListener {
        void onUploadProofClicked(int reservationId);
    }
}

