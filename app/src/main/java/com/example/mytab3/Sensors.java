package com.example.mytab3;

public class Sensors {
    // static variable single_instance of type Singleton
    private static Sensors single_instance = null;

    //Power stats
    private int mAmps       = 0;
    private int mVolts      = 0;
    private double mWattsTime  = 0.0;

    //Flight stats
    private int mAIS        = 0;
    private int mAlt        = 0;
    private int mVario      = 0;
    private int mTotEnergy  = 0;
    //Engine
    private int mRPM          = 0;
    private int mEngineTemp   = 0;
    private int mBatteryTemp  = 0;
    //Alarms
    private int mBatteryHighTemp = 100;
    private int mEngineHighTemp = 100;
    private int mBatteryLowVoltage = 50;



    public int getmAmps() {
        return mAmps;
    }

    public void setmAmps(int mAmps) {
        this.mAmps = mAmps;
    }
    public void setmAmps(String mAmps) {
        this.mAmps = Integer.parseInt(mAmps);
    }


    public int getmVolts() {
        return mVolts;
    }

    public void setmVolts(int mVolts) {
        this.mVolts = mVolts;
    }
    public void setmVolts(String mVolts) {
        this.mVolts = Integer.parseInt(mVolts);
    }


    public int getmWatts() {
        return this.mVolts*this.mAmps;
    }

    /*
      total watts per time calculated by the hour
        T      = time
        dt     = time delta in seconds
        watts  = Volts * Amps
        total time
        total watts/second ?


        Wdt    = Watts consumed in time delta
        WT     = Total Wats consumed per hour
     */
    public double getmWattsTime(int watts , int dt) {
        mWattsTime += watts/dt ; // accumulated watts per second

        return mWattsTime/3600; //watts per hour
    }

    public int getmAIS() {
        return mAIS;
    }

    public void setmAIS(int mAIS) {
        this.mAIS = mAIS;
    }
    public void setmAIS(String mAIS) {
        this.mAIS = Integer.parseInt( mAIS);
    }

    public int getmAlt() {
        return mAlt;
    }

    public void setmAlt(int mAlt) {
        this.mAlt = mAlt;
    }

    public void setmAlt(String mAlt) {
        this.mAlt = Integer.parseInt( mAlt );
    }

    public int getmVario() {
        return mVario;
    }

    public void setmVario(int mVario) {
        this.mVario = mVario;
    }

    public void setmVario(String mVario) {
        this.mVario = Integer.parseInt( mVario ) ;
    }

    public int getmTotEnergy() {
        return mTotEnergy;
    }

    public void setmTotEnergy(int mTotEnergy) {
        this.mTotEnergy = mTotEnergy;
    }

    public int getmRPM() {
        return mRPM;
    }

    public void setmRPM(int mRPM) {
        this.mRPM = mRPM;
    }

    public void setmRPM(String mRPM) {
        this.mRPM = Integer.parseInt( mRPM );
    }

    public int getmEngineTemp() {
        return mEngineTemp;
    }

    public void setmEngineTemp(int mEngineTemp) {
        this.mEngineTemp = mEngineTemp;
    }
    public void setmEngineTemp(String mEngineTemp) {
        this.mEngineTemp = Integer.parseInt( mEngineTemp );
    }

    public int getmBatteryTemp() {
        return mBatteryTemp;
    }

    public void setmBatteryTemp(int mBatteryTemp) {
        this.mBatteryTemp = mBatteryTemp;
    }

    public void setmBatteryTemp(String mBatteryTemp) {
        this.mBatteryTemp = Integer.parseInt( mBatteryTemp );
    }

    public Boolean getmBatteryTempAlert() {
        if ( this.mBatteryTemp > this.mBatteryHighTemp )
           return true;
        else
           return false;
    }


    public int getmBatteryHighTemp() {
        return mBatteryHighTemp;
    }

    public void setmBatteryHighTemp(int mBatteryHighTemp) {
        this.mBatteryHighTemp = mBatteryHighTemp;
    }

    public Boolean getmEngineTempAlert() {
        if ( this.mEngineTemp > this.mEngineHighTemp )
            return true;
        else
            return false;
    }

    public int getmEngineHighTemp() {
        return mEngineHighTemp;
    }

    public void setmEngineHighTemp(int mEngineHighTemp) {
        this.mEngineHighTemp = mEngineHighTemp;
    }

    public Boolean getmLowBatteryAlert() {
        if ( this.mVolts < this.mBatteryLowVoltage )
            return true;
        else
            return false;
    }

    public int getmBatteryLowVoltage() {
        return mBatteryLowVoltage;
    }

    public void setmBatteryLowVoltage(int mBatteryLowVoltage) {
        this.mBatteryLowVoltage = mBatteryLowVoltage;
    }


    // private constructor restricted to this class itself
    private Sensors()
    {

    }

    // static method to create instance of Singleton class
    public static Sensors getInstance()
    {
        if (single_instance == null)
            single_instance = new Sensors();

        return single_instance;
    }

    public double getmWattsTime() {
        return mWattsTime;
    }
}
