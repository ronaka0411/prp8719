//RONAK AGRAWAL cs610 8719 prp
import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class hits8719 {

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
		
		double[] Hub=null;	// value of hub (of current iteration (t))
		double[] Auth=null;   // value of Authority (of current iteration (t))

		double[] pHub = null;	//previous value of hub (of previous iteration (t-1))
		double[] pAuth = null;	//previous value of Authority (of previous iteration (t-1))
		
        
      //Check if the number of arguments is 3 or not
		if(args.length != 3) {
	            System.out.println("Argument format: hits8719 iterations(int) initialvalue(int) filename(name of the file)");
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
		
		//Creating HUB and AUTHORITY vectors for each node (Vertex) or (site)
		Hub = new double[numVertex];
		Auth = new double[numVertex];
		//Initial value assigning
		for(int i=0; i<numVertex; i++) {
			Hub[i] = iniVal;
			Auth[i] = iniVal;
		}        
		
		if(iter>0) { //Start of iterations greater than zero;it is clear that n < vertexLimit, as iterations < 0; we first print Base case
			
				//Base Case
		        System.out.print("Base:    0 :");
		        for(int i = 0; i < numVertex; i++) {
		          System.out.printf(" A/H[%d]=%.6f/%.6f",i,Auth[i],Hub[i]); 
		        }
		        
			
			for(int index = 1; index <= iter; index++) {     //Start of iteration
	            //First Calculate Authority of all vertices and then Hub values
                
				//Start of calculating Authority
				//initialize Authority values to zero, Hub values are still initial Values calculated earlier
				//Because of Greedy Algorithm we do not need to store values of t-2, t-3 iterations
                for(int a = 0; a < numVertex; a++) {
                    Auth[a] = 0.0;
                }
            
                for(int vertex = 0; vertex < numVertex; vertex++) {	//vertex getting links from in Vertex
                    for(int inVertex = 0; inVertex < numVertex; inVertex++) { //inVertex - Vertex giving link to vertex in upper loop
                        if(adjMatrix[inVertex][vertex] == 1) { // if there is link from inVertex --> vertex
                            Auth[vertex] += Hub[inVertex]; 
                        }
                    }
                }
                //End of Calculating Authority 
				
                //Start of Calculating Hub
				for(int h = 0; h < numVertex; h++) {
                    Hub[h] = 0.0;
                }

                for(int vertex = 0; vertex < numVertex; vertex++) { //vertex giving links to outVertex
                    for(int outVertex = 0; outVertex < numVertex; outVertex++) { //outVertex - Vertex taking link from vertex in upper loop
                        if(adjMatrix[vertex][outVertex] == 1) { // if there is link from vertex --> outVertex
                            Hub[vertex] += Auth[outVertex]; 
                        }
                    }
                }
                //End of Calculating Hub
                
                //Start of Scaling values of both Authority and Hub vector
                ScaleValues8719(Auth, numVertex);
                ScaleValues8719(Hub, numVertex);
                //Start of Scaling values of both Authority and Hub vector
                
                //Print of all values in given format for this iteration
                System.out.println();
                System.out.print("Iter:    " + (index) + ":");
                for(int i = 0; i < numVertex; i++) {
                    System.out.printf(" A/H[%d]=%.6f/%.6f",i,Auth[i],Hub[i]); 
                }
   
            }//End of iteration
			
		}// End of iterations>0
		
		else { //when iterations <=0, we use error rate to limit the number of iterations
			//here we need to use previous Authority and Hub values to calculate the difference between two iterations
			pAuth = new double[numVertex];
			pHub = new double[numVertex];
			//Also we need to check number of vertices of the graph
			if (numVertex <= vertexLimit) { // start of when n < 10
				//Base case:
				System.out.print("Base:    0 :");
		        for(int i = 0; i < numVertex; i++) {
		          System.out.printf(" A/H[%d]=%.6f/%.6f",i,Auth[i],Hub[i]); 
		        }
			
				//Here we do not know the number of iterations so we need to keep track of them
				int iterTrack=0;
				do {
					for (int i = 0; i < numVertex; i++) {
						pAuth[i] = Auth[i];
						pHub[i] = Hub[i];
					}
					//Hits Algorithm
					
					//Start of Calculating Authority
					//initialize Authority values to zero, Hub values are still initial Values calculated earlier
					//Because of Greedy Algorithm we do not need to store values of t-2, t-3 iterations
	                for(int a = 0; a < numVertex; a++) {
	                    Auth[a] = 0.0;
	                }
	            
	                for(int vertex = 0; vertex < numVertex; vertex++) {	//vertex getting links from in Vertex
	                    for(int inVertex = 0; inVertex < numVertex; inVertex++) { //inVertex - Vertex giving link to vertex in upper loop
	                        if(adjMatrix[inVertex][vertex] == 1) { // if there is link from inVertex --> vertex
	                            Auth[vertex] += Hub[inVertex]; 
	                        }
	                    }
	                }//End of Calculating Authority
					
	                //Start of Calculating Hub
					for(int h = 0; h < numVertex; h++) {
	                    Hub[h] = 0.0;
	                }
	
	                for(int vertex = 0; vertex < numVertex; vertex++) { //vertex giving links to outVertex
	                    for(int outVertex = 0; outVertex < numVertex; outVertex++) { //outVertex - Vertex taking link from vertex in upper loop
	                        if(adjMatrix[vertex][outVertex] == 1) { // if there is link from vertex --> outVertex
	                            Hub[vertex] += Auth[outVertex]; 
	                        }
	                    }
	                }//End of Calculating Hub
									
					//Start of Scaling values of both Authority and Hub vector
					ScaleValues8719(Auth, numVertex);
	                ScaleValues8719(Hub, numVertex);
	                        
	                iterTrack++;
	                //Print of all values in given format for this iteration
	                System.out.println();
	                System.out.print("Iter:    " + iterTrack + " :");
	                for(int i = 0; i < numVertex; i++) {
	                    System.out.printf(" A/H[%d]=%.6f/%.6f",i,Auth[i],Hub[i]); 
	                }
				} while( isConverge8719(Auth, pAuth,errorRate, numVertex) == false || isConverge8719(Hub, pHub,errorRate, numVertex) == false);
			
			} //end of when n < 10 (vertex limit)
			
			else { // start of when n > 10, large graph we only show the last iteration result for each vertex per line.
				
				int iterTrack=0;
				do {
					for (int i = 0; i < numVertex; i++) {
						pAuth[i] = Auth[i];
						pHub[i] = Hub[i];
					}
					//Hits Algorithm
					
					//Start of Calculating Authority
					//initialize Authority values to zero, Hub values are still initial Values calculated earlier
					//Because of Greedy Algorithm we do not need to store values of t-2, t-3 iterations
	                for(int a = 0; a < numVertex; a++) {
	                    Auth[a] = 0.0;
	                }
	            
	                for(int vertex = 0; vertex < numVertex; vertex++) {	//vertex getting links from in Vertex
	                    for(int inVertex = 0; inVertex < numVertex; inVertex++) { //inVertex - Vertex giving link to vertex in upper loop
	                        if(adjMatrix[inVertex][vertex] == 1) { // if there is link from inVertex --> vertex
	                            Auth[vertex] += Hub[inVertex]; 
	                        }
	                    }
	                }//End of Calculating Authority
					
	                //Start of Calculating Hub
					for(int h = 0; h < numVertex; h++) {
	                    Hub[h] = 0.0;
	                }

	                for(int vertex = 0; vertex < numVertex; vertex++) { //vertex giving links to outVertex
	                    for(int outVertex = 0; outVertex < numVertex; outVertex++) { //outVertex - Vertex taking link from vertex in upper loop
	                        if(adjMatrix[vertex][outVertex] == 1) { // if there is link from vertex --> outVertex
	                            Hub[vertex] += Auth[outVertex]; 
	                        }
	                    }
	                }//End of Calculating Hub
									
					//Start of Scaling values of both Authority and Hub vector
					ScaleValues8719(Auth, numVertex);
	                ScaleValues8719(Hub, numVertex);
	                        
	                iterTrack++;
	           
				} while( isConverge8719(Auth, pAuth,errorRate, numVertex) == false || isConverge8719(Hub, pHub,errorRate, numVertex) == false);
				//Print only the last iteration values in given format
				
                System.out.print("Iter    :" + iterTrack);
				for(int i = 0; i < numVertex; i++) {
					System.out.println();
                    System.out.printf(" A/H[ %d]=%.6f/%.6f",i,Auth[i],Hub[i]); 
                }
				
			} //End of when N > 10
			
		} //End of when iterations <=0
	System.out.println();	
	System.out.println( new Date().toString());			
	}
	//end of main
	
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
	
	//Set error rate according to the number of iteration value
	public static double setErrorRate8719 ( int iterationVal) {
		//if user inputs number of iteration value less than equal to zero
		if (iterationVal == 0) {
			return (1.0/Math.pow(10.0,5.0)); //for iterations = 0, return error rate of 10^-5
		}
		else {
			return (1.0/Math.pow(10.0, -iterationVal)); //for iterations = n, n<0, return error rate of 10^-n
		}
	}
	
	//Scale each authority and HUb value for each vertex in each iteration (Normalization)
	public static void ScaleValues8719(double[] vector, int numVert) {
		double factor = 0.0;
        double sum = 0.0;
        for(int i = 0; i < numVert ; i++) {
            sum += vector[i]*vector[i];    
        }
        factor = Math.sqrt(sum); 
        for(int i = 0; i < numVert; i++) {
            vector[i] = vector[i]/factor;
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