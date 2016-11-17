package com.team16.sopra.sopra16team16.Model;

import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by prime on 17.11.16.
 */

public class Storage {

    public static void writeContact(Contact contact) {
        try{
            FileWriter fw = new FileWriter("contacts.csv");
            ArrayList<String> strings = new ArrayList<String>();

            strings.add(Integer.toString(contact.getId()));
            strings.add(contact.getFirstName());
            strings.add(contact.getLastName());
            strings.add(contact.getTitle());
            strings.add(contact.getCountry());
            strings.add(contact.getGender());
            strings.add(Boolean.toString(contact.getFavorite()));

            CSVUtils.writeLine(fw, strings);
        } catch (Exception E) {
            // handle exception
            // create new contacts file
        }
    }
}
