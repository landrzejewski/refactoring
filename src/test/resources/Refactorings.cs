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

/*
 * SOLUTION: Strategy Pattern (GoF Behavioral)
 * - Defines family of algorithms, encapsulates each one, makes them interchangeable
 * - Strategy lets algorithm vary independently from clients that use it
 */
public interface ICapitalStrategy
{
    double CalculateCapital(Loan loan);
}

public class TermLoanStrategy : ICapitalStrategy
{
    public double CalculateCapital(Loan loan)
    {
        return loan.Notional * loan.Duration() * loan.RiskFactor();
    }
}

public class RevolverStrategy : ICapitalStrategy
{
    public double CalculateCapital(Loan loan)
    {
        return (loan.Notional * 0.5 + loan.Outstanding)
               * loan.Duration() * loan.RiskFactor();
    }
}

public class AdvisedLineStrategy : ICapitalStrategy
{
    public double CalculateCapital(Loan loan)
    {
        return loan.Notional * 0.3 * loan.Duration() * loan.RiskFactor();
    }
}

public class Loan
{
    private readonly ICapitalStrategy _strategy;

    public double Notional { get; }
    public double Outstanding { get; }
    private int Rating { get; }

    public Loan(ICapitalStrategy strategy, double notional, double outstanding, int rating)
    {
        _strategy = strategy;
        Notional = notional;
        Outstanding = outstanding;
        Rating = rating;
    }

    public double CalculateCapital()
    {
        return _strategy.CalculateCapital(this);
    }

    public double Duration() => 1.5;
    public double RiskFactor() => Rating * 0.01;
}

// ============================================================================
// EXAMPLE 2: Extract Adapter (Structural - GoF)
// ============================================================================

/*
 * PROBLEM: Incompatible interfaces force ugly code
 * - Client expects one interface but third-party library provides another
 * - Conversion logic scattered throughout client code
 * - Hard to switch to different library
 * - Code duplication when multiple clients need same conversion
 */
public class BadReportGenerator
{
    public void GenerateReport(List<string[]> data)
    {
        var pdfLib = new ThirdPartyPdfLib();

        // Ugly conversion code scattered in client
        var pdfRows = new List<PdfRow>();
        foreach (var row in data)
        {
            var pdfRow = new PdfRow();
            foreach (var cell in row)
            {
                pdfRow.AddCell(new PdfCell(cell));
            }
            pdfRows.Add(pdfRow);
        }

        pdfLib.CreateDocument(pdfRows);
        pdfLib.Save("report.pdf");
    }
}

// Third-party library with incompatible interface
public class ThirdPartyPdfLib
{
    public void CreateDocument(List<PdfRow> rows)
    {
        Console.WriteLine($"Creating PDF with {rows.Count} rows");
    }

    public void Save(string filename)
    {
        Console.WriteLine($"Saving to {filename}");
    }
}

public class PdfRow
{
    private readonly List<PdfCell> _cells = new();
    public void AddCell(PdfCell cell) => _cells.Add(cell);
    public List<PdfCell> GetCells() => _cells;
}

public class PdfCell
{
    public string Content { get; }
    public PdfCell(string content) => Content = content;
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