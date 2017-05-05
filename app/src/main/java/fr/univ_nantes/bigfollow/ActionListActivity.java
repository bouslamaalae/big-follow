package fr.univ_nantes.bigfollow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import fr.univ_nantes.bigfollow.config.Config;

public class ActionListActivity extends AppCompatActivity {

    @BindView(R.id.lv_actions) ListView mLvActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.actions));
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        String[] actions = new String[]{
                getString(R.string.project_action_list)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                actions);

        mLvActions.setAdapter(adapter);
    }

    @OnItemClick(R.id.lv_actions)
    void onItemClick(int position) {
        Class activity = null;

        switch (position) {
            case 0:
                activity = ProjectActionListActivity.class;
                break;
        }

        Intent intent = new Intent(this, activity);
        intent.putExtra(Config.EXTRA_ID_PROJECT, getIntent().getLongExtra(Config.EXTRA_ID_PROJECT, 0));
        startActivity(intent);
    }
}
