package bridgelabz;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class ReadWriteCSVFile {
    String filePath = "src";

    /**
     * Purpose : Ability to write the user input data in a .csv file
     *           Create a .csv file based on the CompanyName and insert data specific to the CompanyName
     *
     * @param personInfoDict
     */

    public void writeCSVFile(Hashtable<String, ArrayList<ContactInfo>> personInfoDict) {
        personInfoDict.keySet().stream().forEach(companyName -> {
            File file = new File(filePath + companyName + ".csv");
            try {
                FileWriter outputfile = new FileWriter(file);
                CSVWriter writer = new CSVWriter(outputfile);

                //Add header to csv
                String[] header = { "FirstName", "LastName", "Address", "City", "State", "Zip", "PhoneNumber", "Email" };
                writer.writeNext(header);

                //Add data to csv
                personInfoDict.get(companyName).stream().forEach(personInfos -> {
                    String[] person1 = {personInfos.getFirst_name(), personInfos.getLast_name(), personInfos.getAddress(),
                            personInfos.getCity(), personInfos.getState(), String.valueOf(personInfos.getZip()),
                            personInfos.getPhone_number(), personInfos.getEmail()};
                    writer.writeNext(person1);
                });

                //Close writer connection
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Purpose : Ability to read the data from a .csv file
     *           Ask user for the CompanyName and display the data specific to the CompanyName
     */

    public void readCSVFile() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n\nReading Data from .csv file");
        System.out.print("Enter the company name you want to read the details from : ");
        String companyName = input.next();

        String filePathRead = "src/AddressBookCSVOp/" + companyName +".csv";
        try {
            FileReader filereader = new FileReader(filePathRead);
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            //Reading data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
