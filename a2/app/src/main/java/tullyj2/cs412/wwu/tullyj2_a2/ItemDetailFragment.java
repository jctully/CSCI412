package tullyj2.cs412.wwu.tullyj2_a2;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import tullyj2.cs412.wwu.tullyj2_a2.dummy.DummyContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment implements View.OnClickListener{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";


    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    private EditText detail_input;

    private int score = -1;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            if (mItem.details != null) {
                Log.d("ONCREATE", mItem.details);
            }
            else {
                Log.d("ONCREATE", "no details in dummy");

            }

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.detail_header)).setText(DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID)).content);
            if (DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID)).details != null) {
                Log.d("CREATEVIEWIF", DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID)).details);
                ((EditText) rootView.findViewById(R.id.detail_input)).setText(DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID)).details);
            }
            else {
                Log.d("CREATEVIEWELSE", "Drawing without details");

            }

            switch (DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID)).score) {
                case 1:
                    ((RadioButton) rootView.findViewById(R.id.radio_left)).setChecked(true);
                    break;
                case 2:
                    ((RadioButton) rootView.findViewById(R.id.radio_mid)).setChecked(true);
                    break;
                case 3:
                    ((RadioButton) rootView.findViewById(R.id.radio_right)).setChecked(true);
                    break;
            }

            detail_input = (EditText) rootView.findViewById(R.id.detail_input);
            RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.radio_group);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // checkedId is the RadioButton selected

                    switch (checkedId) {
                        case R.id.radio_left:
                            score = 1;
                            break;
                        case R.id.radio_mid:
                            score = 2;
                            break;
                        case R.id.radio_right:
                            score = 3;
                            break;
                    }
                }
            });

            final Button button = rootView.findViewById(R.id.save_button);
            button.setOnClickListener(this);

        }

        return rootView;
    }

    @Override
    public void onClick(View v) {

        if (detail_input.getText() != null) {
            //Toast.makeText(rootView.getContext(), detail_input.getText(), Toast.LENGTH_LONG).show();
            mItem.details = (String) detail_input.getText().toString();

            //                    boolean checked = ((RadioButton) v).isChecked();

            mItem.score = score;
            mItem.edited = true;


            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
           getActivity().recreate();



        }
    }


}
