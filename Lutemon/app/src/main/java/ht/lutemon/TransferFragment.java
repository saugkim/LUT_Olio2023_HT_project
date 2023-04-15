package ht.lutemon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransferFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private String field;

    static String[] fields = new String[] {"HOME", "TRAIN FIELD", "BATTLE FIELD"};
    TextView textView;
    LinearLayout linearLayout;
    RadioGroup radioGroup;
    Button buttonTransfer;

    ArrayList<Lutemon> mons;
    public TransferFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @return A new instance of fragment Test1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransferFragment newInstance(String param1) {
        TransferFragment fragment = new TransferFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            field = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        //textView = view.findViewById(R.id.textView);
        linearLayout = view.findViewById(R.id.linearLayoutForList);
        radioGroup = view.findViewById(R.id.radioGroupField);
        buttonTransfer = view.findViewById(R.id.buttonTransfer);

        mons = getListOfLutemonInThisField(field);
        for (Lutemon lutemon : mons) {
            addCheckboxElement(lutemon.shortInfo(), view);
        }
        //textView.setText(String.format("Current Lutemons at %s", field));
        addRadioButton(view);
        buttonTransfer.setOnClickListener(v-> {
            System.out.println("do something");
        });
        return view;
    }

    public ArrayList<Lutemon> getListOfLutemonInThisField(String field) {
        ArrayList<Lutemon> lutemons = new ArrayList<>();

        switch (field) {
            case "HOME":
                return HomeStorage.getInstance().getLutemons();
            case "TRAIN FIELD":
                return TrainStorage.getInstance().getLutemons();
            case "BATTLE FIELD":
//                lutemons.add(new Orange());
//                lutemons.add(new Pink());
                return BattleStorage.getInstance().getLutemons();
            default:
                return lutemons;
        }
    }
    private void addCheckboxElement(String info, View view) {
        CheckBox checkBox = new CheckBox(view.getContext());
        checkBox.setText(info);
        linearLayout.addView(checkBox);
    }

    private void addRadioButton(View view) {
        for (String s : fields) {
            if (!s.equals(field)) {
                RadioButton button = new RadioButton(view.getContext());
                button.setText(s.toLowerCase());
                radioGroup.addView(button);
            }
        }
    }
}