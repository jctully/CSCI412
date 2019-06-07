package tullyj2.cs412.wwu.tullyj2_a3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.

 * Use the {@link AddDrink#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDrink extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "drink";

    // TODO: Rename and change types of parameters
    private Button addDrinkButton;
    private EditText nameEditText;
    private TextView titleTextView;

    private DBManager dbManager;

    private String mParam1;

    DrinkDetailFragment.RefreshClicked mCallback;


    public AddDrink() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment AddDrink.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDrink newInstance() {
        AddDrink fragment = new AddDrink();
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

        titleTextView.setText("Add Drink");
        nameEditText.setHint("Enter Drink Name");

        dbManager = new DBManager(getContext());
        dbManager.open();

        addDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.save_button:

                        final String name = nameEditText.getText().toString();

                        Drink newDrink = new Drink(name, "", 0);

                        dbManager.insert(newDrink);

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
