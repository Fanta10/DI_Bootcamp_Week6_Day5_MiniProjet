package miniprojet;

public class Card {
	//attributs
	private int value;
	private String title;
	private String type;
	public boolean isHit = false;
	
	//constructeur sans parametres
	public Card() {
		super();
		// TODO Auto-generated constructor stub
	}
	//constructeur avec parametres

	public Card(int value, String title, String type) {
		super();
		this.value = value;
		this.title = title;
		this.type = type;
	}
	//getters et setters

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Card hit() {
        this.isHit = true;

        return this;
    }
	
	
	
	
	
	 

	    

}
