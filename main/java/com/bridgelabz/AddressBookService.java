package com.bridgelabz;


import bridgelabz.ContactInfo;

import java.util.List;

public class AddressBookService {
    private final AddressBookDBService addressBookDBService;
    private List<ContactInfo> personInfoList;

    public enum IOService {
        DB_IO
    }

    public AddressBookService() {
        addressBookDBService = AddressBookDBService.getInstance();
    }

    /**
     * Purpose : To get the list of person info from the database
     *
     * @param ioService
     * @return
     */
    public List<ContactInfo> readPersonInfoData(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            this.personInfoList = addressBookDBService.readData();
        return this.personInfoList;
    }
    /**
     * Purpose : To update the Person Info State in the database
     *           If the value is updated, the result value returned is greater than 0; else it is returned 0
     *           Match the given name with the PersonInfo list
     *           If found, assign the given state to the PersonInfo list
     *
     * @param name
     * @param state
     */
    public void updatePersonInfo(String name, String state) {
        int result = addressBookDBService.updatePersonInfo(name, state);
        if(result == 0)
            return;
        ContactInfo personInfoData = this.getPersonInfoData(name);
        if( personInfoData != null )
            personInfoData.state = state;
    }

    /**
     * Purpose : Check the contactInfo list for the name
     *           If found, return the value else return null
     *
     * @param name
     * @return
     */
    private ContactInfo getPersonInfoData(String name) {
        return this.personInfoList.stream()
                .filter(contactInfo -> contactInfo.first_name.equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Purpose : To check whether the PersonInfo is in sync with the DB
     *           Use to equals() to compare the values
     *
     * @param name
     * @return
     */
    public boolean checkPersonInfoInSyncWithDB(String name) {
        List<ContactInfo> personInfoDataList = addressBookDBService.getPersonInfoData(name);
        return personInfoDataList.get(0).equals(getPersonInfoData(name));
    }
}