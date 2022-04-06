package com.example.coffee_app_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Slider size_slider;
    TextInputLayout name_ET , address_ET;
    private TextView large_tv , medium_tv , small_tv, quantity_price;
    private CheckBox whipped_cream , chocolate , soy_milk , almond_milk;
    private boolean is_whipped_ceram , is_chocolate , is_soy_milk , is_almond_milk;
    private Button order_btn , incease_quantity  , decease_quantity;
    private TextView quantity_txt;
    int init_price = 20 , quantity = 1;


    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Coffee App");

        size_slider = findViewById(R.id.slider);
        large_tv = findViewById(R.id.large_txt);
        medium_tv = findViewById(R.id.medium_txt);
        small_tv = findViewById(R.id.small_txt);
        whipped_cream = findViewById(R.id.whipped_cream_checkbox);
        chocolate = findViewById(R.id.chocolate_checkbox);
        soy_milk = findViewById(R.id.soy_milk);
        almond_milk = findViewById(R.id.almond_milk);
        quantity_price = findViewById(R.id.quantity_price);
        order_btn = findViewById(R.id.order_btn);
        incease_quantity = findViewById(R.id.increment_btn);
        decease_quantity = findViewById(R.id.decrement_btn);
        name_ET = findViewById(R.id.name_txt);
        address_ET = findViewById(R.id.address_txt);
        quantity_txt = findViewById(R.id.quantity_textView);


        decease_quantity.setOnClickListener(view -> {

            if(quantity == 1)
            {
                Toast.makeText(MainActivity.this, "Quantity Must be greater than 0", Toast.LENGTH_SHORT).show();
            }
            else
            {
                quantity -= 1;
                quantity_txt.setText(String.valueOf(quantity));
            }
        });

        incease_quantity.setOnClickListener(view -> {
            if(quantity >= 6)
            {
                Toast.makeText(MainActivity.this, "Sorry! You can't order more than 6 at a time", Toast.LENGTH_SHORT).show();
            }
            else
            {
                quantity +=1;
                quantity_txt.setText(String.valueOf(quantity));
            }
        });

        order_btn.setOnClickListener(view -> {

            is_whipped_ceram = whipped_cream.isChecked();
            is_chocolate = chocolate.isChecked();
            is_soy_milk = soy_milk.isChecked();
            is_almond_milk = almond_milk.isChecked();

            String name , address , summary;
            name = Objects.requireNonNull(name_ET.getEditText()).getText().toString();
            address = Objects.requireNonNull(address_ET.getEditText()).getText().toString();
            name_ET.setErrorEnabled(false);

            if (name.trim().isEmpty())
            {
                name_ET.requestFocus();
                name_ET.setErrorEnabled(true);
                name_ET.setError("Please Enter Name");
            }
            else if (address.trim().isEmpty())
            {
                name_ET.setErrorEnabled(false);
                address_ET.requestFocus();
                address_ET.setErrorEnabled(true);
                address_ET.setError("Please Enter Address");
            }
            else
            {
                summary = "Name: " + name +"\n"+ "Address: " + address
                        + "\n" + "Whipped Cream ? " + is_whipped_ceram + "\n" + "Chocolate ? " + is_chocolate
                        + "\n" + "Soy Milk ? " + is_soy_milk + "\n" + "Almond Milk ? " + is_almond_milk
                        + "\n" + "Quantity: " + quantity
                        + "\n" + "Total Price: " + init_price*quantity;


                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                //emailIntent.setData(Uri.fromParts("mailto:" , "hamzaKohat266@gmail.com" , null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order for Hamza");
                emailIntent.putExtra(Intent.EXTRA_TEXT, summary);
                if(emailIntent.resolveActivity(getPackageManager()) != null)
                {
                    startActivity(Intent.createChooser(emailIntent, "Order mail..."));
                }
            }

        });



        check_box();
        slider_of_size();

    }
    

    private void slider_of_size()
    {
        size_slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                float size_value = slider.getValue();
                if(size_value == 1)
                {
                    change_text(small_tv , 15 , "Small");
                    change_clr_face(large_tv);
                    change_clr_face(medium_tv);
                }
                else if(size_value == 2)
                {
                    change_text(medium_tv , 20 , "Medium");
                    change_clr_face(large_tv );
                    change_clr_face(small_tv);
                }
                else
                {
                    change_text(large_tv , 25 , "Large");
                    change_clr_face(medium_tv);
                    change_clr_face(small_tv);
                }

            }
        });
    }

    private void change_text(TextView txtName, int price , String cup_size)
    {
        // false value for all checkboxes should pass before setting the price, to remove unwanted behavior
        whipped_cream.setChecked(false);
        chocolate.setChecked(false);
        soy_milk.setChecked(false);
        almond_milk.setChecked(false);

        init_price = price;
        txtName.setTextSize(16);
        txtName.setTextColor(getResources().getColor(R.color.stroke_color));
        txtName.setTypeface(null , Typeface.BOLD);
        String price_str = "Price Per " + cup_size + " Cup: " + price +"$";
        quantity_price.setText(price_str);


    }
    private void change_clr_face(TextView txtName)
    {
        //init_price = price;
        txtName.setTextSize(14);
        txtName.setTextColor(getResources().getColor(R.color.black));
        txtName.setTypeface(null , Typeface.NORMAL);
    }

    private void check_box()
    {
        chocolate.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked)
            {
                init_price +=3;
                Toast.makeText(MainActivity.this, "3$ for chocolate is added", Toast.LENGTH_SHORT).show();
            }
            if (!isChecked)
            {
                init_price -= 3;
            }
        });
        whipped_cream.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked)
            {
                init_price += 6;
                Toast.makeText(MainActivity.this, "6$ for Whipped Cream", Toast.LENGTH_SHORT).show();

            }
            if (!isChecked)
            {
                init_price -= 6;
            }
        });

        soy_milk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked)
            {
                Toast.makeText(MainActivity.this, "Soy Milk for free", Toast.LENGTH_SHORT).show();
            }

        });
        almond_milk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    Toast.makeText(MainActivity.this, "Almond Milk is for free", Toast.LENGTH_SHORT).show();
            }
        });


    }

}