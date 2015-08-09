package tictacrest;

public class Pair<A, B> {
	private A a;
	private B b;
	
	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}
	
	public A getFirst() {
		return a;
	}
	
	public B getSecond() {
		return b;
	}
	
	public boolean equals(Object obj) {
		if(!(obj instanceof Pair))
			return false;
		Pair other = (Pair)obj;
		
		if(a == null) {
			if(other.a != null)
				return false;
		} else if(!a.equals(other.a))
			return false;
		
		if(b == null) {
			if(other.b != null)
				return false;
		} else if(!b.equals(other.b))
			return false;
		
		return true;
	}
	
	public int hashCode() {
		int hashA = a == null ? 0 : a.hashCode();
		int hashB = b == null ? 0 : b.hashCode();
		
		return hashA + hashB;
	}
	
	public String toString() {
		return String.format("(%s, %s)", a, b);
	}
}
