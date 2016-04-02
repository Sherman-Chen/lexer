package lexer1;

public class Word extends Token{
	public final String elem;
	public Word(int id,String word){
		super(id);
		this.elem=new String(word);
	}
	
	

}
