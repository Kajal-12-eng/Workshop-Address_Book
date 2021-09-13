package bridgelabz;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.*;


public class ReadWriteJSONFile {
    String filepath = "src";

    /**
     * Purpose : Ability to read the data from a .json file
     *           Ask user for the CompanyName and display the data specific to the CompanyName
     */

    public void readJSONFile() {
        Scanner input = new Scanner(System.in);
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = null;

        System.out.println("\n\nReading Data from .json file");
        System.out.print("Enter the Company Name you want to read the details from : ");
        String companyName = input.next();

        String filePathRead = filepath + companyName +".json";
        try {
            FileReader reader = new FileReader(filePathRead);
            jsonArray = (JsonArray) jsonParser.parse(reader);
            System.out.println(jsonArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Purpose : Ability to Write the Address Book with Persons Contact into a File using GSON library
     *           Create a .json file based on the CompanyName and insert data specific to the CompanyName
     *
     * @param : personInfoDict
     */

    public void writeJSONFile(Hashtable<String, ArrayList<ContactInfo>> personInfoDict) {
        personInfoDict.keySet().stream().forEach(companyName -> {
            String filepath1 = filepath + companyName + ".json";
            try {
                JsonArray jsonArray = new JsonArray();
                personInfoDict.get(companyName).stream().forEach(personInfos -> {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("First name", personInfos.getFirst_name());
                    jsonObject.addProperty("Last name", personInfos.getLast_name());
                    jsonObject.addProperty("Address", personInfos.getAddress());
                    jsonObject.addProperty("City", personInfos.getCity());
                    jsonObject.addProperty("State", personInfos.getState());
                    jsonObject.addProperty("Zip", personInfos.getZip());
                    jsonObject.addProperty("Phone Number", personInfos.getPhone_number());
                    jsonObject.addProperty("Email", personInfos.getEmail());

                    jsonArray.add(jsonObject);
                });
                FileWriter fw = new FileWriter(filepath1);
                fw.write(jsonArray.toString());
                fw.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
