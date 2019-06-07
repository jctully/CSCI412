package tullyj2.cs412.wwu.tullyj2_a3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import tullyj2.cs412.wwu.tullyj2_a3.dummy.DummyContent.DummyItem;

/**
 * A fragment representing a list of Items.
 * <p/>

 */
public class DrinkItemFragment extends Fragment implements DrinkDetailFragment.RefreshClicked{

    private DBManager dbManager;

    private ListView listView;

    public SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID, DatabaseHelper.KEY_DRINK_NAME, DatabaseHelper.KEY_DRINK_DESC, DatabaseHelper.KEY_DRINK_SCORE };

    final int[] to = new int[] { R.id.id1, R.id.id, R.id.content, R.id.score };


    BottomSheetBehavior sheetBehavior;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DrinkItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DrinkItemFragment newInstance(int columnCount) {
        DrinkItemFragment fragment = new DrinkItemFragment();
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

        dbManager = new DBManager(getContext());
        dbManager.open();



//        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drinkitem_list, container, false);

        //dbManager.insert(new Drink("Manhattan", "Yummy", 1));

        Cursor cursor = dbManager.fetch();

        //LinearLayout bottomSheet = (LinearLayout) getActivity().findViewById(R.id.bottom_sheet);
        //sheetBehavior = BottomSheetBehavior.from(bottomSheet);

        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new SimpleCursorAdapter(getContext(), R.layout.fragment_drinkitem, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id1);
                TextView nameTextView = (TextView) view.findViewById(R.id.id);
                TextView descTextView = (TextView) view.findViewById(R.id.content);
                TextView scoreTextView = (TextView) view.findViewById(R.id.score);

                String id = idTextView.getText().toString();
                String name = nameTextView.getText().toString();

                String desc = descTextView.getText().toString();
                int score = Integer.parseInt(scoreTextView.getText().toString());

                Bundle args = new Bundle();
                args.putString("id", id);
                //Log.e("LIST PUSH", "BUNDLING ID " + id);
                args.putString("name", name);
                args.putString("desc", desc);
                args.putInt("score", score);

                DrinkDetailFragment detailFrag = new DrinkDetailFragment();
                detailFrag.setArguments(args);
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.below_list, detailFrag);
                fragmentTransaction.commit();

            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ADD DRINK PRESSED------------------");

//                AddDrink addDrinkFrag = new AddDrink();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(((ViewGroup)(getView().getParent())).getId(), addDrinkFrag)
//                        .commit();
//                toggleBottomSheet();
//                addDrink();

                AddDrink detailFrag = new AddDrink();
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.below_list, detailFrag);
                fragmentTransaction.commit();

            }
        });

        return view;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * click join queue to toggle the bottom sheet
     */
//    public void toggleBottomSheet() {
//        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
//            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        } else {
//            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        }
//    }
//
//    public void onSubmitButtonClick() {
//        View sheet = getActivity().findViewById(R.id.bottom_sheet);
//        EditText drinkNameInput = (EditText) sheet.findViewById(R.id.drink_name_input);
//        final Button submitButton = (Button) sheet.findViewById(R.id.submit_button);
//
//        final String studentName = drinkNameInput.getText().toString();
//
//        // Email Empty
//        if(TextUtils.isEmpty(studentName)) {
//            drinkNameInput.setError("This field cannot be empty!");
//            return;
//        }
//
//
//        //Log.d("AAAAAAAAAAAAAAAAAAAAAA", "In OnCLick");
//
//        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//    }
}
