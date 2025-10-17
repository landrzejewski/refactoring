// ============================================================================
// EXAMPLE 1: Replace Conditional with Strategy Pattern (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Multiple conditionals scattered throughout the code
 * - Hard to add new loan types (violates Open/Closed Principle)
 * - Risk calculation logic is duplicated and hard to maintain
 * - Testing requires checking all conditional branches
 * - Code becomes longer and more complex with each new type
 */
public class BadLoan
{
    private const int TERM_LOAN = 1;
    private const int REVOLVER = 2;
    private const int ADVISED_LINE = 3;

    private int _type; // Type code - a code smell!
    private double _notional;
    private double _outstanding;
    private int _rating;

    // This method will grow with every new loan type
    public double CalculateCapital()
    {
        if (_type == TERM_LOAN)
        {
            return _notional * Duration() * RiskFactor();
        }
        else if (_type == REVOLVER)
        {
            return (_notional * 0.5 + _outstanding) * Duration() * RiskFactor();
        }
        else if (_type == ADVISED_LINE)
        {
            return _notional * 0.3 * Duration() * RiskFactor();
        }
        return 0;
    }

    private double Duration() => 1.5;
    private double RiskFactor() => _rating * 0.01;
}


// ============================================================================
// Supporting Classes
// ============================================================================

public class Product
{
    public string Name { get; set; }
    public double Price { get; set; }
    private int Stock { get; set; }

    public void DecreaseStock(int quantity)
    {
        Stock -= quantity;
    }
}

public class OrderItem
{
    public Product Product { get; set; }
    public int Quantity { get; set; }
    public double Price => Product.Price;
}

public class Order
{
    public List<OrderItem> Items { get; } = new();
    public Customer Customer { get; set; }
    public string PaymentMethod { get; set; }
}

public static class PaymentProcessorFactory
{
    public static IPaymentProcessor Create(string method)
    {
        return new PaymentProcessor();
    }
}

public interface IPaymentProcessor
{
    void Process(double amount);
}

public class PaymentProcessor : IPaymentProcessor
{
    public void Process(double amount)
    {
        Console.WriteLine($"Processing payment: ${amount}");
    }
}