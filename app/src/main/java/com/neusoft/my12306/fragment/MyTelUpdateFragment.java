package com.neusoft.my12306.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.neusoft.my12306.R;
import com.neusoft.my12306.task.UpdateTelTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTelUpdateFragment extends MyFragment implements View.OnClickListener{
    private SharedPreferences sp;
    private EditText telTxt;
    private Button saveBtn;
    private String username;

    public MyTelUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getActivity().getSharedPreferences("myfile", Context.MODE_PRIVATE);
        username = sp.getString("username","");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tel_update, container, false);
        telTxt = (EditText) view.findViewById(R.id.update_tel_txt_id);
        saveBtn = (Button) view.findViewById(R.id.update_mydata_btn_id);
        saveBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("正在提交中…");
        pd.show();
        UpdateTelTask task = new UpdateTelTask(getActivity(),this,pd);
        task.execute(username,telTxt.getEditableText().toString());
    }
}
