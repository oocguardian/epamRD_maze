package com.epam.trash.eller;

import java.util.List;

public class Cel{
    public boolean right,down;

    public List<Cel> set;

    public int x,y;

    Cel(int a,int b){

        x=a;

        y=b;

        right=false;

        down=true;

        set=null;

    }

}