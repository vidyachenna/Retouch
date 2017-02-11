package com.example.sowjich.retouch;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by sowji.ch on 2/11/2017.
 */

public class Compare extends AppCompatActivity {
    Uri uri;
    android.widget.ImageView afterImageView, beforeImageView;
    Intent i;
    String img_a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare);
        Intent intent = getIntent();
        final Bitmap change = (Bitmap) intent.getParcelableExtra("bmp");
        String img_touch = "bitmap received";
        Toast toast = Toast.makeText(Compare.this, img_touch, Toast.LENGTH_SHORT);
        toast.show();

        Button cancelButton = (Button)findViewById(R.id.cancelButton);
        Button save = (Button)findViewById(R.id.save);
        afterImageView = (ImageView)findViewById(R.id.afterImageView);

        afterImageView.setImageBitmap(change);
        // iv1.setImageBitmap(thumbnail);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("before cancel");
                //Intent inten1 = new Intent(Compare.this, Edit_p.class);
                //startActivity(inten1);
                //dialog.dismiss();
                finish();
                System.out.println("after cancel");

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = change;
                String ImagePath = MediaStore.Images.Media.insertImage(
                        getContentResolver(),
                        bitmap,
                        "demo_image",
                        "demo_image"
                );



                Toast.makeText(Compare.this, "Image Saved Successfully", Toast.LENGTH_LONG).show();


                final CharSequence[] options = { "Continue Editing", "Select a photo","quit" };

                AlertDialog.Builder builder = new AlertDialog.Builder(Compare.this);
                builder.setTitle("GO TO..");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Continue Editing"))
                        {
                            System.out.println("before cont. ediit");
                            dialog.dismiss();
                            finish();
                            // Intent intent = new   Intent(Compare.this,Edit_p.class);
                            //startActivity(intent);
                            //System.out.println("after cont. ediit");

                        }
                        else if (options[item].equals("Select photo"))
                        {
                            Intent intent = new   Intent(Compare.this,MainActivity.class);
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

        //  viewImage=(android.widget.ImageView)findViewById(R.id.viewImage);

    }

}
