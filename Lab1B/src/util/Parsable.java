/**     @author Eimutis Karčiauskas, KTU IF Department of Software Engineering, 23/09/2014
        *
        * This is the interface that the data classes created by KTU students must meet.
        * Methods ensure convenient data generation from String strings.
        ******************************************************** ********************************/
package util;

public interface Parsable<T> extends Comparable<T> {
    void parse(String dataString);     // forms an object from a string
}
