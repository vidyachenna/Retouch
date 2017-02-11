package com.example.sowjich.retouch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by sowji.ch on 2/11/2017.
 */

public class Edit_i extends Activity {
    Button ro1, ro2, ro3, ro4, ro5, ro6, ro7, ro8, ro9, ro10, ro11, ro12, ro13, white, detect, effectsButton, fill;
    ImageButton backButton, saveButton;
    Paint paint;
    ImageView viewImage;
    Bitmap mBitmap, bBitmap, cBitmap, thumbnail, change;
    Uri uri;
    String picturePath, img_a;
    TextView tv;
    File imageFile;
    String path_p;
    final static int KERNAL_WIDTH = 3;
    final static int KERNAL_HEIGHT = 3;

    int[][] kernal = {
            {0, -1, 0},
            {-1, 4, -1},
            {0, -1, 0}
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_i);
        backButton = (ImageButton) findViewById(R.id.backButton);
        saveButton = (ImageButton) findViewById(R.id.saveButton);
        ro1 = (Button) findViewById(R.id.ro1);
        ro2 = (Button) findViewById(R.id.ro2);
        ro3 = (Button) findViewById(R.id.ro3);
        ro4 = (Button) findViewById(R.id.ro4);
        ro5 = (Button) findViewById(R.id.ro5);
        ro6 = (Button) findViewById(R.id.ro6);
        ro7 = (Button) findViewById(R.id.ro7);
        ro8 = (Button) findViewById(R.id.ro8);
        ro9 = (Button) findViewById(R.id.ro9);
        ro10 = (Button) findViewById(R.id.ro10);
        ro11 = (Button) findViewById(R.id.ro11);
        ro12 = (Button) findViewById(R.id.ro12);
        ro13 = (Button) findViewById(R.id.ro13);
        viewImage = (ImageView) findViewById(R.id.viewImage);
        Intent intent = getIntent();
        thumbnail = (Bitmap) intent.getParcelableExtra("photo");
        viewImage.setImageBitmap(thumbnail);
        change = thumbnail;
        viewImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Intent i = new Intent(edit.this, ColorDet.class);
                //startActivity(i);
                //return true;
                float eventX = event.getX();
                float eventY = event.getY();
                float[] eventXY = new float[]{eventX, eventY};

                Matrix invertMatrix = new Matrix();
                ((android.widget.ImageView) v).getImageMatrix().invert(invertMatrix);

                invertMatrix.mapPoints(eventXY);
                int  x = Integer.valueOf((int) eventXY[0]);
                int  y = Integer.valueOf((int) eventXY[1]);

                if (x < 0) {
                    x = 0;
                } else if (x > thumbnail.getWidth() - 1) {
                    x = thumbnail.getWidth() - 1;
                }

                if (y < 0) {
                    y = 0;
                } else if (y > thumbnail.getHeight() - 1) {
                    y = thumbnail.getHeight() - 1;
                }

                int touchedRGB = thumbnail.getPixel(x, y);
                String img_touch = "#" + Integer.toHexString(touchedRGB);
                Toast toast = Toast.makeText(Edit_i.this, img_touch, Toast.LENGTH_SHORT);
                toast.show();

                // colorRGB.setText(img_touch);

                return true;
            }
        });
        ro1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // paint.setColor(Color.RED);
                mBitmap = thumbnail;
                viewImage.setImageBitmap(sugession3(mBitmap));
            }
        });

        ro2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //paint.setColor(Color.YELLOW);
                mBitmap = thumbnail;
                viewImage.setImageBitmap(sugession2(mBitmap));
            }
        });
        ro3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //  paint.setColor(Color.BLUE);
                mBitmap = thumbnail;
                viewImage.setImageBitmap(sugession1(mBitmap));
            }
        });
        ro4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //paint.setColor(Color.CYAN);
                mBitmap = thumbnail;
                viewImage.setImageBitmap(sugession4(mBitmap));
            }
        });
        ro5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // paint.setColor(Color.GRAY);
                mBitmap = thumbnail;
                viewImage.setImageBitmap(sugession5(mBitmap));
            }
        });
        ro6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //paint.setColor(Color.GREEN);
                mBitmap = thumbnail;
                viewImage.setImageBitmap(sugession6(mBitmap));
            }
        });
        ro7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //paint.setColor(Color.GREEN);
                mBitmap = thumbnail;
                viewImage.setImageBitmap(sugession7(mBitmap));
            }
        });
        ro8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //paint.setColor(Color.GREEN);
                mBitmap = thumbnail;
                viewImage.setImageBitmap(sugession8(mBitmap));
            }
        });
        ro9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //paint.setColor(Color.GREEN);
                mBitmap = thumbnail;
                viewImage.setImageBitmap(sugession9(mBitmap));
            }
        });
        ro10.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //paint.setColor(Color.GREEN);
                mBitmap = thumbnail;
                viewImage.setImageBitmap(sugession10(mBitmap));
            }
        });
        ro11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //paint.setColor(Color.GREEN);
                mBitmap = thumbnail;
                viewImage.setImageBitmap(sugession11(mBitmap));
            }
        });
        ro12.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //paint.setColor(Color.GREEN);
                mBitmap = thumbnail;
                viewImage.setImageBitmap(sugession12(mBitmap));
            }
        });
        ro13.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //paint.setColor(Color.GREEN);
                mBitmap = thumbnail;
                viewImage.setImageBitmap(sugession13(mBitmap));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tag = "at backButton";
                Log.i(Tag, "before intent");
                Intent i = new Intent(Edit_i.this, MainActivity.class);
                startActivity(i);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ij = new Intent(Edit_i.this, Compare.class);
                ij.putExtra("bmp",change);
                String img_touch = "bitmap gng to be sent";
                Toast toast = Toast.makeText(Edit_i.this, img_touch, Toast.LENGTH_SHORT);
                startActivity(ij);
                toast.show();
            }
        });



    }
    private Bitmap sugession1(Bitmap src){

        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for(int x = 0; x < src.getWidth(); x++){
            for(int y = 0; y < src.getHeight(); y++){
                int pixelColor = src.getPixel(x, y);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelBlue = Color.blue(pixelColor);
                int newPixel= Color.argb(
                        pixelAlpha, pixelBlue, pixelRed, pixelGreen);
                dest.setPixel(x, y, newPixel);
            }
        }
        change = dest;
        return dest;
    }
    private Bitmap sugession2(Bitmap src){

        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for(int x = 0; x < src.getWidth(); x++){
            for(int y = 0; y < src.getHeight(); y++) {
                int pixelColor = src.getPixel(x, y);

                int pixelBlue = Color.blue(pixelColor);
                int pixelGreen = Color.blue(pixelColor);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int newPixel = Color.argb(
                        pixelAlpha, pixelBlue, pixelGreen, pixelRed);
                dest.setPixel(x, y, newPixel);
            }
        }

        change = dest;
        return dest;
    }
    private Bitmap sugession3(Bitmap src){

        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for(int x = 0; x < src.getWidth(); x++){
            for(int y = 0; y < src.getHeight(); y++){
                int pixelColor = src.getPixel(x, y);
                int pixelBlue = Color.blue(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int newPixel= Color.argb(
                        pixelAlpha, pixelRed , pixelGreen, pixelBlue);
                dest.setPixel(x, y, newPixel);
            }
        }
        change = dest;
        return dest;
    }

    private Bitmap sugession4(Bitmap src){

        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for(int x = 0; x < src.getWidth(); x++){
            for(int y = 0; y < src.getHeight(); y++){
                int pixelColor = src.getPixel(x, y);
                int pixelBlue = Color.blue(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int newPixel= Color.argb(
                        pixelAlpha, pixelRed , pixelBlue, pixelGreen);
                dest.setPixel(x, y, newPixel);
            }
        }
        change = dest;
        return dest;
    }
    private Bitmap sugession5(Bitmap src){

        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for(int x = 0; x < src.getWidth(); x++){
            for(int y = 0; y < src.getHeight(); y++){
                int pixelColor = src.getPixel(x, y);
                int pixelBlue = Color.blue(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int newPixel= Color.argb(
                        pixelAlpha, pixelGreen , pixelBlue, pixelRed);
                dest.setPixel(x, y, newPixel);
            }
        }
        change = dest;
        return dest;
    }
    private Bitmap sugession6(Bitmap src){

        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for(int x = 0; x < src.getWidth(); x++){
            for(int y = 0; y < src.getHeight(); y++){
                int pixelColor = src.getPixel(x, y);
                int pixelBlue = Color.blue(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int newPixel= Color.argb(
                        pixelAlpha, pixelGreen , pixelRed, pixelBlue);
                dest.setPixel(x, y, newPixel);
            }
        }
        change = dest;
        return dest;
    }
    private Bitmap sugession7(Bitmap src){

        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for(int x = 0; x < src.getWidth(); x++){
            for(int y = 0; y < src.getHeight(); y++){
                int pixelColor = src.getPixel(x, y);
                int pixelBlue = Color.blue(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int newPixel= Color.argb(
                        pixelAlpha, pixelBlue , pixelRed, pixelRed);
                dest.setPixel(x, y, newPixel);
            }
        }
        change = dest;
        return dest;
    }
    private Bitmap sugession8(Bitmap src){

        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for(int x = 0; x < src.getWidth(); x++){
            for(int y = 0; y < src.getHeight(); y++){
                int pixelColor = src.getPixel(x, y);
                int pixelBlue = Color.blue(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int newPixel= Color.argb(
                        pixelAlpha, pixelBlue , pixelGreen, pixelGreen);
                dest.setPixel(x, y, newPixel);
            }
        }
        change = dest;
        return dest;
    }
    private Bitmap sugession9(Bitmap src){

        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for(int x = 0; x < src.getWidth(); x++){
            for(int y = 0; y < src.getHeight(); y++){
                int pixelColor = src.getPixel(x, y);
                int pixelBlue = Color.blue(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int newPixel= Color.argb(
                        pixelAlpha, pixelRed , pixelGreen, pixelGreen);
                dest.setPixel(x, y, newPixel);
            }
        }
        change = dest;
        return dest;
    }
    private Bitmap sugession10(Bitmap src) {

        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                int pixelColor = src.getPixel(x, y);
                int pixelBlue = Color.blue(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int newPixel = Color.argb(
                        pixelAlpha, pixelRed, pixelBlue, pixelBlue);
                dest.setPixel(x, y, newPixel);
            }
        }
        change = dest;
        return dest;
    }
    private Bitmap sugession11(Bitmap src) {

        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                int pixelColor = src.getPixel(x, y);
                int pixelBlue = Color.blue(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int newPixel = Color.argb(
                        pixelAlpha, pixelGreen, pixelRed, pixelRed);
                dest.setPixel(x, y, newPixel);
            }
        }
        change = dest;
        return dest;
    }
    private Bitmap sugession12(Bitmap src) {

        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                int pixelColor = src.getPixel(x, y);
                int pixelBlue = Color.blue(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int newPixel = Color.argb(
                        pixelAlpha, pixelGreen, pixelBlue, pixelBlue);
                dest.setPixel(x, y, newPixel);
            }
        }
        change = dest;
        return dest;
    }
    private Bitmap sugession13(Bitmap src) {

        Bitmap dest = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(), src.getConfig());

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                int pixelColor = src.getPixel(x, y);
                int pixelBlue = Color.blue(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int newPixel = Color.argb(
                        pixelAlpha, pixelRed, pixelRed, pixelBlue);
                dest.setPixel(x, y, newPixel);
            }

        }
        change = dest;
        return dest;
    }

}
