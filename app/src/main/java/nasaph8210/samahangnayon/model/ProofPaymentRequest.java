package nasaph8210.samahangnayon.model;

public class ProofPaymentRequest {
    private int payment_id;
    private String proof_image;
    private String message;

    public ProofPaymentRequest(int payment_id, String proof_image) {
        this.payment_id = payment_id;
        this.proof_image = proof_image;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getProof_image() {
        return proof_image;
    }

    public void setProof_image(String proof_image) {
        this.proof_image = proof_image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
