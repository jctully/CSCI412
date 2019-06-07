package tullyj2.cs412.wwu.tullyj2_a3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tullyj2 on 6/7/19.
 */
public class RestaurantsAdapter extends ArrayAdapter<Restaurant> {

    public RestaurantsAdapter(Context context, ArrayList<Restaurant> restaurants) {

        super(context, 0, restaurants);

    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        Restaurant restaurant = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_restaurantitem, parent, false);

        }

        // Lookup view for data population

        TextView tvName = (TextView) convertView.findViewById(R.id.rest_id);

        TextView tvDesc = (TextView) convertView.findViewById(R.id.rest_content);

        TextView tvScore = (TextView) convertView.findViewById(R.id.rest_score);


        // Populate the data into the template view using the data object

        tvName.setText(restaurant.name);

        tvDesc.setText(restaurant.desc);

        tvScore.setText(String.valueOf(restaurant.score));


        // Return the completed view to render on screen

        return convertView;

    }

}
