package com.bid.smc.request;

public class CalculateDistance {

	private CallerContext callerContext;

    private Waypoints[] waypoints;

    private String[] exceptionPaths;

    private String[] options;

    public CallerContext getCallerContext ()
    {
        return callerContext;
    }

    public void setCallerContext (CallerContext callerContext)
    {
        this.callerContext = callerContext;
    }

    public Waypoints[] getWaypoints ()
    {
        return waypoints;
    }

    public void setWaypoints (Waypoints[] waypoints)
    {
        this.waypoints = waypoints;
    }

    public String[] getExceptionPaths ()
    {
        return exceptionPaths;
    }

    public void setExceptionPaths (String[] exceptionPaths)
    {
        this.exceptionPaths = exceptionPaths;
    }

    public String[] getOptions ()
    {
        return options;
    }

    public void setOptions (String[] options)
    {
        this.options = options;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [callerContext = "+callerContext+", waypoints = "+waypoints+", exceptionPaths = "+exceptionPaths+", options = "+options+"]";
    }
    
}
