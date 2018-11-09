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

    private enum status {
        IN_STOCK, OUT_OF_STOCK
    };    // http://tutorials.jenkov.com/java/enums.html  // https://www.avajava.com/tutorials/lessons/how-do-i-use-the-enum-type-with-a-constructor.html
    private String orderDate;

    public Order(int lineNumber, int orderItemId, int orderId, String buyerName, String buyerEmail, String address, int postcode, double salePrice, double shippingPrice, int sku, String orderDate) {
        this.lineNumber = lineNumber;
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.address = address;
        this.postcode = postcode;
        this.salePrice = salePrice;
        this.shippingPrice = shippingPrice;
        this.sku = sku;
        this.orderDate = orderDate;
    }

    public double totalItemPrice() {
        return getSalePrice() + getShippingPrice();

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

    @Override
    public String toString() {
        return "Order{" + "lineNumber=" + lineNumber + ", orderItemId=" + orderItemId + ", orderId=" + orderId + ", buyerName=" + buyerName + ", buyerEmail=" + buyerEmail + ", address=" + address + ", postcode=" + postcode + ", salePrice=" + salePrice + ", shippingPrice=" + shippingPrice + ", sku=" + sku + ", orderDate=" + orderDate + '}';
    }

}
