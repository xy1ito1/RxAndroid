package com.hnkeystone.rxandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema:                       
 ***      Author: HC 
 **       2018/2/23.                        
 *********************************************/

public class FL2Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fl2_fragment, container, false);

        ImageView image = inflate.findViewById(R.id.image);

        Glide.with(this).load("http://c.hiphotos.baidu.com/image/h%3D300/sign=5d662b6f291f95cab9f594b6f9177fc5/72f082025aafa40f8197e0cca764034f78f01949.jpg")
                .apply(new RequestOptions().transform(new CircleTransform(getActivity())))
                .into(image);

        return inflate;
    }
}
