package lexer2;

import java.io.IOException;
import java.util.Hashtable;

import com.sun.org.apache.bcel.internal.generic.NEW;

import lexer1.Num;
import lexer1.Token;
import lexer1.Word;

/**
 * 词法分析器，是基于状态转换图
 * @author cxm
 *
 */
public class Lexer {
	private final int STATE_FINALLY=0;
	private final int STATE_NUM=1;
	private final int STATE_STRING=2;
	
	public static final int NUM=256;
	public static final int ID=257;
	public static final int TRUE=258;
	public static final int FALSE=259;
	
	Hashtable words=new Hashtable();
	public Lexer(){
		words.put("true", new Word(TRUE, "true"));
		words.put("true", new Word(TRUE, "true"));
	}
	public Token scan() throws IOException{
		int currentState=0;
		String word="";
		
		while(true){
			char c=(char) System.in.read();
			switch (currentState) {
			case 0:
				if(Character.isDigit(c)){
					word+=c;
					currentState=STATE_NUM;
				}else if(Character.isAlphabetic(c)){
					word+=c;
					currentState=STATE_STRING;
				}
				
				break;
			case STATE_NUM:
				if(!Character.isDigit(c)){
					Token token=new Num(NUM, Integer.parseInt(word));
					return token;
				}
				word+=c;
				break;
			case STATE_STRING:
				if(!Character.isAlphabetic(c)){
					Word word3=(Word) words.get(word);
					if(word3!=null){
						return word3;
					}
					Word word2=new Word( ID,word);
					return word2;
					
				}
				word+=c;
				break;

			default:
				break;
			}
			
		}
	}
	public static void main(String[] args) throws IOException {
		Lexer lexer=new Lexer();
		while(true){
			Token t=lexer.scan();
			switch (t.getId()) {
			case Lexer.NUM:
				System.out.println("识别到数字:"+((Num) t).getValue());
				break;
			case Lexer.ID:
				System.out.println("识别到字符："+((Word) t).elem);
				break;
			case Lexer.TRUE:
				System.out.println("识别到布尔值："+((Word) t).elem);
				break;
			case Lexer.FALSE:
				System.out.println("识别到布尔值:"+((Word) t).elem);
				break;

			default:
				System.out.println("无法识别");
				break;
			}
			
		}
	}

}
