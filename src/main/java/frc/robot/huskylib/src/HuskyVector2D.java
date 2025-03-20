package frc.robot.huskylib.src;

public class HuskyVector2D {
    private double m_x;
    private double m_y;
    private double m_equalTreshold = 0.0;

    public HuskyVector2D(double InitX, double InitY)
    {
        m_x = InitX;
        m_y = InitY;
    }

    public HuskyVector2D()
    {
        this(0.0, 0.0);
    }

    public HuskyVector2D(HuskyVector2D Orig)
    {
        m_x = Orig.getX();
        m_y = Orig.getY();
    }

    public double getX() { return m_x; }
    public void setX(double NewX) { m_x = NewX; }
    public double getY() { return m_y; }
    public void setY(double NewY) { m_y = NewY; }
    public double getEqualThreshold() { return m_equalTreshold; }
    public void setEqualThreshold(double NewThresh) { m_equalTreshold = NewThresh; }

    public void setVals(double NewX, double NewY)
    {
        setX(NewX);
        setY(NewY);
    }

    public void setVals(HuskyVector2D OtherVec)
    {
        setX(OtherVec.getX());
        setY(OtherVec.getY());
    }

    public double getMagnitude()
    {
        return Math.sqrt((m_x * m_x) + (m_y * m_y));
    }

    public void addVec(HuskyVector2D OtherVec)
    {
        setX(getX() + OtherVec.getX());
        setY(getY() + OtherVec.getY());
    }

    public void subVec(HuskyVector2D OtherVec)
    {
        setX(getX() - OtherVec.getX());
        setY(getY() - OtherVec.getY());
    }

    public void rotate(double degrees) {
        setX(getX() * Math.cos(Math.toRadians(degrees)) - getY() * Math.sin(Math.toRadians(degrees)));
        setY(getX() * Math.sin(Math.toRadians(degrees)) - getY() * Math.cos(Math.toRadians(degrees)));
    }

    public double distanceTo(HuskyVector2D OtherVec)
    {
        double DeltaX = OtherVec.getX() - getX();
        double DeltaY = OtherVec.getY() - getY();
        double RawDist = Math.sqrt((DeltaX * DeltaX) + (DeltaY * DeltaY));
        return (RawDist <= m_equalTreshold) ? 0.0 : RawDist;
    }

    public HuskyVector2D vectorTo(HuskyVector2D OtherVec)
    {
        HuskyVector2D WorkVec = new HuskyVector2D(OtherVec);
        WorkVec.subVec(this);
        return WorkVec;
    }

	public double dotProduct(HuskyVector2D OtherVec)
	{
		return (getX() * OtherVec.getX()) + (getY() + OtherVec.getY());
	}

    // Returns the angle from the head of the vector to the head of the other vector
    public double angleTo(HuskyVector2D OtherVec)
    {
        HuskyVector2D WorkVec = vectorTo(OtherVec);
        return Math.toDegrees(Math.atan2(WorkVec.getX(), WorkVec.getY()));
    }

    public String toString() {
        return "("+getX()+", "+getY()+")";
    }
}
