package com.hnkeystone.rxandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private final String tag = "rxAndroid";

    int i = 0;

    String[] str = new String[]{"HI", "hello", "AAA", "BBB"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {

                findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emitter.onNext(str[i]);
                        Log.i(tag, Thread.currentThread().getName());
                    }
                });

//                emitter.onNext("Hello");
//                emitter.onNext("Hi");
//                emitter.onNext("Aloha");
            }
        })
                .subscribeOn(Schedulers.io())
                //针对处理
                .map(new Function<String, Character>() {
                    @Override
                    public Character apply(String s) throws Exception {
                        Log.i(tag, Thread.currentThread().getName());

                        return s.toCharArray()[0];
                    }
                })

                .map(new Function<Character, String>() {
                    @Override
                    public String apply(Character character) throws Exception {
                        Log.i(tag, Thread.currentThread().getName());
                        return character + "888";
                    }
                })
                //转换线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String query) throws Exception {
                        Log.i(tag, query + "");
                        ((TextView) findViewById(R.id.tv)).setText(query);
                        i++;
                        if (i > 3)
                            i = 0;
                    }
                });
    }

}
