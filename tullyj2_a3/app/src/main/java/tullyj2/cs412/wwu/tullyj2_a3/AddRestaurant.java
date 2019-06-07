package tullyj2.cs412.wwu.tullyj2_a3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.

 * Use the {@link AddRestaurant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRestaurant extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "drink";

    // TODO: Rename and change types of parameters
    private Button addDrinkButton;
    private EditText nameEditText;
    private TextView titleTextView;


    DrinkDetailFragment.RefreshClicked mCallback;


    public AddRestaurant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment AddDrink.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRestaurant newInstance() {
        AddRestaurant fragment = new AddRestaurant();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            mCallback = (DrinkDetailFragment.RefreshClicked) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement TextClicked");
        }

        Log.d("ONCREATEVIEW", "CREATING ADD DRINK VIEW");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_record, container, false);
        titleTextView = (TextView) view.findViewById(R.id.detail_header);
        nameEditText = (EditText) view.findViewById(R.id.detail_name);
        addDrinkButton = (Button) view.findViewById(R.id.save_button);

        titleTextView.setText("Add Restaurant");
        nameEditText.setHint("Enter Drink Name");

        addDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.save_button:

                        final String name = nameEditText.getText().toString();

                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("restaurants", 0).edit();
                        SharedPreferences prefs = getActivity().getSharedPreferences("restaurants", 0);
                        String jsonString1 = prefs.getString("restaurantsJSON", null);

                        try {
                            JSONObject mainObject = new JSONObject(jsonString1);

                            JSONObject valuesObject = new JSONObject();
                            valuesObject.put("name", name);
                            valuesObject.put("desc", "");
                            valuesObject.put("score", 0);

                            JSONArray list = new JSONArray();
                            list.put(valuesObject);
                            mainObject.accumulate("restaurants", valuesObject);
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
        });

    return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
