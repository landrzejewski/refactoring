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

/*
 * SOLUTION: Adapter Pattern (GoF Structural)
 * - Converts interface of a class into another interface clients expect
 * - Lets classes work together that couldn't otherwise
 * - Encapsulates conversion logic in one place
 */

// Target interface - what our client expects
public interface IReportWriter
{
    void Write(List<string[]> data);
    void Save(string filename);
}

// Adapter - bridges the gap between interfaces
public class PdfReportAdapter : IReportWriter
{
    private readonly ThirdPartyPdfLib _pdfLib;

    public PdfReportAdapter()
    {
        _pdfLib = new ThirdPartyPdfLib();
    }

    public void Write(List<string[]> data)
    {
        var pdfRows = ConvertToPdfRows(data);
        _pdfLib.CreateDocument(pdfRows);
    }

    public void Save(string filename)
    {
        _pdfLib.Save(filename);
    }

    private List<PdfRow> ConvertToPdfRows(List<string[]> data)
    {
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
        return pdfRows;
    }
}

// Now client code is clean
public class ReportGenerator
{
    private readonly IReportWriter _writer;

    public ReportGenerator(IReportWriter writer)
    {
        _writer = writer;
    }

    public void GenerateReport(List<string[]> data)
    {
        _writer.Write(data);
        _writer.Save("report.pdf");
    }
}

// ============================================================================
// EXAMPLE 3: Introduce Template Method (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Similar algorithms with duplicated structure
 * - Copy-paste programming leads to duplication
 * - Hard to maintain - changes needed in multiple places
 * - Algorithm structure not explicit
 */
public class BadCsvDataExporter
{
    public void ExportCustomers(List<Customer> customers)
    {
        Console.WriteLine("Opening customers.csv");
        Console.WriteLine("Name,Email,VIP");

        foreach (var c in customers)
        {
            Console.WriteLine($"{c.Name},{c.Email},{c.IsVIP}");
        }

        Console.WriteLine("File closed");
    }

    public void ExportProducts(List<Product> products)
    {
        // DUPLICATED structure!
        Console.WriteLine("Opening products.csv");
        Console.WriteLine("Name,Price");

        foreach (var p in products)
        {
            Console.WriteLine($"{p.Name},{p.Price}");
        }

        Console.WriteLine("File closed");
    }
}

/*
 * SOLUTION: Template Method Pattern (GoF Behavioral)
 * - Defines skeleton of algorithm in base class
 * - Lets subclasses override specific steps without changing structure
 */
public abstract class DataExporter
{
    // Template method - sealed to prevent override
    public void Export()
    {
        OpenFile();
        WriteHeader();
        WriteData();
        CloseFile();
    }

    private void OpenFile()
    {
        Console.WriteLine($"Opening {GetFileName()}");
    }

    private void CloseFile()
    {
        Console.WriteLine("File closed");
    }

    // Abstract methods - subclasses must implement
    protected abstract void WriteHeader();
    protected abstract void WriteData();
    protected abstract string GetFileName();
}

public class CustomerExporter : DataExporter
{
    private readonly List<Customer> _customers;

    public CustomerExporter(List<Customer> customers)
    {
        _customers = customers;
    }

    protected override void WriteHeader()
    {
        Console.WriteLine("Name,Email,VIP");
    }

    protected override void WriteData()
    {
        foreach (var c in _customers)
        {
            Console.WriteLine($"{c.Name},{c.Email},{c.IsVIP}");
        }
    }

    protected override string GetFileName() => "customers.csv";
}

public class ProductExporter : DataExporter
{
    private readonly List<Product> _products;

    public ProductExporter(List<Product> products)
    {
        _products = products;
    }

    protected override void WriteHeader()
    {
        Console.WriteLine("Name,Price");
    }

    protected override void WriteData()
    {
        foreach (var p in _products)
        {
            Console.WriteLine($"{p.Name},{p.Price}");
        }
    }

    protected override string GetFileName() => "products.csv";
}

// ============================================================================
// EXAMPLE 4: Replace Constructors with Builder (Creational - GoF)
// ============================================================================

/*
 * PROBLEM: Complex object construction with many parameters
 * - Telescoping constructors (multiple overloaded constructors)
 * - Hard to remember parameter order
 * - Cannot represent optional parameters well
 */
public class BadHttpRequest
{
    public string Url { get; }
    public string Method { get; }
    public string Body { get; }
    public Dictionary<string, string> Headers { get; }
    public int Timeout { get; }
    public bool FollowRedirects { get; }

    // Telescoping constructors - nightmare!
    public BadHttpRequest(string url)
        : this(url, "GET") { }

    public BadHttpRequest(string url, string method)
        : this(url, method, null) { }

    public BadHttpRequest(string url, string method, string body)
        : this(url, method, body, new Dictionary<string, string>()) { }

    public BadHttpRequest(string url, string method, string body,
                          Dictionary<string, string> headers)
        : this(url, method, body, headers, 30000) { }

    public BadHttpRequest(string url, string method, string body,
                          Dictionary<string, string> headers, int timeout)
        : this(url, method, body, headers, timeout, true) { }

    public BadHttpRequest(string url, string method, string body,
                          Dictionary<string, string> headers,
                          int timeout, bool followRedirects)
    {
        Url = url;
        Method = method;
        Body = body;
        Headers = headers;
        Timeout = timeout;
        FollowRedirects = followRedirects;
    }
}

/*
 * SOLUTION: Builder Pattern (GoF Creational)
 * - Separates construction of complex object from its representation
 * - Provides fluent, readable API
 * - Easy to add new optional parameters
 */
public class HttpRequest
{
    public string Url { get; }
    public string Method { get; }
    public string Body { get; }
    public IReadOnlyDictionary<string, string> Headers { get; }
    public int Timeout { get; }
    public bool FollowRedirects { get; }

    private HttpRequest(Builder builder)
    {
        Url = builder.Url;
        Method = builder.Method;
        Body = builder.Body;
        Headers = new Dictionary<string, string>(builder.Headers);
        Timeout = builder.Timeout;
        FollowRedirects = builder.FollowRedirects;
    }

    public class Builder
    {
        // Required parameter
        public string Url { get; }

        // Optional parameters with defaults
        public string Method { get; private set; } = "GET";
        public string Body { get; private set; }
        public Dictionary<string, string> Headers { get; } = new();
        public int Timeout { get; private set; } = 30000;
        public bool FollowRedirects { get; private set; } = true;

        public Builder(string url)
        {
            Url = url;
        }

        // Fluent interface
        public Builder WithMethod(string method)
        {
            Method = method;
            return this;
        }

        public Builder WithBody(string body)
        {
            Body = body;
            return this;
        }

        public Builder WithHeader(string key, string value)
        {
            Headers[key] = value;
            return this;
        }

        public Builder WithTimeout(int timeout)
        {
            Timeout = timeout;
            return this;
        }

        public Builder WithFollowRedirects(bool follow)
        {
            FollowRedirects = follow;
            return this;
        }

        public HttpRequest Build()
        {
            if (string.IsNullOrEmpty(Url))
                throw new InvalidOperationException("URL is required");

            return new HttpRequest(this);
        }
    }
}

// Usage:
// var request = new HttpRequest.Builder("http://api.com")
//     .WithMethod("POST")
//     .WithBody("{\"name\":\"John\"}")
//     .WithHeader("Content-Type", "application/json")
//     .WithTimeout(5000)
//     .Build();

// ============================================================================
// EXAMPLE 5: Introduce Composite (Structural - GoF)
// ============================================================================

/*
 * PROBLEM: Need to treat individual objects and compositions uniformly
 * - Client must know if dealing with single item or collection
 * - Lots of type checking and casting
 */
public class BadFileSystem
{
    public long GetSize(object item)
    {
        if (item is File file)
        {
            return file.Size;
        }
        else if (item is Directory dir)
        {
            long total = 0;
            foreach (var child in dir.Children)
            {
                total += GetSize(child);
            }
            return total;
        }
        return 0;
    }
}

public class File
{
    public string Name { get; }
    public long Size { get; }

    public File(string name, long size)
    {
        Name = name;
        Size = size;
    }
}

public class Directory
{
    public string Name { get; }
    public List<object> Children { get; } = new();

    public Directory(string name)
    {
        Name = name;
    }
}

/*
 * SOLUTION: Composite Pattern (GoF Structural)
 * - Compose objects into tree structures
 * - Lets clients treat individual objects and compositions uniformly
 */
public interface IFileSystemComponent
{
    string Name { get; }
    long GetSize();
    void Display(int indent);
}

// Leaf - represents end objects
public class FileComponent : IFileSystemComponent
{
    public string Name { get; }
    private long Size { get; }

    public FileComponent(string name, long size)
    {
        Name = name;
        Size = size;
    }

    public long GetSize() => Size;

    public void Display(int indent)
    {
        Console.WriteLine($"{new string(' ', indent)}{Name} ({Size} bytes)");
    }
}

// Composite - can contain other components
public class DirectoryComponent : IFileSystemComponent
{
    public string Name { get; }
    private readonly List<IFileSystemComponent> _children = new();

    public DirectoryComponent(string name)
    {
        Name = name;
    }

    public long GetSize()
    {
        return _children.Sum(child => child.GetSize());
    }

    public void Display(int indent)
    {
        Console.WriteLine($"{new string(' ', indent)}{Name}/ ({GetSize()} bytes)");
        foreach (var child in _children)
        {
            child.Display(indent + 2);
        }
    }

    public void Add(IFileSystemComponent component)
    {
        _children.Add(component);
    }

    public void Remove(IFileSystemComponent component)
    {
        _children.Remove(component);
    }
}

// ============================================================================
// EXAMPLE 6: Replace Hard-Coded Notifications with Observer (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Tight coupling between subject and observers
 * - Subject knows about concrete observer classes
 * - Hard to add new observers
 * - Changes to observers require changing subject
 */
public class BadStockTicker
{
    private double _price;
    private readonly StockDisplay _display;
    private readonly StockLogger _logger;

    public BadStockTicker(StockDisplay display, StockLogger logger)
    {
        _display = display;
        _logger = logger;
    }

    public void SetPrice(double price)
    {
        _price = price;
        // Must manually notify each observer
        _display.Update(price);
        _logger.Update(price);
        // Adding new observer requires modifying this!
    }
}

public class StockDisplay
{
    public void Update(double price)
    {
        Console.WriteLine($"Display: ${price}");
    }
}

public class StockLogger
{
    public void Update(double price)
    {
        Console.WriteLine($"Log: Price changed to ${price}");
    }
}

/*
 * SOLUTION: Observer Pattern (GoF Behavioral)
 * - Define one-to-many dependency between objects
 * - When one object changes state, all dependents notified automatically
 * - Loose coupling between subject and observers
 */
public interface IStockObserver
{
    void Update(double price);
}

public class StockTicker
{
    private double _price;
    private readonly List<IStockObserver> _observers = new();

    public void Attach(IStockObserver observer)
    {
        _observers.Add(observer);
    }

    public void Detach(IStockObserver observer)
    {
        _observers.Remove(observer);
    }

    public void SetPrice(double price)
    {
        _price = price;
        NotifyObservers();
    }

    private void NotifyObservers()
    {
        foreach (var observer in _observers)
        {
            observer.Update(_price);
        }
    }
}

public class DisplayObserver : IStockObserver
{
    public void Update(double price)
    {
        Console.WriteLine($"Display: ${price}");
    }
}

public class LoggerObserver : IStockObserver
{
    public void Update(double price)
    {
        Console.WriteLine($"Log: Price changed to ${price}");
    }
}

public class AlertObserver : IStockObserver
{
    private readonly double _threshold;

    public AlertObserver(double threshold)
    {
        _threshold = threshold;
    }

    public void Update(double price)
    {
        if (price > _threshold)
        {
            Console.WriteLine($"Alert: Price exceeded ${_threshold}!");
        }
    }
}

// ============================================================================
// EXAMPLE 7: Replace Conditional with Decorator (Structural - GoF)
// ============================================================================

/*
 * PROBLEM: Adding functionality through inheritance or conditionals
 * - Explosion of subclasses for every combination
 * - Or complex conditionals checking feature flags
 */
public class BadCoffee
{
    private bool _hasMilk;
    private bool _hasSugar;
    private bool _hasWhip;

    public double GetCost()
    {
        double cost = 5.0;
        if (_hasMilk) cost += 1.0;
        if (_hasSugar) cost += 0.5;
        if (_hasWhip) cost += 1.5;
        return cost;
    }

    public string GetDescription()
    {
        string desc = "Coffee";
        if (_hasMilk) desc += " with milk";
        if (_hasSugar) desc += " and sugar";
        if (_hasWhip) desc += " and whip";
        return desc;
    }

    public void SetMilk(bool hasMilk) => _hasMilk = hasMilk;
    public void SetSugar(bool hasSugar) => _hasSugar = hasSugar;
    public void SetWhip(bool hasWhip) => _hasWhip = hasWhip;
}

/*
 * SOLUTION: Decorator Pattern (GoF Structural)
 * - Attach additional responsibilities to object dynamically
 * - Flexible alternative to subclassing
 * - Can combine any decorators in any order
 */
public interface ICoffee
{
    double GetCost();
    string GetDescription();
}

public class BasicCoffee : ICoffee
{
    public double GetCost() => 5.0;
    public string GetDescription() => "Coffee";
}

public abstract class CoffeeDecorator : ICoffee
{
    protected readonly ICoffee _decoratedCoffee;

    protected CoffeeDecorator(ICoffee coffee)
    {
        _decoratedCoffee = coffee;
    }

    public virtual double GetCost() => _decoratedCoffee.GetCost();
    public virtual string GetDescription() => _decoratedCoffee.GetDescription();
}

public class MilkDecorator : CoffeeDecorator
{
    public MilkDecorator(ICoffee coffee) : base(coffee) { }

    public override double GetCost() => base.GetCost() + 1.0;
    public override string GetDescription() => base.GetDescription() + ", milk";
}

public class SugarDecorator : CoffeeDecorator
{
    public SugarDecorator(ICoffee coffee) : base(coffee) { }

    public override double GetCost() => base.GetCost() + 0.5;
    public override string GetDescription() => base.GetDescription() + ", sugar";
}

public class WhipDecorator : CoffeeDecorator
{
    public WhipDecorator(ICoffee coffee) : base(coffee) { }

    public override double GetCost() => base.GetCost() + 1.5;
    public override string GetDescription() => base.GetDescription() + ", whipped cream";
}

// Usage:
// ICoffee coffee = new WhipDecorator(
//     new SugarDecorator(
//         new MilkDecorator(new BasicCoffee())
//     )
// );

// ============================================================================
// EXAMPLE 8: Replace Type Code with State Pattern (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: State management with string type codes
 * - String comparisons are error-prone
 * - State transition logic scattered
 * - No compile-time checking
 */
public class BadSystemPermission
{
    private string _state;
    private bool _isGranted;

    public void ClaimedBy(string userName)
    {
        if (_state == "REQUESTED")
        {
            _state = "CLAIMED";
            _isGranted = false;
        }
    }

    public void DeniedBy(string userName)
    {
        if (_state == "CLAIMED")
        {
            _state = "DENIED";
            _isGranted = false;
        }
    }

    public void GrantedBy(string userName)
    {
        if (_state == "CLAIMED" && !_isGranted)
        {
            _state = "GRANTED";
            _isGranted = true;
        }
    }
}

/*
 * SOLUTION: State Pattern (GoF Behavioral)
 * - Each state is a class that knows its valid transitions
 * - Compile-time type safety
 * - State machine becomes explicit
 */
public interface IPermissionState
{
    void ClaimedBy(SystemPermission permission, string userName);
    void DeniedBy(SystemPermission permission, string userName);
    void GrantedBy(SystemPermission permission, string userName);
}

public class RequestedState : IPermissionState
{
    public void ClaimedBy(SystemPermission permission, string userName)
    {
        permission.SetState(new ClaimedState());
    }

    public void DeniedBy(SystemPermission permission, string userName) { }
    public void GrantedBy(SystemPermission permission, string userName) { }
}

public class ClaimedState : IPermissionState
{
    public void ClaimedBy(SystemPermission permission, string userName) { }

    public void DeniedBy(SystemPermission permission, string userName)
    {
        permission.SetState(new DeniedState());
    }

    public void GrantedBy(SystemPermission permission, string userName)
    {
        permission.SetState(new GrantedState());
    }
}

public class DeniedState : IPermissionState
{
    public void ClaimedBy(SystemPermission permission, string userName) { }
    public void DeniedBy(SystemPermission permission, string userName) { }
    public void GrantedBy(SystemPermission permission, string userName) { }
}

public class GrantedState : IPermissionState
{
    public void ClaimedBy(SystemPermission permission, string userName) { }
    public void DeniedBy(SystemPermission permission, string userName) { }
    public void GrantedBy(SystemPermission permission, string userName) { }
}

public class SystemPermission
{
    private IPermissionState _state;

    public SystemPermission()
    {
        _state = new RequestedState();
    }

    internal void SetState(IPermissionState state)
    {
        _state = state;
    }

    public void ClaimedBy(string userName)
    {
        _state.ClaimedBy(this, userName);
    }

    public void DeniedBy(string userName)
    {
        _state.DeniedBy(this, userName);
    }

    public void GrantedBy(string userName)
    {
        _state.GrantedBy(this, userName);
    }
}

// ============================================================================
// EXAMPLE 9: Extract Proxy for Lazy Loading (Structural - GoF)
// ============================================================================

/*
 * PROBLEM: Expensive objects loaded eagerly
 * - Large images loaded even if never displayed
 * - Wastes memory and time
 */
public class BadImageViewer
{
    private readonly List<HeavyImage> _images = new();

    public void LoadImages(string[] filenames)
    {
        foreach (var filename in filenames)
        {
            _images.Add(new HeavyImage(filename));
        }
        Console.WriteLine($"Loaded {_images.Count} images");
    }

    public void Display(int index)
    {
        _images[index].Display();
    }
}

public class HeavyImage
{
    private readonly string _filename;
    private byte[] _data;

    public HeavyImage(string filename)
    {
        _filename = filename;
        LoadFromDisk();
    }

    private void LoadFromDisk()
    {
        Console.WriteLine($"Loading large image: {_filename}");
        _data = new byte[1000000];
    }

    public void Display()
    {
        Console.WriteLine($"Displaying: {_filename}");
    }
}

/*
 * SOLUTION: Proxy Pattern (GoF Structural) - Virtual Proxy
 * - Provides placeholder for expensive object
 * - Creates real object only when needed
 */
public interface IImage
{
    void Display();
}

public class RealImage : IImage
{
    private readonly string _filename;
    private byte[] _data;

    public RealImage(string filename)
    {
        _filename = filename;
        LoadFromDisk();
    }

    private void LoadFromDisk()
    {
        Console.WriteLine($"Loading image: {_filename}");
        _data = new byte[1000000];
    }

    public void Display()
    {
        Console.WriteLine($"Displaying: {_filename}");
    }
}

public class ImageProxy : IImage
{
    private readonly string _filename;
    private RealImage _realImage;

    public ImageProxy(string filename)
    {
        _filename = filename;
    }

    public void Display()
    {
        if (_realImage == null)
        {
            _realImage = new RealImage(_filename);
        }
        _realImage.Display();
    }
}

public class ImageViewer
{
    private readonly List<IImage> _images = new();

    public void LoadImages(string[] filenames)
    {
        foreach (var filename in filenames)
        {
            _images.Add(new ImageProxy(filename));
        }
        Console.WriteLine($"Loaded {_images.Count} image proxies");
    }

    public void Display(int index)
    {
        _images[index].Display();
    }
}

// ============================================================================
// EXAMPLE 10: Replace Conditional with Chain of Responsibility (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Complex conditional chain for request handling
 * - Hard to add/remove/reorder handlers
 * - Cannot dynamically configure chain
 */
public class BadSupportSystem
{
    public void HandleRequest(string issue, int priority)
    {
        if (priority <= 1)
        {
            Console.WriteLine($"Junior support handling: {issue}");
        }
        else if (priority <= 3)
        {
            Console.WriteLine($"Senior support handling: {issue}");
        }
        else if (priority <= 5)
        {
            Console.WriteLine($"Manager handling: {issue}");
        }
        else
        {
            Console.WriteLine($"Director handling: {issue}");
        }
    }
}

/*
 * SOLUTION: Chain of Responsibility Pattern (GoF Behavioral)
 * - Chain handlers together
 * - Each handler decides to process or pass to next
 */
public abstract class SupportHandler
{
    protected SupportHandler NextHandler { get; set; }

    public void SetNext(SupportHandler handler)
    {
        NextHandler = handler;
    }

    public void HandleRequest(SupportRequest request)
    {
        if (CanHandle(request))
        {
            Process(request);
        }
        else if (NextHandler != null)
        {
            NextHandler.HandleRequest(request);
        }
        else
        {
            Console.WriteLine($"No handler for: {request.Issue}");
        }
    }

    protected abstract bool CanHandle(SupportRequest request);
    protected abstract void Process(SupportRequest request);
}

public class SupportRequest
{
    public string Issue { get; }
    public int Priority { get; }

    public SupportRequest(string issue, int priority)
    {
        Issue = issue;
        Priority = priority;
    }
}

public class JuniorSupport : SupportHandler
{
    protected override bool CanHandle(SupportRequest request) => request.Priority <= 1;

    protected override void Process(SupportRequest request)
    {
        Console.WriteLine($"Junior Support handling: {request.Issue}");
    }
}

public class SeniorSupport : SupportHandler
{
    protected override bool CanHandle(SupportRequest request) => request.Priority <= 3;

    protected override void Process(SupportRequest request)
    {
        Console.WriteLine($"Senior Support handling: {request.Issue}");
    }
}

public class ManagerSupport : SupportHandler
{
    protected override bool CanHandle(SupportRequest request) => request.Priority <= 5;

    protected override void Process(SupportRequest request)
    {
        Console.WriteLine($"Manager handling: {request.Issue}");
    }
}

public class DirectorSupport : SupportHandler
{
    protected override bool CanHandle(SupportRequest request) => true;

    protected override void Process(SupportRequest request)
    {
        Console.WriteLine($"Director handling critical: {request.Issue}");
    }
}

public class SupportSystem
{
    private readonly SupportHandler _chain;

    public SupportSystem()
    {
        var junior = new JuniorSupport();
        var senior = new SeniorSupport();
        var manager = new ManagerSupport();
        var director = new DirectorSupport();

        junior.SetNext(senior);
        senior.SetNext(manager);
        manager.SetNext(director);

        _chain = junior;
    }

    public void SubmitRequest(string issue, int priority)
    {
        var request = new SupportRequest(issue, priority);
        _chain.HandleRequest(request);
    }
}

// ============================================================================
// EXAMPLE 11: Replace Lazy Initialization with Singleton (Creational - GoF)
// ============================================================================

/*
 * PROBLEM: Global state managed incorrectly
 * - Not thread-safe
 * - Multiple instances possible
 * - No control over instantiation
 */
public class BadDatabaseConnection
{
    private static BadDatabaseConnection _instance;

    // Public constructor - anyone can create instances!
    public BadDatabaseConnection()
    {
        Console.WriteLine("Expensive database connection created");
    }

    // Not thread-safe!
    public static BadDatabaseConnection GetInstance()
    {
        if (_instance == null)
        {
            _instance = new BadDatabaseConnection();
            // Two threads could create two instances!
        }
        return _instance;
    }

    public void Query(string sql)
    {
        Console.WriteLine($"Executing: {sql}");
    }
}

/*
 * SOLUTION: Singleton Pattern (GoF Creational) - Thread-safe Lazy
 * - Ensures class has only one instance
 * - Provides global point of access
 * - Thread-safe lazy initialization
 * - Uses C#'s Lazy<T> for thread safety
 */
public sealed class DatabaseConnection
{
    // Lazy<T> ensures thread-safe lazy initialization
    private static readonly Lazy<DatabaseConnection> _instance =
        new Lazy<DatabaseConnection>(() => new DatabaseConnection());

    // Private constructor prevents instantiation
    private DatabaseConnection()
    {
        Console.WriteLine("Database connection established");
    }

    public static DatabaseConnection Instance => _instance.Value;

    public void Query(string sql)
    {
        Console.WriteLine($"Executing: {sql}");
    }
}

// Alternative: Simple eager initialization (thread-safe)
public sealed class ConfigurationManager
{
    private static readonly ConfigurationManager _instance = new();

    private ConfigurationManager()
    {
        Console.WriteLine("Configuration loaded");
    }

    public static ConfigurationManager Instance => _instance;
}

// ============================================================================
// EXAMPLE 12: Replace Type Code with Factory Method (Creational - GoF)
// ============================================================================

/*
 * PROBLEM: Object creation based on type codes
 * - Creation logic scattered
 * - Type codes are error-prone
 * - Hard to add new types
 */
public class BadAnimalCreator
{
    public const int DOG = 1;
    public const int CAT = 2;
    public const int BIRD = 3;

    public Animal CreateAnimal(int type, string name)
    {
        if (type == DOG)
        {
            var dog = new Animal
            {
                Type = "Dog",
                Name = name,
                Sound = "Woof",
                Legs = 4
            };
            return dog;
        }
        else if (type == CAT)
        {
            var cat = new Animal
            {
                Type = "Cat",
                Name = name,
                Sound = "Meow",
                Legs = 4
            };
            return cat;
        }
        else if (type == BIRD)
        {
            var bird = new Animal
            {
                Type = "Bird",
                Name = name,
                Sound = "Chirp",
                Legs = 2
            };
            return bird;
        }
        return null;
    }
}

public class Animal
{
    public string Type { get; set; }
    public string Name { get; set; }
    public string Sound { get; set; }
    public int Legs { get; set; }

    public void MakeSound()
    {
        Console.WriteLine($"{Name} says {Sound}");
    }
}

/*
 * SOLUTION: Factory Method Pattern (GoF Creational)
 * - Define interface for creating objects
 * - Let subclasses decide which class to instantiate
 * - Encapsulates object creation
 */
public interface IAnimal
{
    void MakeSound();
    string Name { get; }
}

public class Dog : IAnimal
{
    public string Name { get; }

    public Dog(string name)
    {
        Name = name;
    }

    public void MakeSound()
    {
        Console.WriteLine($"{Name} says Woof!");
    }
}

public class Cat : IAnimal
{
    public string Name { get; }

    public Cat(string name)
    {
        Name = name;
    }

    public void MakeSound()
    {
        Console.WriteLine($"{Name} says Meow!");
    }
}

public class Bird : IAnimal
{
    public string Name { get; }

    public Bird(string name)
    {
        Name = name;
    }

    public void MakeSound()
    {
        Console.WriteLine($"{Name} says Chirp!");
    }
}

// Factory Method approach
public abstract class AnimalCreator
{
    protected abstract IAnimal CreateAnimal(string name);

    public IAnimal OrderAnimal(string name)
    {
        var animal = CreateAnimal(name);
        Console.WriteLine($"Created: {animal.Name}");
        return animal;
    }
}

public class DogCreator : AnimalCreator
{
    protected override IAnimal CreateAnimal(string name) => new Dog(name);
}

public class CatCreator : AnimalCreator
{
    protected override IAnimal CreateAnimal(string name) => new Cat(name);
}

// Simple Factory (not GoF but commonly used)
public static class AnimalFactory
{
    public static IAnimal CreateAnimal(string type, string name)
    {
        return type.ToLower() switch
        {
            "dog" => new Dog(name),
            "cat" => new Cat(name),
            "bird" => new Bird(name),
            _ => throw new ArgumentException($"Unknown animal type: {type}")
        };
    }
}

// ============================================================================
// EXAMPLE 13: Replace Conditional Dispatcher with Command Pattern (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Giant switch/if-else for command dispatching
 * - Adding new commands requires modifying dispatcher
 * - All command logic in one place
 * - Hard to test individual commands
 */
public class BadCommandProcessor
{
    public void ProcessCommand(string commandType, string data)
    {
        if (commandType == "CREATE")
        {
            Console.WriteLine($"Creating: {data}");
        }
        else if (commandType == "UPDATE")
        {
            Console.WriteLine($"Updating: {data}");
        }
        else if (commandType == "DELETE")
        {
            Console.WriteLine($"Deleting: {data}");
        }
        else if (commandType == "VALIDATE")
        {
            Console.WriteLine($"Validating: {data}");
        }
    }
}

/*
 * SOLUTION: Command Pattern (GoF Behavioral)
 * - Each command is a separate class
 * - Commands stored in dictionary for O(1) lookup
 * - Easy to add new commands
 */
public interface ICommand
{
    void Execute(string data);
}

public class CreateCommand : ICommand
{
    public void Execute(string data)
    {
        Console.WriteLine($"Creating: {data}");
    }
}

public class UpdateCommand : ICommand
{
    public void Execute(string data)
    {
        Console.WriteLine($"Updating: {data}");
    }
}

public class DeleteCommand : ICommand
{
    public void Execute(string data)
    {
        Console.WriteLine($"Deleting: {data}");
    }
}

public class ValidateCommand : ICommand
{
    public void Execute(string data)
    {
        Console.WriteLine($"Validating: {data}");
    }
}

public class CommandProcessor
{
    private readonly Dictionary<string, ICommand> _commands = new();

    public CommandProcessor()
    {
        _commands["CREATE"] = new CreateCommand();
        _commands["UPDATE"] = new UpdateCommand();
        _commands["DELETE"] = new DeleteCommand();
        _commands["VALIDATE"] = new ValidateCommand();
    }

    public void ProcessCommand(string commandType, string data)
    {
        if (_commands.TryGetValue(commandType, out var command))
        {
            command.Execute(data);
        }
        else
        {
            throw new ArgumentException($"Unknown command: {commandType}");
        }
    }

    public void RegisterCommand(string type, ICommand command)
    {
        _commands[type] = command;
    }
}

// ============================================================================
// EXAMPLE 14: Introduce Null Object Pattern
// ============================================================================

/*
 * PROBLEM: Null checks scattered everywhere
 * - Every usage must check for null
 * - Defensive programming clutter
 * - NullReferenceException risk
 */
public class BadCustomerService
{
    public double CalculateDiscount(Customer customer)
    {
        if (customer == null)
            return 0.0;

        if (customer.DiscountPolicy == null)
            return 0.0;

        return customer.DiscountPolicy.CalculateDiscount(customer.TotalPurchases);
    }

    public void SendNewsletter(Customer customer)
    {
        if (customer != null && customer.Email != null)
        {
            Console.WriteLine($"Sending newsletter to {customer.Email}");
        }
    }
}

/*
 * SOLUTION: Null Object Pattern
 * - Special object represents "no object"
 * - Null object has do-nothing or default behavior
 * - Eliminates null checks
 */
public interface IDiscountPolicy
{
    double CalculateDiscount(double amount);
}

public class PercentageDiscount : IDiscountPolicy
{
    private readonly double _percentage;

    public PercentageDiscount(double percentage)
    {
        _percentage = percentage;
    }

    public double CalculateDiscount(double amount)
    {
        return amount * _percentage;
    }
}

// Null Object - safe default behavior
public class NoDiscount : IDiscountPolicy
{
    public double CalculateDiscount(double amount) => 0.0;
}

public class Customer
{
    public string Email { get; }
    public IDiscountPolicy DiscountPolicy { get; }
    public double TotalPurchases { get; set; }

    public Customer(string email, IDiscountPolicy discountPolicy = null)
    {
        Email = email ?? string.Empty;
        DiscountPolicy = discountPolicy ?? new NoDiscount();
    }
}

public class CustomerService
{
    public double CalculateDiscount(Customer customer)
    {
        // No null checks needed!
        return customer.DiscountPolicy.CalculateDiscount(customer.TotalPurchases);
    }

    public void SendNewsletter(Customer customer)
    {
        if (!string.IsNullOrEmpty(customer.Email))
        {
            Console.WriteLine($"Sending newsletter to {customer.Email}");
        }
    }
}

// ============================================================================
// EXAMPLE 15: Compose Method - Extract Till You Drop
// ============================================================================

/*
 * PROBLEM: Long methods doing too many things
 * - Hard to understand
 * - Multiple levels of abstraction mixed
 * - Difficult to test
 */
public class BadOrderProcessor
{
    public void ProcessOrder(Order order)
    {
        // Validation
        if (order.Items.Count == 0)
        {
            throw new InvalidOperationException("Order has no items");
        }

        // Calculate total
        double total = 0;
        foreach (var item in order.Items)
        {
            total += item.Price * item.Quantity;
        }

        // Apply discount
        if (order.Customer.IsVIP)
        {
            total *= 0.9;
        }

        // Process payment
        if (order.PaymentMethod == "CREDIT_CARD")
        {
            Console.WriteLine($"Processing credit card: {total}");
        }
        else if (order.PaymentMethod == "PAYPAL")
        {
            Console.WriteLine($"Processing PayPal: {total}");
        }

        // Update inventory
        foreach (var item in order.Items)
        {
            item.Product.DecreaseStock(item.Quantity);
        }

        // Send confirmation
        Console.WriteLine($"Sending confirmation to: {order.Customer.Email}");
    }
}

/*
 * SOLUTION: Compose Method Pattern
 * - Break into small methods at same abstraction level
 * - Main method reads like documentation
 * - Each method has single clear purpose
 */
public class OrderProcessor
{
    public void ProcessOrder(Order order)
    {
        ValidateOrder(order);
        var total = CalculateTotal(order);
        total = ApplyDiscounts(order, total);
        ProcessPayment(order, total);
        UpdateInventory(order);
        SendConfirmation(order);
    }

    private void ValidateOrder(Order order)
    {
        if (order.Items.Count == 0)
            throw new InvalidOperationException("Order has no items");
    }

    private double CalculateTotal(Order order)
    {
        return order.Items.Sum(item => item.Price * item.Quantity);
    }

    private double ApplyDiscounts(Order order, double total)
    {
        return order.Customer.IsVIP ? total * 0.9 : total;
    }

    private void ProcessPayment(Order order, double total)
    {
        var processor = PaymentProcessorFactory.Create(order.PaymentMethod);
        processor.Process(total);
    }

    private void UpdateInventory(Order order)
    {
        foreach (var item in order.Items)
        {
            item.Product.DecreaseStock(item.Quantity);
        }
    }

    private void SendConfirmation(Order order)
    {
        Console.WriteLine($"Sending confirmation to: {order.Customer.Email}");
    }
}

// ============================================================================
// EXAMPLE 16: Replace Inheritance with Delegation (Favor Composition)
// ============================================================================

/*
 * PROBLEM: Inappropriate inheritance
 * - Subclass tightly coupled to superclass
 * - Cannot reuse without base class baggage
 * - Violates Liskov Substitution Principle
 */
public class BadStack<T> : List<T>
{
    public void Push(T item)
    {
        Add(item);
    }

    public T Pop()
    {
        var item = this[Count - 1];
        RemoveAt(Count - 1);
        return item;
    }

    // PROBLEM: All List<T> methods exposed!
    // stack[5] - breaks stack abstraction
    // stack.Insert(0, item) - breaks stack semantics
}

/*
 * SOLUTION: Composition over Inheritance
 * - Contains list instead of extending it
 * - Only exposes appropriate Stack interface
 * - Maintains proper encapsulation
 */
public class Stack<T>
{
    private readonly List<T> _elements = new();

    public void Push(T item)
    {
        _elements.Add(item);
    }

    public T Pop()
    {
        if (IsEmpty())
            throw new InvalidOperationException("Stack is empty");

        var item = _elements[^1]; // C# 8+ index from end
        _elements.RemoveAt(_elements.Count - 1);
        return item;
    }

    public T Peek()
    {
        if (IsEmpty())
            throw new InvalidOperationException("Stack is empty");

        return _elements[^1];
    }

    public bool IsEmpty() => _elements.Count == 0;
    public int Count => _elements.Count;
}

// ============================================================================
// EXAMPLE 17: Replace Implicit Tree with Interpreter (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Complex expressions evaluated with conditionals
 * - Expression parsing mixed with evaluation
 * - Hard to add new operators
 * - Cannot represent expression tree
 */
public class BadCalculator
{
    public double Evaluate(string expression)
    {
        if (expression.Contains("+"))
        {
            var parts = expression.Split('+');
            return double.Parse(parts[0]) + double.Parse(parts[1]);
        }
        else if (expression.Contains("-"))
        {
            var parts = expression.Split('-');
            return double.Parse(parts[0]) - double.Parse(parts[1]);
        }
        else if (expression.Contains("*"))
        {
            var parts = expression.Split('*');
            return double.Parse(parts[0]) * double.Parse(parts[1]);
        }
        return double.Parse(expression);
    }
}

/*
 * SOLUTION: Interpreter Pattern + Composite (GoF)
 * - Represents grammar rules as class hierarchy
 * - Easy to add new expressions
 * - Tree structure allows multiple operations
 */
public interface IExpression
{
    double Interpret();
    string ToString();
}

// Terminal expression - leaf node
public class NumberExpression : IExpression
{
    private readonly double _value;

    public NumberExpression(double value)
    {
        _value = value;
    }

    public double Interpret() => _value;

    public override string ToString() => _value.ToString();
}

// Non-terminal expressions - composite nodes
public class AddExpression : IExpression
{
    private readonly IExpression _left;
    private readonly IExpression _right;

    public AddExpression(IExpression left, IExpression right)
    {
        _left = left;
        _right = right;
    }

    public double Interpret() => _left.Interpret() + _right.Interpret();

    public override string ToString() => $"({_left} + {_right})";
}

public class SubtractExpression : IExpression
{
    private readonly IExpression _left;
    private readonly IExpression _right;

    public SubtractExpression(IExpression left, IExpression right)
    {
        _left = left;
        _right = right;
    }

    public double Interpret() => _left.Interpret() - _right.Interpret();

    public override string ToString() => $"({_left} - {_right})";
}

public class MultiplyExpression : IExpression
{
    private readonly IExpression _left;
    private readonly IExpression _right;

    public MultiplyExpression(IExpression left, IExpression right)
    {
        _left = left;
        _right = right;
    }

    public double Interpret() => _left.Interpret() * _right.Interpret();

    public override string ToString() => $"({_left} * {_right})";
}

// Usage
public class Calculator
{
    public void Calculate()
    {
        // Build: (5 + 3) * (10 - 2)
        IExpression expr = new MultiplyExpression(
            new AddExpression(
                new NumberExpression(5),
                new NumberExpression(3)
            ),
            new SubtractExpression(
                new NumberExpression(10),
                new NumberExpression(2)
            )
        );

        Console.WriteLine($"Expression: {expr}");
        Console.WriteLine($"Result: {expr.Interpret()}");
    }
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