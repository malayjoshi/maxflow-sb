package edu.syr.cs.maxflow.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edges {
    private int source;
    private int target;
    private int capacity;
}
