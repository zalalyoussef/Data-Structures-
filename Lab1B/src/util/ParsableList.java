/** @author Eimutis Karčiauskas, KTU IF Department of Software Engineering, 23/09/2014
        *
        * This is a derived complex data structure class that allows
        * additionally perform input-output actions with list items.
        * The objects to be added to the list must satisfy the Parsable <E> interface.
        ******************************************************** ****************************/
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Function;

public class ParsableList<E extends Parsable<E>> extends LinkedList<E> {

    private Function<String, E> createFunction;

    public ParsableList() {
    }

    public ParsableList(Function<String, E> createFunction) {
        this.createFunction = createFunction;
    }
    
    public void add(String dataString) {        // sukuria elementą iš String
        add(createElement(dataString)); // and puts it at the end
    }

    public void load(String fName) {//forms a list from the fName file
        clear();
        if (fName.length() == 0) {
            return;
        }
        try {
            (new File(Ks.getDataFolder())).mkdir();
            String fN = Ks.getDataFolder() + File.separatorChar + fName;
            BufferedReader fReader
                    = new BufferedReader(new FileReader(new File(fN)));
            String dLine;
            while ((dLine = fReader.readLine()) != null) {
                add(dLine);
            }
            fReader.close();
        } catch (FileNotFoundException e) {
            Ks.ern("Data file " + fName + " not found");
//            System.exit(0);
        } catch (IOException e) {
            Ks.ern("File " + fName + " read error");
            System.exit(0);
        }
    }

    public void save(String fName) {    // saves the list in the fName file
        PrintWriter fWriter = null;     // in text format
        try {                           // suitable for later reading
            // if there is no name, the file is not formatted
            if (fName.equals("")) {
                return;
            }

            String fN = Ks.getDataFolder() + File.separatorChar + fName;
            fWriter = new PrintWriter(new FileWriter(new File(fN)));
            for (Parsable d1 = super.get(0); d1 != null; d1 = super.getNext()) {
                fWriter.println(d1.toString());
            }
            fWriter.close();
        } catch (IOException e) {
            Ks.ern("!!! Formatting error" + fName + " file.");
            System.exit(0);
        }
    }

    public void println() {  // list is printed to Ks.oun ("");
        int eilNr = 0;
        if (super.isEmpty()) {
            Ks.oun("The list is empty");
        } else {
            for (Parsable d1 = super.get(0); d1 != null; d1 = super.getNext()) {
                String printData = String.format("%3d: %s ", eilNr++, d1.toString());
                Ks.oun(printData);
            }
        }
        Ks.oun("****** The total number of items is " + super.size());
    }

    public void println(String title) { // a header can be specified when printing
        Ks.oun("========" + title + "=======");
        println();
        Ks.oun("======== End of list =======");
    }

    protected E createElement(String data) {
        if (createFunction == null) {
            throw new IllegalStateException("No list item creation feature set");
        }
        return createFunction.apply(data);
    }
}
