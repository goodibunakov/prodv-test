package ru.goodibunakov.prodvtest;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.goodibunakov.prodvtest.api.ApiService;
import ru.goodibunakov.prodvtest.api.ApiUtils;
import ru.goodibunakov.prodvtest.fragments.ChoseCityFragment;
import ru.goodibunakov.prodvtest.fragments.MainFragment;
import ru.goodibunakov.prodvtest.model.CityModel;
import ru.goodibunakov.prodvtest.utils.HawkHelper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        apiService = ApiUtils.getApiService();
        initHawk();

        setSupportActionBar(toolbar);
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                toolbar.setTitle(R.string.choose_city);
                toolbar.setNavigationOnClickListener(v -> {
                    ((MainFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("main"))).updateScreen();
                    onBackPressed();
                });
            } else {
                toolbar.setTitle(R.string.app_name);
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, new MainFragment(), "main")
                .addToBackStack(null)
                .commit();
    }

    private void initHawk() {
        HawkHelper hawkHelper = HawkHelper.getInstance();
        hawkHelper.init(getApplicationContext());
        if (!hawkHelper.checkIfContain(HawkHelper.ITEMS)){
            List<CityModel> items = new ArrayList<>();
            items.add(new CityModel("Новосибирск", true));
            items.add(new CityModel("Москва", false));
            items.add(new CityModel("Томск", false));
            items.add(new CityModel("Прага", false));
            items.add(new CityModel("Париж", false));
            items.add(new CityModel("Кемерово", false));
            hawkHelper.setItem(HawkHelper.ITEMS, items);
        }
    }

    public ApiService getApiService() {
        return apiService;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_fragment_container, new ChoseCityFragment(), null)
                    .addToBackStack(null)
                    .commit();
        }
        return true;
    }
}