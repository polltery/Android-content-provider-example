# Android content provider example

## About
A small app that demonstrates how to build a custom content provider and perform CURD operations on the content.

## Classes and their descriptions

The application has several class files that build up the content provider along with other class files that contain the logic for the GUI. Below is a list of all the classes and their descriptions.

- **CountryContract** : Country contract is a class made to simplify creation of uniform resource identifiers (URI). It contains all the necessary information to create URIs using baseColumns and an custom built interface for country columns. 
- **CountryDatabase** : This class extends SQLiteOpenHelper class which allows for creation of the SQLite Database along with default data. It also has an interface to let other classes know which database this class contains. 
- **CountryProvider** : This class extends ContentProvider, it provides a CRUD interface to manage the database contents. 
- **Country** : A class created to store and retrieve country information to and from the database. It has six fields ( _id, name, capital, population, area, lang)  with getters and setter methods. 
- **CountryListLoader** : This class extends AsyncTaskLoader&lt;E&gt;, it is used to load information from the database asynchronously when the application starts up or makes any changes. It makes use of ContentResolver to query the data and Cursor to iterate through the data. 
- **CountryCustomAdapter** : This class extends ArrayAdapter&lt;E&gt;, it is used to initialize the data into the list that displays a list of country. It also contains the logic for handling edit and delete functions.  
- **CountryListFragment** : extends ListFragment and implements LoaderManager.LoaderCallback&lt;E&gt;, it is used to bind the data to CountryCustomAdapter by loading the list from CountryListLoader. 
- **MainActivity** : This class is the entry point for the application. It extends FragmentActivity and It sets up a CountryListFragment to generate the list at start. It also defines the logic for adding new entries. 
- **AddActivity** : Extends FragmentActivity, it contains logic for add activity screen. It uses add_edit.xml as its layout. 
- **EditActivity** : Extends FragmentActivity, it contains logic for edit activity screen. It uses add_edit.xml as its layout. 
- **CountryDialog** : Extends DialogFragment, it contains logic for confirmation screen when deleting an entry.

## Author
- Balraj Singh Bains

Note: This application was built as a part of Heriot Watt University Coursework for Mobile Communications and Programming.
