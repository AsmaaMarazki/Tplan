package com.example.sulta.tplan.presenter.interfaces;

import android.content.Context;
import android.widget.ListView;

import com.example.sulta.tplan.model.Trip;

/**
 * Created by Passant on 3/17/2018.
 */

public interface IHomeActivityPresenter {
    public void viewUpComingTrips(Context context, ListView upComingTripsList);
    public void viewHistoryTrips(Context context, ListView historyTripsList);
    public boolean deleteTrip(Context context, int tripId);
    public boolean editTrip(Context context, Trip trip);
    public void editSettings(Context context, int value);
    public void startSerivice();
    public void stopService();
    public int viewSettings(Context context);
    public void synchTripsToFireBase(Context context);
    public void logOutSettings(Context context);
    public void shareTrip(Context context, Trip trip);
    public void viewMapTrip(Context context, Trip trip);
    public void refreshList(Context context);
    public void removeAllAlarms(Context context);
}
