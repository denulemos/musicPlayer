package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Crear variables de botones dinamicos (Cambian de apariencia)
    Button play, shuffle;

    //Clase para reproducir audios largos
    MediaPlayer mp;

    //Control de cambio de portada de cancion
    ImageView iv;

    //¿La cancion se repite o no? Indice que recorre vector
    int repetir = 2, posicion = 0;

    //Vector tipo MP con la cantidad de posiciones igual a la cantidad de canciones
    MediaPlayer vectormp[] = new MediaPlayer[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.play);
        shuffle = (Button) findViewById(R.id.shuffle);
        iv = (ImageView) findViewById(R.id.imageView);

        //Introducir canciones dentro del vector
        vectormp[0] = MediaPlayer.create(this, R.raw.race);
        vectormp[1] = MediaPlayer.create(this, R.raw.sound);
        vectormp[2] = MediaPlayer.create(this, R.raw.tea);
    }

    //Boton Play/Pause
    public void play(View view) {
        //Si la pista de audio se esta reproduciendo (isPlaying)
        if (vectormp[posicion].isPlaying()) {
            //Si se esta reproduciendo, al apretar el boton, pausar
            vectormp[posicion].pause();
            //Cambiar apariencia boton
            play.setBackgroundResource(R.drawable.reproducir);
            Toast.makeText(this, "Paused", Toast.LENGTH_SHORT).show();

        } else {
            //Si no se esta reproduciendo, reproducir y cambiar estilo del boton
            vectormp[posicion].start();
            //Cambiar apariencia boton
            play.setBackgroundResource(R.drawable.pausa);
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo boton Stop
    public void Stop(View view) {
        //El boton Stop indica que el reproductor debe dejar de reproducir la pista actual
        //Si nuestro vector esta en alguna posicion o hay alguna cancion en curso..
        if (vectormp[posicion] != null) {
            //La cancion debe parar
            vectormp[posicion].stop();
            //Volver a declarar los objetos en el vector
            vectormp[0] = MediaPlayer.create(this, R.raw.race);
            vectormp[1] = MediaPlayer.create(this, R.raw.sound);
            vectormp[2] = MediaPlayer.create(this, R.raw.tea);
            //Que vuelva todo a la posicion cero
            posicion = 0;
            play.setBackgroundResource(R.drawable.reproducir);
            //Modificar imagen dentro del imageView al principio
            iv.setImageResource(R.drawable.portada1);
            Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();

        }
    }

    public void Repetir(View view) {
        //repetir = 1 (repetir) repetir = 2 (no repetir)
        if (repetir == 1) {
            shuffle.setBackgroundResource(R.drawable.no_repetir);
            Toast.makeText(this, "No Repeat", Toast.LENGTH_SHORT).show();
            //Indicar loop mediante setLooping a false, ya que no queremos que se repita mas
            vectormp[posicion].setLooping(false);
            repetir = 2;
        } else {
            //Lo inverso a lo anterior
            shuffle.setBackgroundResource(R.drawable.repetir);
            Toast.makeText(this, "Repeat", Toast.LENGTH_SHORT).show();
            //Indicar loop mediante setLooping a false, ya que no queremos que se repita mas
            vectormp[posicion].setLooping(true);
            repetir = 1;
        }
    }

    //Pasar a la siguiente cancion
    public void Next(View view) {
        //El -1 evita un StackOverflow error
        //Si nuestro indice esta en la posicion cero
        if (posicion < vectormp.length - 1) {
            //Si hay algo reproduciendose
            if (vectormp[posicion].isPlaying()) {
                //Detener pista actual
                vectormp[posicion].stop();
                posicion++;
                //Pasar a la siguiente cancion
                vectormp[posicion].start();

                cambioPortadas();
            } else {
                posicion++;
                cambioPortadas();
            }
        } else {
            Toast.makeText(this, "No more songs", Toast.LENGTH_SHORT).show();
        }
    }

    //Ir a la cancion anterior
    public void Back(View view) {
        //Si no es igual o mayor a uno, va a retroceder mas de lo que debe
        if (posicion >= 1) {
            if (vectormp[posicion].isPlaying()) {
                //Detener la cancion si se esta reproduciendo
                vectormp[posicion].stop();
                //Volver a indicar canciones
                vectormp[0] = MediaPlayer.create(this, R.raw.race);
                vectormp[1] = MediaPlayer.create(this, R.raw.sound);
                vectormp[2] = MediaPlayer.create(this, R.raw.tea);
                posicion--;
                cambioPortadas();
                vectormp[posicion].start();
            } else {
                posicion--;
               cambioPortadas();
            }
        } else {
            Toast.makeText(this, "No more songs", Toast.LENGTH_SHORT).show();
        }
    }


    private void cambioPortadas(){
        if (posicion == 0) {
            iv.setImageResource(R.drawable.portada1);
        }
        if (posicion == 1) {
            iv.setImageResource(R.drawable.portada2);
        } else {
            iv.setImageResource(R.drawable.portada3);
        }
    }
}