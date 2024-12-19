import java.math.BigDecimal;

public class PaypalPayment implements PaymentStrategy{

 @Override
 public BigDecimal pay(BigDecimal amount) {
  System.out.println(amount + " paid using Paypal");
  System.out.println("Transaction fee: " + amount.multiply(new BigDecimal(0.02)));
  System.out.println("Total amount: " + amount.add(amount.multiply(new BigDecimal(0.02))));
  return amount.add(amount.multiply(new BigDecimal(0.02)));
 }
}