package cz.jhorcicka.counters.model;

public class Counter {
	private long id = 0; 
	private String name = "";
	private String note = "";
	private long value = 0;  
	private String created = "";
	private String lastUpdate = "";
	
	public Counter(String name) {
		this.name = name;
	}
	public void add(int incrementValue) {
		value += incrementValue;
	}
	public void sub(int decrementValue) {
		value -= decrementValue;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String toString() {
		return String.format("%s: %s", name, value); 
	}
}
