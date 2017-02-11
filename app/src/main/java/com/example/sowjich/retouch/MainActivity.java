package com.example.sowjich.retouch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by sowji.ch on 2/11/2017.
 */

public class MainActivity extends Activity {

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    final int CAMERA_CAPTURE = 1;
    final int CROP_PIC = 2;
    private Uri picUri;
    ImageView imgview;
    Button color_b,add_f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        color_b=(Button)findViewById(R.id.color_b);
        add_f=(Button)findViewById(R.id.add_f);

        color_b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        add_f.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ip = new Intent(MainActivity.this,Middle.class);
                startActivity(ip);
            }
        });

    }
    private void selectImage() {

        final CharSequence[] options = {"Camera","Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Crop image from....");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Camera"))
                {

// call android
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
    /*create instance of File with name img.jpg*/
                    File file = new File(Environment.getExternalStorageDirectory()+File.separator + "img.jpg");
    /*put uri as extra in intent object*/
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
    /*start activity for result pass intent as argument and request code */
                    startActivityForResult(intent, 1);
                }

                if (options[item].equals("Gallery"))
                {

                    Intent intent = new Intent("com.android.camera.action.CROP");
// call android default gallery
                    intent.setType("image/*");
                    // intent.setAction(Intent.ACTION_GET_CONTENT);
// ******** code for crop image
                    intent.putExtra("crop", "true");
                    intent.putExtra("aspectX", 0);
                    intent.putExtra("aspectY", 0);
                    intent.putExtra("outputX", 0);
                    intent.putExtra("outputY", 0);

                    try {

                        intent.putExtra("return-data", true);
                        startActivityForResult(Intent.createChooser(intent,
                                "Complete action using"), PICK_FROM_GALLERY);

                    } catch (ActivityNotFoundException e) {
// Do nothing for now
                    }

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            //create instance of File with same name we created before to get image from storage
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "img.jpg");
            //Crop the captured image using an other intent
            try {
    /*the user's device may not support cropping*/
                cropCapturedImage(Uri.fromFile(file));
            }
            catch(ActivityNotFoundException aNFE){
                //display an error message if user device doesn't support
                String errorMessage = "Sorry - your device doesn't support the crop action!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        if(requestCode==2){
            //Create an instance of bundle and get the returned data
            Bundle ext= data.getExtras();
            //get the cropped bitmap from extras
            Bitmap Pic = ext.getParcelable("data");
            //set image bitmap to image view
            Intent sa = new Intent(MainActivity.this,Edit_i.class);
            sa.putExtra("photo",Pic);
            startActivity(sa);
            // imgview.setImageBitmap(thePic);
        }


        /*if (requestCode == PICK_FROM_GALLERY) {
            Bundle extras2 = data.getExtras();
            if (extras2 != null) {
                Bitmap photo = extras2.getParcelable("data");
              //  imgview.setImageBitmap(photo);
                Intent sai = new Intent(MainActivity.this,Edit_i.class);
                sai.putExtra("photo",photo);
                startActivity(sai);
            }
        }*/
    }
    public void cropCapturedImage(Uri picUri){
        //call the standard crop action intent
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri of image
        cropIntent.setDataAndType(picUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 0);
        cropIntent.putExtra("aspectY", 0);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 0);
        cropIntent.putExtra("outputY", 0);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, 2);
    }

}
