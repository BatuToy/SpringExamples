import java.math.BigDecimal;
import java.util.HashMap;

public class StrategyExample {
 public static void main(String[] args) {
     ShoppingCart cart = new ShoppingCart();
     HashMap <String, BigDecimal> map = new HashMap <String, BigDecimal>();
     map.put("CreditCart", val_1);

     cart.setPaymentStrategy(new CreditCartPayment());
     BigDecimal val_1 = cart.checkout(BigDecimal.valueOf(200));

     cart.setPaymentStrategy(new PaypalPayment());
     BigDecimal val_2 = cart.checkout(BigDecimal.valueOf(100));

     return 
     
    }
}