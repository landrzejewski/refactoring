1. You're building a centralized logging system. You want to ensure that no matter where or when the logger is used, it's always the same single instance, and that instance must be accessible globally.
2. You’re developing a document editor that can create various types of documents (PDF, Word, Excel). The code for creating the documents may vary, and you want to let subclasses decide what kind of document to create at runtime.
3. You are building a cross-platform UI toolkit. The system should be able to create families of related UI widgets (buttons, menus, scrollbars) that vary depending on the operating system (Windows, macOS, Linux).
4. You’re designing a car customization interface. Customers build a car by selecting parts step-by-step (engine, wheels, color, interior, etc.). Some parts are optional.
5. You need a way for users to duplicate objects (like graphic shapes) in your app. You want to create new objects by copying existing ones without knowing their exact class or constructor parameters.
6. Your app currently plays .mp3 files using a legacy AudioPlayer. You want it to also support .mp4 and .vlc files by integrating new APIs, but you don’t want to change the existing codebase.
7. You’re building a drawing program where the abstraction (shape) and the implementation (rendering API like OpenGL/DirectX) should be independently extendable.
8. You're building a file explorer. Files and folders should be treated uniformly, even though folders can contain other folders and files. You want to use the same interface for both.
9. You have a TextView class. Sometimes, you want to add borders, scrollbars, or shadows to the view, possibly at the same time. You want to add these features at runtime without modifying the original class.
10. You’re integrating a home theater system that consists of multiple subsystems (DVD player, projector, surround sound, lights). You want to provide users with a simple interface to start or stop watching a movie.
11. You’re building a word processor that needs to handle documents with millions of characters. Many characters share formatting and font properties. You want to minimize memory use by sharing common data.
12. You are implementing an image viewer. Some images are very large, and you want to defer loading them until they are actually needed. The client should not know whether the image is loaded yet.
13. You want to design a help desk system where a request is passed along a chain of support levels (basic → advanced → supervisor) until one of them handles it.
14. You’re building a GUI where each button triggers an action. You want to encapsulate these requests as objects, so you can support undo, logging, and queuing of actions.
15. You're designing a calculator that evaluates boolean expressions like true AND false OR true. You want to define a grammar and parse expressions accordingly.
16. You’re building a custom data structure (BookShelf) and want users to iterate over it using for...in loops or other iteration logic without exposing the internal structure.
17. In a chat app with multiple users and components, each component needs to notify others when actions occur (e.g., user joins, messages sent). You want to avoid tight coupling between components.
18. In a drawing application, users should be able to undo/redo actions (like moving or deleting shapes). You want to save object state and restore it later.
19. You're building a weather monitoring app. When the temperature changes, all UI elements (like the dashboard, mobile widget, and watch app) should update automatically.
20. You’re simulating a traffic light system with states like Green, Yellow, and Red. Each state has its own behavior and defines which state it transitions to next.
21. Your app supports multiple sorting algorithms. Based on the size and type of data, you want to select the best algorithm at runtime.
22. You're building a game engine where all games follow the same basic steps: initialize, start, play, end. Some games define these steps differently. You want to define the skeleton algorithm, but allow parts of it to be overridden.
23. You want to create multiple types of reports (e.g., for Employees, Products, Orders). Instead of changing these classes to add reporting logic, you want to "visit" each one and apply external logic.

Answer
1	Singleton	Ensures a class has only one instance and provides a global point of access.
2	Factory Method	Defers instantiation to subclasses.
3	Abstract Factory	Creates families of related objects without specifying concrete classes.
4	Builder	Constructs complex objects step-by-step.
5	Prototype	Clones existing objects without using their constructors.
6	Adapter	Converts the interface of a class into one the client expects.
7	Bridge	Decouples abstraction from its implementation.
8	Composite	Treats individual objects and compositions of objects uniformly.
9	Decorator	Adds responsibilities to objects dynamically.
10	Facade	Provides a simplified interface to a complex system.
11	Flyweight	Shares objects to support large numbers efficiently.
12	Proxy	Controls access to another object (e.g., for lazy loading).
13	Chain of Responsibility	Passes a request along a chain of handlers.
14	Command	Encapsulates requests as objects.
15	Interpreter	Evaluates language grammar or expressions.
16	Iterator	Provides a way to access elements sequentially.
17	Mediator	Centralizes communication between objects.
18	Memento	Captures and restores object state.
19	Observer	Notifies observers of state changes.
20	State	Allows an object to alter behavior when its internal state changes.
21	Strategy	Selects algorithm behavior at runtime.
22	Template Method	Defines an algorithm's skeleton in a method.
23	Visitor	Adds new operations to objects without modifying them.

##################################################################

Project: Online Shopping System (E-Commerce Platform)
Overview:
Design a simplified online shopping system. It should support user registration, product catalog,
shopping cart, checkout process, order management, and admin controls.
