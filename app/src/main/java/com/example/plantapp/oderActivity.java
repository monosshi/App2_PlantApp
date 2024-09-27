package com.example.plantapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class oderActivity extends AppCompatActivity {

    private CheckBox pothos, peace_lily, rain_lily, coint_plant;
    private ArrayList<String> selectedTrees = new ArrayList<>();

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private TextView quantityTextView, priceTextView, treesTextView, growthValueText;
    private Button increment, decrement, placeOrder;
    private int quantity = 0;
    private int price = 0;

    private AlertDialog.Builder builder;
    private RatingBar ratingBar;


    private SeekBar seekBar;
    private Switch Promotional_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_oder);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        pothos = findViewById(R.id.oak);
        peace_lily = findViewById(R.id.pine);
        rain_lily = findViewById(R.id.maple);
        coint_plant = findViewById(R.id.birch);
        treesTextView = findViewById(R.id.trees);


        radioGroup = findViewById(R.id.radioGroupCare);


        quantityTextView = findViewById(R.id.quantityTextView);
        priceTextView = findViewById(R.id.priceTextView);
        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);

        placeOrder = findViewById(R.id.order_btn);
        builder = new AlertDialog.Builder(this);


        ratingBar = findViewById(R.id.ratingBar);


        seekBar = findViewById(R.id.seekBar);
        Promotional_msg = findViewById(R.id.btn_switch);
        growthValueText= findViewById(R.id.value);


        pothos.setOnCheckedChangeListener(this::onTreeChecked);
        peace_lily.setOnCheckedChangeListener(this::onTreeChecked);
        rain_lily.setOnCheckedChangeListener(this::onTreeChecked);
        coint_plant.setOnCheckedChangeListener(this::onTreeChecked);


        increment.setOnClickListener(v -> updateQuantity(1));
        decrement.setOnClickListener(v -> updateQuantity(-1));


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                growthValueText.setText(progress + "inch");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        Promotional_msg.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(oderActivity.this, "Promotional Message Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(oderActivity.this, "Promotional Message Disabled", Toast.LENGTH_SHORT).show();
            }
        });



        placeOrder.setOnClickListener(v -> placeOrder());
    }

    private void onTreeChecked(CompoundButton buttonView, boolean isChecked) {
        String treeName = buttonView.getText().toString();
        if (isChecked) {
            selectedTrees.add(treeName);
        } else {
            selectedTrees.remove(treeName);
        }
        treesTextView.setText("Selected Trees: " + selectedTrees.toString());
        calculatePrice();
    }

    private void updateQuantity(int change) {
        quantity += change;
        if (quantity < 0) quantity = 0;
        quantityTextView.setText(String.valueOf(quantity));
        calculatePrice();
    }

    private void calculatePrice() {
        int numberOfSelectedTrees = selectedTrees.size();
        price = numberOfSelectedTrees * 250 * quantity;
        priceTextView.setText("BDT " + price);
    }

    private void placeOrder() {
        StringBuilder orderMessage = new StringBuilder("Order Details:\n");
        orderMessage.append("Selected Trees: ").append(selectedTrees).append("\n");
        orderMessage.append("Payment Method: ");
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        if (radioButton != null) {
            orderMessage.append(radioButton.getText()).append("\n");
        }
        orderMessage.append("Quantity: ").append(quantity).append("\n");
        orderMessage.append("Total Price: BDT ").append(price);
        orderMessage.append("\nRating: ").append(ratingBar.getRating());

        builder.setTitle("Confirm Order")
                .setMessage(orderMessage.toString())
                .setCancelable(false)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    Toast.makeText(oderActivity.this, "Order Placed!", Toast.LENGTH_SHORT).show();
                    resetOrder();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void resetOrder() {

        selectedTrees.clear();
        treesTextView.setText("Selected Trees: " + selectedTrees.toString());


        pothos.setChecked(false);
        peace_lily.setChecked(false);
        rain_lily.setChecked(false);
        coint_plant.setChecked(false);


        quantity = 0;
        price = 0;
        quantityTextView.setText("0");
        priceTextView.setText("BDT 0");


        radioGroup.clearCheck();


        seekBar.setProgress(0);
        growthValueText.setText("0");


        Promotional_msg.setChecked(false);


        ratingBar.setRating(0);

    }
}
