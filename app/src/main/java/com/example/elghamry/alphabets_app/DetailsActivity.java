package com.example.elghamry.alphabets_app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ELGHAMRY on 18/03/2016.
 */

public class DetailsActivity extends AppCompatActivity {
    private TextView titleTextView;
    private ImageView imageView;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView txtSpeechInput
            ;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__view);


        title  = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        titleTextView = (TextView) findViewById(R.id.title);
        imageView = (ImageView) findViewById(R.id.grid_item_image);
        titleTextView.setText("letter " + Html.fromHtml(title));

        Picasso.with(this).load(image).into(imageView);
        ImageView youtube = (ImageView) findViewById(R.id.youtube);

        txtSpeechInput = (TextView) findViewById(R.id.txSpeechInput);
        ImageView speech_image = (ImageView) findViewById(R.id.speeach_img);

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/results?search_query=Letter+" + title;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });
        speech_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();

            }
        });


    }
    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result.get(0).toUpperCase().charAt(0)==title.charAt(0))
                    txtSpeechInput.setText("True");
                    else
                        txtSpeechInput.setText("False");


                }

                break;
            }

        }
    }


}