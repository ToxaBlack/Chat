package com.toxablack.chat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class MessagesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat[] DATE_FORMATS = new DateFormat[]{
            DateFormat.getDateInstance(), DateFormat.getTimeInstance()};

    private OnFragmentInteractionListener mListener;
    private SimpleCursorAdapter mAdapter;
    private Date mNow;
    private Calendar mCalendar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNow = new Date();
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(mNow);

        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.chat_list_item,
                null,
                new String[]{DataProvider.COL_MSG, DataProvider.COL_AT},
                new int[]{R.id.text1, R.id.text2},
                0);

        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                switch (view.getId()) {
                    case R.id.text1:
                        LinearLayout root = (LinearLayout) view.getParent().getParent();
                        if (cursor.getString(cursor.getColumnIndex(DataProvider.COL_FROM)) == null) {
                            root.setGravity(Gravity.RIGHT);
                            root.setPadding(50, 10, 10, 10);
                        } else {
                            root.setGravity(Gravity.LEFT);
                            root.setPadding(10, 10, 50, 10);
                        }
                        break;

                    case R.id.text2:
                        TextView tv = (TextView) view;
                        tv.setText(getDisplayTime(cursor.getString(columnIndex)));
                        return true;
                }
                return false;
            }
        });


        setListAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setDivider(null);
        getListView().setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                getListView().setSelection(mAdapter.getCount() - 1);
            }
        });


        Bundle args = new Bundle();
        args.putString(DataProvider.COL_EMAIL, mListener.getProfileEmail());
        getLoaderManager().initLoader(0, args, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public String getProfileEmail();
    }

    private String getDisplayTime(String datetime) {
        try {
            Date date = SIMPLE_DATE_FORMAT.parse(datetime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (mCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                    && mCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                    && mCalendar.get(Calendar.DATE) == calendar.get(Calendar.DATE)) {
                return DATE_FORMATS[1].format(date);
            }
            return DATE_FORMATS[0].format(date);
        } catch (ParseException e) {
            return datetime;

        }
    }

    //----------------------------------------------------------------------------

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String profileEmail = args.getString(DataProvider.COL_EMAIL);
        CursorLoader loader = new CursorLoader(getActivity(),
                DataProvider.CONTENT_URI_MESSAGES,
                null,
                DataProvider.COL_FROM + " = ? or " + DataProvider.COL_TO + " = ?",
                new String[]{profileEmail, profileEmail},
                DataProvider.COL_AT + " ASC");
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
