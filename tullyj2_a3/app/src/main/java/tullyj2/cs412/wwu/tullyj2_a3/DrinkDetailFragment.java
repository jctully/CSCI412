package tullyj2.cs412.wwu.tullyj2_a3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import tullyj2.cs412.wwu.tullyj2_a3.Drink;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class DrinkDetailFragment extends Fragment implements View.OnClickListener{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */

    private TextView titleText;
    private Button updateBtn, deleteBtn;
    private EditText descText;
    private int score = -1;


    private long _id;

    private DBManager dbManager;

    RefreshClicked mCallback;

    public interface RefreshClicked {
        public void refreshList(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DBManager(getContext());
        dbManager.open();

    }

    public static DrinkDetailFragment newInstance(String string) {
        DrinkDetailFragment f = new DrinkDetailFragment();

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
            mCallback = (RefreshClicked) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement TextClicked");
        }
        Bundle bundle = getArguments();

        final View rootView = inflater.inflate(R.layout.fragment_modify_drink, container, false);

        titleText = (TextView) rootView.findViewById(R.id.detail_title);
        descText = (EditText) rootView.findViewById(R.id.description_edittext);

        updateBtn = (Button) rootView.findViewById(R.id.btn_update);
        deleteBtn = (Button) rootView.findViewById(R.id.btn_delete);

        if (bundle != null) {
            String id = bundle.getString("id");
            String name = bundle.getString("name");
            String desc = bundle.getString("desc");
            score = bundle.getInt("score");

            _id = Long.parseLong(id);

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
        switch (v.getId()) {
            case R.id.btn_update:
                String name = titleText.getText().toString();
                String desc = descText.getText().toString();

                dbManager.update(_id, new Drink(name, desc, score));
                mCallback.refreshList(1);
                break;

            case R.id.btn_delete:
                dbManager.deleteDrink(_id);
                mCallback.refreshList(1);
                //BaseAdapter.notifyDataSetChanged()
                break;
        }
    }

    public void returnHome() {
        DrinkItemFragment list = new DrinkItemFragment();
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.list_view, list);
        fragmentTransaction.commit();

        //getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }


}
