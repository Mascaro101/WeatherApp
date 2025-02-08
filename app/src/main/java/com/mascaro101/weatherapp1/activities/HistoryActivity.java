package com.mascaro101.weatherapp1.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mascaro101.weatherapp1.R;
import com.mascaro101.weatherapp1.adapters.HistoryAdapter;
import com.mascaro101.weatherapp1.database.AppDatabase;
import com.mascaro101.weatherapp1.database.ClothingAdvice;
import java.util.List;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHistory;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));

        loadHistory();
    }

    private void loadHistory() {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            List<ClothingAdvice> adviceList = db.clothingAdviceDAO().getAllAdvice(); // Fetch all records

            runOnUiThread(() -> {
                historyAdapter = new HistoryAdapter(adviceList);
                recyclerViewHistory.setAdapter(historyAdapter);
            });
        });
    }
}