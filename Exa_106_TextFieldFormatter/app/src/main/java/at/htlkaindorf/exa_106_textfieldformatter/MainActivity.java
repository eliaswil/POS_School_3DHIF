package at.htlkaindorf.exa_106_textfieldformatter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private CheckBox cbBold;
    private CheckBox cbItalic;
    private SeekBar sbSize;
    private RadioGroup rgFont;
    private EditText etInput;
    private TextView tvSize;
    private boolean isBold = false;
    private boolean isItalic = false;


    // ToDo: change color of seekbar, radio buttons and checkboxes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cbBold = findViewById(R.id.cbStyleBold);
        cbItalic = findViewById(R.id.cbStyleItalic);
        sbSize = findViewById(R.id.sbSize);
        rgFont = findViewById(R.id.rgFont);
        etInput = findViewById(R.id.etInputText);
        tvSize = findViewById(R.id.tvSizeValue);

        MyFontStyle mfs = new MyFontStyle();
        cbBold.setOnClickListener(mfs);
        cbItalic.setOnClickListener(mfs);

        sbSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MainActivity.this.onProgressChanged(seekBar, i, b);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rgFont.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Toast.makeText(getApplicationContext(), "on radio group hopefully changed", Toast.LENGTH_SHORT).show();
                Typeface font;

                HashMap<Integer, Integer> fonts = new HashMap<>();
                fonts.put(R.id.rbUbuntu, R.font.ubuntu_light);
                fonts.put(R.id.rbRoboto, R.font.roboto_mono_light);
                fonts.put(R.id.rbComicSans, R.font.coming_soon);
                int fontId = fonts.get(i);

                font = ResourcesCompat.getFont(getApplicationContext(), fontId);
                etInput.setTypeface(font);
            }
        });
    }



    private void onProgressChanged(SeekBar seekBar, int i, boolean b){
        Toast.makeText(getApplicationContext(), "on seekbar hopefully changed ...", Toast.LENGTH_SHORT).show();
        tvSize.setText(Integer.toString(i));
        etInput.setTextSize(i);

    }

    public class MyFontStyle implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Typeface font;
            int typefaceId = 0;
            CheckBox clickedCheckBox = (CheckBox) view;

            Map<Integer, Integer> fontStyle = new HashMap<>();
            fontStyle.put(0, Typeface.NORMAL);
            fontStyle.put(1, Typeface.BOLD);
            fontStyle.put(3, Typeface.ITALIC);
            fontStyle.put(4, Typeface.BOLD_ITALIC);

            if(clickedCheckBox.equals(cbBold)){
                isBold = !isBold;
            }
            if(clickedCheckBox.equals(cbItalic)){
                isItalic = !isItalic;
            }

            if(isBold){
                typefaceId += 1;
            }
            if(isItalic){
                typefaceId += 3;
            }



            font = Typeface.defaultFromStyle(fontStyle.get(typefaceId));
            etInput.setTypeface(font);
            Toast.makeText(getApplicationContext(), "OnCheckBox hopefully clicked ...", Toast.LENGTH_SHORT).show();
        }
    }
}
