package bridgelabz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;

public class ReadWriteOperations {
    String filePath = "C:\\Users\\admin\\IdeaProjects\\AddressBookDatabase\\src\\AddressBook.txt";

    /**
     * Purpose : Ability to Write the Address Book with Persons Contact into a File using File IO
     *
     * @param : personInfoDict
     */
    public void writeInAddressBook(Hashtable<String, ArrayList<contactInfo>> personInfoDict) {
        StringBuffer empBuffer = new StringBuffer();
        personInfoDict.forEach( (companyName, personInfos) -> {
            String empDataString = companyName.concat(personInfos.toString().concat("\n"));
            empBuffer.append(empDataString);
        });

        try {
            Files.write(Paths.get(filePath) , empBuffer.toString().getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Purpose : Ability to Read the Address Book with Persons Contact into a File using File IO
     *
     * @param : personInfoDict
     */
    public void readFromAddressBook() {
        try {
            Files.lines(new File(filePath).toPath())
                    .forEach(System.out::println);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}