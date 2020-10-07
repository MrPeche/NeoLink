package com.example.neolink_app.clases.clasesparaformargraficos;

import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;

public class statedatapack {
    LineData humedadrelativa;
    LineData presionbarometrica;
    LineData energia;
    LineData temperaturavulvoseco;
    LineData windspeed;
    ArrayList<String> label;
    public statedatapack(LineData humedadrelativa, LineData presionbarometrica, LineData energia, LineData temperaturavulvoseco,LineData windspeed ,ArrayList<String> label){
        this.humedadrelativa = humedadrelativa;
        this.presionbarometrica = presionbarometrica;
        this.energia = energia;
        this.temperaturavulvoseco = temperaturavulvoseco;
        this.windspeed = windspeed;
        this.label = label;
    }
    public LineData sacarlahumedadrelativa(){return this.humedadrelativa;}
    public LineData sacarlapresionbarometrica(){return this.presionbarometrica;}
    public LineData sacarlaenergia(){return this.energia;}
    public LineData sacarlatemperaturavulvoseco(){return this.temperaturavulvoseco;}
    public LineData sacarelwindspeed(){return this.windspeed;}
    public ArrayList<String> sacaraxislabels(){return this.label;}
}
