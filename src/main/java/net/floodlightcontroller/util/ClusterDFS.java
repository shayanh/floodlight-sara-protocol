package net.floodlightcontroller.util;

public class ClusterDFS {
    long dfsIndex;
    long parentDfsIndex;
    long lowpoint;
    boolean visited; 

    public ClusterDFS() {
        visited = false; 
        dfsIndex = Long.MAX_VALUE;
        lowpoint = Long.MAX_VALUE;
    }

    public long getDfsIndex() {
        return dfsIndex;
    }

    public void setDfsIndex(long dfsIndex) {
        this.dfsIndex = dfsIndex;
    }

    public long getParentDfsIndex() {
        return parentDfsIndex;
    }

    public void setParentDfsIndex(long parentDfsIndex) {
        this.parentDfsIndex = parentDfsIndex;
    }
    public long getLowpoint() {
        return lowpoint;
    }

    public void setLowpoint(long lowpoint) {
        this.lowpoint = lowpoint;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }


}
