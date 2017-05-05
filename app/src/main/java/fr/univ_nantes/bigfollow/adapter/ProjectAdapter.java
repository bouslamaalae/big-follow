package fr.univ_nantes.bigfollow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.univ_nantes.bigfollow.R;
import fr.univ_nantes.bigfollow.model.Project;

public class ProjectAdapter extends ArrayAdapter<Project> {

    public ProjectAdapter(Context context, List<Project> projects) {
        super(context, 0, projects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.row_project, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        Project project = getItem(position);

        holder.name.setText(project.getName());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name) TextView name;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
