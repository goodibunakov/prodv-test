package ru.goodibunakov.prodvtest.adapters;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.goodibunakov.prodvtest.R;
import ru.goodibunakov.prodvtest.model.CityModel;
import ru.goodibunakov.prodvtest.utils.HawkHelper;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<CityModel> items;

    public CityAdapter(List<CityModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        CityModel item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        } else {
            return items.size();
        }
    }

    class CityViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.city_text)
        TextView cityText;
        @BindView(R.id.switch_item)
        Switch switchItem;

        CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final CityModel item) {
            cityText.setText(item.getCity());
            switchItem.setChecked(item.isSelected());
            switchItem.setOnClickListener(v -> {
                Switch switchClicked = (Switch) v;
                if (!switchClicked.isChecked()) switchClicked.setChecked(true);
                int position = getAdapterPosition();
                for (int i = 0; i < items.size(); i++){
                    CityModel cityModel = items.get(i);
                    if (i == position) cityModel.setSelected(true);
                    else cityModel.setSelected(false);
                }
                HawkHelper.getInstance().setItem(HawkHelper.ITEMS, items);
                new Handler().post(CityAdapter.this::notifyDataSetChanged);
            });
        }
    }
}