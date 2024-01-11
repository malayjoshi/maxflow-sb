package edu.syr.cs.maxflow.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EdgeAdj {
    
        public int v; // Vertex v (or "to" vertex)
                     // of a directed edge u-v. "From"
                     // vertex u can be obtained using
                     // index in adjacent array
       
        public int flow;    // flow of data in edge
       
        public int C;        // Capacity
       
        public int rev;        // To store index of reverse
                           // edge in adjacency list so that
                           // we can quickly find it.
         
        
}
