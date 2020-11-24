package ppjh.mvvm.maskinfojava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ppjh.mvvm.maskinfojava.model.Store;
import ppjh.mvvm.maskinfojava.model.StoreInfo;
import ppjh.mvvm.maskinfojava.repository.MaskService;
import ppjh.mvvm.maskinfojava.viewmodel.MainViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                performAction();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();
    }

    @SuppressLint("MissingPermission")
    private void performAction() {
        fusedLocationClient.getLastLocation()
                .addOnFailureListener(this, e -> {
                    Log.e("ppjh", e.getCause().toString());
                })
                .addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    Log.d("ppjh", "getLatitude: " + location.getLatitude());
                    Log.d("ppjh", "getLongitude: " + location.getLongitude());

                    viewModel.setLocation(location);
                    viewModel.fetchStoreInfo();
                }
            }
        });

        RecyclerView rvStore = findViewById(R.id.rv_store);
        rvStore.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        final StoreAdapter adapter = new StoreAdapter();
        rvStore.setAdapter(adapter);

        /**
         * UI 변경 감지
         */
        viewModel.getItemLiveData().observe(this, new Observer<List<Store>>() {
            @Override
            public void onChanged(List<Store> stores) {
                adapter.updateItems(stores);
                getSupportActionBar().setTitle("마스크 재고 있는 곳: " + stores.size());
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
                viewModel.fetchStoreInfo();
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
        holder.tvDistance.setText(String.format("%.2fkm", store.getDistance()));

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