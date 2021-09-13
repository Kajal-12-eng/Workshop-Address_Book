package bridgelabz;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AddressBookMain {
    Scanner input = new Scanner(System.in);
    Hashtable<String, ArrayList<ContactInfo>> personInfoDict = new Hashtable<>();
    ContactInfo p = null;
    ArrayList<ContactInfo> pList = new ArrayList<>();

    public Hashtable<String, ArrayList<ContactInfo>> insertContactDetails() {
        boolean found = false;
        p = new ContactInfo();
        System.out.print("\nEnter the Address Book Name: ");
        String addressBookName = input.next();

        System.out.print("Enter the First Name: ");
        p.setFirst_name(input.next().toString());
        System.out.print("Enter the Last Name: ");
        p.setLast_name(input.next().toString());
        System.out.print("Enter the Address: ");
        p.setAddress(input.next().toString());
        System.out.print("Enter the City: ");
        p.setCity(input.next().toString());
        System.out.print("Enter the State: ");
        p.setState(input.next().toString());
        System.out.print("Enter the Zip: ");
        p.setZip(input.nextInt());
        System.out.print("Enter the Phone Number: ");
        p.setPhone_number(input.next().toString());
        System.out.print("Enter the Email: ");
        p.setEmail(input.next().toString());

        if (personInfoDict.containsKey(addressBookName)) {
            ArrayList<ContactInfo> value = personInfoDict.get(addressBookName);
            for (int j = 0; j < value.size(); j++) {

                List<String> names = value.stream().map(ContactInfo::getFirst_name).collect(Collectors.toList());
                for ( int k = 0; k < names.size(); k++)  {
                    if(names.get(j).equals(p.getFirst_name())) {
                        found = true;
                        break;
                    }
                }
            }
            if (found == true)
                System.out.println("\nDuplicate First Name in Address Book!\n");
            else {
                value.add(p);
                personInfoDict.put(addressBookName, value);
            }
        }
        else {
            pList = new ArrayList<>();
            pList.add(p);
            personInfoDict.put(addressBookName, pList);
        }

        return personInfoDict;
    }


    public void updateContact(String addressBookName, Hashtable<String, ArrayList<ContactInfo>> personInfoDict) {
        boolean flag = findContact(addressBookName, personInfoDict);
        if (flag == true) {
            editContactDetails(addressBookName, personInfoDict);
        } else {
            System.out.println("\nNo such Address Book found to update!\n");
        }
    }


    public boolean findContact(String addressBookName, Hashtable<String, ArrayList<ContactInfo>> personInfoDict) {
        for (int i = 0; i < personInfoDict.size(); i++) {
            if (personInfoDict.containsKey(addressBookName))
                return true;
        }
        return false;
    }


    public void editContactDetails(String addressBookName, Hashtable<String, ArrayList<ContactInfo>> personInfoDict) {
        System.out.print("\nEnter the first name you want to edit the details for : ");
        String fName = input.next();

        ArrayList<ContactInfo> value = personInfoDict.get(addressBookName);
        for (int j = 0; j < value.size(); j++) {
            if(value.get(j).getFirst_name().equals(fName)) {
                System.out.println("Choose your edit option: ");
                System.out.println("1. Last Name");
                System.out.println("2. Address");
                System.out.println("3. City");
                System.out.println("4. State");
                System.out.println("5. Zip");
                System.out.println("6. Phone Number");
                System.out.println("7. Email");
                int editOption = input.nextInt();

                switch (editOption) {
                    case 1:
                        System.out.println("Enter new Last Name: ");
                        value.get(j).setLast_name(input.next().toString());
                        break;
                    case 2:
                        System.out.println("Enter new Address: ");
                        value.get(j).setAddress(input.next().toString());
                        break;
                    case 3:
                        System.out.println("Enter new City: ");
                        value.get(j).setCity(input.next().toString());
                        break;
                    case 4:
                        System.out.println("Enter new State: ");
                        value.get(j).setState(input.next().toString());
                        break;
                    case 5:
                        System.out.println("Enter new Zip: ");
                        value.get(j).setZip(input.nextInt());
                        break;
                    case 6:
                        System.out.println("Enter new Phone Number: ");
                        value.get(j).setPhone_number(input.next().toString());
                        break;
                    case 7:
                        System.out.println("Enter new Email: ");
                        value.get(j).setEmail(input.next().toString());
                        break;
                }
                System.out.println("\nUpdated successfully!\n");
                break;
            }
            else
                System.out.println("\nNo First Name Found!\n");
        }
    }


    public void deleteContact(String addressBookName, Hashtable<String, ArrayList<ContactInfo>> personInfoDict) {
        boolean found = false;
        boolean flag = findContact(addressBookName, personInfoDict);

        if (flag == true) {
            System.out.print("\nEnter the first name you want to delete : ");
            String fName = input.next();

            ArrayList<ContactInfo> value = personInfoDict.get(addressBookName);
            for (int j = 0; j < value.size(); j++) {
                if(value.get(j).getFirst_name().equals(fName)) {
                    value.remove(j);
                    found = true;
                    break;
                }
            }
            if( found == true) {
                System.out.println("\nContact in Address Book Deleted.\n");
            }
            else
                System.out.println("\nNo such First Name found in the Address Book.\n");
        }
        else
            System.out.println("\nNo contacts found in the Address Book.\n");
    }


    public void displayCompanyContacts(Hashtable<String, ArrayList<ContactInfo>> personInfoDict) {
        personInfoDict.keySet().forEach(entry -> {
            System.out.println(entry + "->" + personInfoDict.get(entry)+ "\n");
        });
    }

    /*Purpose : Using Java Streams to search for Person in a City or State across the multiple AddressBook.
                Maintain Dictionary of City and Person as well as State and Person
                Finally get the count of Persons by City or State using java streams
      @since : 06.07.2021
    */


    public void searchPerson() {
        Hashtable<String, Hashtable<String, ArrayList<String>>> hSearch = new Hashtable<>();
        AtomicInteger count = new AtomicInteger();

        System.out.println("Press 1 to search person by city");
        System.out.println("Press 2 to search person by state");
        int choice = input.nextInt();

        switch (choice) {
            case 1:
                System.out.print("\nEnter the city name to search: ");
                String cityName = input.next();

                personInfoDict.keySet().forEach(entry -> {
                    ArrayList<ContactInfo> value = personInfoDict.get(entry);
                    List<String> city = value.stream().map(ContactInfo::getCity).collect(Collectors.toList());
                    Hashtable<String, ArrayList<String>> person = new Hashtable<>();
                    ArrayList<String> firstName = new ArrayList<>();
                    for ( int k = 0; k < city.size(); k++)  {
                        if (city.get(k).equals(cityName)) {
                            firstName.add(value.get(k).getFirst_name());
                        }
                        person.put(cityName , firstName);
                    }
                    count.addAndGet((int)firstName.stream().count());
                    hSearch.put(entry , person);
                });

                break;
            case 2:
                System.out.print("\nEnter the state name to search: ");
                String stateName = input.next();

                personInfoDict.keySet().forEach(entry -> {
                    ArrayList<ContactInfo> value = personInfoDict.get(entry);
                    List<String> city = value.stream().map(ContactInfo::getState).collect(Collectors.toList());
                    Hashtable<String, ArrayList<String>> person = new Hashtable<>();
                    ArrayList<String> firstName = new ArrayList<>();
                    for ( int k = 0; k < city.size(); k++)  {
                        if (city.get(k).equals(stateName)) {
                            firstName.add(value.get(k).getFirst_name());
                        }
                        person.put(stateName , firstName);
                    }
                    count.addAndGet((int)firstName.stream().count());
                    hSearch.put(entry , person);
                });

                break;
        }
        System.out.println("\nViewing Persons by City or State\n" +hSearch);
        System.out.println("\nNumber of contact persons i.e. count by City or State is : " +count +"\n");
    }

    /*Purpose : Sort entries in the Address Book based on First Name aphabetically
                Sort entries in the Address Book based on City, State, or Zip
      @since : 06.07.2021
     */

    public void sortPerson() {
        System.out.println("Press 1 to sort person by First Name");
        System.out.println("Press 2 to sort person by City");
        System.out.println("Press 3 to sort person by State");
        System.out.println("Press 4 to sort person by Zip");
        int choice = input.nextInt();

        switch(choice) {
            case 1:
                System.out.println("\nSorting Address Book based on First Name");
                personInfoDict.keySet().forEach(entry -> {
                    List<ContactInfo> data = personInfoDict.get(entry).stream().sorted(Comparator.comparing(ContactInfo::getFirst_name)).collect(Collectors.toList());
                    System.out.println(data);
                });
                break;
            case 2:
                System.out.println("\nSorting Address Book based on City");
                personInfoDict.keySet().forEach(entry -> {
                    List<ContactInfo> data = personInfoDict.get(entry).stream().sorted(Comparator.comparing(ContactInfo::getCity)).collect(Collectors.toList());
                    System.out.println(data);
                });
                break;
            case 3:
                System.out.println("\nSorting Address Book based on State");
                personInfoDict.keySet().forEach(entry -> {
                    List<ContactInfo> data = personInfoDict.get(entry).stream().sorted(Comparator.comparing(ContactInfo::getState)).collect(Collectors.toList());
                    System.out.println(data);
                });
                break;
            case 4:
                System.out.println("\nSorting Address Book based on Zip");
                personInfoDict.keySet().forEach(entry -> {
                    List<ContactInfo> data = personInfoDict.get(entry).stream().sorted(Comparator.comparing(ContactInfo::getZip)).collect(Collectors.toList());
                    System.out.println(data);
                });
                break;
        }
    }
}