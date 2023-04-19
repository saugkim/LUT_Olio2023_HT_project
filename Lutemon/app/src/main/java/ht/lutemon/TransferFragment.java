package ht.lutemon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransferFragment extends Fragment {

    private static final String TAG = "ZZ Transfer";
    private static final String ARG_PARAM1 = "param1";
    static String[] fields = new String[] {"HOME", "TRAIN FIELD", "BATTLE FIELD"};
    LutemonRepository repository;
    LinearLayout linearLayout;
    RadioGroup radioGroup;
    Button buttonTransfer;
    private String field;

    ArrayList<RadioButton> radioButtons;
    ArrayList<Integer> lutemonIndexes;

    LutemonViewModel viewModel;
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
        repository = new LutemonRepository(requireActivity().getApplication());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        linearLayout = view.findViewById(R.id.linearLayoutForList);
        radioGroup = view.findViewById(R.id.radioGroupField);
        buttonTransfer = view.findViewById(R.id.buttonTransfer);

        viewModel = new LutemonViewModel(requireActivity().getApplication());

        addRadioButton(view);
        resetUI();
        cleanLayout();

//        ArrayList<Lutemon> mons = getListOfLutemonInThisField(field);
//        for (Lutemon lutemon : mons) {
//            addCheckboxElement(lutemon.shortInfo(), view);
//        }

        buttonTransfer.setOnClickListener(v-> {
            String arena = getArena();
            if (arena == null) {
                Toast.makeText(v.getContext(), "Select field first", Toast.LENGTH_LONG).show();
                return;
            }

            getSelectedLutemons();
            if (lutemonIndexes.size() == 0) {
                Toast.makeText(v.getContext(), "Select lutemons first", Toast.LENGTH_LONG).show();
                return;
            }

            for (Integer index : lutemonIndexes) {
                repository.updateArena(index, arena.split(" ")[0]);
            }
            resetUI();
        });

        return view;
    }

    private void resetUI() {
        for (RadioButton rb : radioButtons) {
            rb.setChecked(false);
        }

        if (linearLayout.getChildCount() > 0) {
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                CheckBox cb = (CheckBox) linearLayout.getChildAt(i);
                cb.setChecked(false);
            }
        }
        cleanLayout();
    }

    private void cleanLayout() {
        if (linearLayout.getChildCount() > 0) {
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                linearLayout.removeView(linearLayout.getChildAt(i));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        resetUI();
        getLutemonsInArena(field);
    }

    private void getLutemonsInArena(String field) {

        Log.d(TAG, "getLutemonsInArena: onStart only " + field);
        switch (field){
            case "HOME":
                viewModel.getHomeLutemons().observe(getViewLifecycleOwner(), listOfLutemons -> {
                    cleanLayout();
                    for (Lutemon lutemon : listOfLutemons) {
                        addCheckboxElement(lutemon.shortInfo(), lutemon.getId());
                    }
                });
                return;
            case "TRAIN FIELD":
                viewModel.getTrainLutemons().observe(getViewLifecycleOwner(), listOfLutemons -> {
                    cleanLayout();
                    for (Lutemon lutemon : listOfLutemons) {
                        addCheckboxElement(lutemon.shortInfo(), lutemon.getId());
                    }
                });
                return;
            case "BATTLE FIELD":
                viewModel.getBattleLutemons().observe(getViewLifecycleOwner(), listOfLutemons -> {
                    cleanLayout();
                    for (Lutemon lutemon : listOfLutemons) {
                        addCheckboxElement(lutemon.shortInfo(), lutemon.getId());
                    }
                });
                return;
            default:
                break;
        }
    }
    private void addCheckboxElement(String info, int id) {
        CheckBox checkBox = new CheckBox(getContext());
        checkBox.setText(info);
        checkBox.setId(id);
        linearLayout.addView(checkBox);
    }

    private void addRadioButton(View view) {
        radioButtons = new ArrayList<>();
        for (String s : fields) {
            if (!s.equals(field)) {
                RadioButton button = new RadioButton(view.getContext());
                button.setText(s.toLowerCase());
                radioGroup.addView(button);
                radioButtons.add(button);
            }
        }
    }

    private String getArena () {
        for (RadioButton button : radioButtons) {
            if (button.isChecked()) {
                return button.getText().toString().toUpperCase();
            }
        }
        return null;
    }
    private void getSelectedLutemons () {
        lutemonIndexes = new ArrayList<>();
        ArrayList<CheckBox> boxes = new ArrayList<>();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            boxes.add((CheckBox) linearLayout.getChildAt(i));
        }
        for (CheckBox cb : boxes) {
            if (cb.isChecked()) {
                Log.d(TAG, "checkBox id: " + cb.getId());
                lutemonIndexes.add(cb.getId());
            }
        }
    }

    //will be deleted soon
    public ArrayList<Lutemon> getListOfLutemonInThisField(String field) {
        ArrayList<Lutemon> lutemons = new ArrayList<>();
        switch (field) {
            case "HOME":
                return LutemonStorage.getInstance().getLutemonsAtHome();
            case "TRAIN FIELD":
                return LutemonStorage.getInstance().getLutemonsInTrain();
            case "BATTLE FIELD":
                return LutemonStorage.getInstance().getLutemonsInBattles();
            default:
                return lutemons;
        }
    }

}