import java.math.BigDecimal;

public class CreditCartPayment implements PaymentStrategy{

 @Override
 public BigDecimal pay(BigDecimal amount) {
  System.out.println(amount + " paid using Paypal");
  System.out.println("Transaction fee: " + amount.multiply(new BigDecimal(0.03)));
  System.out.println("Total amount: " + amount.add(amount.multiply(new BigDecimal(0.03))));
  return amount.add(amount.multiply(new BigDecimal(0.03)));
 }
}