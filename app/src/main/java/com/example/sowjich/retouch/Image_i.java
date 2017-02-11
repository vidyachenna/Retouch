package com.example.sowjich.retouch;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by sowji.ch on 2/11/2017.
 */

public class Image_i extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    ImageView inimg,outimg;
    Bitmap thumbnail,thumb;
    Button select;
    private Matrix matrix = new Matrix();
    private Matrix matrix1 = new Matrix();
    private Matrix savedMatrix = new Matrix();
    // we can be in one of these 3 state
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    // remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;
    private float[] lastEvent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_i);
        inimg = (ImageView)findViewById(R.id.imageView);
        matrix1 = inimg.getImageMatrix();
        outimg = (ImageView)findViewById(R.id.out);
        select = (Button)findViewById(R.id.frame);
        Button backButton = (Button) findViewById(R.id.back);
        Button saveButton = (Button) findViewById(R.id.save);
        Uri in_img = getIntent().getData();
        Toast toast = Toast.makeText(Image_i.this,"got uri", Toast.LENGTH_SHORT);
        toast.show();
        String path = getPathFromURI(in_img);
        thumbnail = BitmapFactory.decodeFile(path);
        Toast toas = Toast.makeText(Image_i.this,"got bitmap", Toast.LENGTH_SHORT);
        toas.show();
        // Intent intent = getIntent();
        //thumbnail = (Bitmap) intent.getParcelableExtra("photo");
        //Toast toa = Toast.makeText(Image_i.this,"got the image", Toast.LENGTH_SHORT);
        //toa.show();
        inimg.setImageBitmap(thumbnail);
        select.setOnClickListener(this);
        outimg.setOnTouchListener(this);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tag = "at backButton";
                Log.i(Tag, "before intent");
                Intent i = new Intent(Image_i.this, Middle.class);
                startActivity(i);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  Intent ij = new Intent(Image_i.this, Compare.class);
                ij.putExtra("bmp",change);
                String img_touch = "bitmap gng to be sent";
                Toast toast = Toast.makeText(Edit_i.this, img_touch, Toast.LENGTH_SHORT);
                startActivity(ij);
                toast.show();*/
                //Bitmap bigImage =thumbnail;
                //Bitmap smallImage = thumb;
                Bitmap mergedImages = createSingleImageFromMultipleImages(thumbnail, thumb);
                //Bitmap change = mergedImages;
                // Toast.makeText(Image_i.this,"img",Toast.LENGTH_SHORT).show();
                //collageImage.setImageBitmap(mergedImages);
                //Intent intent = new Intent(Image_i.this,Show.class);
                //intent.putExtra("bitmap",mergedImages);
                //startActivity(intent);

                String ImagePath = MediaStore.Images.Media.insertImage(
                        getContentResolver(),
                        mergedImages,
                        "demo_image",
                        "demo_image"
                );

                Uri uri = Uri.parse(ImagePath);

                Toast.makeText(Image_i.this, ImagePath, Toast.LENGTH_LONG).show();

                final CharSequence[] options = { "Continue Editing", "Select a photo","quit" };

                AlertDialog.Builder builder = new AlertDialog.Builder(Image_i.this);
                builder.setTitle("GO TO..");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Continue Editing"))
                        {
                            System.out.println("before cont. ediit");
                            dialog.dismiss();
                            // finish();
                            // Intent intent = new   Intent(Compare.this,Edit_p.class);
                            //startActivity(intent);
                            //System.out.println("after cont. ediit");

                        }
                        else if (options[item].equals("Select photo"))
                        {
                            Intent intent = new   Intent(Image_i.this,Middle.class);
                            startActivity(intent);

                        }
                        else if (options[item].equals("quit")) {
                           /* Intent intent = new Intent(getApplicationContext(), Splash_screen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);*/
                            finish();
                            moveTaskToBack(true);
                            dialog.dismiss();
                            finish();
                        }
                    }
                });
                builder.show();


            }
        });


    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                String picpath = getPathFromURI(selectedImageUri);
                thumb = (BitmapFactory.decodeFile(picpath));
                outimg.setImageBitmap(thumb);


            }
        }
    }


    @Override
    public void onClick(View v) {
        openImageChooser();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    matrix.postTranslate(dx, dy);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {

                        matrix.set(savedMatrix);

                        float scale = (newDist / oldDist);
                        matrix.postScale(scale, scale, mid.x, mid.y);

                    }
                    if (lastEvent != null && event.getPointerCount() == 3) {

                        newRot = rotation(event);
                        float r = newRot - d;
                        float[] values = new float[9];

                        matrix.getValues(values);
                        float tx = values[2];
                        float ty = values[5];
                        float sx = values[0];
                        float xc = (view.getWidth() / 2) * sx;
                        float yc = (view.getHeight() / 2) * sx;
                        matrix.postRotate(r, tx + xc, ty + yc);

                    }
                }
                break;
        }
        view.setImageMatrix(matrix);
        return true;
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);

        return (float)Math.sqrt(x*x + y*y);
    }


    private Bitmap createSingleImageFromMultipleImages(Bitmap c, Bitmap s){

        Bitmap cs = null;

        int width, height = 0;

        if(c.getWidth() > s.getWidth()) {
            width = c.getWidth();
            height = c.getHeight() + s.getHeight();
        } else {
            width = s.getWidth();
            height = c.getHeight() + s.getHeight();
        }

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);
        comboImage.drawBitmap(c, matrix1, null);
        comboImage.drawBitmap(s,matrix, null);
        return cs;
    }


}
