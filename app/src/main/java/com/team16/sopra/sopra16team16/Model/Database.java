package com.team16.sopra.sopra16team16.Model;

import java.util.ArrayList;

/**
 * Created by prime on 17.11.16.
 */
public class Database {
    private static Database ourInstance = new Database();
    private ArrayList<Contact> contactList = new ArrayList<Contact>();

    public static Database getInstance() {
        return ourInstance;
    }

    private Database() {
        this.initialize();
    }

    public ArrayList<Contact> getContact() {
        return contactList;
    }

    public void addContact(Contact contact) {
        contactList.add(contact);
        Storage.writeContact(contact);
    }

    public void initialize() {
        ArrayList<String[]> list = CSVUtils.readContactFile("filepath");
        for (int i = 0; i < list.size(); i++) {
            String[] cur = list.get(i);
            this.addContact(new Contact(cur[0], cur[1], cur[2], cur[3], cur[4], cur[5], Integer.getInteger(cur[6])));
        }
    }
}
