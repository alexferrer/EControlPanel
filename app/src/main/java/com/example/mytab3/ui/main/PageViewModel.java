package com.example.mytab3.ui.main;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    //Power stats
    private MutableLiveData<Integer> mAmps       = new MutableLiveData<>();
    private MutableLiveData<Integer> mVolts      = new MutableLiveData<>();
    private MutableLiveData<Integer> mWatts      = new MutableLiveData<>();
    private MutableLiveData<Double> mWattsTime  = new MutableLiveData<>();

    //Flight stats
    private MutableLiveData<Integer> mAIS        = new MutableLiveData<>();
    private MutableLiveData<Integer> mAlt        = new MutableLiveData<>();
    private MutableLiveData<Integer> mVario      = new MutableLiveData<>();
    private MutableLiveData<Integer> mTotEnergy  = new MutableLiveData<>();
    //Engine
    private MutableLiveData<Integer> mRPM          = new MutableLiveData<>();
    private MutableLiveData<Integer> mEngineTemp   = new MutableLiveData<>();
    private MutableLiveData<Integer> mBatteryTemp  = new MutableLiveData<>();
    //Alarms
    private MutableLiveData<Boolean> mBatteryTempAlert = new MutableLiveData<>();
    private MutableLiveData<Boolean> mEngineTempAlert  = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLowBatteryAlert  = new MutableLiveData<>();



    public MutableLiveData<Integer> getmAmps() {
        return mAmps;
    }

    public void setmAmps(MutableLiveData<Integer> mAmps) {
        this.mAmps = mAmps;
    }

    public MutableLiveData<Integer> getmVolts() {
        return mVolts;
    }

    public void setmVolts(MutableLiveData<Integer> mVolts) {
        this.mVolts = mVolts;
    }

    public MutableLiveData<Integer> getmWatts() {
        return mWatts;
    }

    public void setmWatts(MutableLiveData<Integer> mWatts) {
        this.mWatts = mWatts;
    }

    public MutableLiveData<Double> getmWattsTime() {
        return mWattsTime;
    }

    public void setmWattsTime(MutableLiveData<Double> mWattsTime) {
        this.mWattsTime = mWattsTime;
    }

    public MutableLiveData<Integer> getmAIS() {
        return mAIS;
    }

    public void setmAIS(MutableLiveData<Integer> mAIS) {
        this.mAIS = mAIS;
    }

    public MutableLiveData<Integer> getmAlt() {
        return mAlt;
    }

    public void setmAlt(MutableLiveData<Integer> mAlt) {
        this.mAlt = mAlt;
    }

    public MutableLiveData<Integer> getmVario() {
        return mVario;
    }

    public void setmVario(MutableLiveData<Integer> mVario) {
        this.mVario = mVario;
    }

    public MutableLiveData<Integer> getmTotEnergy() {
        return mTotEnergy;
    }

    public void setmTotEnergy(MutableLiveData<Integer> mTotEnergy) {
        this.mTotEnergy = mTotEnergy;
    }

    public MutableLiveData<Integer> getmRPM() {
        return mRPM;
    }

    public void setmRPM(MutableLiveData<Integer> mRPM) {
        this.mRPM = mRPM;
    }

    public MutableLiveData<Integer> getmEngineTemp() {
        return mEngineTemp;
    }

    public void setmEngineTemp(MutableLiveData<Integer> mEngineTemp) {
        this.mEngineTemp = mEngineTemp;
    }

    public MutableLiveData<Integer> getmBatteryTemp() {
        return mBatteryTemp;
    }

    public void setmBatteryTemp(MutableLiveData<Integer> mBatteryTemp) {
        this.mBatteryTemp = mBatteryTemp;
    }

    public MutableLiveData<Boolean> getmBatterytempAlert() {
        return mBatteryTempAlert;
    }

    public void setmBatterytempAlert(MutableLiveData<Boolean> mBatterytempAlert) {
        this.mBatteryTempAlert = mBatterytempAlert;
    }

    public MutableLiveData<Boolean> getmEngineTempAlert() {
        return mEngineTempAlert;
    }

    public void setmEngineTempAlert(MutableLiveData<Boolean> mEngineTempAlert) {
        this.mEngineTempAlert = mEngineTempAlert;
    }

    public MutableLiveData<Boolean> getmLowBatteryAlert() {
        return mLowBatteryAlert;
    }

    public void setmLowBatteryAlert(MutableLiveData<Boolean> mLowBatteryAlert) {
        this.mLowBatteryAlert = mLowBatteryAlert;
    }


    //********************************** old stuff
    private MutableLiveData<Integer> mIndex  = new MutableLiveData<>();
    private MutableLiveData<String>  currentName ;


    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });



    public void setIndex(int index) {
        mIndex.setValue(index);
    }


    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<String> getCurrentName() {
        if (currentName == null ){
            currentName = new MutableLiveData<String>();
        }
        return currentName;
    }


}