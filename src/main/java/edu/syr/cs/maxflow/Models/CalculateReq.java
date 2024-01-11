package edu.syr.cs.maxflow.Models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculateReq {
    private int nodes;
    private int noOfEdges;
    private int s;
    private int t;
    private List<Edges> edges;
}
