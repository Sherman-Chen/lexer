package lexer5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class NFA {
	private Stack<Integer> oldState=
			new Stack<Integer>();
	private Stack<Integer> newState=
			new Stack<Integer>();
	private boolean[] alreadyOn=
			new boolean[100];
	private ArrayList<HashMap<String
	,ArrayList<Integer>>> move=new ArrayList<HashMap<String
		,ArrayList<Integer>>>();
	ArrayList<Integer> F=new ArrayList<Integer>();
	private final int STATE_NULL=-1;
	public NFA(){
		ArrayList<Integer> fa_states=new ArrayList<Integer>();
		fa_states.add(0);
		fa_states.add(1);
		ArrayList<Integer> fb_states=new ArrayList<Integer>();
		fb_states.add(0);
		ArrayList<Integer> fN_states=new ArrayList<Integer>();
		fN_states.add(STATE_NULL);
		Map<String, List<Integer>> f_map=new HashMap<String, List<Integer>>();
		f_map.put("a", fa_states);
		f_map.put("b", fb_states);
		f_map.put("", fN_states);
		move.add((HashMap)f_map);
		//1   /    {2}   /
		ArrayList<Integer> sa_states=new ArrayList<Integer>();
		sa_states.add(STATE_NULL);
		ArrayList<Integer> sb_states=new ArrayList<Integer>();
		sb_states.add(2);
		ArrayList<Integer> sN_states=new ArrayList<Integer>();
		sN_states.add(STATE_NULL);
		Map<String, List<Integer>> s_map=new HashMap<String, List<Integer>>();
		s_map.put("a", sa_states);
		s_map.put("b", sb_states);
		s_map.put("", sN_states);
		move.add((HashMap)s_map);
		//2   /    {3}   /
		ArrayList<Integer> ta_states=new ArrayList<Integer>();
		ta_states.add(STATE_NULL);
		ArrayList<Integer> tb_states=new ArrayList<Integer>();
		tb_states.add(3);
		ArrayList<Integer> tN_states=new ArrayList<Integer>();
		tN_states.add(STATE_NULL);
		Map<String, List<Integer>> t_map=new HashMap<String, List<Integer>>();
		t_map.put("a", ta_states);
		t_map.put("b", tb_states);
		t_map.put("", tN_states);
		move.add((HashMap)t_map);
		//3   /     /    /
		ArrayList<Integer> foa_states=new ArrayList<Integer>();
		foa_states.add(STATE_NULL);
		ArrayList<Integer> fob_states=new ArrayList<Integer>();
		fob_states.add(STATE_NULL);
		ArrayList<Integer> foN_states=new ArrayList<Integer>();
		foN_states.add(STATE_NULL);
		Map<String, List<Integer>> fo_map=new HashMap<String, List<Integer>>();
		fo_map.put("a", foa_states);
		fo_map.put("b", fob_states);
		fo_map.put("", foN_states);
		move.add((HashMap)fo_map);
		F.add(3);
	}
	public boolean run() throws IOException{
		int initState=0;
		addState(initState);
		oldState.add(0);
		char c=(char) System.in.read();
		while(c!=-1 && c!='\r'){
			four(Character.toString(c));
			c=(char) System.in.read();
		}
		
		for(;oldState.size()>0;){
			int i=oldState.pop();
			if(F.contains(i)){
				return true;
			}
		}
		
		return false;
	}
	public ArrayList<Integer> move(int s,String a){
		ArrayList<Integer> tmp=
				new ArrayList<Integer>();
		
		for(int j:move.get(s).get(a)){
			if(j!=STATE_NULL && !tmp.contains(j)){
				tmp.add(j);
			}
				
		}
		
		return tmp;
		
	}
	private void four(String c){
		for(;oldState.size()>0;){
			int s=oldState.pop();
			for(int t:move(s,c)){
				if(t!=STATE_NULL &&!alreadyOn[t]){
					addState(t);
				}
			}
			
		}
		
		for(;newState.size()>0;){
			int s=newState.pop();
			oldState.push(s);
			alreadyOn[s]=false;
		}
	}
	private void addState(int s){
		alreadyOn[s]=true;
		newState.push(s);
		for(int t:move(s, "")){
			if(t!=STATE_NULL && !alreadyOn[t]){
				addState(t);
			}
		}
	}
	public static void main(String[] args) throws IOException {
		NFA nfa=new NFA();
		System.out.println(nfa.run());
	}

}
