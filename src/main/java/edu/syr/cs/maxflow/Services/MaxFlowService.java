package edu.syr.cs.maxflow.Services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import edu.syr.cs.maxflow.Models.CalculateReq;
import edu.syr.cs.maxflow.Models.EdgeAdj;
import edu.syr.cs.maxflow.Models.Edges;
import edu.syr.cs.maxflow.Models.MaxCalculateResponse;

@Service
public class MaxFlowService {

    private static final Logger LOGGER = Logger.getLogger(MaxFlowService.class.getName());

    public MaxCalculateResponse calculate(CalculateReq req) {
        req.setS(req.getS()-1);
        req.setT(req.getT()-1);
        int[] level = new int[req.getNodes()];
        List<EdgeAdj>[] adj = new ArrayList[req.getNodes()];
        for (int i = 0; i < req.getNodes(); i++) {
            adj[i] = new ArrayList<EdgeAdj>();
        }
        for (int i = 0; i < req.getEdges().size(); i++) {
              // Forward edge : 0 flow and C capacity
        EdgeAdj a = new EdgeAdj(req.getEdges().get(i).getTarget()-1,
        0,
        req.getEdges().get(i).getCapacity(),
        adj[req.getEdges().get(i).getTarget()-1].size() );
       
        // Back edge : 0 flow and 0 capacity
        EdgeAdj b = new EdgeAdj(
        req.getEdges().get(i).getSource()-1,
        0,
        0,
        adj[req.getEdges().get(i).getSource()-1].size() 
        );

        adj[req.getEdges().get(i).getSource()-1].add(a);
        adj[req.getEdges().get(i).getTarget()-1].add(b);
    
        }

        for(int i=0;i<adj.length;i++){
            for(EdgeAdj e:adj[i]){
               // System.out.println(e.v+" "+e.flow+" "+e.C+" "+e.rev);
            }
        }	


        if (req.getS() == req.getT()) {
            return null;
        }
 
        int total = 0;
 
          // Augment the flow while there is path
        // from source to sink
       // LOGGER.info("BFS: " + BFS(req.getS(), req.getT(), req.getNodes(),level,adj));
        while (BFS(req.getS(), req.getT(), req.getNodes(),level,adj) == true) {
           
              // store how many edges are visited
            // from V { 0 to V }
            int[] start = new int[req.getNodes() + 1];
 
              // while flow is not zero in graph from S to D
            while (true) {
                
                int flow = sendFlow(req.getS(), Integer.MAX_VALUE, req.getT(), start,level,adj);
               // LOGGER.info("Flow: " + flow);
                if (flow == 0) {
                    break;
                }
               
                  // Add path flow to overall flow
                total += flow;
            }
        }
            LOGGER.info("Total flow: " + total);
            for(int i=0;i<adj.length;i++){
              
                for(EdgeAdj e:adj[i]){
                  if(e.flow>0)
                    LOGGER.info(i+" "+e.v+" "+e.flow+" "+e.C+" "+e.rev);
                }
            }
            List<Edges> edges = new ArrayList<>();
            for(int i=0;i<adj.length;i++){
              
                for(EdgeAdj e:adj[i]){
                  if(e.flow>0)
                    edges.add(new Edges(i+1,e.v+1,e.flow));
                }
            }
          // Return maximum flow
        return new MaxCalculateResponse(total,edges);
    }

    public int sendFlow(int u, int flow, int t, int start[], int[] level, List<EdgeAdj>[] adj) {
       
        // Sink reached
      if (u == t) {
          return flow;
      }

        // Traverse all adjacent edges one -by - one.
      for (; start[u] < adj[u].size(); start[u]++) {
         
            // Pick next edge from adjacency list of u
          EdgeAdj e = adj[u].get(start[u]);

          if (level[e.v] == level[u] + 1 && e.flow < e.C) {
                // find minimum flow from u to t
              int curr_flow = Math.min(flow, e.C - e.flow);

              int temp_flow = sendFlow(e.v, curr_flow, t, start,level,adj);
               
                // flow is greater than zero
              if (temp_flow > 0) {
                    // add flow  to current edge
                  e.flow += temp_flow;
                 
                    // subtract flow from reverse edge
                  // of current edge
                  adj[e.v].get(e.rev).flow -= temp_flow;
                  return temp_flow;
              }
          }
      }

      return 0;
  }

    public boolean BFS(int s, int t, int V,int[] level, List<EdgeAdj>[] adj) {
        for (int i = 0; i < V; i++) {
            level[i] = -1;
        }
 
        level[s] = 0;        // Level of source vertex
 
           // Create a queue, enqueue source vertex
        // and mark source vertex as visited here
        // level[] array works as visited array also.
        LinkedList<Integer> q = new LinkedList<Integer>();
        q.add(s);
 
        ListIterator<EdgeAdj> i;
        while (q.size() != 0) {
            int u = q.poll();
 
            for (i = adj[u].listIterator(); i.hasNext();) {
                EdgeAdj e = i.next();
               // LOGGER.info("Edge: " + e.v +" "+ e.flow +" "+ e.C +" "+ e.rev);
                if (level[e.v] < 0 && e.flow < e.C) {
                        LOGGER.info("Edge: " + e.v);
                      // Level of current vertex is -
                      // Level of parent + 1
                    level[e.v] = level[u] + 1;
                   // LOGGER.info("Level: " + level[e.v]);
                    q.add(e.v);
                }
            }
        }
        //LOGGER.info("Level: " + level[t]);
          // IF we can not reach to the sink we
        return level[t] < 0 ? false : true;
    }
 
    

}
