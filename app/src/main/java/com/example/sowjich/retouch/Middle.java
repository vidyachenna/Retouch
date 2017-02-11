package com.example.sowjich.retouch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by sowji.ch on 2/11/2017.
 */

public class Middle extends Activity {

    ImageView collageImage;
    private static final int PICK_FROM_GALLERY = 2;
    final int CAMERA_CAPTURE = 1;
    final int CROP_PIC = 2;
    private Uri picUri;
    ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.middle);

        Button selimg = (Button) findViewById(R.id.pic_b);
        selimg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }
    private void selectImage() {

        final CharSequence[] options = {"Camera","Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Middle.this);
        builder.setTitle("Crop image from....");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Camera"))
                {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);//zero can be replaced with any action code

// call android
                    //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
    /*create instance of File with name img.jpg*/
                    //File file = new File(Environment.getExternalStorageDirectory()+File.separator + "img.jpg");
    /*put uri as extra in intent object*/
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
    /*start activity for result pass intent as argument and request code */
                    //startActivityForResult(intent, 1);*/
                }

                else if (options[item].equals("Gallery"))
                {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);//one can be replaced with any acti
                }

                   /* Intent intent = new Intent("com.android.camera.action.CROP");
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

                }*/
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    Intent in = new Intent(Middle.this,Image_i.class);
                    in.setData(uri);
                    startActivity(in);
                    //Toast toast = Toast.makeText(Middle.this,"ready", Toast.LENGTH_SHORT);
                    //toast.show();
                    // imageview.setImageURI(selectedImage);
                   /* try {
                        ParcelFileDescriptor parcelFileDescriptor =
                                getContentResolver().openFileDescriptor(uri, "r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        Intent i = new Intent(Middle.this,Image_i.class);
                        i.putExtra("photo",image);
                        Toast toast = Toast.makeText(Middle.this,"ready to transfer", Toast.LENGTH_SHORT);
                        toast.show();
                        startActivity(i);

                        parcelFileDescriptor.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    Intent i = new Intent(Middle.this,Image_i.class);
                    i.setData(uri);
                    startActivity(i);

                    /*try {
                        ParcelFileDescriptor parcelFileDescriptor =
                                getContentResolver().openFileDescriptor(uri, "r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        Toast toast = Toast.makeText(Middle.this,"converted to bitmap", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent i = new Intent(Middle.this,Image_i.class);
                        i.putExtra("photo",image);
                        startActivity(i);

                        parcelFileDescriptor.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    // Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolverâ€Œâ€‹(), uri);
                    //imageview.setImageURI(selectedImage);
                }
                break;
        }
    }

}
