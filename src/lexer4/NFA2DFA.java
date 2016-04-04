package lexer4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * (a|b)*abb
 * 
 * ******NFA*********
 *    a     b    /
 *------------------
 *0 {0,1}  {0}   /
 *1   /    {2}   /
 *2   /    {3}   /
 *3   /     /    /
 * *******************

 */
public class NFA2DFA {
	class Dstate{
		private ArrayList<Integer> states=
				new ArrayList<Integer>();
		private boolean tag=false;
		public Dstate(ArrayList<Integer> state,boolean tag){
			this.states=state;
			this.tag=tag;
		}
		public ArrayList<Integer> getStates() {
			return states;
		}
		public void setStates(ArrayList<Integer> states) {
			this.states = states;
		}
		public boolean isTag() {
			return tag;
		}
		public void setTag(boolean tag) {
			this.tag = tag;
		}
		
	}
	private List<Map<String, List<Integer>>> NFAState=
			new ArrayList<Map<String,List<Integer>>>();
	private List<Map<String, List<Integer>>> DFAState=
			new ArrayList<Map<String,List<Integer>>>();
	
	private final int STATE_NULL=-1;
	public NFA2DFA(){
		//init NFA((a|b)*abb)
		//0 {0,1}  {0}   /
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
		NFAState.add(f_map);
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
		NFAState.add(s_map);
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
		NFAState.add(t_map);
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
		NFAState.add(fo_map);
	}
	
	public void Conv(){
		int numState=4;
		boolean add1=true,add2=true;
		int initState=0;
		ArrayList<Dstate> dstates=new ArrayList<NFA2DFA.Dstate>();
		ArrayList<Integer> tmp1=
				new ArrayList<Integer>();
		ArrayList<Integer> tmp2=
				new ArrayList<Integer>();
		ArrayList<Integer> tmp=new ArrayList<Integer>();
		tmp.add(initState);
		dstates.add(new Dstate(tmp, false));
		for(int i=0;dstates.size()>i && dstates.get(i).isTag()==false;i++){
			dstates.get(i).setTag(true);
			ArrayList<Integer> tmp3=dstates.get(i).getStates();
			tmp1=closure(move(tmp3, "a"));
			tmp2=closure(move(tmp3, "b"));
			for(Dstate j:dstates){
				if(j.getStates().equals(tmp1)){
					add1=false;
					
				}
				if (j.getStates().equals(tmp2)) {
					add2=false;
					
				}
				
			}
			if(add1){
				dstates.add(new Dstate(tmp1, false));
				
			}
			if(add2){
				dstates.add(new Dstate(tmp2, false));
			}
			add1=add2=true;
			Map<String, List<Integer>> tmp_map=
					new HashMap<String, List<Integer>>();
			tmp_map.put("a", tmp1);
			tmp_map.put("b", tmp2);
			DFAState.add(tmp_map);
		}

		
		
	}
	public void printDFA(){
		for(Map<String, List<Integer>> m:DFAState){
			System.out.println(m.toString());
		}
	}
	public Stack<Integer> move(ArrayList<Integer> t,String a){
		Stack<Integer> tmp=new Stack<Integer>();
		for(int i:t){
			for(int j:NFAState.get(i).get(a)){
				if(j!=STATE_NULL){
					tmp.add(j);
				}
				
			}
		}
		return tmp;
		
	}
	public ArrayList<Integer> closure(Stack<Integer> stack){
		ArrayList<Integer> closure_t=new ArrayList<Integer>();
		while(stack.size()>0){
			int t=stack.pop();
			closure_t.add(t);
			for(int u:NFAState.get(t).get("")){
				if(closure_t.indexOf(u)!=-1){
					closure_t.add(u);
				}
			}
		}
		return closure_t;
	}
	public static void main(String[] args) {
		NFA2DFA tmp=new NFA2DFA();
		tmp.Conv();
		tmp.printDFA();
	}

}


















