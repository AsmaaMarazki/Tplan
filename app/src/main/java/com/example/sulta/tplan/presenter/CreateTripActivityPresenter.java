package com.example.sulta.tplan.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.PlacePoint;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.presenter.interfaces.ICreateTripActivityPresenter;
import com.example.sulta.tplan.view.activities.interfaces.ICreateTripActivity;
import com.example.sulta.tplan.view.services.ReminderService;

/**
 * Created by Asmaa on 3/19/2018.
 */

public class CreateTripActivityPresenter implements ICreateTripActivityPresenter {
    private Context mContext;
    private ICreateTripActivity mIView;
    Trip trip;
    ReminderService myService;
    boolean isBound = false;

    public CreateTripActivityPresenter(Context mContext, ICreateTripActivity mIView) {
        this.mContext = mContext;
        this.mIView = mIView;
    }

    @Override
    public void createTrip() {
        trip = new Trip();
        PlacePoint startPlacePoint = new PlacePoint();
        PlacePoint endPlacePoint = new PlacePoint();

        trip.setTitle(mIView.getTripName());

        startPlacePoint.setLongitude(mIView.startPointLan());
        startPlacePoint.setLatitude(mIView.startPointLat());
        trip.setStartPoint(startPlacePoint);

        endPlacePoint.setLongitude(mIView.endPointLan());
        endPlacePoint.setLatitude(mIView.endPointLat());
        trip.setEndPoint(endPlacePoint);
        trip.setDate(mIView.getTripDate());
        trip.setStatus("upComing");
        trip.setRoundTrip(mIView.getTripDirection());
       // Toast.makeText(mContext, "round: "+mIView.getTripDirection(), Toast.LENGTH_SHORT).show();
        String noteString="";
        for (int i=0;i<mIView.getNotes().size();i++){

            if(mIView.getNotes().get(i).isChecked()==true)
                noteString+="*"+mIView.getNotes().get(i).getText()+",";
            else
                noteString+=mIView.getNotes().get(i).getText()+",";

            if(i==(mIView.getNotes().size()-1)){
                noteString=noteString.substring(0,noteString.length()-1);
            }
        }
        trip.setNotes(noteString);

        trip.setStartTimeInMillis(mIView.getTripStartTimeInMillis());

        trip.setStartPointName(mIView.getStartPointName());
        trip.setEndPointName(mIView.getEndPointName());

        trip.setId(new SqlAdapter(mContext).insertTrip(trip));


    }
    private ServiceConnection myconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ReminderService.MyLocalBinder binder = (ReminderService.MyLocalBinder) iBinder;
            myService = binder.geService();
            isBound = true;
            setAlarmSetting();


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
           // Toast.makeText(myService, "service un bounded", Toast.LENGTH_SHORT).show();

        }
    };


    @Override
    public void startSerivice() {
        Intent mintent = new Intent(mContext, ReminderService.class);
        mContext.bindService(mintent, myconnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void stopService() {
        if(isBound){
        mContext.stopService(new Intent(mContext, ReminderService.class));
        mContext.unbindService(myconnection);
        isBound=false;
        }
    }

    private void setAlarmSetting() {
        if (isBound) {
            myService.startNewAlarm(mContext, trip.getStartTimeInMillis(), trip.getId());//send request conde from trip id
            stopService();
        }
    }



}
