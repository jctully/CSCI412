package tullyj2.cs412.wwu.tullyj2_a3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>

 */
public class RestaurantItemFragment extends Fragment implements DrinkDetailFragment.RefreshClicked{


    private ListView listView;

    ArrayAdapter<String> adapter;

    SharedPreferences.Editor editor;

    JSONObject raw;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RestaurantItemFragment newInstance(int columnCount) {
        RestaurantItemFragment fragment = new RestaurantItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void refreshList(int position) {
        Log.e("REFRESH", "IN REFRESH FRAG ___________________________");
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurantitem_list, container, false);

        listView = (ListView) view.findViewById(R.id.rest_list_view);

        ArrayList<Restaurant> arrayOfRestaurants = new ArrayList<>();

        SharedPreferences prefs = getActivity().getSharedPreferences("restaurants", 0);
        String restoredText = prefs.getString("restaurantsJSON", null);
        if (restoredText != null) {
            try {
                raw = new JSONObject(restoredText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                raw = new JSONObject(loadJSONFromAsset());
                editor = getActivity().getSharedPreferences("restaurants", 0).edit();
                editor.putString("restaurantsJSON", raw.toString());
                editor.apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            JSONArray m_jArry = raw.getJSONArray("restaurants");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                //Log.d("Details-->", jo_inside.getString("formule"));
                String name = jo_inside.getString("name");
                String desc = jo_inside.getString("desc");
                int score = jo_inside.getInt("score");

                //Add your values in your `ArrayList` as below:
                arrayOfRestaurants.add(new Restaurant(name, desc, score));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RestaurantsAdapter adapter = new RestaurantsAdapter(getContext(), arrayOfRestaurants);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                //TextView idTextView = (TextView) view.findViewById(R.id.rest_id1);
                TextView nameTextView = (TextView) view.findViewById(R.id.rest_id);
                TextView descTextView = (TextView) view.findViewById(R.id.rest_content);
                TextView scoreTextView = (TextView) view.findViewById(R.id.rest_score);

                //String id = idTextView.getText().toString();
                String name = nameTextView.getText().toString();

                String desc = descTextView.getText().toString();
                int score = Integer.parseInt(scoreTextView.getText().toString());

                Bundle args = new Bundle();
                //args.putString("id", id);
                //Log.e("LIST PUSH", "BUNDLING ID " + id);
                args.putString("name", name);
                args.putString("desc", desc);
                args.putInt("score", score);

                //TODO: call detail fragment
                RestaurantDetailFragment detailFrag = new RestaurantDetailFragment();
                detailFrag.setArguments(args);
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rest_below_list, detailFrag);
                fragmentTransaction.commit();

            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.rest_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRestaurant detailFrag = new AddRestaurant();
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rest_below_list, detailFrag);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("restaurants.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
