package orderClass;

public class Order {

    private int lineNumber;
    private int orderItemId;
    private int orderId;
    private String buyerName;
    private String buyerEmail;
    private String address;
    private int postcode;
    private double salePrice;
    private double shippingPrice;
    private int sku;
    private String orderDate;
    private String errorMessage = " ";
    private String onStock;

    public Order(int lineNumber, int orderItemId, int orderId, String buyerName,
            String buyerEmail, String address, int postcode, double salePrice,
            double shippingPrice, int sku, String orderDate, String onStock,
            String errorMessage) {

        this.lineNumber = lineNumber;
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.address = address;
        this.postcode = postcode;
        this.salePrice = Math.round(salePrice * 100.00) / 100.00;
        this.shippingPrice = Math.round(shippingPrice * 100.00) / 100.00;
        this.sku = sku;
        this.orderDate = orderDate;
        this.onStock = onStock;
        this.errorMessage = errorMessage;

    }

    public double totalItemPrice() {
        return getSalePrice() + getShippingPrice();
    }

    public String geterrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public double getSalePrice() {

        return salePrice;
    }

    public double getShippingPrice() {
        return shippingPrice;
    }

    public int getSku() {
        return sku;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public String getAddress() {
        return address;
    }

    public int getPostcode() {
        return postcode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getOnStock() {
        return onStock;
    }

}
