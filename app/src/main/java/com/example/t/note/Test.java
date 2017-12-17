package com.example.t.note;

import android.content.res.Resources;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t on 11/30/17.
 */

public class Test {

    static class t{
        int x;
        int y;

        public t(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String [] args){
        List<t> list=new ArrayList();

        list.add(new t(1,2));

        System.out.println(list.contains(new t(1,2)));
        System.out.println(list.contains(new t(1,3)));


        list.remove(new t(1,2));


    }


    /**
     * Converting dp to pixel
     */

}
