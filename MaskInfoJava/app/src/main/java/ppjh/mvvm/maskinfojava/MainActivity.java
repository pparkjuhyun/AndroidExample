package ppjh.mvvm.maskinfojava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ppjh.mvvm.maskinfojava.model.Store;
import ppjh.mvvm.maskinfojava.model.StoreInfo;
import ppjh.mvvm.maskinfojava.repository.MaskService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvStore = findViewById(R.id.rv_store);
        rvStore.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        final StoreAdapter adapter = new StoreAdapter();
        rvStore.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MaskService.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        MaskService service = retrofit.create(MaskService.class);
        Call<StoreInfo> storeInfoCall = service.fetchStoreInfo();
        storeInfoCall.enqueue(new Callback<StoreInfo>() {
            @Override
            public void onResponse(Call<StoreInfo> call, Response<StoreInfo> response) {
                Log.e("ppjh", "onResponse: Refresh");
                List<Store> items = response.body().getStores();
                adapter.updateItems(items.stream().filter(item -> item.getRemainStat() != null).collect(Collectors.toList()));
                getSupportActionBar().setTitle("마스크 재고 있는 곳: " + adapter.getItemCount());
            }

            @Override
            public void onFailure(Call<StoreInfo> call, Throwable t) {
                Log.e("ppjh", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {
    private List<Store> mItems = new ArrayList<>();

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store store = mItems.get(position);
        holder.tvName.setText(store.getName());
        holder.tvAddr.setText(store.getAddr());
        holder.tvDistance.setText("123km");

        String count = "100개 이상";
        String remainStat = "충분";
        int color = Color.GREEN;

        switch (store.getRemainStat()) {
            case "plenty":
                count = "100개 이상";
                remainStat = "충분";
                color = Color.GREEN;
                break;
            case "some":
                count = "30개 이상";
                remainStat = "여유";
                color = Color.YELLOW;
                break;
            case "few":
                count = "2개 이상";
                remainStat = "매진 임박";
                color = Color.RED;
                break;
            case "empty":
                count = "1개 이하";
                remainStat = "재고 없음";
                color = Color.GRAY;
                break;
        }
        holder.tvRemainStat.setText(remainStat);
        holder.tvRemainStat.setTextColor(color);
        holder.tvCount.setText(count);
        holder.tvCount.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateItems(List<Store> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    static class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAddr;
        TextView tvDistance;
        TextView tvRemainStat;
        TextView tvCount;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddr = itemView.findViewById(R.id.tv_addr);
            tvDistance = itemView.findViewById(R.id.tv_distance);
            tvRemainStat = itemView.findViewById(R.id.tv_remain_stat);
            tvCount = itemView.findViewById(R.id.tv_count);
        }
    }
}