package com.hnkeystone.rxandroid;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binaryfork.spanny.Spanny;

/*********************************************
 ****    Copyright (C)  2018
 ****    河南坚磐电子科技有限公司
 ****    All Rights Reserved
 ****    Author:HC
 ****    Schema:
 ****    2018/2/23.
 *********************************************/
public class FL1Fragment extends Fragment {

    FGinterfae fGinterfae;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fl1_fragment, container, false);
        TextView tv1 = inflate.findViewById(R.id.tv1);

        Spanny spanny = new Spanny("StyleSpan", new StyleSpan(Typeface.BOLD_ITALIC))
                .append("\nUnderlineSpan, ", new UnderlineSpan())
                .append(" TypefaceSpan, ", new TypefaceSpan("serif"))
                .append("URLSpan, ", new URLSpan("google.com"))
                .append("StrikethroughSpan", new StrikethroughSpan())
                .append("\nQuoteSpan", new QuoteSpan(Color.RED))
                .append("\nPlain text")
                .append("SubscriptSpan", new SubscriptSpan())
                .append("SuperscriptSpan", new SuperscriptSpan())
                .append("\n\nBackgroundSpan", new BackgroundColorSpan(Color.LTGRAY))
                .append("\n\nForegroundColorSpan", new ForegroundColorSpan(Color.LTGRAY))
                .append("\nAlignmentSpan", new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER))
                .append("\nTextAppearanceSpan\n", new TextAppearanceSpan(getActivity(), android.R.style.TextAppearance_Medium))
                .append("\nRelativeSizeSpan", new RelativeSizeSpan(1.5f))
                .append("\n\nMultiple spans", new StyleSpan(Typeface.ITALIC), new UnderlineSpan(),
                        new TextAppearanceSpan(getActivity(), android.R.style.TextAppearance_Large),
                        new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), new BackgroundColorSpan(Color.LTGRAY));

        tv1.setText(spanny);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fGinterfae.onButtonClick();
            }
        });
        return inflate;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            fGinterfae = (FGinterfae) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

}
