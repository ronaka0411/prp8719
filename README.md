# prp8719 (prp aka programming project)
Kleinberg’s HITS Algorithm and Google’s PageRank algorithm in Java


Compilation/ Bug Report:
1. Use of graph with no negative values for vertices and edges
2. If the graph is not formatted properly means if number of edges is not equal to the actual number after reading whole file, then the algorithm throws exception. Only integer values should be included in file.
3. If the value or argument 0 and argument 1 is a fraction then algorithm throws exception
4. For HITS algorithm, for initial value 0, the authority and Hub values come out to be 'NaN'.
5. Both the algorithms do not have any logic to be executed for vertices upper bound 1,000,000 vertices.
6. File name without any extension (.txt) also works but should be exactly same as the saved file name.
7. Tested both algorithms for small and big (20 vertices) graphs on AFS server.
8. Not tested for very large graph (vertices > 20).
9. For some of the criteria such as number of arguments to be 3, value of second argument to be greater than equal to -2 and less than equal to 1, number of vertices value (first value in text file) should not be negative. The algorithm exits and shows defensive nature.
10. Tested unzipping the archive file and compilation of the algorithm, on personal PC with JAVAC version 1.8.0_151 and on AFS JAVAC version 1.8.0_144
