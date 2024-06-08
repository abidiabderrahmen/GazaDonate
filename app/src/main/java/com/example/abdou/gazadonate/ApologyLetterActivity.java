package com.example.abdou.gazadonate;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ApologyLetterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apology_letter);

        TextView textView = findViewById(R.id.textView);
        String apologyText = "I am writing this letter with a heavy heart to express my deepest apologies for the inaction and lack of tangible support from many of us in Tunisia regarding the ongoing struggle of the Palestinian people against Zionism and oppression.\n" +
                "\n" +
                "It pains me to acknowledge that despite our shared values of justice, freedom, and solidarity, we have not done enough to stand by your side during these challenging times. The injustices and hardships faced by Palestinians, the loss of lives, the destruction of homes, and the denial of basic human rights are deeply distressing, and I understand the frustration and disappointment that many of you must feel towards us.\n" +
                "\n" +
                "As a Tunisian, I believe in the importance of standing up against injustice wherever it may occur. However, I also recognize that words alone are not enough. Action is needed to bring about meaningful change and support for your cause.\n" +
                "\n" +
                "Please know that there are many of us who are deeply committed to advocating for Palestinian rights, raising awareness about the situation, and urging our government and international communities to take concrete steps towards justice and peace in the region. We stand in solidarity with you and vow to continue our efforts to support your struggle.\n" +
                "\n" +
                "I sincerely apologize for any hurt or disappointment caused by our inaction, and I hope that our collective voices and actions can contribute to a brighter future where all people, including Palestinians, can live in dignity, freedom, and peace.\n" +
                "\n" +
                "With heartfelt apologies and solidarity.";
        textView.setText(apologyText);
    }
}
