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
    static String[] ARENAS = new String[] {"HOME", "TRAIN FIELD", "BATTLE FIELD"};
    LutemonRepository repository;
    LinearLayout layoutForLutemons;
    RadioGroup radioGroupArena;
    Button buttonTransfer;
    private String arena;

    ArrayList<RadioButton> radioButtons;
    ArrayList<Integer> lutemonIndexes;
    ArrayList<String> lutemonInfoStrings;

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
            arena = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        repository = new LutemonRepository(requireActivity().getApplication());
        viewModel = new LutemonViewModel(requireActivity().getApplication());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        layoutForLutemons = view.findViewById(R.id.linearLayoutForList);
        radioGroupArena = view.findViewById(R.id.radioGroupField);
        buttonTransfer = view.findViewById(R.id.buttonTransfer);

        createArenaListToTransferInRadioGroup(view);

        resetUI();

        buttonTransfer.setOnClickListener(this::transfer);

        return view;
    }

    /** invoked by buttonTransfer
     * transfer lutemon from current arena to another
     * transfer to Home recover current health to max health
     * @param view
     */
    private void transfer(View view) {
        String arena = getSelectedArenaToTransfer();
        if (arena == null) {
            Toast.makeText(view.getContext(), "Select field first", Toast.LENGTH_LONG).show();
            return;
        }

        getSelectedLutemonsToTransfer();
        if (lutemonIndexes.size() == 0) {
            Toast.makeText(view.getContext(), "Lutemon not selected", Toast.LENGTH_LONG).show();
            return;
        }

        for (int i = 0; i < lutemonIndexes.size(); i++) {
            int index = lutemonIndexes.get(i);
            repository.updateArena(index, arena.split(" ")[0]);

            if (arena.equals("HOME")) {
                String HP = lutemonInfoStrings.get(i).split("\\[")[1].split(" ")[6].split("/")[1];
                repository.updateCurrentHealthToMax(index, Integer.parseInt(HP));
            }
        }

        resetUI();
    }

    private void resetUI() {
        radioGroupArena.clearCheck();
        cleanLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetUI();
        createListOfLutemonsInArena(arena);
    }

    private void createListOfLutemonsInArena(String field) {
        Log.d(TAG, "getLutemonsInArena: called at onResume() " + field);
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
        if (layoutForLutemons.getChildCount() > 0) {
            for (int i = 0; i < layoutForLutemons.getChildCount(); i++) {
                CheckBox cb = (CheckBox) layoutForLutemons.getChildAt(i);
                if (cb.getId() == id)
                    layoutForLutemons.removeView(cb);
            }
        }

        CheckBox checkBox = new CheckBox(getContext());
        checkBox.setText(info);
        checkBox.setId(id);
        layoutForLutemons.addView(checkBox);
    }

    private void createArenaListToTransferInRadioGroup(View view) {
        radioButtons = new ArrayList<>();
        for (String s : ARENAS) {
            if (!s.equals(arena)) {
                RadioButton button = new RadioButton(view.getContext());
                button.setText(s.toLowerCase());
                radioGroupArena.addView(button);
                radioButtons.add(button);
            }
        }
    }

    private String getSelectedArenaToTransfer() {
        for (RadioButton button : radioButtons) {
            if (button.isChecked()) {
                return button.getText().toString().toUpperCase();
            }
        }
        return null;
    }
    private void getSelectedLutemonsToTransfer() {
        lutemonIndexes = new ArrayList<>();
        lutemonInfoStrings = new ArrayList<>();
        ArrayList<CheckBox> boxes = new ArrayList<>();
        for (int i = 0; i < layoutForLutemons.getChildCount(); i++) {
            boxes.add((CheckBox) layoutForLutemons.getChildAt(i));
        }
        for (CheckBox cb : boxes) {
            if (cb.isChecked()) {
                Log.d(TAG, "checkBox id: " + cb.getId());
                lutemonIndexes.add(cb.getId());
                lutemonInfoStrings.add(cb.getText().toString());
            }
        }
    }

    private void cleanLayout() {
        if (layoutForLutemons.getChildCount() > 0) {
            for (int i = 0; i < layoutForLutemons.getChildCount(); i++) {
                layoutForLutemons.removeView(layoutForLutemons.getChildAt(i));
            }
        }
    }
}