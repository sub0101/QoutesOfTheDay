package com.example.quoteofd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quoteofd.networking.ErrorListener;
import com.example.quoteofd.networking.ResponseListener;
import com.example.quoteofd.viewModel.MviewModel;
import com.example.quoteofd.viewModel.MyviewModelFactory;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    MviewModel mviewModel;
 @BindView(R.id.shimmerCard) ShimmerFrameLayout shimmerFrameLayout;
 @BindView(R.id.quote)   TextView quote ;
 @BindView(R.id.author) TextView author;
  @BindView(R.id.card_view)  CardView cardView;
    MyviewModelFactory myviewModelFactory;

   @BindView(R.id.nextbtn) Button nextbtn;

   @BindView(R.id.prevbtn)
   Button  prevbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        myviewModelFactory = new MyviewModelFactory(getApplication());
mviewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) myviewModelFactory).get(MviewModel.class);
nextbtn.setOnClickListener(new ButtonClickListner());
prevbtn.setOnClickListener(new ButtonClickListner());
mviewModel.getLiveData().observe(this, new Observer<ArrayList<QoutesData>>() {
    @Override
    public void onChanged(ArrayList<QoutesData> qoutesData) {
        System.out.println("changed");
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
        nextbtn.performClick();
    }
});
    }

   private void setData(String q ,String a)
    {
        quote.setText(q);
        author.setText(a);
    }

    class ButtonClickListner implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            QoutesData qoutesData;
            if(v==nextbtn)
            {
                qoutesData = mviewModel.nextQoute();
            }
            else {
                qoutesData = mviewModel.prevQoute();
                System.out.println("calick");
            }
            setData(qoutesData.quote , qoutesData.author);
        }
    }



    public void share(View view)
    {
        Intent shareintent = new Intent(Intent.ACTION_SEND);
        shareintent.setType("text/plain");
        shareintent.putExtra(Intent.EXTRA_TEXT , quote.getText() + "\n" +author.getText());

        startActivity(shareintent);
    }

}