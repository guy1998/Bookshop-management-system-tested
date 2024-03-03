# Bookshop-Management-System
***
## General description of the system
This is an application to manage the financial life of a bookshop. It allows a bookshop owner to maintain his business in an automated manner by generating live financial reviews of the business, providing detailed overview of the employees, and allowing easy modification of the available stock. It has three levels of users: Administrator, Manager, and Librarian. Each of which has different permissions and features. <br>
The administrator user is at the top of the hierarchy of users. It has the permission to create other users and manage their levels of access. It can manage the overall income and expenses on the finances tab. Can manipulate the bookshop's stock by adding, removing, or editing books. Finally, the administrator has the right to manage and manipulate the information of the employees. It can view statistics about how many books each librarian has sold or how much money each librarian has generated during a period. Can see the number of books a manager has ordered. All these statistics can be filtered by date and come with preset business goals and quotas.<br>
Moving on, the manager user has the right to manage the book stock. This is the primary role of a manager. This user gets notified in case the book stock is empty or there are books which have no copies or only few copies left. The manager just like the administrator can edit the information of the books and can purchase books when needed or remove them from the stock. Finally, the manager can also see the statistics of each librarian. These statistics come with a date filter which can be used to set the time frame. <br>
Lastly, the librarian user has the permission to search books and sell them. For each transaction carried by a librarian the system maintains the information so that it can be used for the financial overview later on. It also creates a printed version of the bill for the transaction. It is noteworthy to say that the librarian does not have any access over other users or over the book stock. It can only interact with the books that are available in stock.<br>
For all the users of the system there is a profile section where they can modify their personal information such as the name, username, password, and so on. There is also a messages feature which allows the users to exchange messages with each other. They can access these messages in the inbox found on the dashboard. It is worth it to mention that the librarian and manager can have their access removed partially or completely by the administrator. In such cases they are denied the usage of some or all features provided by their dashboard.<br>

## Information regarding the implementation

The system is implemented in java using javafx as the frontend framework. The information is retained in binary and text files which are stored in the Database folder. The general design patterns found in the system are the inheritance pattern (in the case of the user levels) and the factory class pattern (in the case of the manager classes such as BookStock or UserStack). The users all inherit from the abstract class called User. There are 2 main classes of users Administrator and Employee. The Employee class is then further divided in Manager and Librarian. This way the system is able to distinguish the type of users and give the correct permissions to each. The business logic of the system is managed by the factory classes. The main factory classes are the BookStock and the UserStack. The BookStock class carries the CRUD operations on the books found at the bookshop's stock. To do this it makes use of the BookProxy class which has the access to the file database. Similarly, the UserStack class carries out the CRUD operations for the users by making use of a class called UserProxy to access the database. This division of classes such that one does the logical operations and the other saves these operations on the database is done for two reasons: To comply to the Single Responsibility Principle and to make it easier for injection of the mocked interfaces during the testing stage. There are other factory classes in the system such as TransactionControl or BillWriter which use the same structure to communicate with the database. The proxy classes that are used for this purpose implement their specific interfaces. This is done so that during testing the mocking process is performed easily. 

## App's preview

<img width="596" style="margin: auto" alt="image" src="https://github.com/guy1998/Bookshop-management-system-tested/assets/104024859/a5deeefe-d57a-497c-96f2-c6a054a32a12">

