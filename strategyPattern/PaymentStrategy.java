import java.math.BigDecimal;

public interface PaymentStrategy {
    BigDecimal pay(BigDecimal amount);
}