package com.bid.smc.request;

public class Waypoints {

	private Coords[] coords;

    private String linkType = "NEXT_SEGMENT";

    public Coords[] getCoords ()
    {
        return coords;
    }

    public void setCoords (Coords[] coords)
    {
        this.coords = coords;
    }

    public String getLinkType ()
    {
        return linkType;
    }

    public void setLinkType (String linkType)
    {
        this.linkType = linkType;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [coords = "+coords+", linkType = "+linkType+"]";
    }
    
}
