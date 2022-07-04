package br.edu.ifsuldeminas.tarefas2;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

import br.edu.ifsuldeminas.tarefas2.databinding.FragmentMainBinding;
import br.edu.ifsuldeminas.tarefas2.db.TaskDAO;
import br.edu.ifsuldeminas.tarefas2.domain.Task;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerForContextMenu(binding.todoList);

        binding.todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Task task = (Task) binding.todoList.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("task", task);

                NavController navController = Navigation.findNavController(getActivity() ,R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.action_MainFragment_to_TaskFragment, bundle);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateTasks();
    }

    public void updateTasks() {
        TaskDAO dao = new TaskDAO(getContext());
        List<Task> tasks = dao.listAll();

        ArrayAdapter adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, tasks);

        binding.todoList.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem item = menu.add(R.string.delete_task);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info =
                        (AdapterView.AdapterContextMenuInfo) menuInfo;

                Task taskSelected = (Task) binding.todoList.getItemAtPosition(
                        info.position);

                TaskDAO taskDAO = new TaskDAO(getContext());
                taskDAO.delete(taskSelected);

                Toast.makeText(getContext(), R.string.task_done,
                        Toast.LENGTH_SHORT).show();

                updateTasks();
                return true;
            }
        });
    }

}