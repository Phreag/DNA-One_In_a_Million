package inb_bach;

import java.util.Arrays;

public class CodeEvaluation {
	private double[][] values;
	
	public CodeEvaluation(double[][] values){
		this.values=values;
	}
	public void countBetterCodes(){
		System.out.println("Evaluating Results and counting better codes found...");
		int[] betterCodes=new int[values[0].length+1];
		for (int i=0;i<values.length;i++){
			boolean isCompleteBetter=true;
			for (int x=0;x<values[0].length;x++){
				if(values[i][x]<values[0][x]){
					betterCodes[x]++;
				}else{
					isCompleteBetter=false;
				}
			}
			if(isCompleteBetter)betterCodes[betterCodes.length-1]++;
		}
		System.out.println("Number of better codes found:");
		System.out.println(Arrays.toString(betterCodes));
	}

}
