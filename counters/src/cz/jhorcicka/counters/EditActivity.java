package cz.jhorcicka.counters;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;
import cz.jhorcicka.counters.db.Db;
import cz.jhorcicka.counters.model.Counter;

public class EditActivity extends Activity {
	private static final String MENU_SAVE = "Save";
	private static final String MENU_CLEAR = "Clear";
	private static final String MENU_EXIT = "Exit";
	
	private Counter counter = null;
	private Db db = null;
	private EditText nameInput = null;
	private EditText noteInput = null;
	private EditText valueInput = null;
	
	private Db getDb() {
		if (db == null) {
			db = new Db(this);
		}
		
		return db;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        
    	nameInput = (EditText) findViewById(R.id.input_name);
    	noteInput = (EditText) findViewById(R.id.input_note);
    	valueInput = (EditText) findViewById(R.id.input_value); 
        
        handleParameters();
        setButtonsListeners();
    }
    
    private void handleParameters() {
        Bundle parameters = getIntent().getExtras();
        
        if (parameters != null && parameters.containsKey("id")) {
        	long id = parameters.getLong("id");
        	
        	if (id > 0) { 
	        	loadCounter(id);
	        	fillInputFromCounter();
        	}
        }
    	else {
    		valueInput.setText("0");
    	}
    }
    
    private void setButtonsListeners() {
        Button addOneButton = (Button) findViewById(R.id.button_add_one);
        addOneButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		setValue(getValue() + 1);
        	}
        });
        
        Button subOneButton = (Button) findViewById(R.id.button_sub_one);
        subOneButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		setValue(getValue() - 1);
        	}
        }); 
        
        Button addTenButton = (Button) findViewById(R.id.button_add_ten);
        addTenButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		setValue(getValue() + 10);
        	}
        }); 
        
        Button subTenButton = (Button) findViewById(R.id.button_sub_ten);
        subTenButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		setValue(getValue() - 10);
        	}
        });

        Button clearButton = (Button) findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		setValue(0L);
        	}
        });
    }
    
    private void setValue(long value) {
    	valueInput.setText("" + value);
    }
    
    private long getValue() {
    	String stringValue = valueInput.getText().toString();
    	long value = Long.parseLong(stringValue);
    	
    	return value;
    }
	
	private void fillCounterFromInput() {
		String name = nameInput.getText().toString();
		String note = noteInput.getText().toString();
		long value = 0; 
		
		try {
			value = Long.parseLong(valueInput.getText().toString());
		}
		catch (Exception e) {
			value = 0;
		}
		
		if (counter == null) {
			counter = new Counter(name);
		}
		else {
			counter.setName(name);
		}
		
		counter.setNote(note);
		counter.setValue(value);
	}
	
	private void fillInputFromCounter() {
		nameInput.setText(counter.getName());
		noteInput.setText(counter.getNote());
		valueInput.setText("" + counter.getValue());
	}
	
	private void saveCounter() {
		if (counter.getId() == 0) {
			getDb().addCounter(counter);
		}
		else {
			getDb().updateCounter(counter);
		}
	}
	
	private void loadCounter(long id) {
		counter = getDb().getCounter(id);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		getDb().close();
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		menu.add(MENU_SAVE);
		menu.add(MENU_CLEAR);
		menu.add(MENU_EXIT);
		
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		String title = item.getTitle().toString();
		
		if (title.equals(MENU_SAVE)) {
			fillCounterFromInput();
			saveCounter();
			goToActivity(MainActivity.class);
		}
		else if (title.equals(MENU_CLEAR)) {
			counter.setValue(0L);
			saveCounter();
			goToActivity(MainActivity.class);
		}
		else if (title.equals(MENU_EXIT)) {
			finish();
		}
		
		return true;
	}
	
	private void goToActivity(Class targetActivity) {
		Intent intent = new Intent(this, targetActivity);
		finish();
		startActivity(intent);
	}
}
