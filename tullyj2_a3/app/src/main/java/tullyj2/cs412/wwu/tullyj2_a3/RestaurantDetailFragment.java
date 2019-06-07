package tullyj2.cs412.wwu.tullyj2_a3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class RestaurantDetailFragment extends Fragment implements View.OnClickListener{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */

    private TextView titleText;
    private Button updateBtn, deleteBtn;
    private EditText descText;
    private int score = -1;

    DrinkDetailFragment.RefreshClicked mCallback;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static RestaurantDetailFragment newInstance(String string) {
        RestaurantDetailFragment f = new RestaurantDetailFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("test_string", string);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        try {
            mCallback = (DrinkDetailFragment.RefreshClicked) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement TextClicked");
        }
        Bundle bundle = getArguments();

        final View rootView = inflater.inflate(R.layout.fragment_modify_item, container, false);

        titleText = (TextView) rootView.findViewById(R.id.detail_title);
        descText = (EditText) rootView.findViewById(R.id.description_edittext);

        updateBtn = (Button) rootView.findViewById(R.id.btn_update);
        deleteBtn = (Button) rootView.findViewById(R.id.btn_delete);

        if (bundle != null) {
            String name = bundle.getString("name");
            String desc = bundle.getString("desc");
            score = bundle.getInt("score");

            titleText.setText(name);
            descText.setText(desc);
            switch (score) {
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
        }

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        return rootView;
    }



    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("restaurants", 0).edit();
        SharedPreferences prefs = getActivity().getSharedPreferences("restaurants", 0);
        String jsonString1 = prefs.getString("restaurantsJSON", null);

        String name = titleText.getText().toString();
        String desc = descText.getText().toString();
        switch (v.getId()) {
            case R.id.btn_update:

                try {
                    JSONObject raw = new JSONObject(jsonString1);
                    JSONArray m_jArry = raw.getJSONArray("restaurants");
                    int i;
                    for (i = 0; i < m_jArry.length(); i++) {
                        JSONObject jo_inside = m_jArry.getJSONObject(i);
                        //Log.d("Details-->", jo_inside.getString("formule"));
                        if ( name.equals(jo_inside.getString("name"))) {
                            break;
                        }
                    }

                    m_jArry.getJSONObject(i).put("desc", desc).put("score", score);
                    JSONObject mainObject = new JSONObject();
                    mainObject.put("restaurants", m_jArry);
                    System.out.println(mainObject);

                    editor.putString("restaurantsJSON", mainObject.toString());
                    editor.apply();


                } catch (Exception e) {
                    e.printStackTrace();

                }

                mCallback.refreshList(1);
                break;

            case R.id.btn_delete:
                //TODO:delete

                try {
                    JSONObject raw = new JSONObject(jsonString1);
                    JSONArray m_jArry = raw.getJSONArray("restaurants");
                    int i;
                    for (i = 0; i < m_jArry.length(); i++) {
                        JSONObject jo_inside = m_jArry.getJSONObject(i);
                        if ( name.equals(jo_inside.getString("name"))) {
                            break;
                        }
                    }

                    m_jArry.remove(i);
                    JSONObject mainObject = new JSONObject();
                    mainObject.put("restaurants", m_jArry);
                    System.out.println(mainObject);

                    editor.putString("restaurantsJSON", mainObject.toString());
                    editor.apply();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                mCallback.refreshList(1);
                break;
        }
    }


}
