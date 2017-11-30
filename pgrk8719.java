//RONAK AGRAWAL cs610 8719 prp
import static java.lang.Math.*;
import java.io.*;
import java.util.*;

public class pgrk8719 {

	public static void main(String[] args) {
		System.out.println( new Date().toString()); //Prints out the system's current date and time
		
		//Initialization of all the variables
				double iniVal = 0.0;
				double errorRate = 0.0;
				int iter = 0;
				int vertexLimit = 10;
				int numVertex = 0;
				int numEdges = 0;
				int[][] adjMatrix = null; //adjacency matrix representation of graph
				
				
				double[] pageRank=null; // Page rank value vector 
				double[] prevPR = null; // Page rank value of previous iteration (t-1)
				int[] outDegree= null;  // NUmber of outgoing links from a vertex or node
				final double d = 0.85;  // constant factor used to calculate page rank value
				
 
		      //Check if the number of arguments is 3 or not
				if(args.length != 3) {
			            System.out.println("Argument format: hits8719 iterations(int) initialvalue(int) filename(name of file)");
			            System.exit(1);
			    }
			  //store the argument values
			    iter = Integer.parseInt(args[0]);
			    int initial_value = Integer.parseInt(args[1]);
			    if(initial_value < -2 || initial_value > 1)  {
			    	System.out.println("Enter initialvalue in the range of -2 to 1.");
			        System.exit(1);
			    }
			        
			    String filename = args[2];
			  //end of taking argument values      
			   
			  //start reading input file and assigning values for no of vertex and edges creating adjacency matrix.  
					try {      
				        
				        Scanner sc = new Scanner(new File(filename));              
				        
				        numVertex = sc.nextInt();
				        numEdges = sc.nextInt();
				        				       
				        //Adjacency matrix representation of graph
				        adjMatrix = new int[numVertex][numVertex];
				        
				        //initializing all the values of vertex to zero (initial no edges)
				        for(int i = 0; i < numVertex; i++) {
				         for(int j = 0; j < numVertex; j++) {
				        	 adjMatrix[i][j] = 0;
				         }
				        } 
				        
				        //building each edge in the matrix by adding 1 to each edge
				        for(int i=0; i<numEdges; ++i) {
				        	adjMatrix[sc.nextInt()][sc.nextInt()] = 1; 
				            
				        }
				        sc.close();
				
					}
				    catch(FileNotFoundException e) {
				    	System.out.println("File not found");
				    	System.exit(1);
				    }	
					//end of reading input and assigning values for no of vertex and edges creating adjacency matrix.
				
				//Main logic of program according to the given conditions, criteria
				//start assign the initial value based on number of vertices
				if(numVertex > vertexLimit) {
				   	iniVal = InitValue8719(-1,numVertex); //if no of vertex greater than 10 then according to criteria arg[1] is -1
				    iter = 0;
				    errorRate = setErrorRate8719(iter);
				}
				else if(numVertex > 0 && numVertex <=vertexLimit) {
				  	iniVal = InitValue8719(Integer.parseInt(args[1]),numVertex);
				  	if(Integer.parseInt(args[0]) <=0) {
				  		
				  		errorRate = setErrorRate8719(Integer.parseInt(args[0]));
				  	}
				  	else {
				  		iter = Integer.parseInt(args[0]);
				  	}
				}
				else {
				   	System.out.println("Number of vertex cannot be negative.");
				   	System.exit(1);
				}	
				//Initial value and iteration value set	
	
		///////////////// START ////////////////////
						
		//Start of calculating number of outgoing links for each vertex (out degree)
		outDegree = new int[numVertex];
		for (int i=0; i < numVertex; i++) {
			outDegree[i] = 0;
			for (int j = 0; j < numVertex; j++) {
				outDegree[i] += adjMatrix[i][j];
			}
		}
		//End of calculating Out Degree of each vertex.
		
		//Initialize all page ranks with the initial value
		pageRank = new double[numVertex];
		
		prevPR = new double[numVertex];
		for(int i=0; i<numVertex; i++) {
			prevPR[i] = iniVal;
		}   
		//End of initialization of page ranks
		
		if(iter>0) { //Start of case: iterations >=1, i.e. it is clear that n < vertexLimit
			
			//Print the Base case as with the initial values of page ranks
			System.out.print("Base    : 0");
	        for(int vertex = 0; vertex < numVertex; vertex++) {
	            System.out.printf(" :P[" + vertex + "]=%.6f",prevPR[vertex]);
	        }
	        //End of Base case
	        
	        //Start calculating page rank of each vertex based on PageRank formula
	        for (int index=1; index <= iter; index++) { //Start of iteration
	        
		        //Initialize the pageRank vector to all zero
		        for (int i = 0; i < numVertex; i++) {
		        	pageRank[i] = 0.0;
		        }
		        
		        //Calculate contribution of each vertex towards pageRank of vertex under test (vertex --> testV)
		        for (int testV = 0; testV < numVertex; testV++) {
		        	for (int vertex = 0; vertex < numVertex; vertex++) {
		        		if(adjMatrix[vertex][testV] == 1) {
		        			pageRank[testV] += prevPR[vertex]/outDegree[vertex];
		        		}
		        	}
		        }
		        //End of calculating contribution
		        
		        //Start of Calculating actual page Rank from the Google's Page Rank Formula where d
		        //Calculate and print the pageRank values for each iteration
		        System.out.println();
	            System.out.print("Iter    : " + index);
		        for (int vertex=0; vertex < numVertex; vertex++) {
		        	pageRank[vertex] = (1-d)/numVertex + d*pageRank[vertex];
		        	System.out.printf(" :P[" + vertex + "]=%.6f",pageRank[vertex]);
		        }
	
		        //Retain the values of pageRank in the prevPR vector, to be used in next iteration
		        for (int k = 0; k < numVertex; k++) {
		        	prevPR[k] = pageRank[k];
		        }

	        } //End of iterations
	   
		}	//End of case: iterations >=1, i.e. it is clear that n < 10
		
		else { //Start of case when iterations <=0, we have two cases here n > 10 or n < 10
			if (numVertex <= vertexLimit) { // start of when n <= vertexLimit
				//Base case:
				System.out.print("Base    : 0");
		        for(int vertex = 0; vertex < numVertex; vertex++) {
		            System.out.printf(" :P[" + vertex + "]=%.6f",prevPR[vertex]);
		        }
			 
				//Also here we do not know the number of iterations so we need to keep track of them
				int iterTrack=0;
				do {
					
					//we need this retaining for loop to be run for all iterations except the first iteration
					if (iterTrack == 0) { //do not retain any value for 1st iteration
					}
					else {
						//Retain the values of pageRank in the prevPR vector, to be used in next iteration
				        for (int k = 0; k < numVertex; k++) {
				        	prevPR[k] = pageRank[k];
				        }						
					}
					
					//Initialize the pageRank vector to all zero
			        for (int i = 0; i < numVertex; i++) {
			        	pageRank[i] = 0.0;
			        }
			        
			        //Calculate contribution of each vertex towards pageRank of vertex under test (vertex --> testV)
			        for (int testV = 0; testV < numVertex; testV++) {
			        	for (int vertex = 0; vertex < numVertex; vertex++) {
			        		if(adjMatrix[vertex][testV] == 1) {
			        			pageRank[testV] += prevPR[vertex]/outDegree[vertex];
			        		}
			        	}
			        }
			        //End of calculating contribution
			        
			        iterTrack++;
			        //Start of Calculating actual page Rank from the Google's Page Rank Formula where d
			        //Calculate and print the pageRank values for each vertex for this iteration
			        System.out.println();
		            System.out.print("Iter    : " + iterTrack);
			        for (int vertex=0; vertex < numVertex; vertex++) {
			        	pageRank[vertex] = (1-d)/numVertex + d*pageRank[vertex];
			        	System.out.printf(" :P[" + vertex + "]=%.6f",pageRank[vertex]);
			        }
				} while (isConverge8719(pageRank,prevPR,errorRate,numVertex) == false);
				
			} //End of case: n <= vertexLimit and iteration <=0
			
			else { //Start of case: n > vertexLimit and iteration = 0
				//Also here we do not know the number of iterations so we need to keep track of them
				int iterTrack=0;
				do {
					
					//we need this retaining for loop to be run for all iterations except the first iteration
			
					if (iterTrack == 0) { //do not retain any value for 1st iteration
					}
					else {
						//Retain the values of pageRank in the prevPR vector, to be used in next iteration
				        for (int k = 0; k < numVertex; k++) {
				        	prevPR[k] = pageRank[k];
				        }						
					}
					
					//Initialize the pageRank vector to all zero
			        for (int i = 0; i < numVertex; i++) {
			        	pageRank[i] = 0.0;
			        }
			        
			        //Calculate contribution of each vertex towards pageRank of vertex under test (vertex --> testV)
			        for (int testV = 0; testV < numVertex; testV++) {
			        	for (int vertex = 0; vertex < numVertex; vertex++) {
			        		if(adjMatrix[vertex][testV] == 1) {
			        			pageRank[testV] += prevPR[vertex]/outDegree[vertex];
			        		}
			        	}
			        }
			        //End of calculating contribution
			        
			        iterTrack++;
			        //Start of Calculating actual page Rank from the Google's Page Rank Formula where d
			        //Calculate the pageRank values for each vertex for this iteration
			        
			        for (int vertex=0; vertex < numVertex; vertex++) {
			        	pageRank[vertex] = (1-d)/numVertex + d*pageRank[vertex];
			        }
			        
				} while (isConverge8719(pageRank,prevPR,errorRate,numVertex) == false);
				//Print the pageRank values for each vertex for last iteration
				System.out.print("Iter    : " + iterTrack);
				
	            for (int vertex=0; vertex < numVertex; vertex++) {
	            	System.out.println();
		           	System.out.printf("P["   + vertex + "]=%.6f",pageRank[vertex]);
		        }
			}//End of case: n > vertexLimit and iteration = 0
			
		} //End of case iteration <=0
	System.out.println();	
	System.out.println( new Date().toString());			
	} //End of main

	//Start of initial value assigning
		public static double InitValue8719 ( int iniVal, int numVertices)	{
			
			double initialvalue = 0;
			// if input is 0, then the initial value is 0
			if(iniVal == 0 ) {
				initialvalue = 0.0;
			}
			// if input is 1, then the initial value is 1
			else if(iniVal == 1) {
				initialvalue = 1.0;
			}
			// if input is -1, then the initial value is 1 / n
			else if(iniVal == -1) {
				
				initialvalue = 1.0/numVertices;
			}
			// if input is -2, then the initial value is 1 / square root(n)
			else if(iniVal == -2) {
				initialvalue = 1.0/Math.sqrt( numVertices );
			}		
			
			return initialvalue;
		}
		//end of initial value assigning
		
		//Scale each authority and HUb value for each vertex in each iteration (Normalization)
		public static double setErrorRate8719 ( int iterationVal) {
			//if user 
			if (iterationVal == 0) {
				return (1.0/Math.pow(10.0,5.0));
			}
			else {
				return (1.0/Math.pow(10.0, -iterationVal));
			}
		}
		
		//start of converge function to converge iteration values to error rate
		public static boolean isConverge8719(double[] curr, double[] prev, double errorRate, int numVert)
	    {
	       for(int i = 0 ; i < numVert; i++) {
	           if ( abs(prev[i] - curr[i]) > errorRate ) 
	             return false;
	       }
	       return true;
	    } 
}
