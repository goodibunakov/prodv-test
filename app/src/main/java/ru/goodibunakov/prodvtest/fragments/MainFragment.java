package ru.goodibunakov.prodvtest.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.goodibunakov.prodvtest.MainActivity;
import ru.goodibunakov.prodvtest.R;
import ru.goodibunakov.prodvtest.model.WeatherForecastModel;
import ru.goodibunakov.prodvtest.model.WeatherModel;
import ru.goodibunakov.prodvtest.utils.DateUtils;
import ru.goodibunakov.prodvtest.utils.ImageUtils;
import ru.goodibunakov.prodvtest.utils.TranslateUtils;

public class MainFragment extends Fragment {

    private Unbinder unbinder;
    private String city;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.city)
    TextView cityText;
    @BindView(R.id.temp)
    TextView tempText;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.sheet_text1)
    TextView sheetText1;
    @BindView(R.id.sheet_text2)
    TextView sheetText2;
    @BindView(R.id.sheet_text3)
    TextView sheetText3;
    @BindView(R.id.sheet_text4)
    TextView sheetText4;
    @BindView(R.id.sheet_text5)
    TextView sheetText5;

    public MainFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        updateScreen();

        return view;
    }

    public void updateScreen() {
        city = TranslateUtils.fromRuToEng();
        getTodayWeather();
        getForecastWeather();
    }

    private void getTodayWeather() {
        progressBar.setVisibility(View.VISIBLE);
        Call<WeatherModel> callToday = ((MainActivity) Objects.requireNonNull(getActivity())).getApiService().getToday(city);
        callToday.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(@NonNull Call<WeatherModel> call, @NonNull Response<WeatherModel> response) {
                WeatherModel data = response.body();
                progressBar.setVisibility(View.INVISIBLE);

                if (response.isSuccessful()) {
                    if (data != null) {
                        Log.d("debug", "WeatherModel = " + data.toString());
                        cityText.setText(TranslateUtils.fromEngToRu(data.getName()));
                        tempText.setText(getString(R.string.gradus, String.format(Locale.getDefault(), "%.1f", data.getMain().getTemp())));
                        image.setImageResource(ImageUtils.getImageDrawable(data.getWeather().get(0).getDescription()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), getResources().getString(R.string.download_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getForecastWeather() {
        progressBar.setVisibility(View.VISIBLE);
        Call<WeatherForecastModel> callForecast = ((MainActivity) Objects.requireNonNull(getActivity())).getApiService().getForecast(city);
        callForecast.enqueue(new Callback<WeatherForecastModel>() {
            @Override
            public void onResponse(@NonNull Call<WeatherForecastModel> call, @NonNull Response<WeatherForecastModel> response) {
                WeatherForecastModel data = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("debug", "response.body() = " + response.body());
                if (response.isSuccessful()) {
                    if (data != null) {
//                        https://api.openweathermap.org/data/2.5/forecast?q=Prague&appid=b849169e8518a852747d3feae7403e88&units=metric&lang=ru
                        sheetText1.setText(DateUtils.convertDateForUI(data.getList().get(0).getDtTxt()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecastModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), getResources().getString(R.string.download_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}