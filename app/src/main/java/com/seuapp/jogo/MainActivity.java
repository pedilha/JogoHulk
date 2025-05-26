package com.seuapp.jogo;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView hulk01, hulk02, hulk03, hulk04, hulk05, hulk06, hulk07, hulk08;
    private ProgressBar progressBar;
    private TextView pts, ptsM;
    private Button iniciar;
    private Random r;
    private int pontos = 0;
    private int fps = 1000;
    private int ptsMax = 0;
    private int resta = 5;
    private int tempoStatus = 0;
    private int imgAtv = 0;
    private int imgAtvSalva = 0;
    private AnimationDrawable an;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        iniciar = findViewById(R.id.button);
        pts = findViewById(R.id.pts);
        ptsM = findViewById(R.id.recorde);
        progressBar = findViewById(R.id.progressBar);
        hulk01 = findViewById(R.id.hulk_01);
        hulk02 = findViewById(R.id.hulk_02);
        hulk03 = findViewById(R.id.hulk_03);
        hulk04 = findViewById(R.id.hulk_04);
        hulk05 = findViewById(R.id.hulk_05);
        hulk06 = findViewById(R.id.hulk_06);
        hulk07 = findViewById(R.id.hulk_07);
        hulk08 = findViewById(R.id.hulk_08);
        hulk01.setVisibility(View.INVISIBLE);
        hulk02.setVisibility(View.INVISIBLE);
        hulk03.setVisibility(View.INVISIBLE);
        hulk04.setVisibility(View.INVISIBLE);
        hulk05.setVisibility(View.INVISIBLE);
        hulk06.setVisibility(View.INVISIBLE);
        hulk07.setVisibility(View.INVISIBLE);
        hulk08.setVisibility(View.INVISIBLE);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        r = new Random();
        iniciar.setOnClickListener(v -> {
            resta = 0;
            tempoStatus = 100;
            pontos = 0;
            pts.setText("Pontos: " + pontos);
            iniciar.setEnabled(false);
            new Handler().postDelayed(this::minhaEngine, 1000);
            final Handler handler2 = new Handler();
            new Thread(() -> {
                while (tempoStatus > resta) {
                    tempoStatus--;
                    android.os.SystemClock.sleep(250);
                    handler2.post(() -> progressBar.setProgress(tempoStatus));
                }
            }).start();
        });
        View.OnClickListener hulkClick = v -> {
            pontos++;
            pts.setText("Pontos: " + pontos);
            v.setEnabled(false);
        };
        hulk01.setOnClickListener(hulkClick);
        hulk02.setOnClickListener(hulkClick);
        hulk03.setOnClickListener(hulkClick);
        hulk04.setOnClickListener(hulkClick);
        hulk05.setOnClickListener(hulkClick);
        hulk06.setOnClickListener(hulkClick);
        hulk07.setOnClickListener(hulkClick);
        hulk08.setOnClickListener(hulkClick);
    }

    private void minhaEngine() {
        if (pontos < 10) fps = 1000;
        else if (pontos < 15) fps = 950;
        else if (pontos < 20) fps = 900;
        else if (pontos < 25) fps = 850;
        else if (pontos < 30) fps = 800;
        else if (pontos < 35) fps = 750;
        else if (pontos < 40) fps = 700;
        else if (pontos < 45) fps = 650;
        else if (pontos < 50) fps = 600;
        else if (pontos < 55) fps = 550;
        else if (pontos < 60) fps = 500;
        else if (pontos < 65) fps = 450;
        else fps = 400;
        an = (AnimationDrawable) ContextCompat.getDrawable(this, R.drawable.animacao);
        do {
            imgAtv = r.nextInt(8) + 1;
        } while (imgAtv == imgAtvSalva);
        imgAtvSalva = imgAtv;
        ImageView ativo = null;
        switch (imgAtv) {
            case 1:
                ativo = hulk01;
                break;
            case 2:
                ativo = hulk02;
                break;
            case 3:
                ativo = hulk03;
                break;
            case 4:
                ativo = hulk04;
                break;
            case 5:
                ativo = hulk05;
                break;
            case 6:
                ativo = hulk06;
                break;
            case 7:
                ativo = hulk07;
                break;
            case 8:
                ativo = hulk08;
                break;
        }
        if (ativo != null) {
            ativo.setImageDrawable(an);
            ativo.setVisibility(View.VISIBLE);
            ativo.setEnabled(true);
        }
        an.start();
        new Handler().postDelayed(() -> {
            hulk01.setVisibility(View.INVISIBLE);
            hulk02.setVisibility(View.INVISIBLE);
            hulk03.setVisibility(View.INVISIBLE);
            hulk04.setVisibility(View.INVISIBLE);
            hulk05.setVisibility(View.INVISIBLE);
            hulk06.setVisibility(View.INVISIBLE);
            hulk07.setVisibility(View.INVISIBLE);
            hulk08.setVisibility(View.INVISIBLE);
            hulk01.setEnabled(false);
            hulk02.setEnabled(false);
            hulk03.setEnabled(false);
            hulk04.setEnabled(false);
            hulk05.setEnabled(false);
            hulk06.setEnabled(false);
            hulk07.setEnabled(false);
            hulk08.setEnabled(false);
            if (tempoStatus < 1) {
                Toast.makeText(MainActivity.this,"Game Over",Toast.LENGTH_SHORT).show();
                iniciar.setEnabled(true);
                if(ptsMax<pontos) ptsMax = pontos;
                SharedPreferences prefs = getSharedPreferences("recorde",MODE_PRIVATE);
                ptsMax = prefs.getInt("record",0);
                ptsM.setText("recorde: "+ptsMax);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("recorde",ptsMax);
                editor.apply();
            } else minhaEngine();
        }, fps);
    }
}