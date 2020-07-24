package Model;

import Server.DataCenter;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class Receipt {
    private static ArrayList<Receipt> receipts = new ArrayList<>();
    private final String receiptType;
    private final double money;
    private final long sourceId;
    private final long destId;
    private final String description;
    private final int id;
    private boolean paid = false;

    public Receipt(String receiptType, double money, long sourceId, long destId, String description) {
        this.receiptType = receiptType;
        this.money = money;
        this.sourceId = sourceId;
        this.destId = destId;
        this.description = description;
        this.id = receipts.size();

    }

    public static Receipt add(Receipt receipt) {
        if (!receipts.contains(receipt))
            receipts.add(receipt);
        return receipt;
    }


    public static @NotNull ArrayList<Receipt> getReceipts(AuthToken authToken) {
        ArrayList<Receipt> var10 = new ArrayList<>();
        for (Receipt receipt : receipts) {
            if (receipt.sourceId == authToken.getAccount().getAccountNumber() || receipt.destId == authToken.getAccount().getAccountNumber())
                var10.add(receipt);
        }
        return var10;
    }

    public static void setReceipts(ArrayList<Receipt> receipts) {
        Receipt.receipts = receipts;
    }

    public static @NotNull Receipt getReceipt(int id) throws Exception {
        for (Receipt receipt : receipts) {
            if (receipt.getId() == id)
                return receipt;
        }
        throw new Exception("invalid receipt id");
    }

    public static @NotNull ArrayList<Receipt> getDestReceipts(AuthToken var10) {
        ArrayList<Receipt> var100 = new ArrayList<>();
        for (Receipt receipt : receipts) {
            if (receipt.destId == var10.getAccount().getAccountNumber())
                var100.add(receipt);
        }
        return var100;
    }

    public static @NotNull ArrayList<Receipt> getSourceReceipts(AuthToken var10) {
        ArrayList<Receipt> var100 = new ArrayList<>();
        for (Receipt receipt : receipts) {
            if (receipt.sourceId == var10.getAccount().getAccountNumber())
                var100.add(receipt);
        }

        return var100;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return Double.compare(receipt.money, money) == 0 &&
                sourceId == receipt.sourceId &&
                destId == receipt.destId &&
                paid == receipt.paid &&
                id == receipt.id &&
                Objects.equals(receiptType, receipt.receiptType) &&
                Objects.equals(description, receipt.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiptType, money, sourceId, destId, description, paid, id);
    }

    public double getMoney() {
        return money;
    }

    public long getSourceId() {
        return sourceId;
    }

    public long getDestId() {
        return destId;
    }



    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
        DataCenter.save(this);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
