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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import br.edu.ifsuldeminas.tarefas2.databinding.FragmentMainCategoryBinding;
import br.edu.ifsuldeminas.tarefas2.db.CategoriesDAO;
import br.edu.ifsuldeminas.tarefas2.domain.Category;

public class MainCategoryFragment extends Fragment {

    private FragmentMainCategoryBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMainCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerForContextMenu(binding.todoListCategory);

        binding.todoListCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Category categories = (Category) binding.todoListCategory.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("categories", categories);

                NavController navController = Navigation.findNavController(getActivity() ,R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.action_MainCategoryFragment_to_CategoryFragment, bundle);
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

        updateCategories();
    }

    public void updateCategories() {
        CategoriesDAO dao = new CategoriesDAO(getContext());
        List<Category> categories = dao.listAll();

        ArrayAdapter adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, categories);

        binding.todoListCategory.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem item = menu.add(R.string.delete_category);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info =
                        (AdapterView.AdapterContextMenuInfo) menuInfo;

                Category categorySelected = (Category) binding.todoListCategory.getItemAtPosition(
                        info.position);

                CategoriesDAO CategoriesDAO = new CategoriesDAO(getContext());
                CategoriesDAO.delete(categorySelected);

                Toast.makeText(getContext(), R.string.task_done,
                        Toast.LENGTH_SHORT).show();

                updateCategories();
                return true;
            }
        });
    }
}
