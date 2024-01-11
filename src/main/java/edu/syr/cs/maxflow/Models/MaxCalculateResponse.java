package edu.syr.cs.maxflow.Models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaxCalculateResponse {
    private int maxFlow;
    private List<Edges> edges;
}


