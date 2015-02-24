package com.mayo.inspiee.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.mayo.inspiee.Inspiee;
import com.mayo.inspiee.R;

/**
 * Created by mayo on 2/1/15.
 */
public class PersonalitiesDialog extends DialogFragment {
    ListView personalitiesList;
    Button ok;
    Button cancel;
    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Choose personalities");
        View view = inflater.inflate(R.layout.dialog_personalities, container, false);

        personalitiesList = (ListView) view.findViewById(R.id.personalitiesPrefList);
        personalitiesList.setAdapter(new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_checked,
                android.R.id.text1, Inspiee.PERSONALITIES));
        personalitiesList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//			personalitiesList.setAdapter(new ArrayAdapter<String>());
        ok = (Button) view.findViewById(R.id.okPersonalitiesPref);
        cancel = (Button) view.findViewById(R.id.cancelPersonalitiesPref);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inspiee.SELECTED_PERSONALITIES.clear();
                int count = personalitiesList.getCount();
                for (int index = 0; index < count; index++) {
                    if (personalitiesList.isItemChecked(index))
                        Inspiee.SELECTED_PERSONALITIES.add(Inspiee.PERSONALITIES.get(index));
                }

                SharedPreferences db = mContext.getSharedPreferences(Inspiee.DB, mContext.MODE_PRIVATE);
                SharedPreferences.Editor editor = db.edit();

                editor.putString(Inspiee.TAG_PERSONALITIES, Inspiee.SELECTED_PERSONALITIES.toString());
                editor.commit();

                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int count = Inspiee.SELECTED_PERSONALITIES.size();
        int pos;
        for (int index = 0; index < count; index++) {
            pos = Inspiee.PERSONALITIES.indexOf(Inspiee.SELECTED_PERSONALITIES.get(index));
            if(pos != -1){
                personalitiesList.setItemChecked(pos, true);
            }
        }

    }
}