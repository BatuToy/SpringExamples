import java.math.BigDecimal;

public class ShoppingCart {
 private PaymentStrategy paymentStrategy;

 public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
  this.paymentStrategy = paymentStrategy;
 }

 public BigDecimal checkout(BigDecimal amount) {
  return paymentStrategy.pay(amount);
 }
}