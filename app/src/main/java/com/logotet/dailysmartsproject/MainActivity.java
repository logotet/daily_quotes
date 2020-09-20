package com.logotet.dailysmartsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.logotet.dailysmartsproject.adapters.ViewPagerAdapter;
import com.logotet.dailysmartsproject.data.local.QuoteEntity;
import com.logotet.dailysmartsproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements DailyQouteFragment.OnDailyQouteInteractionListener, MyQoutesFragment.OnMyQouteInteractionListener {

    public static final String DAILY_QOUTE = "Daily Qoute";
    public static final String MY_QOUTES = "My Qoutes";
    MyQoutesFragment myQoutesFragment;
    DailyQouteFragment dailyQouteFragment;
    ActivityMainBinding binding;
    MenuListener menuListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.toolbar);
        setupViewPager(binding.viewPager);
        binding.tabLayout.setupWithViewPager(binding.viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DailyQouteFragment(), DAILY_QOUTE);
        adapter.addFragment(new MyQoutesFragment(), MY_QOUTES);
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            menuListener.onMenuClicked();
            Toast.makeText(MainActivity.this, "refresh", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDailyQuotesInteraction(QuoteEntity entity) {
        MyQoutesFragment frag = (MyQoutesFragment)
                getSupportFragmentManager().findFragmentById(R.id.frg_my_quotes);
        if (frag != null) {
            frag.updateMyQuotesList(entity);
        } else {
            MyQoutesFragment newFragment = new MyQoutesFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frg_my_quotes, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    @Override
    public void onMyQoutesInteraction(Uri uri) {
    }

    public interface MenuListener{
        void onMenuClicked();
    }
}
