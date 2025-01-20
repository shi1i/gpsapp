package com.example.myapplication.ui.home;

import android.health.connect.datatypes.ExerciseRoute;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentHomeBinding;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationViewSource;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapWindow;
import com.yandex.mapkit.mapview.MapView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private MapView mapView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mapView = binding.mapview;
        MapWindow mapWindow = mapView.getMapWindow();
        MapKit targetM = MapKitFactory.getInstance();
        targetM.createUserLocationLayer(mapWindow).setVisible(true);
        mapWindow.getMap().move(new CameraPosition(new Point(45.0365, 41.9630), 17.12f, 0.0f,30),
                new Animation(Animation.Type.SMOOTH,0), null);
        MapKitFactory.getInstance().onStart();
        mapView.onStart();

        // final TextView textView = binding.textHome;
        // homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MapKitFactory.getInstance().onStop();
        mapView.onStop();
        binding = null;
    }
}