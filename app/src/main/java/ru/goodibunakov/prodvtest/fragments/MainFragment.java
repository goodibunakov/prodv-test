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

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.goodibunakov.prodvtest.Constants;
import ru.goodibunakov.prodvtest.MainActivity;
import ru.goodibunakov.prodvtest.R;
import ru.goodibunakov.prodvtest.model.List;
import ru.goodibunakov.prodvtest.model.WeatherForecastModel;
import ru.goodibunakov.prodvtest.model.WeatherModel;
import ru.goodibunakov.prodvtest.utils.DateUtils;
import ru.goodibunakov.prodvtest.utils.ImageUtils;
import ru.goodibunakov.prodvtest.utils.TranslateUtils;

public class MainFragment extends Fragment {

    private Unbinder unbinder;
    private String city;
    private int imageId;
    private String[] forecastTexts = new String[5];

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

        if (savedInstanceState == null) {
            updateScreen();
        } else {
            setRestoredData(savedInstanceState);
        }

        return view;
    }

    private void setRestoredData(Bundle savedInstanceState) {
        cityText.setText(savedInstanceState.getString(Constants.KEY_CITY));
        tempText.setText(savedInstanceState.getString(Constants.KEY_TEMP));
        if (savedInstanceState.getInt(Constants.KEY_IMAGE_ID) != 0) {
            image.setImageResource(savedInstanceState.getInt(Constants.KEY_IMAGE_ID));
        }

        forecastTexts = savedInstanceState.getStringArray(Constants.KEY_FORECAST_ARRAY);
        if (forecastTexts != null && forecastTexts.length == 5) {
            sheetText1.setText(forecastTexts[0]);
            sheetText2.setText(forecastTexts[1]);
            sheetText3.setText(forecastTexts[2]);
            sheetText4.setText(forecastTexts[3]);
            sheetText5.setText(forecastTexts[4]);
        }
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
                        imageId = ImageUtils.getImageDrawable(data.getWeather().get(0).getDescription());
                        image.setImageResource(imageId);
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
                        fillSheet(data);
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

    private void fillSheet(WeatherForecastModel data) {
        // https://api.openweathermap.org/data/2.5/forecast?q=Prague&appid=b849169e8518a852747d3feae7403e88&units=metric&lang=ru
        ArrayList<List> list_ = data.getList();
        ArrayList<String> dates = new ArrayList<>();
        for (List list : list_) {
            String date = DateUtils.convertDate(list.getDtTxt());
            if (!dates.contains(date)) {
                dates.add(date);
            }
        }

        if (dates.size() > 0) {
            for (int i = 0; i < dates.size(); i++) {
                ArrayList<List> forecast = new ArrayList<>();
                String date = dates.get(i);
                for (List list : list_) {
                    if (list.getDtTxt().contains(date)) {
                        forecast.add(list);
                    }
                }

                double avgTemp = 0;
                double avgWind = 0;
                double avgPressure = 0;

                for (List list : forecast) {
                    avgTemp = avgTemp + list.getMain().getTemp();
                    avgWind = avgWind + list.getWind().getSpeed();
                    avgPressure = avgPressure + list.getMain().getPressure();
                }

                avgTemp = avgTemp / forecast.size();
                avgWind = avgWind / forecast.size();
                avgPressure = avgPressure / forecast.size() / 1.333;
                String dateSheet = DateUtils.convertDateForUI(forecast.get(0).getDtTxt());

                if (i <= 4) {
                    forecastTexts[i] = getString(R.string.sheet_line, dateSheet,
                            String.format(Locale.getDefault(), "%.1f", avgTemp),
                            String.format(Locale.getDefault(), "%.1f", avgWind),
                            String.format(Locale.getDefault(), "%.1f", avgPressure)
                    );
                }
            }
        }

        sheetText1.setText(forecastTexts[0]);
        sheetText2.setText(forecastTexts[1]);
        sheetText3.setText(forecastTexts[2]);
        sheetText4.setText(forecastTexts[3]);
        sheetText5.setText(forecastTexts[4]);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.KEY_CITY, cityText.getText().toString());
        outState.putString(Constants.KEY_TEMP, tempText.getText().toString());
        outState.putInt(Constants.KEY_IMAGE_ID, imageId);
        outState.putStringArray(Constants.KEY_FORECAST_ARRAY, forecastTexts);
    }
}