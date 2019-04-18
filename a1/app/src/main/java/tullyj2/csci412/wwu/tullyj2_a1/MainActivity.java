package tullyj2.csci412.wwu.tullyj2_a1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        final Button nameButton = findViewById(R.id.nameButton);
        nameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment frag = new NameFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.head_container, frag)
                        .commit();
            }
        });

        final Button bioButton = findViewById(R.id.bioButton);
        bioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment frag = new BioFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.head_container, frag)
                        .commit();
            }
        });

        final Button picButton = findViewById(R.id.picButton);
        picButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment frag = new PicFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.head_container, frag)
                        .commit();
            }
        });
    }


}



