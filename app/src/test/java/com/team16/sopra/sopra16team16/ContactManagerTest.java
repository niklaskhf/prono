package com.team16.sopra.sopra16team16;

//import com.team16.sopra.sopra16team16.Controller.ContactListAdapter;

/**
 * Tests the ContactManager class
 */
public class ContactManagerTest {
/*
    @Mock
    Context mockContext = Mockito.mock(Context.class);
    @Mock
    final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
    @Mock
    SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
    @Mock
    ContactListAdapter listAdapter = Mockito.mock(ContactListAdapter.class);


    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockContext.getSharedPreferences("contactsList", Context.MODE_PRIVATE)).thenReturn(sharedPrefs);
        Mockito.when(mockContext.getSharedPreferences("contId", Context.MODE_PRIVATE)).thenReturn(sharedPrefs);
    }
    @Test
    public void addContact_test() throws Exception {
        DBManager db = DBManager.getInstance(mockContext);
        Gson gson = new Gson();
        int id = 0;
        ArrayList<Contact> testList = new ArrayList<Contact>();
        String defaultPrefs = gson.toJson(testList);

        Contact testContact = new Contact("test", "test", "test", "test", Gender.MALE, 0);

        Mockito.when(sharedPrefs.getString("contactsList", defaultPrefs)).thenReturn(gson.toJson(testList));
        Mockito.when(sharedPrefs.getInt("contId", 0)).thenReturn(0);
        Mockito.when(sharedPrefs.getString(
                "contactsList", gson.toJson(new ArrayList<Contact>()))).thenReturn(defaultPrefs);
        Mockito.when(sharedPrefs.edit()).thenReturn(editor);
        Mockito.when(editor.putString(Mockito.anyString(), Mockito.anyString())).thenReturn(editor);

        ContactManager manager = new ContactManager(mockContext);
        manager.addContact("test", "test", "test", "test", Gender.MALE);

        assertTrue("Contact wasnt added to ArrayList", db.getContact().get(0).getId() == testContact.getId());


        SharedPreferences myPrefs = mockContext.getSharedPreferences("contId", Context.MODE_PRIVATE);
        assertTrue("contId wasnt saved correctly", myPrefs.getInt("uniqId", 0) == id);

        // this is literally the worst OH GOD
    }*/
}