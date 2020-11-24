package ppjh.mvvm.maskinfojava.viewmodel;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
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

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<Store>> itemLiveData = new MutableLiveData<>();
    private Location location;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MaskService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

    private MaskService service = retrofit.create(MaskService.class);

    public MainViewModel() {
//        fetchStoreInfo();
    }

    public void fetchStoreInfo() {
        service.fetchStoreInfo(location.getLatitude(), location.getLongitude()).enqueue(new Callback<StoreInfo>() {
            @Override
            public void onResponse(Call<StoreInfo> call, Response<StoreInfo> response) {
                Log.d("ppjh", "refresh");
                List<Store> items = response.body().getStores();
                itemLiveData.postValue(items.stream()
                        .filter(item -> item.getRemainStat() != null)
                        .collect(Collectors.toList()));
            }

            @Override
            public void onFailure(Call<StoreInfo> call, Throwable t) {
                itemLiveData.postValue(Collections.emptyList());
            }
        });
    }

    public MutableLiveData<List<Store>> getItemLiveData() {
        return itemLiveData;
    }

    public void setItemLiveData(MutableLiveData<List<Store>> itemLiveData) {
        this.itemLiveData = itemLiveData;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
