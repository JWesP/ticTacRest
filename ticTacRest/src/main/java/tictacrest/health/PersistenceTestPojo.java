package tictacrest.health;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class PersistenceTestPojo {

	@Element
	private int num;
	
	@Element
	private String text;
	
	public PersistenceTestPojo(){
		// empty constructor
	}
	
	@Override
    public boolean equals(Object obj) {
       if (!(obj instanceof PersistenceTestPojo))
            return false;
        if (obj == this)
            return true;

        PersistenceTestPojo rhs = (PersistenceTestPojo) obj;
        if (this.getNum() == rhs.getNum() && this.getText().equals(rhs.getText())){
        	return true;
        }
        else {
        	return false;
        }
    }

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
