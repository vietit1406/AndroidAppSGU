package com.example.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class EditActivity extends AppCompatActivity {

    public String noteID = "null";
    public NoteItem noteItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        String path = this.getFilesDir().getAbsolutePath() + "/abc.xml";
        ArrayList<NoteItem> listNote = MainActivity.readByDOM(path);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            noteID = bundle.getString("noteID");
        }


        if(!noteID.equals("null"))
        {
            Log.i("test",noteID);
            noteItem = listNote.get(Integer.parseInt(noteID));
            EditText title = (EditText)findViewById(R.id.title);
            title.setText(noteItem.getTitle());
            EditText content = (EditText)findViewById(R.id.content);
            content.setText(noteItem.getContent());

            actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Edit Notepad</font>"));
        }
        else
            actionBar.setTitle(Html.fromHtml("<font color='##FFFFFF'>New Notepad</font>"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.save:
                if(noteID.equals("null"))
                {
                    EditText title = (EditText)findViewById(R.id.title);
                    EditText content = (EditText)findViewById(R.id.content);

                    NoteItem note = new NoteItem();
                    note.setTitle(title.getText().toString());
                    note.setContent(content.getText().toString());

                    String path = this.getFilesDir().getAbsolutePath() + "/abc.xml";
                    Log.i("test",path);
                    writeToDOM(path,note);

                    Intent main = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(main);
                }
                return true;
            case R.id.delete:
                Toast.makeText(this, "hello 2", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void writeToDOM(String file,NoteItem item) {
        try {
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fac.newDocumentBuilder();

            FileInputStream fIn = new FileInputStream(file);
            Document doc = builder.parse(fIn);

            Element root = doc.getDocumentElement(); //lấy tag Root ra

            Element title = doc.createElement("note");
            title.setAttribute("title",item.getTitle());

            Element content = doc.createElement("content");
            content.appendChild(doc.createTextNode(item.getContent()));

            title.appendChild(content);
            root.appendChild(title);

            try {
                //for output to file, console
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                //for pretty print
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);

                Log.i("src", String.valueOf(source));

                //write to console or file
                StreamResult console = new StreamResult(System.out);
                StreamResult file2 = new StreamResult(new File(file));

                //write data
                transformer.transform(source, console);
                transformer.transform(source, file2);
                System.out.println("DONE");

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        } catch (SAXException | IOException ex) {
            ex.printStackTrace();
        }
    }
}