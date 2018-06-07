public class Queens {

   /***************************************************************************
    * Return true if queen placement q[n] does not conflict with
    * other queens q[0] through q[n-1]
    ***************************************************************************/
    public static boolean isConsistent(int[] q, int n) {
        for (int i = 0; i < n; i++) {
            if (q[i] == q[n])             return false;   // same column
            if ((q[i] - q[n]) == (n - i)) return false;   // same major diagonal
            if ((q[n] - q[i]) == (n - i)) return false;   // same minor diagonal
        }
        return true;
    }

   /***************************************************************************
    * Returns true if any three queens are in a line
    ***************************************************************************/
    public static boolean noneCollinear(int[] q) {
	int length = q.length;
	
	for (int i = 0; i < length-2; i++) {
	    for (int j = i+1; j < length-1; j++) {
		for (int k = j+1; k < length; k++) {
		    // Based on the determinant test for collinear points
		    // https://www.geeksforgeeks.org/program-check-three-points-collinear/
		    if (((q[i]*(j-k)) + (q[j]*(k-i)) + (q[k]*(i-j))) == 0) {
			return false;
		    }
		}
	    }
	}
	return true;
    }
    
   /***************************************************************************
    * Prints the rows where each queen is placed
    ***************************************************************************/
    public static void printQueens(int[] q) {
        for (int i = 0; i < q.length; i++) {
	    System.out.print(q[i] + " ");
        }  
        System.out.println();
    }


   /***************************************************************************
    *  Try all permutations using backtracking
    ***************************************************************************/
    public static void enumerate(int n) {
        int[] a = new int[n];
        enumerate(a, 0);
    }

    public static void enumerate(int[] q, int k) {
        int n = q.length;
	if (k == n){
	    if (noneCollinear(q)) printQueens(q);
	}
	
        else {
            for (int i = 0; i < n; i++) {
                q[k] = i;
                if (isConsistent(q, k)) enumerate(q, k+1);
            }
        }
    }  


    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        enumerate(n);
    }

}
