package lk.ijse.Jayalath_Smart_Pharma.constant;

public class ResponseMessage {
    // Generic Messages
    public static final String SUCCESS_MESSAGE = "Operation Successful";
    public static final String FAIL_MESSAGE = "Operation Unsuccessful";
    public static final String NOT_FOUND = "Requested Resource Not Found";

    // Auth Messages
    public static final String AUTH_SUCCESS = "User Authenticated Successfully";
    public static final String AUTH_FAILED = "Invalid Email or Password";

    // POS & Stock Messages
    public static final String SALE_COMPLETED = "Sale Transaction Completed Successfully";
    public static final String STOCK_INSUFFICIENT = "Insufficient Stock Available";

    // Spring Mail & PO Messages
    public static final String PO_EMAIL_SENT = "Purchase Order Email Dispatched to Supplier Successfully";

    // AI Messages
    public static final String AI_DISCOUNT_APPLIED = "AI Dynamic Discount Applied Successfully";
}
