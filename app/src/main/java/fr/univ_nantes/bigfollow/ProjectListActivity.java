package fr.univ_nantes.bigfollow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import fr.univ_nantes.bigfollow.adapter.ProjectAdapter;
import fr.univ_nantes.bigfollow.config.Config;
import fr.univ_nantes.bigfollow.model.Project;

public class ProjectListActivity extends AppCompatActivity {

    @BindView(R.id.lv_projects) ListView mLvProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.projects));
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mLvProjects.setAdapter(new ProjectAdapter(this, Project.getAll()));
    }

    @OnItemClick(R.id.lv_projects)
    void onItemClick(int position) {
        Project project = (Project) mLvProjects.getItemAtPosition(position);

        Intent intent = new Intent(this, ActionListActivity.class);
        intent.putExtra(Config.EXTRA_ID_PROJECT, project.getId());
        startActivity(intent);
    }
}
